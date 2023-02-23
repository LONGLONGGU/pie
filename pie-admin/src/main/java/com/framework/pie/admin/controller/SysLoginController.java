package com.framework.pie.admin.controller;

import com.framework.pie.admin.dao.SysOrgMapper;
import com.framework.pie.admin.service.SysLoginLogService;
import com.framework.pie.admin.service.SysUserService;
import com.framework.pie.admin.util.ImageUtil;
import com.framework.pie.constant.RedisConstants;
import com.framework.pie.http.HttpResult;
import com.framework.pie.redis.client.RedisClient;
import com.framework.pie.utils.IOUtils;
import com.framework.pie.web.utils.IPUtils;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 登录控制器
 * @author longlong
 */
@RestController
public class SysLoginController {

	@Autowired
	private Producer producer;
	@Autowired
	private RedisClient redisClient;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysLoginLogService sysLoginLogService;
	@Autowired
	private SysOrgMapper sysOrgMapper;


	@GetMapping("captcha.jpg")
	public void captcha(HttpServletResponse response, HttpServletRequest request) throws ServletException, IOException {
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		response.setContentType("image/jpeg");

		// 生成文字验证码
		String text = producer.createText();
		// 生成图片验证码
		BufferedImage image = producer.createImage(text);
		// 保存到验证码到 session
		request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, text);

		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "jpg", out);	
		IOUtils.closeQuietly(out);
	}

	/**
	 * 获取登录验证码
	 * @throws ServletException
	 * @throws IOException
	 */
	@GetMapping("getLoginCaptcha")
	public HttpResult getLoginCaptcha() throws IOException {
		// 生成文字验证码
		String text = producer.createText();
		// 生成图片验证码,并将图片验证码转换成base的格式返回
		BufferedImage image = producer.createImage(text);
		String imageInfo = ImageUtil.getBufferedImageToBase64(image,"jpg");

		//将验证码信息存储到redis当中
		String capToken = UUID.randomUUID().toString();
		redisClient.set(RedisConstants.CAP_CODE_PREFIX+capToken,text,RedisConstants.CAP_CODE_EXPIRE);
		Map<String,Object> capInfo = new HashMap<>();
		capInfo.put("imageInfo",imageInfo);
		capInfo.put("capToken",capToken);
		return HttpResult.ok(capInfo);
	}

	/**
	 * 登录接口
	 *//*
	@PostMapping(value = "/login")
	public HttpResult login(@RequestBody LoginBean loginBean, HttpServletRequest request) throws IOException {
		String username = loginBean.getAccount();
		String password = loginBean.getPassword();
		String captcha = loginBean.getCaptcha();
		// 从session中获取之前保存的验证码跟前台传来的验证码进行匹配
		Object kaptcha = request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
		if(kaptcha == null){
			return HttpResult.error("验证码已失效！");
		}
		if(!captcha.equals(kaptcha)){
			return HttpResult.error("验证码不正确！");
		}
		// 用户信息
		SysUser user = sysUserService.findByName(username);
		// 账号不存在、密码错误
		if (user == null) {
			return HttpResult.error("账号不存在！");
		}
		if (!PasswordUtils.matches(user.getSalt(), password, user.getPassword())) {
			return HttpResult.error("密码不正确！");
		}
		//检查机构是否禁用
		SysOrg sysOrg = sysOrgMapper.findByOrg(username);
		if (sysOrg.getStatus() == 0){
			return HttpResult.error("机构已停用,请联系服务提供商！");
		}
		// 账号锁定
		if (user.getStatus() == 0) {
			return HttpResult.error("账号已被锁定,请联系管理员！");
		}
		// 系统登录认证
		JwtAuthenticatioToken token = SecurityUtils.login(request, username, password, authenticationManager);
		// 记录登录日志
		sysLoginLogService.writeLoginLog(username, IPUtils.getIpAddr(request));
		return HttpResult.ok(token);
	}*/

	/**
	 * 退出登录
	 * @throws ServletException
	 * @throws IOException
	 */
	@GetMapping("loginOut")
	public HttpResult loginOut(@RequestParam("userName") String userName, @RequestParam("loginType") String loginType, HttpServletRequest request)  {
		String ip = IPUtils.getIpAddr(request);
		String ipAddr = IPUtils.getCityInfo(ip);
		sysLoginLogService.writeLoginOut(userName,ip,ipAddr,loginType);
		return HttpResult.ok("退出成功!");
	}

}
