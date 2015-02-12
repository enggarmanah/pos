package com.android.pos.util;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;

import com.android.pos.Constant;
import com.android.pos.dao.ProductGroup;

@SuppressLint("SimpleDateFormat")
public class CommonUtil {
	
	public static Long getMerchantId() {
		
		return Long.valueOf(1);
	}
	
	public static boolean isEmpty(String str) {
		
		if (str != null && str.length() != 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public static Long getNvlId(ProductGroup prdGroup) {
		
		if (prdGroup == null) {
			return null;
		} else {
			return prdGroup.getId();
		}
	}
	
	public static Integer getNvl(Integer value) {
		
		if (value == null) {
			return 0;
		} else {
			return value;
		}
	}
	
	public static Integer strToInt(String s) {
		
		Integer number = null;
		
		try {
			number = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			// do nothing
		}
		
		return number;
	}
	
	public static String intToStr(Integer i) {
		
		String number = null;
		
		try {
			if (i != null) {
				number = String.valueOf(i);
			}
		} catch (NumberFormatException e) {
			// do nothing
		}
		
		return number;
	}
	
	static Locale locale;
	
	public static Locale getLocale() {
		
		if (locale == null) {
			locale = new Locale("id", "ID");
		}
		
		return locale;
	}
	
	public static DateFormat getDateFormat() {
		
		return SimpleDateFormat.getDateInstance(DateFormat.DEFAULT, getLocale());
	}
	
	public static DateFormat getDateFormat(String format) {
		
		return new SimpleDateFormat(format, getLocale());
	}
	
	public static DateFormat getDateTimeFormat() {
		
		return new SimpleDateFormat("dd MMM yyyy, HH:mm", getLocale());
	}
	
	public static DateFormat getDayDateTimeFormat() {
		
		return new SimpleDateFormat("EEEE, dd MMM yyyy, HH:mm", getLocale());
	}
	
	public static Date getFirstDayOfMonth(Date date) {
		
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(date);
		cal.set(Calendar.DATE, 1);
		
		return cal.getTime();
	}
	
	public static Date getLastDayOfMonth(Date date) {
		
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(date);
		cal.set(Calendar.DATE, 1);
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DATE, -1);
		
		return cal.getTime();
	}
	
	public static Date getCurrentMonth() {
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, 1);
		
		return cal.getTime();
	}
	
	public static Date parseDate(String dateStr) {
		
		Date date = null;
		
		try {
			date = getDateFormat().parse(dateStr);
		} catch (ParseException e) {
			// do nothing
		}
		
		return date;
	}
	
	public static Date parseDate(String dateStr, String format) {
		
		Date date = null;
		
		try {
			date = getDateFormat(format).parse(dateStr);
		} catch (ParseException e) {
			// do nothing
		}
		
		return date;
	}
	
	public static String formatDate(Date inputDate) {
		
		String dateStr = Constant.EMPTY_STRING;
		
		try {
			dateStr = getDateFormat().format(inputDate);
		} catch (Exception e) {
			// do nothing
		}
		
		return dateStr;
	}
	
	public static String formatDayDate(Date inputDate) {
		
		String dateStr = Constant.EMPTY_STRING;
		
		try {
			dateStr = getDateFormat("EEEE, dd MMM yyyy").format(inputDate);
		} catch (Exception e) {
			// do nothing
		}
		
		return dateStr;
	}
	
	public static String formatMonthDate(Date inputDate) {
		
		String dateStr = Constant.EMPTY_STRING;
		
		try {
			dateStr = getDateFormat("MMMM yyyy").format(inputDate);
		} catch (Exception e) {
			// do nothing
		}
		
		return dateStr;
	}
	
	public static String formatDateTime(Date inputDate) {
		
		String dateStr = Constant.EMPTY_STRING;
		
		try {
			dateStr = getDateTimeFormat().format(inputDate);
		} catch (Exception e) {
			// do nothing
		}
		
		return dateStr;
	}
	
	public static String formatDayDateTime(Date inputDate) {
		
		String dateStr = Constant.EMPTY_STRING;
		
		try {
			dateStr = getDayDateTimeFormat().format(inputDate);
		} catch (Exception e) {
			// do nothing
		}
		
		return dateStr;
	}
	
	public static String getTransactionNo() {
		
		Date inputDate = new Date();
		
		String transactionNo = Constant.EMPTY_STRING;
		
		try {
			DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			transactionNo = df.format(inputDate);
		} catch (Exception e) {
			// do nothing
		}
		
		return transactionNo;
	}
	
	public static String formatString(String inputStr) {
		
		if (inputStr != null) {
			return inputStr;
		} else {
			return Constant.EMPTY_STRING;
		}
	}
	
	public static String formatString(Integer inputInt) {
		
		if (inputInt != null) {
			return String.valueOf(inputInt);
		} else {
			return Constant.EMPTY_STRING;
		}
	}
	
	public static String formatString(Long inputInt) {
		
		if (inputInt != null) {
			return String.valueOf(inputInt);
		} else {
			return Constant.EMPTY_STRING;
		}
	}
	
	public static String formatCurrency(String inputStr) {
		
		String formatted = inputStr;
		
		NumberFormat nf = NumberFormat.getCurrencyInstance(getLocale());
		
        try{
            formatted = nf.format(Long.parseLong(inputStr));
            formatted = formatted.replace("Rp", "Rp ");
        } catch (NumberFormatException nfe) {
        	// do nothing
        }
        
        return formatted;
	}
	
	public static String formatCurrencyUnsigned(String inputStr) {
		
		String formatted = inputStr;
		
		NumberFormat nf = NumberFormat.getCurrencyInstance(getLocale());
		
        try{
            formatted = nf.format(Long.parseLong(inputStr));
            formatted = formatted.replace("Rp", "");
        } catch (NumberFormatException nfe) {
        	// do nothing
        }
        
        return formatted;
	}
	
	public static String formatCurrency(Integer inputInt) {
		
		return formatCurrency(formatString(inputInt));
	}
	
	public static String formatCurrency(Long inputInt) {
		
		return formatCurrency(formatString(inputInt));
	}
	
	public static String formatCurrencyUnsigned(Integer inputInt) {
		
		return formatCurrencyUnsigned(formatString(inputInt));
	}
	
	public static Integer parseCurrency(String inputStr) {
		
		String unformatted = inputStr;
		Integer number = null;
		
		if (!isEmpty(inputStr)) {
			unformatted = inputStr.replaceAll("\\D", "");
		}
        
		try {
			number = Integer.valueOf(unformatted);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
        return number; 
	}
	
	public static Integer parseInteger(String inputStr) {
		
		Integer number = null;
		
		try {
			if (!isEmpty(inputStr)) {
				number = Integer.valueOf(inputStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
        return number; 
	}
}
