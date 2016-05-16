<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="./FileUploadServlet.do" method="post"
		enctype="multipart/form-data">
		随机字符串：<input type="text" name="random"><br> 加密签名：<input
			type="text" name="signature"><br> 客户端标识：<input
			type="text" name="device"><br> 类型：<input type="text"
			name="type"><br> 文件：<input type="file" name="file"><br>
		<input type="submit">&nbsp;&nbsp;<input type="reset">
	</form>
</body>
</html>