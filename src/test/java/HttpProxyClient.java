import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;

public class HttpProxyClient {

    public static void main(String[] args) throws Exception {
        final String user = "probe";
        final String password = "probe";

        Proxy proxyTest = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 10779));

        java.net.Authenticator.setDefault(new java.net.Authenticator() {
            private PasswordAuthentication authentication = new PasswordAuthentication(user, password.toCharArray());

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return authentication;
            }
        });

        OkHttpClient client = new OkHttpClient.Builder().proxy(proxyTest).build();
        Request request = new Request.Builder().url("https://www.baidu.com").build();
        Response response = client.newCall(request).execute();
        System.out.println("状态码: " + response.code());
        System.out.println("响应内容: \n" + response.body().string());

        client.dispatcher().executorService().shutdown();
        client.connectionPool().evictAll();
    }

}
