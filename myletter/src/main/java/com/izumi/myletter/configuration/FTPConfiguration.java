package com.izumi.myletter.configuration;

import com.izumi.myletter.common.FTPUtils;
import lombok.Data;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.tomcat.util.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.apache.commons.net.ftp.FTPClient;


import javax.annotation.PreDestroy;


/**
* 自定义对象池连接FTP
* */
@Configuration
//判断有没有括号里面的类，如果有，才加载FTPConfiguration
@ConditionalOnClass({GenericObjectPool.class, FTPClient.class})
@ConditionalOnProperty(value = "ftp.enabled", havingValue = "true")
//指定类装载配置文件，一起使用
@EnableConfigurationProperties(FTPConfiguration.FtpConfigProperties.class)
public class FTPConfiguration {

    //定义日志对象
    private  final static Logger logger = LoggerFactory.getLogger(FTPConfiguration.class);

    //定义FTPClient连接池
    private ObjectPool<FTPClient> pool;

    public FTPConfiguration(FtpConfigProperties temp){
        // 默认最大连接数与最大空闲连接数都为8，最小空闲连接数为0
        // 其他未设置属性使用默认值，可根据需要添加相关配置
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        //部分通用对象池配置
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setMinEvictableIdleTimeMillis(60000);
        poolConfig.setSoftMinEvictableIdleTimeMillis(50000);
        poolConfig.setTimeBetweenEvictionRunsMillis(30000);
        pool = new GenericObjectPool<>(new FtpClientPooledObjectFactory(temp), poolConfig);
        preLoadingFtpClient(temp.getInitialSize(), poolConfig.getMaxIdle());
        // 初始化ftp工具类中的ftpClientPool
        FTPUtils.init(pool);
    }

    /**
     * 预先加载FTPClient连接到对象池中
     */
    private void preLoadingFtpClient(Integer initialSize, int maxIdle) {
        if (initialSize == null || initialSize <= 0) {
            return;
        }
        int size = Math.min(initialSize.intValue(), maxIdle);
        for (int i = 0; i < size; i++) {
            try {
                pool.addObject();
            } catch (Exception e) {
                logger.error("preLoadingFtpClient error...", e);
            }
        }
    }

    //在服务器卸载servlet的时候执行一次，并且只执行这一次
    @PreDestroy
    public void destroy() {
        if (pool != null) {
            pool.close();
            logger.info("销毁ftpClientPool...");
        }
    }

    /**
     * Ftp配置属性类，建立ftpClient时使用
     */
    @Data
    @ConfigurationProperties(prefix = "ftp")
    static class FtpConfigProperties {

        private String ip;

        private int port;

        private String username;

        public String getIp() {
            return ip;
        }

        public int getPort() {
            return port;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public int getBufferSize() {
            return bufferSize;
        }

        public Integer getInitialSize() {
            return initialSize;
        }

        private String password;

        private int bufferSize = 8096;

        /**
         * 初始化连接数
         */
        private Integer initialSize = 0;


        }

    /**
     * FtpClient对象工厂类
     */
    static class FtpClientPooledObjectFactory implements PooledObjectFactory<FTPClient> {

        private FtpConfigProperties props;

        public FtpClientPooledObjectFactory(FtpConfigProperties props) {
            this.props = props;
        }

        @Override
        public PooledObject<FTPClient> makeObject() throws Exception {
            FTPClient ftpClient = new FTPClient();
            try {
                ftpClient.connect(props.getIp(), props.getPort());
                ftpClient.login(props.getUsername(), props.getPassword());
                logger.info("连接FTP服务器返回码{}", ftpClient.getReplyCode());
                ftpClient.setBufferSize(props.getBufferSize());
            /*    ftpClient.setControlEncoding(props.getEncoding());*/
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();
                return new DefaultPooledObject<>(ftpClient);
            } catch (Exception e) {
                logger.error("建立FTP连接失败", e);
                if (ftpClient.isAvailable()) {
                    ftpClient.disconnect();
                }
                ftpClient = null;
                throw new Exception("建立FTP连接失败", e);
            }
        }

        @Override
        public void destroyObject(PooledObject<FTPClient> p) throws Exception {
            FTPClient ftpClient = getObject(p);
            if (ftpClient != null && ftpClient.isConnected()) {
                ftpClient.disconnect();
            }
        }

        @Override
        public boolean validateObject(PooledObject<FTPClient> p) {
            FTPClient ftpClient = getObject(p);
            if (ftpClient == null || !ftpClient.isConnected()) {
                return false;
            }
            try {
                ftpClient.changeWorkingDirectory("/");
                return true;
            } catch (Exception e) {
               /* logger.error("验证FTP连接失败::{}", ExceptionUtils.getStackTrace(e));*/
                return false;
            }
        }

        @Override
        public void activateObject(PooledObject<FTPClient> p) throws Exception {
        }

        @Override
        public void passivateObject(PooledObject<FTPClient> p) throws Exception {
        }

        private FTPClient getObject(PooledObject<FTPClient> p) {
            if (p == null || p.getObject() == null) {
                return null;
            }
            return p.getObject();
        }

    }

}