package version2.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//使用线程池管理 处理request的线程
public class ThreadPoolRPCServer implements PRCServerInterface{
    private final ThreadPoolExecutor threadPool;
    private serviceInterfaceProvider provider;

    //默认构造
    public ThreadPoolRPCServer(serviceInterfaceProvider provide){
        //参数详解:  获取当前可用的最大处理器数量作为线程池的线程数   回收线程的最大等待时间   阻塞的最大等待时间   单位为秒   大小为100的阻塞队列
        this.threadPool = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
                1000, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));
        this.provider = provide;
    }

    // 自定义构造函数
    public ThreadPoolRPCServer(serviceInterfaceProvider serviceProvider, int corePoolSize,
                                  int maximumPoolSize,
                                  long keepAliveTime,
                                  TimeUnit unit,
                                  BlockingQueue<Runnable> workQueue){

        threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        this.provider = serviceProvider;
    }
    @Override
    public void start(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("基于线程池的服务器启动了");
            while (true){
                Socket socket = serverSocket.accept();
                threadPool.execute(new workThread(socket,provider));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("服务器启动失败！");
        }
    }

    @Override
    public void stop() {

    }
}
