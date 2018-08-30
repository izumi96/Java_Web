package com.izumi.myletter.common;

/**
 * 常量
 */
public class Constants {

    public static final Integer RESULT_STATUS_SUCCESS = 0; //返回状态 成功
    public static final Integer RESULT_STATUS_FAIL = 1; //返回状态 失败
    public static final Integer RESULT_STATUS_ERROR = 2; //返回状态 程序出错
    public static final Integer RESULT_FILE_NULL = 3;//文件为空

    //返回说明
    public static final String RESULT_MESSAGE_SUCCESS = "操作成功！";
    public static final String RESULT_MESSAGE_FAIL = "操作失败！";
    public static final String RESULT_MESSAGE_ERROR = "程序出错！";
    public static final String RESULT_MESSAGE_FILENULL="文件为空";



    //七牛云相关常量
    //两个key
    public static final String ACCESS_KEY = "vwpV7QXm248qWEZm8X9zIQ6Xm3wYQ8K9ukKmGfSP";
    public static final String SECRET_KEY = "8H1Du7JBLIhoP2te0ZzB01-85vCNvuCbovR0qtz-";
    //空间名称
    public static final String BUCKETNAME = "izumi";
    //图片上传路径（外联默认域名）
    public static final String DOMAIN = "pe5vfu8ls.bkt.clouddn.com";
    //自定义图片样式
    public static final String STYLE ="imageView2/0/q/75|watermark/2/text/5byg5bel5by65ZWK/font/5a6L5L2T/fontsize/520/fill/I0Y4MDEwMQ==/dissolve/100/gravity/SouthEast/dx/10/dy/10";

}
