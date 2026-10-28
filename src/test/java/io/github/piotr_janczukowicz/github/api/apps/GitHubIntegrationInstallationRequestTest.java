package io.github.piotr_janczukowicz.github.api.apps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubIntegrationInstallationRequestTest {

    @Test
    void constructor_shouldThrow_whenAccountNull() {
        final GitHubIntegrationInstallationRequestBuilder builder = GitHubIntegrationInstallationRequestBuilder.builder(
                        AppsCatalogFixtures.integrationInstallationRequest())
                .account(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("account");
    }

    @Test
    void constructor_shouldThrow_whenRequesterNull() {
        final GitHubIntegrationInstallationRequestBuilder builder = GitHubIntegrationInstallationRequestBuilder.builder(
                        AppsCatalogFixtures.integrationInstallationRequest())
                .requester(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("requester");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final GitHubIntegrationInstallationRequestBuilder builder = GitHubIntegrationInstallationRequestBuilder.builder(
                        AppsCatalogFixtures.integrationInstallationRequest())
                .createdAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }
}
