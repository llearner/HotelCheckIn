package cn.edu.xmu.ultraci.hotelcheckin.server.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class ParamUtil {

	/**
	 * 转换HTTP请求参数<br>
	 * 对同名参数只保留第一个
	 * 
	 * @param params 请求参数
	 * @return 转换结果
	 */
	public static Map<String, String> convertParams(Map<String, String[]> params) {
		Map<String, String> map = new HashMap<String, String>();
		Iterator<Entry<String, String[]>> it = params.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String[]> entry = it.next();
			map.put(entry.getKey(), entry.getValue()[0]);
		}
		return map;
	}
}
