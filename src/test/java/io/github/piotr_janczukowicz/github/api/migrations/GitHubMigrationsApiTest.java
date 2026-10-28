package io.github.piotr_janczukowicz.github.api.migrations;

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
class GitHubMigrationsApiTest {

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubMigrationsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void orgs_shouldReturnOrgsApi_whenCalled() {
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubMigrationsApi api = new GitHubMigrationsApi(TestClients.caller(client));

            final GitHubMigrationsOrgsApi orgs = api.orgs();

            assertThat(orgs).isNotNull();
        }
    }

    @Test
    void sourceImports_shouldReturnSourceImportsApi_whenCalled() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubMigrationsApi api = new GitHubMigrationsApi(TestClients.caller(client));

            final GitHubMigrationsSourceImportsApi sourceImports = api.sourceImports();

            assertThat(sourceImports).isNotNull();
        }
    }

    @Test
    void users_shouldReturnUsersApi_whenCalled() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubMigrationsApi api = new GitHubMigrationsApi(TestClients.caller(client));

            final GitHubMigrationsUsersApi users = api.users();

            assertThat(users).isNotNull();
        }
    }
}
