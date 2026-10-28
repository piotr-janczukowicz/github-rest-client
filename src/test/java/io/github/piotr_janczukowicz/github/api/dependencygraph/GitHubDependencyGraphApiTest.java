package io.github.piotr_janczukowicz.github.api.dependencygraph;

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
class GitHubDependencyGraphApiTest {

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubDependencyGraphApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void dependencyReview_shouldReturnDependencyReviewApi_whenCalled() {
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubDependencyGraphApi api = new GitHubDependencyGraphApi(TestClients.caller(client));

            final GitHubDependencyGraphDependencyReviewApi dependencyReview = api.dependencyReview();

            assertThat(dependencyReview).isNotNull();
        }
    }

    @Test
    void dependencySubmission_shouldReturnDependencySubmissionApi_whenCalled() {
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubDependencyGraphApi api = new GitHubDependencyGraphApi(TestClients.caller(client));

            final GitHubDependencyGraphDependencySubmissionApi dependencySubmission = api.dependencySubmission();

            assertThat(dependencySubmission).isNotNull();
        }
    }

    @Test
    void sboms_shouldReturnSbomsApi_whenCalled() {
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubDependencyGraphApi api = new GitHubDependencyGraphApi(TestClients.caller(client));

            final GitHubDependencyGraphSbomsApi sboms = api.sboms();

            assertThat(sboms).isNotNull();
        }
    }

    @Test
    void licenseCompliance_shouldReturnApi_whenConstructed() {
        final GitHubDependencyGraphApi api;
        try (final GitHubClient client = TestClients.client(transport)) {
            api = new GitHubDependencyGraphApi(TestClients.caller(client));
        }

        final GitHubDependencyGraphLicenseComplianceApi licenseCompliance = api.licenseCompliance();

        assertThat(licenseCompliance).isNotNull();
    }
}
