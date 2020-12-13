package pro.sb2.todoclient.ex;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;
import java.nio.charset.Charset;

public class ToDoErrorHandler extends DefaultResponseErrorHandler {

    private final Logger log = LogManager.getLogger(ToDoErrorHandler.class);

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        log.error(response.getStatusCode().toString());
        log.error(StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
    }
}
