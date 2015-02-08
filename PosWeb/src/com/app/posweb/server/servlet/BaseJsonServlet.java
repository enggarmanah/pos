package com.app.posweb.server.servlet;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
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

		BufferedReader br = new BufferedReader(new InputStreamReader(
				request.getInputStream()));

		StringBuffer json = new StringBuffer();

		String line = null;

		while ((line = br.readLine()) != null) {
			json.append(line);
		}
		
		String input = uncompressString(json.toString());

		//Object output = processJsonRequest(json.toString());
		Object output = processJsonRequest(input);

		response.setContentType("application/json");

		ObjectMapper mapper = new ObjectMapper();

		mapper.writeValue(response.getOutputStream(), output);
	}

	protected abstract Object processJsonRequest(String jsonStr)
			throws IOException;

	public static String uncompressString(String zippedBase64Str)
			throws IOException {
		
		String result = null;
		byte[] bytes = Base64.decodeBase64(zippedBase64Str);
		
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
