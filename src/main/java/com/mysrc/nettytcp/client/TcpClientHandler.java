package com.mysrc.nettytcp.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class TcpClientHandler extends SimpleChannelInboundHandler<Object>{

    private static Logger logger = LoggerFactory.getLogger(TcpClientHandler.class);
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        
        logger.info("******Thread is : " + Thread.currentThread());
        logger.info("******Server say : " + msg);
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        logger.warn("Unexpected exception from downstream.", cause);
        ctx.close();
    }

}
