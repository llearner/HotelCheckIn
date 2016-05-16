package cn.edu.xmu.ultraci.hotelcheckin.server.constant;

public class Action {
	// 系统类
	public static final String HEARTBEAT = "heartbeat";
	public static final String INIT = "init";
	public static final String LOGIN = "login";
	public static final String LOGOUT = "logout";
	@Deprecated
	public static final String SMS = "sms";
	// 查询类
	public static final String QUERY_MEMBER = "member";
	public static final String QUERY_TYPE = "type";
	public static final String QUERY_FLOOR = "floor";
	public static final String QUERY_STATUS = "status";
	public static final String QUERY_ROOM = "room";
	public static final String QUERY_INFO = "info";
	// 房务类
	public static final String NEW_GUEST = "guest";
	public static final String CHECK_IN = "checkin";
	public static final String EXTENSION = "extension";
	public static final String CHECK_OUT = "checkout";
	@Deprecated
	public static final String PAY_RESULT = "pay";

}
