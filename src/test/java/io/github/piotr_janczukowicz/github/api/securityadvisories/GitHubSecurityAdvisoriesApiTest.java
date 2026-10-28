package io.github.piotr_janczukowicz.github.api.securityadvisories;

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
class GitHubSecurityAdvisoriesApiTest {

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubSecurityAdvisoriesApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void globalAdvisories_shouldReturnGlobalAdvisoriesApi_whenCalled() {
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubSecurityAdvisoriesApi api = new GitHubSecurityAdvisoriesApi(TestClients.caller(client));

            final GitHubSecurityAdvisoriesGlobalAdvisoriesApi globalAdvisories = api.globalAdvisories();

            assertThat(globalAdvisories).isNotNull();
        }
    }

    @Test
    void repositoryAdvisories_shouldReturnRepositoryAdvisoriesApi_whenCalled() {
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubSecurityAdvisoriesApi api = new GitHubSecurityAdvisoriesApi(TestClients.caller(client));

            final GitHubSecurityAdvisoriesRepositoryAdvisoriesApi repositoryAdvisories = api.repositoryAdvisories();

            assertThat(repositoryAdvisories).isNotNull();
        }
    }
}
