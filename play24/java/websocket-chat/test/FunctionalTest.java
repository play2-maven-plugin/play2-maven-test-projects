import org.junit.Test;

import play.mvc.Result;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;


public class FunctionalTest {
    @Test
    public void sendJavaScript() {
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                Result result = route(controllers.routes.Application.chatRoomJs("'"));
                assertThat(result.status()).isEqualTo(OK);
                assertThat(result.contentType()).isEqualTo("text/javascript");
            }
        });
    }

    @Test
    public void resistToXSS() {
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                Result result = route(controllers.routes.Application.chatRoomJs("'"));
                assertThat(contentAsString(result)).contains("if(data.user == '\\'')");
            }
        });
    }
}
