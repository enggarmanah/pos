package com.android.pos;

public class CodeBean {

	private String code;
	private String label;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public static String getNvlCode(CodeBean bean) {
		
		if (bean == null) {
			return null;
		} else {
			return bean.getCode();
		}
	}
}
