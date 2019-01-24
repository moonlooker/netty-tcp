package com.mysrc.nettytcp.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * NettyTcp客户端程序
 * @author LL
 *
 */
public class NettyClient {

    private static Logger logger = LoggerFactory.getLogger(NettyClient.class);

    public static String HOST = "127.0.0.1";
    public static int PORT = 9999;
    private static int gourp_size = Runtime.getRuntime().availableProcessors();
//    private static int thread_size = 10;
    public static int connect_time_out = 5000;
    private static EventLoopGroup group = new NioEventLoopGroup(gourp_size*2);
    private  static Bootstrap bootstrap = getBootstrap();

    /**
     * 初始化Bootstrap
     * @return
     */
    private static final Bootstrap getBootstrap() {

        Bootstrap b = new Bootstrap();
        b.group(group).channel(NioSocketChannel.class);
        b.handler(new TcpClientChannelInitializer());
        b.option(ChannelOption.SO_KEEPALIVE, true);
        b.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connect_time_out);
        return b;
    }

    private static final Channel getChannel(String host, int port) {

        Channel channel = null;
        try {
            channel = bootstrap.connect(host, port).sync().channel();
        } catch (Exception e) {
            logger.error("连接Server(IP[{}],PORT[{}])失败", host, port, e);
            return null;
        }
        return channel;
    }

    public static void sendMsg(String msg) throws Exception {

        Channel channel = getChannel(HOST, PORT);
        if (channel != null) {
            channel.writeAndFlush(msg).sync();
        } else {
            logger.warn("消息发送失败,连接尚未建立!");
        }
    }

    public static void shutdown() {

        group.shutdownGracefully();
    }

    public static void main(String[] args) throws Exception {

        try {
            long t0 = System.nanoTime();
            //            int i = 1;NettyClient.sendMsg("你好");
            for (int i = 0; i < 1000; i++) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        try {
                            NettyClient.sendMsg(System.nanoTime() + "-你好啊阿三地方了;枯萎哦人;了卡桑德拉废话;了好几位人家;卢卡斯地方;来看哈顺利打开附件;了卡死的;发来看哈撒;灯笼裤飞机;阿里山扩大废话;拉克丝的发挥;拉克丝的发挥;拉克丝的发挥;拉萨扩大回复;拉克丝的发挥;拉萨扩大废话;啊拉萨扩大回复;啊拉克丝的发挥;啊拉萨扩大回复;啊昆仑山搭街坊;了卡折;人了客户;啊了卡死的回复;阿里山扩大废话++++++++++++=++++");
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                }).run();;

            }
            long t1 = System.nanoTime();
            System.out.println((t1 - t0) / 1000000.0);
            NettyClient.shutdown();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("", e);
            NettyClient.shutdown();
        }
    }
}
