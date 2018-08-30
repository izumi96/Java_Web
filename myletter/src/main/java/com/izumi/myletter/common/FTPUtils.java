package com.izumi.myletter.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@ConfigurationProperties(prefix = "ftp")
public class FTPUtils {,

    private static int downloadSleep;

    private static int downloadRetry;

    private static int uploadSleep;

    private static int uploadRetry;

    public static boolean doUpLoad(String ftpPath,File file){

    }
}
