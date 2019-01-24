package com.mysrc.nettytcp.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class TcpServerHandler extends SimpleChannelInboundHandler<String> {

    private static Logger logger = LoggerFactory.getLogger(TcpServerHandler.class);

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        logger.info("Thread is : " + Thread.currentThread());
        logger.info("SERVER接收到消息:{}", msg);
        ctx.writeAndFlush("yes, server is accepted you ,nice !" + msg);
//        Thread.sleep(500);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        logger.warn("Unexpected exception from downstream.", cause);
        ctx.close();
    }

}
