package version3.client;

import version3.common.RPCRequest;
import version3.common.RPCResponse;

public interface RPCClient {
    RPCResponse sendRequest(RPCRequest request);
}
