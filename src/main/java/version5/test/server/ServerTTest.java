package version5.test.server;

import version5.server.serverImp.blogServiceImp;
import version5.server.serverImp.userServiceImp;
import version5.service.blogService;
import version5.service.userService;

public class ServerTTest {
    public static void main(String[] args) {
        ServerInterfaceProvider provider = new ServerInterfaceProvider("localhost", 8899);
        userService uService = new userServiceImp();
        blogService bService = new blogServiceImp();
        provider.putInstance(uService);
        provider.putInstance(bService);

        server server = new nettyServer(provider);
        server.start(8899);
    }
}
