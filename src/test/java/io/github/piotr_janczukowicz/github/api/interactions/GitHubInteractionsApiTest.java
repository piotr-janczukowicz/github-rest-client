package io.github.piotr_janczukowicz.github.api.interactions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.client.GitHubClient;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubInteractionsApiTest {

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubInteractionsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void orgs_shouldReturnApi_whenConstructed() {
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubInteractionsApi api = new GitHubInteractionsApi(TestClients.caller(client));

            assertThat(api.orgs()).isNotNull();
        }
    }

    @Test
    void repos_shouldReturnApi_whenConstructed() {
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubInteractionsApi api = new GitHubInteractionsApi(TestClients.caller(client));

            assertThat(api.repos()).isNotNull();
        }
    }

    @Test
    void user_shouldReturnApi_whenConstructed() {
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubInteractionsApi api = new GitHubInteractionsApi(TestClients.caller(client));

            assertThat(api.user()).isNotNull();
        }
    }
}
