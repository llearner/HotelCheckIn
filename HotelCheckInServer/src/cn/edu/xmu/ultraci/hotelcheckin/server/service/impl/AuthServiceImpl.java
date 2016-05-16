package cn.edu.xmu.ultraci.hotelcheckin.server.service.impl;

import java.util.Arrays;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.edu.xmu.ultraci.hotelcheckin.server.constant.LogTemplate;
import cn.edu.xmu.ultraci.hotelcheckin.server.factory.BaseFactory;
import cn.edu.xmu.ultraci.hotelcheckin.server.service.IAuthService;
import cn.edu.xmu.ultraci.hotelcheckin.server.service.IConfService;
import cn.edu.xmu.ultraci.hotelcheckin.server.util.StringUtil;

public class AuthServiceImpl implements IAuthService {

	private static Logger logger = LogManager.getLogger();

	@Override
	public boolean doAuth(String random, String signature) {
		// 检查参数有效性
		IConfService confServ = ((IConfService) BaseFactory.getInstance(IConfService.class));
		String token = confServ.getConf("token");
		if (StringUtil.isBlank(random)) {
			logger.warn(String.format(LogTemplate.AUTH_INVAILD_PARAM, "random"));
			return false;
		}
		if (StringUtil.isBlank(signature)) {
			logger.warn(String.format(LogTemplate.AUTH_INVAILD_PARAM, "signature"));
			return false;
		}
		if (StringUtil.isBlank(token)) {
			logger.warn(LogTemplate.AUTH_UNKNOWN_TOKEN);
			return false;
		}
		// 排序和合并
		String[] array = new String[] { token, random };
		Arrays.sort(array);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			sb.append(array[i]);
		}
		// 与加密签名相比较
		return DigestUtils.sha1Hex(sb.toString()).equalsIgnoreCase(signature);
	}

}
