package version5.register;

import java.net.InetSocketAddress;

public interface serviceRegister {
    void register(String serviceName, InetSocketAddress address);
    InetSocketAddress serviceDiscovery(String serviceName);
}
