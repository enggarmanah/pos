package com.app.posweb.shared;

public class Constant {
	
	public static final String ENV_STAGING = "Staging";
	public static final String ENV_PRODUCTION = "Production";
	
	public static final String APP_CALLBACK_URL_PROD = "http://posweb.appspot.com/verifyCredential";
	public static final String APP_CALLBACK_URL_DEV = "http://posweb.appspot.com:8888/verifyCredential";
	
	public static final String LINK_WEBSITE = "http://";
	public static final String LINK_FACEBOOK = "http://www.facebook.com/";
	public static final String LINK_TWITTER = "http://www.twitter.com/";
	
	public static final String SMS_GW_BULKSMS = "SMS_GW_BULKSMS";
	public static final String SMS_GW_WEBSMS = "SMS_GW_WEBSMS";
	
	public static final String URL_INST = "/instInfo";
	public static final String URL_DOCTOR = "/doctorInfo";
	public static final String URL_SERVICE = "/serviceInfo";
	
	public static final String SEX_MALE = "L";
	public static final String SEX_FEMALE = "P";
	
	public static final String IMAGE_URL = "/image?id=";
	
	public static final String STATUS_ACTIVE = "A";
	public static final String STATUS_INACTIVE = "I";
	
	public static final String SYSTEM_ID = "System";
	
	public static final String EMPTY_STRING = "";
	public static final String ZERO_STRING = "0";
	
	public static final String YES = "Y";
	public static final String NO = "N";
	
	public static final String YES_DESC = "Ya";
	public static final String NO_DESC = "Tidak";
	
	public static final String ACTIVE = "A";
	public static final String INACTIVE = "I";
	
	public static final String ACTIVE_DESC = "Aktif";
	public static final String INACTIVE_DESC = "Tidak Aktif";
	
	public static final int DAY_START = 1;
	public static final int DAY_END = 7;
	
	public static final int DAY_ALL = -1;
	public static final String DAY_ALL_DESC = "Semua Hari";
	
	public static final int DAY_MONDAY = 1;
	public static final String DAY_MONDAY_DESC = "Senin";
	
	public static final int DAY_TUESDAY = 2;
	public static final String DAY_TUESDAY_DESC = "Selasa";
	
	public static final int DAY_WEDNESDAY = 3;
	public static final String DAY_WEDNESDAY_DESC = "Rabu";
	
	public static final int DAY_THURSDAY = 4;
	public static final String DAY_THURSDAY_DESC = "Kamis";
	
	public static final int DAY_FRIDAY = 5;
	public static final String DAY_FRIDAY_DESC = "Jumat";
	
	public static final int DAY_SATURDAY = 6;
	public static final String DAY_SATURDAY_DESC = "Sabtu";
	
	public static final int DAY_SUNDAY = 7;
	public static final String DAY_SUNDAY_DESC = "Minggu";
	
	public static final int APPT_INTERVAL_MINUTES = 30;
	
	public static final String TIMEZONE = "GMT+7:00";
	
	public static final int HOUR_SECS = 3600;
	public static final int MIN_SECS = 60;
	public static final int MILISECS = 1000;
	
	public static final int OPENING_TIME_START = 7 * 60 * 60 * 1000;
	public static final int OPENING_TIME_END = 22 * 60 * 60 * 1000;
	public static final int OPENING_PERIOD = 30 * 60 * 1000;
	
	public static final int QUERY_MAX_RESULT = 100;
	
	public static final int DISPLAY_TIME = 2000;
	public static final int FADE_TIME = 700;
	
	public static final int GALLERY_TIME = 6;
	
	public static final int MAP_DEFAULT_SCALE = 15;
	
	public static final double MAP_METER_LENGTH = 0.000011;
	
	public static final double MAP_DEFAULT_LAT = -6.211807421831029;
	public static final double MAP_DEFAULT_LNG = 106.81933879852295;
	
	public static final int SOCIAL_VERIFICAITON_TIMEOUT = 60000;
	
	public static final int PAGE_SIZE = 10;
	
	public static final int POPUP_L1_TOP = 70;
	public static final int POPUP_L2_TOP = 108;
	public static final int POPUP_L2_LEFT = 14;
	
	public static final int FILE_UPLOAD_MAX_SIZE = 500000;
	
	public static final String TASK_VALIDATE_MERCHANT = "TASK_VALIDATE_MERCHANT";
	public static final String TASK_VALIDATE_USER = "TASK_VALIDATE_USER";
	
	public static final String TASK_ROOT_GET_MERCHANT = "TASK_ROOT_GET_MERCHANT";
	public static final String TASK_ROOT_GET_MERCHANT_ACCESS = "TASK_ROOT_GET_MERCHANT_ACCESS";
	
	public static final String TASK_GET_LAST_SYNC = "TASK_GET_LAST_SYNC";
	public static final String TASK_GET_PRODUCT_GROUP = "TASK_GET_PRODUCT_GROUP";
	public static final String TASK_GET_DISCOUNT = "TASK_GET_DISCOUNT";
	public static final String TASK_GET_MERCHANT = "TASK_GET_MERCHANT";
	public static final String TASK_GET_EMPLOYEE = "TASK_GET_EMPLOYEE";
	public static final String TASK_GET_CUSTOMER = "TASK_GET_CUSTOMER";
	public static final String TASK_GET_CASHFLOW = "TASK_GET_CASHFLOW";
	public static final String TASK_GET_PRODUCT = "TASK_GET_PRODUCT";
	public static final String TASK_GET_USER = "TASK_GET_USER";
	public static final String TASK_GET_TRANSACTION = "TASK_GET_TRANSACTION";
	public static final String TASK_GET_TRANSACTION_ITEM = "TASK_GET_TRANSACTION_ITEM";
	public static final String TASK_GET_ORDER = "TASK_GET_ORDER";
	public static final String TASK_GET_ORDER_ITEM = "TASK_GET_ORDER_ITEM";
	public static final String TASK_GET_SUPPLIER = "TASK_GET_SUPPLIER";
	public static final String TASK_GET_BILL = "TASK_GET_BILL";
	public static final String TASK_GET_INVENTORY = "TASK_GET_INVENTORY";
	public static final String TASK_GET_MERCHANT_ACCESS = "TASK_GET_MERCHANT_ACCESS";
	public static final String TASK_GET_USER_ACCESS = "TASK_GET_USER_ACCESS";
	public static final String TASK_UPDATE_USER_ACCESS = "TASK_UPDATE_USER_ACCESS";
	
	public static final String TASK_COMPLETED = "TASK_COMPLETED";
}
