package com.framework.pie.admin.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.framework.pie.admin.constant.SysConstants;
import com.framework.pie.admin.dao.*;
import com.framework.pie.admin.dto.UserExportDTO;
import com.framework.pie.admin.model.*;
import com.framework.pie.admin.service.*;
import com.framework.pie.admin.util.ExcelReaderUtil;
import com.framework.pie.admin.vo.AddressBookBean;
import com.framework.pie.admin.vo.OAuthSysUser;
import com.framework.pie.constant.GlobalConstants;
import com.framework.pie.http.HttpResult;
import com.framework.pie.mybatis.model.BaseEntity;
import com.framework.pie.mybatis.page.MybatisPageHelper;
import com.framework.pie.mybatis.page.PageRequest;
import com.framework.pie.mybatis.page.PageResult;
import com.framework.pie.redis.client.RedisClient;
import com.framework.pie.utils.MD5Utils;
import com.framework.pie.utils.PoiUtils;
import com.framework.pie.web.utils.IPUtils;
import com.framework.pie.web.utils.JwtUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper,SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysOrgMapper sysOrgMapper;
    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private RedisClient redisClient;
    // 注入配置上下文信息
    @Autowired
    private ConfigurableApplicationContext applicationContext;
    @Autowired
    private SysLoginLogService sysLoginLogService;

    private static final Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Override
    public HttpResult saveUser(SysUser record) {
        // 用户帐号验重
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUser::getName,record.getName());
        if(StrUtil.isNotBlank(record.getId())){
            queryWrapper.lambda().ne(SysUser::getId,record.getId());
        }
        Integer userNum = sysUserMapper.selectCount(queryWrapper);
        if(userNum > 0){
            return HttpResult.error("用户帐号已存在,请修改后重新添加");
        }

        // 新增用户
        if(StrUtil.isBlank(record.getId())){
            // 保存用户基本信息
            setUserPwdInfo(record);
            record.setCreateBy(JwtUtils.getUserId());
            sysUserMapper.insert(record);

            // 保存用户角色信息
            List<SysUserRole> userRoleList =  record.getUserRoles();
            if(CollectionUtil.isNotEmpty(userRoleList)){
                userRoleList.forEach(userRole -> {
                    userRole.setUserId(record.getId());
                    userRole.setCreateBy(JwtUtils.getUserId());
                    sysUserRoleMapper.insert(userRole);
                });
            }
            return HttpResult.ok("用户添加成功");
        // 修改用户
        } else {
            SysUser user = this.getById(record.getId());
            if(sysRoleService.checkedRole(user.getName(),SysConstants.SUPERADMIN)) {
                return HttpResult.error("超级管理员不允许修改!");
            }
            if(sysRoleService.checkedRole(user.getName(),SysConstants.ADMIN)) {
                return HttpResult.error("系统管理员不允许修改!");
            }

            // 校验用户密码是否修改
            if(!record.getPassword().equals(user.getPassword())){
                setUserPwdInfo(record);
            }

            // 更新用户角色信息,先删除之前的角色信息，再添加新的角色信息
            UpdateWrapper<SysUserRole> updateWrapper = new UpdateWrapper<>();
            updateWrapper.lambda()
                    .set(SysUserRole::getDelFlag,GlobalConstants.DEL_FLAG_DELETE)
                    .set(SysUserRole::getLastUpdateBy,JwtUtils.getUserId())
                    .eq(SysUserRole::getUserId,record.getId());
            sysUserRoleMapper.update(new SysUserRole(),updateWrapper);

            List<SysUserRole> userRoleList =  record.getUserRoles();
            if(CollectionUtil.isNotEmpty(userRoleList)){
                userRoleList.forEach(userRole -> {
                    userRole.setUserId(record.getId());
                    userRole.setCreateBy(JwtUtils.getUserId());
                    sysUserRoleMapper.insert(userRole);
                });
            }

            // 更新用户信息
            record.setLastUpdateBy(JwtUtils.getUserId());
            sysUserMapper.updateById(record);
            return HttpResult.ok("用户更新成功");
        }
    }

    private void setUserPwdInfo(SysUser record){
        String salt = MD5Utils.getSalt();
        String password = record.getPassword();
        // 如果没有输入密码就设置默认密码
        if(StrUtil.isBlank(password)){
            password = applicationContext.getEnvironment().getProperty("user.defaultPwd");
        }
        String enPwd = MD5Utils.encrypt(salt, password);
        record.setSalt(salt);
        record.setPassword(enPwd);
    }

    @Override
    public int updatePersonal(SysUser record) {
        // 更新用户信息
        return sysUserMapper.updateById(record);
    }

    @Override
    public HttpResult switchingUserStatus(SysUser record){
        SysUser user = this.getById(record.getId());
        if(user != null) {
            if(sysRoleService.checkedRole(user.getName(),SysConstants.SUPERADMIN)) {
                return HttpResult.error("超级管理员不允许切换状态!");
            }
            if(sysRoleService.checkedRole(user.getName(),SysConstants.ADMIN)) {
                return HttpResult.error("系统管理员不允许切换状态!");
            }
        }
        // 更新用户信息
        return HttpResult.ok(sysUserMapper.updateById(record));
    }

    @Override
    public SysUser getByName(String username) {
        return sysUserMapper.getByName(username);
    }

    @Override
    public List<SysUser> findAll() {
        return sysUserMapper.findAll();
    }

    @Override
    public Set<String> listPermissions(String userName) {
        Set<String> perms = new HashSet<>();
        List<SysMenu> sysMenus = new ArrayList<>();
        //添加机构权限
        SysOrg sysOrg = sysOrgMapper.findByOrg(userName);
        sysMenus.addAll(sysMenuMapper.findOrgMenus(sysOrg.getId()));
        //添加用户权限
        sysMenus.addAll(sysMenuService.findByUser(userName));
        for(SysMenu sysMenu:sysMenus) {
            if(sysMenu.getPerms() != null && !"".equals(sysMenu.getPerms())) {
                perms.add(sysMenu.getPerms());
            }
        }
        return perms;
    }

    @Override
    public List<SysUserRole> listUserRoles(String userId) {
        return sysUserRoleMapper.findUserRoles(userId);
    }

    @Override
    public HttpResult getSysUserByName(String userName, HttpServletRequest request) {
        //查询用户信息
        String[] loginInfo = userName.split("&");
        String realUserName = loginInfo[0];
        String loginType = loginInfo[1];

        SysUser sysUser = sysUserMapper.getByName(realUserName);
        if(sysUser == null){
            return HttpResult.error("账号不存在");
        }
        OAuthSysUser oAuthSysUser = new OAuthSysUser();
        //拷贝用户属性
        BeanUtils.copyProperties(sysUser,oAuthSysUser);
        oAuthSysUser.setStatus(Integer.parseInt(sysUser.getStatus().toString()));
        oAuthSysUser.setUsername(sysUser.getName());
        //查询用户角色信息
        Map param = new HashMap();
        param.put("userId",sysUser.getId());
        param.put("deptId",sysUser.getDeptId());
        List<Map<String, Object>> userRolesList = sysRoleMapper.listRoleByUserId(param);
        Set<String> roleIds = new HashSet<>();
        if(userRolesList != null && userRolesList.size() > 0){
            userRolesList.forEach(role->{
                roleIds.add(role.get("roleId").toString());
                String roleName = role.get("roleName").toString();
                if(GlobalConstants.SUPER_ADMIN_ROLE.equals(roleName)){
                    oAuthSysUser.setSuperAdminFlag(true);
                }
            });
        }

        //判断用户是否是机构管理员，如果是机构管理员，则需要添加机构菜单信息
        SysOrg sysOrg = sysOrgMapper.findByOrg(realUserName);
        //将机构ID加入org前缀，加入到角色信息中
        roleIds.add("org_"+sysOrg.getId());
        oAuthSysUser.setRoleIds(roleIds);

        if (sysOrg != null && sysOrg.getStatus() == 0){
            oAuthSysUser.setOrgStatus(false);
        } else {
            oAuthSysUser.setOrgStatus(true);
        }
        oAuthSysUser.setOrgId(sysOrg.getId());
        oAuthSysUser.setOrgDistrictId(sysOrg.getDistrictId());
        oAuthSysUser.setLoginType(loginType);
        // 记录登录日志
        String ip = IPUtils.getIpAddr(request);
        sysLoginLogService.writeLoginLog(realUserName+"["+oAuthSysUser.getNickName()+"]",ip ,IPUtils.getCityInfo(ip),loginType);

        return HttpResult.ok(oAuthSysUser);
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        Map<String,Object> params = new HashMap<>();
        params = pageRequest.getParams();
        params.put("orgId", JwtUtils.getOrgId());
        PageResult page = MybatisPageHelper.findPage(pageRequest, sysUserMapper, "findPageByOrgAndDeptAndName", params);
        if(CollectionUtil.isNotEmpty(page.getContent())){
            List<SysUser> userList = (List<SysUser>) page.getContent();
            userList.forEach(user -> {
                List<String> deptList = Arrays.asList(user.getDeptPath().split("_"));
                List<String> sysDeptList = sysDeptMapper.getDeptName(deptList);
                StringBuffer deptName = new StringBuffer("");
                sysDeptList.forEach(name -> {
                    deptName.append(name+"-");
                });
                String deptNameStr = deptName.toString();
                if(StrUtil.isNotBlank(deptNameStr)){
                    deptNameStr = deptNameStr.substring(0,deptNameStr.lastIndexOf("-"));
                }
                user.setDeptName(deptNameStr);

                System.out.println(user.getDeptPath());
            });
        }
        return page;
    }

    @Override
    public HttpResult resetPwdByOrgId(String orgId) {
        //先通过组织机构部门中间表及部门表，获取到部门ID，通过部门ID获取到用户，设置默认用户密码信息
        Map<String,Object> params = new HashMap<>();
        params.put("orgId",orgId);
        params.put("deptName",GlobalConstants.DEFAULT_ADMIN_DEPT_NAME);
        SysDept sysDept = sysDeptMapper.getDeptByOrgId(params);

        //通过部门ID获取对应的用户信息
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUser::getDeptId,sysDept.getId());
        SysUser sysUser = sysUserMapper.selectOne(queryWrapper);

        //重置密码信息
        String defaultPwd = applicationContext.getEnvironment().getProperty("user.defaultPwd");
        String salt = MD5Utils.getSalt();
        sysUser.setPassword(MD5Utils.encrypt(salt,defaultPwd));
        sysUser.setSalt(salt);
        sysUserMapper.updateById(sysUser);
        return HttpResult.ok("密码重置成功，重置后的密码为["+defaultPwd+"]，请提醒用户及时调整默认密码!");
    }

    @Override
    public HttpResult resetPwd(String id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        //重置密码信息
        String defaultPwd = applicationContext.getEnvironment().getProperty("user.defaultPwd");
        String salt = MD5Utils.getSalt();
        sysUser.setPassword(MD5Utils.encrypt(salt,defaultPwd));
        sysUser.setSalt(salt);
        sysUserMapper.updateById(sysUser);
        return HttpResult.ok("密码重置成功，重置后的密码为["+defaultPwd+"]，请提醒用户及时调整默认密码!");
    }

    @Override
    public HttpResult findAddressBook(String parentId) {
        List<AddressBookBean> list = new LinkedList<>();
        List<SysDept> deptList = sysDeptService.findTree(parentId);
        deptList.forEach(i->{
            //组织部门信息
            AddressBookBean addressBookBean = new AddressBookBean();
            addressBookBean.setId(i.getId());
            addressBookBean.setName(i.getName());
            addressBookBean.setType("department");
            addressBookBean.setHasChildren(i.isHasChildren());
            addressBookBean.setChildren(this.findUsers(i.getId()));
            list.add(addressBookBean);
        });
        return HttpResult.ok(list);
    }

    @Override
    public HttpResult updatePassWord(Map map) {
        try {
            String userId = map.get("userId").toString();
            String oldPwd = map.get("oldPwd").toString();
            String pwd = map.get("pwd").toString();
            SysUser sysUser = this.getById(userId);
            String oldPassword = MD5Utils.encrypt(sysUser.getSalt(), oldPwd);
            if (!sysUser.getPassword().equals(oldPassword)){
                return HttpResult.error("旧密码不符！");
            }
            sysUser.setPassword(MD5Utils.encrypt(sysUser.getSalt(), pwd));
            sysUserMapper.updateById(sysUser);
        }catch (Exception e){
            return HttpResult.error("密码更新失败!");

        }
        return HttpResult.ok("密码更新成功！");
    }

    @Override
    public List<UserExportDTO> listUserExportData(Map<String, Object> params) {
        List<UserExportDTO> userExportList = sysUserMapper.listUserExportData(params);
        userExportList.forEach(user -> {
            List<String> deptList = Arrays.asList(user.getPathInfo().split("_"));
            List<String> sysDeptList = sysDeptMapper.getDeptName(deptList);
            StringBuffer deptName = new StringBuffer("");
            sysDeptList.forEach(name -> {
                deptName.append(name+"-");
            });
            String deptNameStr = deptName.toString();
            if(StrUtil.isNotBlank(deptNameStr)){
                deptNameStr = deptNameStr.substring(0,deptNameStr.lastIndexOf("-"));
            }
            user.setDeptNameInfo(deptNameStr);
        });
        return (userExportList != null && userExportList.size()> 0)?userExportList:new ArrayList<>();
    }

    @Override
    public HttpResult getUserByDeptId(Map<String,Object> params) {
        List<SysUser> userList = null;
        try {
//            Map<String,Object> params = new HashMap<>();
//            params.put("orgId", params.put("orgId", JwtUtils.getOrgId()));
//            params.put("deptId",deptId);
            userList = sysUserMapper.getByOrgIdAndDeptId(params);
        }catch (Exception e){
            e.printStackTrace();
            return HttpResult.error("用户查询异常");
        }
        return HttpResult.ok(userList);
    }

    @Override
    public Map<String,String> sendCode(String phone) {
        //检查是否是注册手机号
        Map<String,String> map = new HashMap<>();
        String registCHeak = sysUserMapper.checkPhone(phone);
        if(registCHeak != null && !registCHeak.equals("")){
            String BASIC = "1234567890";
            char[] basicArray = BASIC.toCharArray();
            Random random = new Random();
            char[] result = new char[6];
            for (int i = 0; i < result.length; i++) {
                int index = random.nextInt(100) % (basicArray.length);
                result[i] = basicArray[index];
            }
            redisClient.set(phone+new String(result),"验证码",300);
            map.put("status","success");
            map.put("info","调用短信接口。。。");
            map.put("phone",phone);
            map.put("code",new String(result));
        }else{
            map.put("status","err");
            map.put("info","手机号未注册，请检查！");
        }
        return map;
    }

    @Override
    public boolean checkCode(String phone, String code) {
        boolean flag = false;
        String check = redisClient.get(phone+code);
        if(check != null && !check.equals("")){
            flag = true;
        }
        return flag;
    }

    @Override
    public HttpResult updatePassByCheck(Map map) {
        try {
            String name = map.get("name").toString();
            String pwd = map.get("pwd").toString();
            SysUser sysUser = this.getByName(name);
            sysUser.setPassword(MD5Utils.encrypt(sysUser.getSalt(), pwd));
            sysUserMapper.updateById(sysUser);
        }catch (Exception e){
            return HttpResult.error("密码更新失败!");

        }
        return HttpResult.ok("密码更新成功！");
    }

    public List<AddressBookBean> findUsers(String deptId){
        List<AddressBookBean> addressBookBeanList = new LinkedList<>();
        List<SysUser> users = this.sysUserMapper.findByDept(deptId);
        users.forEach(i->{
            AddressBookBean addressBookBean = new AddressBookBean();
            addressBookBean.setId(i.getId());
            addressBookBean.setName(i.getName());
            addressBookBean.setNickName(i.getNickName());
            addressBookBean.setMobile(i.getMobile());
            addressBookBean.setType("user");
            addressBookBeanList.add(addressBookBean);
        });
        return addressBookBeanList;
    }

    @Override
    public File createUserExcelFile(PageRequest pageRequest) {
        Map<String, Object> params = pageRequest.getParams();
        if(MapUtil.isEmpty(params)){
            params = new HashMap<>();
        }
        params.put("status",1);
        List<SysUser> userList = sysUserMapper.findPageByOrgAndDeptAndName(params);
        return createUserExcelFile(userList);
    }
    @Override
    public File exportAllExcelUser() {
        List<UserExportDTO> userExportDTOS = this.listUserExportData(new HashedMap());
        return exportAllExcelUser(userExportDTOS);
    }

    /*@Override
    public HttpResult importUserExcel(SysAttachments sysAttachments) {
        try {
            Workbook workbook = ExcelReaderUtil.getWorkBook(sysAttachments.getFilePath());
            assert workbook != null;
            List<SysUser> sysUsers = parseExcel(workbook);
            sysUsers.forEach(this::saveUser);
        }catch (Exception e){
            e.printStackTrace();
            return HttpResult.error(" 部分数据导入失败，请检查 excel");
        }
        return HttpResult.ok("导入成功");
    }*/

    /**
     * 解析Excel数据
     *
     * @param workbook Excel工作簿对象
     * @return 解析结果
     */
    public  List<SysUser> parseExcel(Workbook workbook) {
        List<SysUser> resultDataList = new ArrayList<>();
        // 解析sheet
        int numberOfSheets = workbook.getNumberOfSheets();
        for (int sheetNum = 0; sheetNum < numberOfSheets; sheetNum++) {
            Sheet sheet = workbook.getSheetAt(sheetNum);

            // 校验sheet是否合法
            if (sheet == null) {
                continue;
            }
            // 获取第一行数据
            int firstRowNum = sheet.getFirstRowNum();
            Row firstRow = sheet.getRow(firstRowNum);
            if (null == firstRow) {
                logger.warn("解析Excel失败，在第一行没有读取到任何数据！");
            }
            // 解析每一行的数据，构造数据对象
            int rowStart = firstRowNum + 1;
            int rowEnd = sheet.getPhysicalNumberOfRows();
            for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (null == row) {
                    continue;
                }
                SysUser resultData = convertRowToData(row);
                if (null == resultData) {
                    logger.warn("第 " + row.getRowNum() + "行数据不合法，已忽略！");
                    continue;
                }
                resultDataList.add(resultData);
            }
        }
        return resultDataList;
    }

    /**
     * 提取每一行中需要的数据，构造成为一个结果数据对象
     * <p>
     * 当该行中有单元格的数据为空或不合法时，忽略该行的数据
     *
     * @param row 行数据
     * @return 解析后的行数据对象，行数据错误时返回null
     */
    public SysUser convertRowToData(Row row) {
        SysUser resultData = new SysUser();
        Cell cell;
        int cellNum = 0;
        // 获取部门
        cell = row.getCell(cellNum++);
        String deptName = ExcelReaderUtil.convertCellValueToString(cell);
        SysDept dept = sysDeptService.selectByName(deptName);
        resultData.setDeptName(dept.getName());
        resultData.setDeptId(dept.getId());
        // 获取账号
        cell = row.getCell(cellNum++);
        String name = ExcelReaderUtil.convertCellValueToString(cell);
        resultData.setName(name);

        // 获取密码
        cell = row.getCell(cellNum++);
        String password = ExcelReaderUtil.convertCellValueToString(cell);
        resultData.setPassword(password);

        //获取电话号码
        cell = row.getCell(cellNum++);
        String mobile = ExcelReaderUtil.convertCellValueToString(cell);
        resultData.setMobile(mobile);

        return resultData;
    }

    public  File createUserExcelFile(List<?> records) {
        if (records == null) {
            records = new ArrayList<>();
        }
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row row0 = sheet.createRow(0);
        int columnIndex = 0;
        row0.createCell(++columnIndex).setCellValue("部门");
        row0.createCell(++columnIndex).setCellValue("账号");
        row0.createCell(++columnIndex).setCellValue("密码");
        row0.createCell(++columnIndex).setCellValue("电话");
        for (int i = 0; i < records.size(); i++) {
            SysUser user = (SysUser) records.get(i);
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < columnIndex + 1; j++) {
                row.createCell(j);
            }
            columnIndex = 0;
            row.getCell(++columnIndex).setCellValue(sysDeptService.getById(user.getDeptId()).getName());
            row.getCell(++columnIndex).setCellValue(user.getName());
            row.getCell(++columnIndex).setCellValue("*");
            row.getCell(++columnIndex).setCellValue(user.getMobile()==null?"*":user.getMobile());
        }
        return PoiUtils.createExcelFile(workbook, "download_user");
    }

    public  File exportAllExcelUser(List<?> records) {
        if (records == null) {
            records = new ArrayList<>();
        }
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row row0 = sheet.createRow(0);
        int columnIndex = 0;
        row0.createCell(columnIndex).setCellValue("部门");
        row0.createCell(++columnIndex).setCellValue("账号");
        row0.createCell(++columnIndex).setCellValue("用户名");
        row0.createCell(++columnIndex).setCellValue("电话");
        for (int i = 0; i < records.size(); i++) {
            UserExportDTO userExportDTO = (UserExportDTO) records.get(i);
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < columnIndex + 1; j++) {
                row.createCell(j);
            }
            columnIndex = 0;
            row.getCell(columnIndex).setCellValue(userExportDTO.getDeptNameInfo());
            row.getCell(++columnIndex).setCellValue(userExportDTO.getName());
            row.getCell(++columnIndex).setCellValue(userExportDTO.getNickName());
            row.getCell(++columnIndex).setCellValue(userExportDTO.getMobile());
        }
        return PoiUtils.createExcelFile(workbook, "download_all_user");
    }



}
