package com.rayllanderson.util;

import javax.servlet.http.HttpServletResponse;

public class FileUtil {

    /**
     * 
     * @param response
     * @param fileBytes
     * @param downloadFileName inidica qual será o nome arquivo. Ex: relatorio.pdf
     */
    public static void setToDownload(HttpServletResponse response, byte[] fileBytes, String downloadFileName, String contentType) {
	try {
	    response.setContentLength(fileBytes.length);
	    response.setContentType("application/pdf");
	    String headerKey = "Content-Disposition";
	    String headerValue = String.format("attachment; filename=\"%s\"", (downloadFileName + "." + contentType));
	    response.setHeader(headerKey, headerValue);
	    response.getOutputStream().write(fileBytes);
	    response.getOutputStream().close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

}
