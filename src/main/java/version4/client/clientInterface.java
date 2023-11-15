package version4.client;

import version4.common.RPCRequest;
import version4.common.RPCResponse;

public interface clientInterface {
    RPCResponse sendRequest(RPCRequest request);
}
