package version3.nettyTest.Cclient;

import version3.nettyTest.common.request;
import version3.nettyTest.common.response;

public interface RPCClient {
    response sendRequest(request Req);
}
