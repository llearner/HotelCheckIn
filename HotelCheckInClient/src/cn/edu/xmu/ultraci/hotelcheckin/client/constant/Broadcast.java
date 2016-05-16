package cn.edu.xmu.ultraci.hotelcheckin.client.constant;

public class Broadcast {
	private static final String THIRDPARTY_PREFIX = "action.thirdparty.";
	private static final String IFLYTEK_PREFIX = THIRDPARTY_PREFIX + "iflytek.";
	private static final String MOB_PREFIX = THIRDPARTY_PREFIX + "mob.";
	private static final String CORE_PREFIX = "action.core.";
	private static final String MISC_PREFIX = "action.misc.";

	// 第三方服务广播
	public static final String THIRDPARTY_SERIVCE_BOUND = THIRDPARTY_PREFIX + "SERVICE_BOUND";

	public static final String IFLYTEK_INIT_FAIL = IFLYTEK_PREFIX + "init.FAIL";
	public static final String IFLYTEK_SYNTHESIS_OK = IFLYTEK_PREFIX + "synthesis.OK";
	public static final String IFLYTEK_RECORD_START = IFLYTEK_PREFIX + "record.START";
	public static final String IFLYTEK_RECORD_END = IFLYTEK_PREFIX + "record.END";
	public static final String IFLYTEK_RECORD_VOLUME_CHANGE = IFLYTEK_PREFIX + "record.VOLUME_CHANGE";
	public static final String IFLYTEK_VERIFY_OK = IFLYTEK_PREFIX + "verify.OK";
	public static final String IFLYTEK_VERIFY_FAIL_VOICE = IFLYTEK_PREFIX + "verify.FAIL_VOICE";
	public static final String IFLYTEK_VERIFY_FAIL_TEXT = IFLYTEK_PREFIX + "verify.FAIL_TEXT";
	public static final String IFLYTEK_VERIFY_FAIL_OTHER = IFLYTEK_PREFIX + "verify.FAIL_OTHER";

	public static final String MOB_CAPTCHA_SMS_SEND = MOB_PREFIX + "captcha.SMS_SEND";
	public static final String MOB_CAPTCHA_VOICE_SEND = MOB_PREFIX + "captcha.VOICE_SEND";
	public static final String MOB_CAPTCHA_VERIFY_OK = MOB_PREFIX + "captcha.verify.OK";
	public static final String MOB_CAPTCHA_VERIFY_FAIL = MOB_PREFIX + "captcha.verify.FAIL";

	// 核心服务广播
	public static final String CORE_SERIVCE_BOUND = CORE_PREFIX + "SERVICE_BOUND";

	public static final String CORE_SERVER_PROCESS_FAIL = CORE_PREFIX + "server.PROCESS_FAIL";
	public static final String CORE_SERVER_REQUEST_FAIL = CORE_PREFIX + "server.REQUEST_FAIL";

	public static final String CORE_INIT_OK = CORE_PREFIX + "init.OK";
	public static final String CORE_LOGIN_OK = CORE_PREFIX + "login.OK";
	public static final String CORE_LOGIN_NO_PREMISSION = CORE_PREFIX + "login.NO_PREMISSION";
	public static final String CORE_LOGIN_NO_SUCH_CARD = CORE_PREFIX + "login.NO_SUCH_CARD";
	public static final String CORE_LOGOUT_OK = CORE_PREFIX + "logout.SUCC";
	public static final String CORE_LOGOUT_NO_PREMISSION = CORE_PREFIX + "logout.NO_PREMISSION";
	public static final String CORE_LOGOUT_NO_SUCH_CARD = CORE_PREFIX + "logout.NO_SUCH_CARD";

	public static final String CORE_QUERY_MEMBER_OK = CORE_PREFIX + "query.member.OK";
	public static final String CORE_QUERY_MEMBER_NO_SUCH_CARD = CORE_PREFIX + "query.member.NO_SUCH_CARD";
	public static final String CORE_QUERY_TYPE_OK = CORE_PREFIX + "query.type.OK";
	public static final String CORE_QUERY_FLOOR_OK = CORE_PREFIX + "query.floor.OK";
	public static final String CORE_QUERY_STATUS_OK = CORE_PREFIX + "query.status.OK";
	public static final String CORE_QUERY_ROOM_OK = CORE_PREFIX + "query.room.OK";
	public static final String CORE_QUERY_ROOM_NO_CHECKIN = CORE_PREFIX + "query.room.NO_CHECKIN";
	public static final String CORE_QUERY_ROOM_NO_SUCH_CARD = CORE_PREFIX + "query.room.NO_SUCH_CARD";

	public static final String CORE_GUEST_OK = CORE_PREFIX + "guest.OK";
	public static final String CORE_CHECKIN_OK = CORE_PREFIX + "checkin.OK";
	public static final String CORE_CHECKOUT_OK = CORE_PREFIX + "checkout.OK";
	public static final String CORE_CHECKOUT_NEED_PAY = CORE_PREFIX + "checkout.NEED_PAY";
	public static final String CORE_CHECKOUT_NO_CHECKIN = CORE_PREFIX + "checkout.NO_CHECKIN";
	public static final String CORE_CHECKOUT_NO_SUCH_CARD = CORE_PREFIX + "checkout.NO_SUCH_CARD";

	public static final String CORE_FILE_UPLOAD_OK = CORE_PREFIX + "file.upload.OK";
	public static final String CORE_FILE_UPLOAD_FAIL = CORE_PREFIX + "file.upload.FAIL";
	public static final String CORE_FILE_DOWNLOAD_OK = CORE_PREFIX + "file.download.OK";
	public static final String CORE_FILE_DOWNLOAD_FAIL = CORE_PREFIX + "file.download.FAIL";

	// 杂项服务广播
	public static final String MISC_SERIVCE_BOUND = MISC_PREFIX + "SERVICE_BOUND";

	public static final String MISC_BLUETOOTH_NONSUPPORT = MISC_PREFIX + "bluetooth.NONSUPPORT";
	public static final String MISC_BLUETOOTH_DISABLE = MISC_PREFIX + "bluetooeh.DISABLE";
	public static final String MISC_NFC_NONSUPPORT = MISC_PREFIX + "nfc.NONSUPPORT";
	public static final String MISC_NFC_DISABLE = MISC_PREFIX + "nfc.DISABLE";
	public static final String MISC_PRINTER_CONN_FAIL = MISC_PREFIX + "printer.CONN_FAIL";
}
