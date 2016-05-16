package cn.edu.xmu.ultraci.hotelcheckin.client.constant;

public class TTS {
	// 初始化界面
	public static final String INIT_WELCOME = "欢迎使用酒店自助入住终端。";
	public static final String INIT_FAIL = "系统初始化失败，请检查配置。";

	// 刷卡界面
	public static final String SWIPE_CRAD_LOGIN_OUT = "请将您的员工卡靠近感应区。";
	public static final String SWIPE_CRAD_LOGIN_NO_PREMISSION = "您没有权限登录本系统。";
	public static final String SWIPE_CRAD_LOGOUT_NO_PREMISSION = "您没有权限登出本系统。";
	public static final String SWIPE_CRAD_LOGIN_OUT_NO_SUCH_CARD = "请刷员工卡。";

	public static final String SWIPE_CRAD_QUERY_MEMBER = "请将您的会员卡放置在感应区内。";
	public static final String SWIPE_CRAD_QUERY_MEMBER_NO_SUCH_CARD = "请刷会员卡。";

	public static final String SWIPE_CRAD_QUERY_ROOM = "请将您的房卡放置在感应区内。";
	public static final String SWIPE_CRAD_QUERY_ROOM_NO_CHECKIN = "没有此房间的入住信息，请联系前台。";
	public static final String SWIPE_CRAD_QUERY_ROOM_NO_SUCH_CARD = "请刷房卡。";

	// 声纹验证界面
	public static final String VOICEPRINT = "我们需要验证您的身份。请在听到提示音后，朗读屏幕上的数字。";
	public static final String VOICEPRINT_OK = "身份验证成功。";
	public static final String VOICEPRINT_FAIL_TEXT = "您朗读的数字有误。";
	public static final String VOICEPRINT_FAIL_VOICE = "听不清您的声音。";
	public static final String VOICEPRINT_FAIL_OTHER = "验证失败。";
	public static final String VOICEPRINT_RETRY = "让我们再试一次。";
	public static final String VOICEPRINT_LIMITED = "验证次数已超过限制。";

}
