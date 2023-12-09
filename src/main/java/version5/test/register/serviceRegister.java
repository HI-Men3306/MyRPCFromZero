package version5.test.register;

import java.net.InetSocketAddress;

public interface serviceRegister {
    void register(String serviceName, InetSocketAddress address);
    InetSocketAddress serviceDiscovery(String address);
}
