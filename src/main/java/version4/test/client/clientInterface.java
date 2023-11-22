package version4.test.client;

import version4.test.common.RequestOfV4Test;
import version4.test.common.ResponseOfV4Test;

public interface clientInterface {
    ResponseOfV4Test sendRequest(RequestOfV4Test request) throws InterruptedException;
}
