package version3.server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class threadPoolServer implements RPCServerInterface{
    //服务对象实例
    private serviceInstanceProvider provider;
    //线程池
    private ThreadPoolExecutor threadPool;

    public threadPoolServer(serviceInstanceProvider provider) {
        this.provider = provider;
        threadPool = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), 1000,
                60, TimeUnit.SECONDS,new ArrayBlockingQueue<>(100));
    }

    // 自定义构造函数
    public threadPoolServer(serviceInstanceProvider serviceProvider, int corePoolSize,
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
            System.out.println("基于线程池的服务器启动成功！");
            while (true) {
                Socket socket = serverSocket.accept();
                //使用线程池中的线程来处理request
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
