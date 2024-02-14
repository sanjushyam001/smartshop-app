package com.smartshop.admin.user.export;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

public class AbstractExporter {

	public void setResponseHeader(HttpServletResponse response,String contentType,String extension) {
		DateFormat dateFormatter=new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String timeStamp=dateFormatter.format(new Date());
		String fileName="user_"+timeStamp+extension;
		response.setContentType(contentType);
		
		String headerKey="Content-Disposition";
		String headerValue= "attachment; filename="+fileName;
		response.setHeader(headerKey, headerValue);
	}
}
