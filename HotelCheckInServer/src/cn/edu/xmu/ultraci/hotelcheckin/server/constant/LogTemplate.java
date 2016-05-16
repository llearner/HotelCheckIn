package cn.edu.xmu.ultraci.hotelcheckin.server.constant;

public class LogTemplate {
	// 鉴权失败
	public static final String AUTH_FAIL = "request from %s authenticate failure.";
	public static final String AUTH_INVAILD_PARAM = "invalid authentication parameter '%s'.";
	public static final String AUTH_UNKNOWN_TOKEN = "authentication parameter 'token' not set.";
	// 无效请求
	public static final String INVALID_ACTION = "request with content %s has invalid action.";
	public static final String INVALID_PARAMS = "request with content %s has invalid params.";
	// 内部错误
	public static final String INTERNAL_ERR = "something unexpected occurred within the system.";
	// 心跳、初始化
	public static final String HEARTBEAT = "receive heartbeat from client %s.";
	public static final String INIT = "client %s initialize success.";
	// 登录登出
	public static final String LOGIN_OK = "client %s login success by card %s.";
	public static final String LOGIN_NO_SUCH_CARD = "client %s login failure by card %s, card unregistered.";
	public static final String LOGIN_NO_PREMISSION = "client %s login failure by card %s, permission denied.";
	public static final String LOGOUT_OK = "client %s logout success by card %s.";
	public static final String LOGOUT_NO_SUCH_CARD = "client %s logout failure by card %s, card unregistered.";
	public static final String LOGOUT_NO_PREMISSION = "client %s logout failure by card %s, permission denied.";
	// 查询
	public static final String QUERY_MEMBER_OK = "client %s query member info of card %s success.";
	public static final String QUERY_MEMBER_NO_SUCH_CARD = "client %s query member info of card %s failure, card unregistered.";
	public static final String QUERY_TYPE_OK = "client %s query type info success, total %s types.";
	public static final String QUERY_FLOOR_OK = "client %s query floor info success, total %s floors.";
	public static final String QUERY_STATUS_OK = "client %s query status info success, total %s statuses.";
	public static final String QUERY_STATUS_INVALID_FILTER = "client %s query status info failure, invalid filter %s & %s.";
	public static final String QUERY_ROOM_OK = "client %s query room info of card %s success.";
	public static final String QUERY_ROOM_NO_SUCH_CARD = "client %s query room info of card %s failure, card unregistered.";
	public static final String QUERY_ROOM_NO_CHECK_IN = "client %s query room info of card %s failure, room no check in.";
	public static final String QUERY_INFO_OK = "client %s query %s info success.";
	public static final String QUERY_INFO_NO_SUCH_TYPE = "client %s query %s info failure, type is unknown";
	// 房务
	public static final String NEW_GUEST_OK = "client %s upload guest with id %s success.";
	public static final String NEW_GUEST_FILE_NOT_FOUND = "client %s upload guest failure, idcard pic file %s not found.";
	public static final String CHECK_IN_OK = "client %s check in of room %s success.";
	public static final String EXTENSION_OK = "client %s extension of room %s success.";
	public static final String CHECK_OUT_OK = "client %s check out of card %s success.";
	public static final String CHECK_OUT_NO_SUCH_CARD = "client %s check out of card %s failure, card unregistered.";
	public static final String CHECK_OUT_NO_CHECK_IN = "client %s check out of card %s failure, room no check in.";
	public static final String CHECK_OUT_NEED_PAY = "client %s check out of card %s failure, customer need to pay extra.";
	// 异常
	public static final String SQL_EXCP = "error while doing database operation.";
	public static final String IO_EXCP_PROP = "error while loading configuration file %s.";
	public static final String IO_EXCP_MD5 = "error while calculating MD5.";
	public static final String UPLOAD_EXCP = "error while uploading file.";
	public static final String PARSE_EXCP = "error while parsing date string.";
	public static final String INST_EXCP = "error while instancing a object for class %s.";
	// 其他
	public static final String ROOT_PATH = "real path of server is %s.";
	public static final String INSTANT = "instantiation a object for class %s.";
	public static final String INST_NOT_MAP = "implementation class of %s not found in factory.properties";
	public static final String FILE_UPLOAD = "client %s upload file %s of type %s success.";
}
