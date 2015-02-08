package com.app.posweb.server.servlet;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("serial")
public abstract class BaseJsonServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		BufferedInputStream bis = new BufferedInputStream(request.getInputStream());
		
		StringBuffer sb = new StringBuffer();
		
		int size = -1;
		byte[] bytes = new byte[1024];
		
		while ((size = bis.read(bytes)) != -1) {
			
			byte[] input = Arrays.copyOf(bytes, size);
			sb.append(uncompressString(input));
		}
		
		Object output = processJsonRequest(sb.toString());

		response.setContentType("application/json");

		ObjectMapper mapper = new ObjectMapper();

		mapper.writeValue(response.getOutputStream(), output);
	}

	protected abstract Object processJsonRequest(String jsonStr)
			throws IOException;

	public static String uncompressString(byte[] bytes)
			throws IOException {
		
		String result = null;
		
		GZIPInputStream zi = null;
		
		try {
			
			zi = new GZIPInputStream(new ByteArrayInputStream(bytes));
			result = IOUtils.toString(zi);
			
		} finally {
			if (zi != null) {
				zi.close();
			}
		}
		
		return result;
	}
}
