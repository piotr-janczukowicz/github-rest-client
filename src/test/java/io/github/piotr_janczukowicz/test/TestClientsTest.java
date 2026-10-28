package io.github.piotr_janczukowicz.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.users.GitHubUsersEmailsApi;
import io.github.piotr_janczukowicz.github.client.GitHubClient;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.client.HttpClientConfiguration;
import io.github.piotr_janczukowicz.http.client.authentication.Authentication;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.http.HttpResponses;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.lang.reflect.Constructor;
import java.net.URI;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class TestClientsTest {

    private static final URI BASE = URI.create("https://api.github.com/");

    @Test
    void configuration_shouldSetBaseUriAndToken_whenCalled() {
        final HttpClientConfiguration config = TestClients.configuration(BASE);

        assertSoftly(softly -> {
            softly.assertThat(config.baseUri()).isEqualTo(BASE);
            softly.assertThat(TestClients.auth().authentication().map(Authentication::headerValue))
                    .contains("Bearer " + TestClients.TOKEN);
            softly.assertThat(TestClients.perPageQuery()).isEqualTo("per_page=" + 100);
        });
    }

    @Test
    void client_shouldUseConfiguration_whenTransportProvided() {
        final HttpTransport transport = HttpTransportMocks.mock();

        try (final GitHubClient client = TestClients.client(BASE, transport)) {
            assertThat(client).isNotNull();
        }
    }

    @Test
    void client_shouldUseGithubBase_whenOnlyTransportProvided() throws Exception {
        final HttpTransport transport = HttpTransportMocks.mock();

        when(transport.execute(any())).thenReturn(HttpResponses.response(204, null));

        try (final GitHubClient client = TestClients.client(transport)) {
            new GitHubUsersEmailsApi(TestClients.caller(client)).deleteEmail("octocat@github.com");
        }

        final ArgumentCaptor<HttpRequest> request = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(request.capture());
        assertSoftly(softly -> {
            softly.assertThat(TestClients.GITHUB_BASE).isEqualTo(BASE);
            softly.assertThat(request.getValue().uri().toString()).startsWith(TestClients.GITHUB_BASE.toString());
        });
    }

    @Test
    void constructor_shouldRejectInstantiation_whenCalledViaReflection() throws Exception {
        final Constructor<TestClients> constructor = TestClients.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        final Throwable thrown = catchThrowable(constructor::newInstance);

        assertThat(thrown).hasCauseInstanceOf(UnsupportedOperationException.class);
    }
}
