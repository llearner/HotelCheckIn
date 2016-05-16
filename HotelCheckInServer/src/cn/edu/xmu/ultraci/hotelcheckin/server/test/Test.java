package cn.edu.xmu.ultraci.hotelcheckin.server.test;

import cn.edu.xmu.ultraci.hotelcheckin.server.dto.BaseDTO;
import cn.edu.xmu.ultraci.hotelcheckin.server.dto.HeartbeatDTO;
import net.sf.json.JSONObject;

public class Test {
	public static void main(String[] args) {
		String tmp = JSONObject.fromObject(new BaseDTO()).toString();
		JSONObject obj = JSONObject.fromObject(tmp);
		HeartbeatDTO dto = (HeartbeatDTO) JSONObject.toBean(obj, HeartbeatDTO.class);
		System.out.println(dto);
		
	}
}
