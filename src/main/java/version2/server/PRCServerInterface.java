package version2.server;

//对服务器的抽象  用于实现不同方式的服务器运行方式
public interface PRCServerInterface {
    void start(int port);
    void stop();
}
