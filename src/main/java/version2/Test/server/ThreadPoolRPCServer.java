package version2.Test.server;

import version2.Test.server.workThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolRPCServer implements RPCServer{
    private ThreadPoolExecutor threadPool;
    private interfaceProvider provider;
    public ThreadPoolRPCServer(interfaceProvider provide){
        threadPool = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
                1000,60, TimeUnit.SECONDS,new ArrayBlockingQueue<>(100));
        this.provider = provide;
    }

    // 自定义构造函数
    public ThreadPoolRPCServer(interfaceProvider serviceProvider, int corePoolSize,
                                  int maximumPoolSize,
                                  long keepAliveTime,
                                  TimeUnit unit,
                                  BlockingQueue<Runnable> workQueue){

        threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        this.provider = serviceProvider;
    }
    @Override
    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("基于线程池的服务器启动了");
            while (true){
                Socket socket = serverSocket.accept();
                //在线程池中拿出一个线程用来处理当前的request请求
                threadPool.execute(new workThread(provider,socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("基于线程池的服务器启动失败！");
        }
    }

    @Override
    public void stop() {

    }
}
