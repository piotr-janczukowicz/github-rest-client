package io.github.piotr_janczukowicz.github.api.codespaces;

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
class GitHubCodespacesApiTest {

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCodespacesApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void codespace_shouldReturnApi_whenConstructed() {
        final GitHubCodespacesApi api = api();

        final GitHubCodespaceApi codespace = api.codespace();

        assertThat(codespace).isNotNull();
    }

    @Test
    void organizations_shouldReturnApi_whenConstructed() {
        final GitHubCodespacesApi api = api();

        final GitHubCodespacesOrganizationsApi organizations = api.organizations();

        assertThat(organizations).isNotNull();
    }

    @Test
    void organizationSecrets_shouldReturnApi_whenConstructed() {
        final GitHubCodespacesApi api = api();

        final GitHubCodespacesOrganizationSecretsApi organizationSecrets = api.organizationSecrets();

        assertThat(organizationSecrets).isNotNull();
    }

    @Test
    void machines_shouldReturnApi_whenConstructed() {
        final GitHubCodespacesApi api = api();

        final GitHubCodespacesMachinesApi machines = api.machines();

        assertThat(machines).isNotNull();
    }

    @Test
    void repositorySecrets_shouldReturnApi_whenConstructed() {
        final GitHubCodespacesApi api = api();

        final GitHubCodespacesRepositorySecretsApi repositorySecrets = api.repositorySecrets();

        assertThat(repositorySecrets).isNotNull();
    }

    @Test
    void secrets_shouldReturnApi_whenConstructed() {
        final GitHubCodespacesApi api = api();

        final GitHubCodespacesSecretsApi secrets = api.secrets();

        assertThat(secrets).isNotNull();
    }

    private GitHubCodespacesApi api() {
        try (final GitHubClient client = TestClients.client(transport)) {
            return new GitHubCodespacesApi(TestClients.caller(client));
        }
    }
}
