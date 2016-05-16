package cn.edu.xmu.ultraci.hotelcheckin.client.constant;

public class URL {
	public static final String PREFIX = "http://" + Config.SERVER_IP + ":" + Config.SERVER_PORT;

	public static final String ADMIN_URL = PREFIX + "/HotelCheckInServer/AdminServlet.do";
	public static final String CLIENT_URL = PREFIX + "/HotelCheckInServer/ClientServlet.do";
	public static final String FILE_UPLOAD_URL = PREFIX + "/HotelCheckInServer/FileUploadServlet.do";
}
