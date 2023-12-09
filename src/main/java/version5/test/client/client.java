package version5.test.client;

import version5.common.request;
import version5.common.response;

public interface client {
    response sendRequest(request req);
}
