package io.github.piotr_janczukowicz.github.api.gists;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGistsApiTest {

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubGistsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void gist_shouldReturnGistApi_whenCalled() {
        final GitHubGistsApi api = new GitHubGistsApi(TestClients.caller(transport));

        assertThat(api.gist()).isInstanceOf(GitHubGistApi.class);
    }

    @Test
    void comments_shouldReturnCommentsApi_whenCalled() {
        final GitHubGistsApi api = new GitHubGistsApi(TestClients.caller(transport));

        assertThat(api.comments()).isInstanceOf(GitHubGistsCommentsApi.class);
    }
}
