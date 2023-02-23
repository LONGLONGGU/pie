import com.framework.pie.admin.PieAdminApplication;
import com.framework.pie.admin.dto.UserExportDTO;
import com.framework.pie.admin.model.SysUser;
import com.framework.pie.admin.service.SysUserService;
import com.framework.pie.mybatis.page.PageRequest;
import com.framework.pie.mybatis.page.PageResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {PieAdminApplication.class,UserTest.class})
public class UserTest {

    @Autowired
    private SysUserService sysUserService;

    @Test
    public void getByIdTest(){
        SysUser sysUser = sysUserService.getById(4);
        System.err.println("根据ID查询用户信息:"+sysUser);
    }

    @Test
    public void findPageTest(){
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageNum(0);
        pageRequest.setPageSize(2);
        PageResult page = sysUserService.findPage(pageRequest);
        System.err.println("分页查询信息:"+page);
    }

    @Test
    public void testExport(){
        List<UserExportDTO> userExportList = sysUserService.listUserExportData(new HashMap<>());
        userExportList.forEach(user -> {
            System.out.println(user);
        });
    }
}
