package com.sift.loan.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream; 
import javax.servlet.http.*; 

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
 
@Controller
public class FileController {
 
	@RequestMapping(value = "/indexP", method = RequestMethod.GET)
	public String start() throws IOException {
		return "indexP";
	}
 
	@RequestMapping(value = "/downloadReport", method = RequestMethod.GET)
	public void download(HttpServletResponse response,HttpServletRequest req) throws IOException {
 
		File file = new File(req.getParameter("filename"));
		InputStream is = new FileInputStream(file);
 
		// MIME type of the file
		response.setContentType("application/octet-stream");
		// Response header
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ file.getName() + "\"");
		// Read from the file and write into the response
		OutputStream os = response.getOutputStream();
		byte[] buffer = new byte[1024];
		int len;
		while ((len = is.read(buffer)) != -1) {
			os.write(buffer, 0, len);
		}
		os.flush();
		os.close();
		is.close();
	}
}