package com.wechat.sys;

import com.wechat.util.PathUtil;
import com.wechat.util.PropertiesUtil;

/**
 * Created by zhusen on 2017/1/4.
 */
public class CommonConstant {
    public static final String ENCRYPT_KEY = "e-edusky_20161230";

    public static String weixinappid;
    public static String weixinSecret;
    public static String weixinpartnerid;
    public static String weixinpartnerKey;
    public static String unifiedorderUrl;
    public static String notify_url;
    public static String FILE_UPLOAD_BASE_URL;
    public static String IMG_BASE_URL;


    static {
        PropertiesUtil properties = new PropertiesUtil(PathUtil.getClassPath() + "/common.properties");
        weixinappid = properties.getString("weixinappid");
        weixinSecret = properties.getString("weixinSecret");
        weixinpartnerid = properties.getString("weixinpartnerid");
        weixinpartnerKey = properties.getString("weixinpartnerKey");
        unifiedorderUrl = properties.getString("unifiedorderUrl");
        notify_url = properties.getString("notify_url");
        FILE_UPLOAD_BASE_URL = properties.getString("file_upload_base_url");
        IMG_BASE_URL = properties.getString("img_base_url");
    }

}
