package com.framework.pie.auth.security.util;

import com.framework.pie.utils.MD5Utils;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 密码加密
 * @author longlong
 */
@Component
@Data
public class SelfPasswordEncoder implements PasswordEncoder {

	private Object salt;

	/**
	 * 密码加密
	 * @param rawPass
	 * @return
	 */

	@Override
	public String encode(CharSequence rawPass) {
		String result = MD5Utils.encrypt((String) salt,rawPass.toString());
		return result;
	}

	/**
	 * 密码匹配验证
	 * @param rawPass 明文
	 * @param encPass 密文
	 * @return
	 */
	@Override
	public boolean matches(CharSequence rawPass, String encPass) {
		return MD5Utils.matches(rawPass.toString(),encPass);
	}

	@Override
	public boolean upgradeEncoding(String encodedPassword) {
		return false;
	}
}