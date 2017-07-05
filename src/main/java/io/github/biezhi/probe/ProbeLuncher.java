package io.github.biezhi.probe;

import com.alibaba.fastjson.JSON;
import io.github.biezhi.probe.config.Config;
import io.github.biezhi.probe.proxy.ProxyServer;
import io.github.biezhi.probe.proxy.Socks5ProxyServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 代理启动
 * <p>
 * Created by biezhi on 05/07/2017.
 */
public class ProbeLuncher {

    private static final Logger log = LoggerFactory.getLogger(ProbeLuncher.class);

    public static void main(String[] args) throws Exception {
        Config config = parseConfig(args);
        ProxyServer proxyServer = getProxyServer(config.getProxyType());
        proxyServer.start(config);
    }

    /**
     * 获取代理类型
     *
     * @param proxyType
     * @return
     */
    private static ProxyServer getProxyServer(String proxyType) {
        if ("socks5".equalsIgnoreCase(proxyType)) {
            return new Socks5ProxyServer();
        }
        if ("http".equalsIgnoreCase(proxyType)) {

        }
        return null;
    }

    /**
     * 解析参数
     *
     * @param args
     * @return
     * @throws FileNotFoundException
     */
    private static Config parseConfig(String[] args) throws FileNotFoundException {
        Config config = Config.of();
        if (null != args && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                if (args[i].startsWith("--config=")) {
                    String[] conf = args[i].split("=");
                    String content = new Scanner(new File(conf[1])).useDelimiter("\\Z").next();
                    log.info("Probe config: \n{}", content);
                    config = JSON.parseObject(content, Config.class);
                    break;
                }
            }
        }
        return config;
    }
}
