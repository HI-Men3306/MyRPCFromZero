package version3.nettyTest.Sserver;

public interface RPCServer {
    void Start(int port) throws InterruptedException;
    void Stop();
}
