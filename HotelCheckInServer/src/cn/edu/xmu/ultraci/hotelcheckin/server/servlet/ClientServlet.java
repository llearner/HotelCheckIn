package cn.edu.xmu.ultraci.hotelcheckin.server.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.edu.xmu.ultraci.hotelcheckin.server.constant.Action;
import cn.edu.xmu.ultraci.hotelcheckin.server.constant.ErrorCode;
import cn.edu.xmu.ultraci.hotelcheckin.server.constant.LogTemplate;
import cn.edu.xmu.ultraci.hotelcheckin.server.dto.BaseDTO;
import cn.edu.xmu.ultraci.hotelcheckin.server.factory.BaseFactory;
import cn.edu.xmu.ultraci.hotelcheckin.server.service.IAuthService;
import cn.edu.xmu.ultraci.hotelcheckin.server.service.IConfService;
import cn.edu.xmu.ultraci.hotelcheckin.server.service.IQueryService;
import cn.edu.xmu.ultraci.hotelcheckin.server.service.IRoomService;
import cn.edu.xmu.ultraci.hotelcheckin.server.service.ISystemService;
import cn.edu.xmu.ultraci.hotelcheckin.server.util.ParamUtil;
import cn.edu.xmu.ultraci.hotelcheckin.server.util.StringUtil;
import net.sf.json.JSONObject;

/**
 * 客户端(大堂用)请求入口
 * 
 * @author LuoXin
 *
 */
@WebServlet(name = "ClientServlet.do", urlPatterns = { "/ClientServlet.do" })
public class ClientServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager.getLogger();

	private IAuthService authServ;
	private IConfService confServ;
	private IQueryService queryServ;
	private IRoomService roomServ;
	private ISystemService systemServ;

	@Override
	public void init() throws ServletException {
		super.init();

		authServ = (IAuthService) BaseFactory.getInstance(IAuthService.class);
		confServ = (IConfService) BaseFactory.getInstance(IConfService.class);
		queryServ = (IQueryService) BaseFactory.getInstance(IQueryService.class);
		roomServ = (IRoomService) BaseFactory.getInstance(IRoomService.class);
		systemServ = (ISystemService) BaseFactory.getInstance(ISystemService.class);

		// 将服务端实际目录写入配置文件中
		confServ.setConf("root", getServletContext().getRealPath("/"));
		logger.info(String.format(LogTemplate.ROOT_PATH, confServ.getConf("root")));
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 不支持以GET方式请求
		response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, String> params = ParamUtil.convertParams(request.getParameterMap());
		if (authServ.doAuth(params.get("random"), params.get("signature"))) {
			// 鉴权成功，提供服务
			String respJson = buildResponse(parseRequest(params));
			try (PrintWriter pr = response.getWriter()) {
				pr.write(respJson);
				pr.flush();
			}
		} else {
			// 鉴权失败
			logger.warn(String.format(LogTemplate.AUTH_FAIL, request.getRemoteAddr()));
			try (PrintWriter pr = response.getWriter()) {
				pr.write(JSONObject.fromObject(new BaseDTO(ErrorCode.AUTH_FAIL)).toString());
				pr.flush();
			}
		}
	}

	/**
	 * 解析请求<br>
	 * 根据请求的action转发到特定的业务逻辑
	 * 
	 * @param params 请求参数
	 * @return 业务逻辑处理结果
	 */
	private BaseDTO parseRequest(Map<String, String> params) {
		String action = params.get("action");
		if (!StringUtil.isBlank(action)) {
			switch (action) {
			case Action.HEARTBEAT:
				return systemServ.heartbeat(params);
			case Action.INIT:
				return systemServ.init(params);
			case Action.LOGIN:
				return systemServ.login(params);
			case Action.LOGOUT:
				return systemServ.logout(params);
			// case Action.SMS:
			// break;
			case Action.QUERY_MEMBER:
				return queryServ.queryMember(params);
			case Action.QUERY_TYPE:
				return queryServ.queryType(params);
			case Action.QUERY_FLOOR:
				return queryServ.queryFloor(params);
			case Action.QUERY_STATUS:
				return queryServ.queryStatus(params);
			case Action.QUERY_ROOM:
				return queryServ.queryRoom(params);
			case Action.QUERY_INFO:
				return queryServ.queryInfo(params);
			case Action.NEW_GUEST:
				return roomServ.guest(params);
			case Action.CHECK_IN:
				return roomServ.checkin(params);
			case Action.EXTENSION:
				return roomServ.extension(params);
			case Action.CHECK_OUT:
				return roomServ.checkout(params);
			// case Action.PAY_RESULT:
			// break;
			}
		}
		logger.warn(String.format(LogTemplate.INVALID_ACTION, params));
		return new BaseDTO(ErrorCode.INVALID_REQ);
	}

	/**
	 * 封装响应<br>
	 * 将DTO转换成JSON以通过IO传输
	 * 
	 * @param resp 响应对应的DTO
	 * @return 响应对应的JSON
	 */
	private <T extends BaseDTO> String buildResponse(T resp) {
		return JSONObject.fromObject(resp).toString();
	}

}
