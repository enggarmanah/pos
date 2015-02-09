package com.app.posweb.server.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

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

		Object output = processJsonRequest(uncompress(request.getInputStream()));

		response.setContentType("application/json");

		ObjectMapper mapper = new ObjectMapper();

		byte[] bytes = mapper.writeValueAsBytes(output);
		bytes = compress(bytes);
		
		response.getOutputStream().write(bytes);
	}

	protected abstract Object processJsonRequest(String jsonStr)
			throws IOException;

	public static String uncompress(InputStream inputStream)
			throws IOException {
		
		String result = null;
		
		GZIPInputStream zi = null;
		
		try {
			
			zi = new GZIPInputStream(inputStream);
			result = IOUtils.toString(zi);
			
		} catch(Exception e) {
			e.printStackTrace();
			
		} finally {
			if (zi != null) {
				zi.close();
			}
		}
		
		return result;
	}
	
	public static byte[] compress(byte[] bytes) throws IOException {

		ByteArrayOutputStream rstBao = new ByteArrayOutputStream();
		GZIPOutputStream zos = new GZIPOutputStream(rstBao);
		
		zos.write(bytes);
		zos.close();

		byte[] output = rstBao.toByteArray();
		
		return output;
	}
}
