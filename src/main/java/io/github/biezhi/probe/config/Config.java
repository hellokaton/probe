package io.github.biezhi.probe.config;

/**
 * 配置
 * Created by biezhi on 05/07/2017.
 */
public class Config {

    private boolean auth =true;

    private boolean logging = true;

    // 服务端绑定端口
    private int port = 10779;

    // 代理类型，默认socks5
    private String proxyType = "socks5";

    private String method = "";

    private String username = "";

    private String password = "";

    public static Config of(){
        return new Config();
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getProxyType() {
        return proxyType;
    }

    public void setProxyType(String proxyType) {
        this.proxyType = proxyType;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public boolean isLogging() {
        return logging;
    }

    public void setLogging(boolean logging) {
        this.logging = logging;
    }
}
