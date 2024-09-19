package com.zyf.cola.statemachine.demo.config;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class SystemStartupPrint implements CommandLineRunner {

    @Value("${spring.application.name:statemachine}")
    private String appName;

    @Value("${server.port:8080}")
    private String port;

    @Value("${server.servlet.context-path:}")
    private String contextPath;

    @Value("${spring.profiles.active:default}")
    private String env;
    @Override
    public void run(String... args) throws Exception {
        log.info("启动完毕...");

        ThreadUtil.execute(() -> {
            List<String> ipList = getIps();

            final StringBuilder stringBuilder = new StringBuilder(
                    "\n------------- " + appName + " (" + env + ") 启动成功 --by " + DateUtil.now() + " -------------\n");
            stringBuilder.append("\t主页访问: \n");
            stringBuilder.append("\t\t- 访问: http://")
                    .append("localhost")
                    .append(":")
                    .append(port);
            if (contextPath != null && !contextPath.isEmpty()) {
                stringBuilder.append(contextPath);
            }
            stringBuilder.append("\n");
            for (String ip : ipList) {
                stringBuilder.append("\t\t- 访问: http://")
                        .append(ip)
                        .append(":")
                        .append(port);
                if (contextPath != null && !contextPath.isEmpty()) {
                    stringBuilder.append(contextPath);
                }
                stringBuilder.append("\n");
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println(stringBuilder);
        });
    }

    private static List<String> getIps() {
        return new ArrayList<>(NetUtil.localIpv4s());
    }
}
