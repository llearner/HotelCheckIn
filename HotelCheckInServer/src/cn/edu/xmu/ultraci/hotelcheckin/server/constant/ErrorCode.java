package cn.edu.xmu.ultraci.hotelcheckin.server.constant;

public class ErrorCode {
	// 鉴权失败
	public static final int AUTH_FAIL = -1;
	// 成功
	public static final int OK = 0;
	// 内部错误
	public static final int INTERNAL_ERR = 30000;
	// 非法请求
	public static final int INVALID_REQ = 40001;
	// 登录登出
	public static final int LOGIN_OUT_NO_SUCH_CARD = 40101;
	public static final int LOGIN_OUT_NO_PREMISSION = 40102;
	// 查询
	public static final int QUERY_MEMBER_NO_SUCH_CARD = 40201;
	public static final int QUERY_STATUS_INVALID_FILTER = 40301;
	public static final int QUERY_ROOM_NO_SUCH_CARD = 40401;
	public static final int QUERY_ROOM_NO_CHECK_IN = 40402;
	public static final int QUERY_INFO_NO_SUCH_TYPE = 40501;
	// 房务
	public static final int NEW_GUEST_FILE_NOT_FOUND = 40601;
	public static final int CHECK_OUT_NO_SUCH_CARD = 40801;
	public static final int CHECK_OUT_NO_CHECK_IN = 40802;
	public static final int CHECK_OUT_NEED_PAY = 40803;
	// 文件上传
	public static final int FILE_UPLOAD_ERROR = 40901;
}
