package com.lingecho.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 用户服务
 */
@Slf4j
@SpringBootApplication
@EnableFeignClients
public class UserServiceApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(UserServiceApplication.class, args);
        printStartupInfo(context);
    }

    /**
     * 打印启动完成信息
     */

    private static void printStartupInfo(ApplicationContext context) {
        try {
            Environment env = context.getEnvironment();
            String applicationName = env.getProperty("soulnexus.name");
            String port = env.getProperty("server.port", "8080");
            String path = env.getProperty("server.servlet.context-path", "");
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            log.info("----------------------------------------------------------");
            log.info("{} 启动成功！", applicationName);
            log.info("应用名称：{}", applicationName);
            log.info("作者：{}", env.getProperty("soulnexus.copyright.owner"));
            log.info("应用起始年份：{}", env.getProperty("soulnexus.copyright.since-year"));
            log.info("应用许可证：{}", env.getProperty("soulnexus.copyright.license"));
            log.info("本地访问：https://{}:{}{}", hostAddress, port, path);
            log.info("外部访问：https://{}:{}{}", hostAddress, port, path);
            log.info("Swagger UI：https://{}:{}{}/doc.html", hostAddress, port, path);
            log.info("Actuator：https://{}:{}{}/actuator", hostAddress, port, path);
            log.info("----------------------------------------------------------");
        } catch (UnknownHostException e) {
            log.warn("无法确定主机地址", e);
        }
    }
}

