package io.github.biezhi.probe.socks5;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.traffic.ChannelTrafficShapingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProxyChannelTrafficShapingHandler extends ChannelTrafficShapingHandler {

	private static final Logger log = LoggerFactory.getLogger(ProxyChannelTrafficShapingHandler.class);
	
	public static final String PROXY_TRAFFIC = "ProxyChannelTrafficShapingHandler";
	
	private long beginTime;
	
	private long endTime;
	
	private String username = "anonymous";
	
	public static ProxyChannelTrafficShapingHandler get(ChannelHandlerContext ctx) {
		return (ProxyChannelTrafficShapingHandler)ctx.pipeline().get(PROXY_TRAFFIC);
	}
	
	public ProxyChannelTrafficShapingHandler(long checkInterval) {
		super(checkInterval);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		beginTime = System.currentTimeMillis();
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		endTime = System.currentTimeMillis();
		this.log(ctx);
		super.channelInactive(ctx);
	}

	public void log(ChannelHandlerContext ctx) {

		long readByte = this.trafficCounter().cumulativeReadBytes();
		long writeByte = this.trafficCounter().cumulativeWrittenBytes();

		log.info("{}", ctx.channel());
		log.info("Begin: {}, End: {}", this.getBeginTime(), this.getEndTime());
		log.info("Read: {}Byte, Write: {}Byte, Total: {}Byte", readByte, writeByte, (readByte + writeByte));
	}



	public long getBeginTime() {
		return beginTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public static void username(ChannelHandlerContext ctx, String username) {
		get(ctx).username = username;
	}
	
	public String getUsername() {
		return username;
	}

}
