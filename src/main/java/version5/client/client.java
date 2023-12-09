package version5.client;

import version5.common.request;
import version5.common.response;

public interface client {
    response sendRequest(request request);
}
