package io.github.piotr_janczukowicz.github.api.emojis;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.net.URI;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubEmojisApiTest {

    private static final String EMOJIS_JSON =
            "{\"+1\":\"https://example.test/1f44d.png\",\"100\":\"https://example.test/1f4af.png\"}";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubEmojisApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void get_shouldReturnEmojis_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), EMOJIS_JSON));

        final Map<String, String> emojis = api().get();
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(emojis).isEqualTo(EmojisCatalogFixtures.emojis());
            softly.assertThat(requestUri).hasToString("https://api.github.com/emojis");
        });
    }

    @Test
    void get_shouldThrow_whenResponseIsNotModified() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_MODIFIED.code(), null));

        final GitHubEmojisApi api = api();

        final Throwable thrown = catchThrowable(api::get);

        assertThat(thrown).isInstanceOf(GitHubApiException.class);
    }

    @Test
    void get_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final GitHubEmojisApi api = api();

        final Throwable thrown = catchThrowable(api::get);

        assertThat(thrown).isInstanceOf(GitHubApiException.class);
    }

    private GitHubEmojisApi api() {
        return new GitHubEmojisApi(TestClients.caller(transport));
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
