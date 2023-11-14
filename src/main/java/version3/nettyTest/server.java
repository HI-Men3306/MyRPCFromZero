package version3.nettyTest;

public class server {
    public static void main(String[] args) throws InterruptedException {
        /*//创建EventLoopGroup 作为主从轮询池
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup work = new NioEventLoopGroup(1);
        //创建一个额外的线程池用来处理耗时操作   当处理耗时操作时，每次都会从线程池中拿一个新的线程来处理不同的事件
        EventLoopGroup handle = new DefaultEventLoopGroup();
        //创建服务端引导类
        ServerBootstrap bootstrap = new ServerBootstrap();
        //对服务端的引导类进行配置
        System.out.println(Thread.currentThread().getName() + "服务器开始运行！");
        bootstrap.group(boss,work)
                .channel(NioServerSocketChannel.class)//连接中的缓冲区的类型
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        //配置缓冲区的处理器pipeline
                        socketChannel.pipeline()
                                //.addLast(new LengthFieldBasedFrameDecoder(1024, 0, 1))
                                .addLast(new StringDecoder())
                                .addLast(new ChannelInboundHandlerAdapter(){
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        System.out.println(Thread.currentThread().getName() + " 服务器收到的数据为" + msg);
                                        handle.submit(()->{
                                            System.out.println(Thread.currentThread().getName() + " 异步回复");
                                            ctx.channel().writeAndFlush("客户端接收成功！");
                                        });
                                    }
                                })
                                .addLast(new StringEncoder());
                    }
                });
        //启动类绑定本地端口
        ChannelFuture channelFuture = bootstrap.bind(8080);*/

        String s1 = "a";
        String s4 = s1;
        String s5 = "a" + "b";
        String s6 = "ab";
        System.out.println(s5 == s6);
        System.out.println(s1 == s4);

    }
}
