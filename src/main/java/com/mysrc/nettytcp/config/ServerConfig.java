package com.mysrc.nettytcp.config;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mysrc.nettytcp.server.ChrLengthFieldChannelInitializer;
import com.mysrc.nettytcp.server.SocketServerImpl;

import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;

@Configuration
public class ServerConfig {

	@Value("${socket.server.port}")
	private int port;

	@Value("${socket.server.charset}")
	private String chaset;
	
	@Value("${socket.server.bizThreadSize:10}")
    private String bizThreadSize;

	/**
	 * 服务器启动就会启动TcpServer
	 * @return
	 */
	@Bean(initMethod = "run", destroyMethod = "shutdown")
	public SocketServerImpl socketServer() {

		SocketServerImpl socketServer = new SocketServerImpl(port);
		socketServer.setBossGroup(new NioEventLoopGroup(2));
		socketServer.setWorkerGroup(new NioEventLoopGroup(Integer.parseInt(bizThreadSize)));
		socketServer.setChannelOptions(channelOptions());
		socketServer.setChannelInitalizer(channelInitalizer());
		return socketServer;
	}

	@Bean
	public Map<ChannelOption<?>, Object> channelOptions() {
		Map<ChannelOption<?>, Object> map = new HashMap<>();
		map.put(ChannelOption.SO_REUSEADDR, true);
		map.put(ChannelOption.SO_KEEPALIVE, true);
		map.put(ChannelOption.TCP_NODELAY, true);
		return map;
	}

	@Bean
	public ChrLengthFieldChannelInitializer channelInitalizer() {
		ChrLengthFieldChannelInitializer channelInitializer = new ChrLengthFieldChannelInitializer();
		channelInitializer.setTimeout(1000);
		channelInitializer.setCharset(Charset.forName(chaset));
		channelInitializer.setFailFast(true);
		return channelInitializer;
	}

}
