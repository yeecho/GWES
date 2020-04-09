package com.yuanye.gwes.Constant;

public class YC {
    public static final String VERSION_FILE_REMOTE = "http://yeecho.qicp.vip/GWES/code/version_code.txt";
    public static final String APK_FILE_REMOTE = "http://yeecho.qicp.vip/GWES/code/GWES.apk";

    public static final String REGEX_USERNAME = "^[a-zA-Z0-9_-]{4,16}$";
    public static final String REGEX_PASSWORD_HIGN = "^.*(?=.{6,})(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*? ]).*$";
    public static final String REGEX_PASSWORD_NORMAL = "^.*(?=.{6,})(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*? ]).*$";
    public static final String REGEX_PASSWORD_LOW = "^[a-zA-Z0-9_-]{4,16}$";
    public static final String REGEX_EMAIL = "^([A-Za-z0-9_\\-\\.])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,4})$";
    public static final String REGEX_PHONE = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$";
    public static final String REGEX_ID_CARD = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";

}
