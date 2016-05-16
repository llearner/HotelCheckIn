package cn.edu.xmu.ultraci.hotelcheckin.server.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.edu.xmu.ultraci.hotelcheckin.server.constant.ErrorCode;
import cn.edu.xmu.ultraci.hotelcheckin.server.constant.LogTemplate;
import cn.edu.xmu.ultraci.hotelcheckin.server.dto.BaseDTO;
import cn.edu.xmu.ultraci.hotelcheckin.server.dto.FileuploadDTO;
import cn.edu.xmu.ultraci.hotelcheckin.server.factory.BaseFactory;
import cn.edu.xmu.ultraci.hotelcheckin.server.service.IAuthService;
import cn.edu.xmu.ultraci.hotelcheckin.server.util.StringUtil;
import net.sf.json.JSONObject;

/**
 * 文件上传请求入口<br>
 * 使用Apache Fileupload实现
 * 
 * @author LuoXin
 *
 */
@WebServlet(name = "FileUploadServlet.do", urlPatterns = { "/FileUploadServlet.do" })
public class FileUploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager.getLogger();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try (PrintWriter pr = response.getWriter()) {
			pr.write(JSONObject.fromObject(uploadFile(request)).toString());
			pr.flush();
		}
	}

	private BaseDTO uploadFile(HttpServletRequest req) {
		if (ServletFileUpload.isMultipartContent(req)) {
			// 初始化Fileupload组件
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			// upload.setHeaderEncoding("UTF-8");
			// 解析请求
			List<FileItem> items = null;
			try {
				items = upload.parseRequest(req);
			} catch (FileUploadException e) {
				logger.error(LogTemplate.UPLOAD_EXCP, e);
			}
			Map<String, Object> params = new HashMap<String, Object>();
			for (FileItem item : items) {
				if (item.isFormField()) {
					params.put(item.getFieldName(), item.getString());
				} else {
					// 要求文件表单域名为“file”
					params.put("file", item);
				}
			}
			// 鉴权
			IAuthService authServ = (IAuthService) BaseFactory.getInstance(IAuthService.class);
			if (authServ.doAuth(String.valueOf(params.get("random")),
					String.valueOf(params.get("signature")))) {
				String device = String.valueOf(params.get("device"));
				String type = String.valueOf(params.get("type"));
				if (!StringUtil.isBlank(type)) {
					// 根据上传类型决定文件保存路径
					File filePath = null;
					switch (type) {
					case "idcard":
						filePath = new File(
								getServletContext().getRealPath("/") + "upload\\idcard");
						break;
					case "video":
						filePath = new File(getServletContext().getRealPath("/") + "upload\\video");
						break;
					default:
						filePath = new File(
								getServletContext().getRealPath("/") + "upload\\undefine");
						break;
					}
					if (!filePath.exists()) {
						filePath.mkdirs();
					}
					FileItem item = (FileItem) params.get("file");
					String fileName = item.getName()
							.substring(item.getName().lastIndexOf('\\') + 1);
					try {
						item.write(new File(filePath, fileName));
					} catch (Exception e) {
						logger.error(LogTemplate.UPLOAD_EXCP, e);
					}
					FileuploadDTO retModel = new FileuploadDTO();
					retModel.setFilename(fileName);
					logger.info(String.format(LogTemplate.FILE_UPLOAD, device, fileName, type));
					return retModel;
				} else {
					logger.warn(String.format(LogTemplate.INVALID_ACTION, params));
					return new BaseDTO(ErrorCode.INVALID_REQ);
				}
			} else {
				// 鉴权失败
				logger.warn(String.format(LogTemplate.AUTH_FAIL, req.getRemoteAddr()));
				return new BaseDTO(ErrorCode.AUTH_FAIL);
			}
		}
		return null;
	}
}
