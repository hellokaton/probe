package io.github.biezhi.probe.socks5;

import io.github.biezhi.probe.config.Config;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.socksx.v5.Socks5CommandRequestDecoder;
import io.netty.handler.codec.socksx.v5.Socks5InitialRequestDecoder;
import io.netty.handler.codec.socksx.v5.Socks5PasswordAuthRequestDecoder;
import io.netty.handler.codec.socksx.v5.Socks5ServerEncoder;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * Created by biezhi on 05/07/2017.
 */
public class SocksServerInitializer extends ChannelInitializer<SocketChannel> {

    private Config config;

    public SocksServerInitializer(Config config) {
        this.config = config;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //流量统计
        ch.pipeline().addLast(
                ProxyChannelTrafficShapingHandler.PROXY_TRAFFIC,
                new ProxyChannelTrafficShapingHandler(3000)
        );
        //channel超时处理
        ch.pipeline().addLast(new IdleStateHandler(3, 30, 0));
        ch.pipeline().addLast(new ProxyIdleHandler());
        //netty日志
        if (config.isLogging()) {
            ch.pipeline().addLast(new LoggingHandler());
        }
        ch.pipeline().addLast(Socks5ServerEncoder.DEFAULT);
        //sock5 init decode
        ch.pipeline().addLast(new Socks5InitialRequestDecoder());
        //sock5 init handler
        ch.pipeline().addLast(new Socks5InitialRequestHandler(config));
        if (config.isAuth()) {
            //socks auth
            ch.pipeline().addLast(new Socks5PasswordAuthRequestDecoder());
            //socks auth
            ch.pipeline().addLast(new Socks5PasswordAuthRequestHandler(config));
        }
        //socks connection
        ch.pipeline().addLast(new Socks5CommandRequestDecoder());
        //Socks connection
        ch.pipeline().addLast(new Socks5CommandRequestHandler());
    }
}
