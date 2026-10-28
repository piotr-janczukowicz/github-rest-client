package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubLiveMigrationCreatePayloadTest {

    @Test
    void constructor_shouldThrow_whenSourceOrganizationLoginNull() {
        final GitHubLiveMigrationCreatePayloadBuilder builder = GitHubLiveMigrationCreatePayloadBuilder.builder(
                        EnterpriseAdminCatalogFixtures.liveMigrationCreatePayload())
                .sourceOrganizationLogin(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("sourceOrganizationLogin");
    }

    @Test
    void constructor_shouldThrow_whenSourceRepositoryNameNull() {
        final GitHubLiveMigrationCreatePayloadBuilder builder = GitHubLiveMigrationCreatePayloadBuilder.builder(
                        EnterpriseAdminCatalogFixtures.liveMigrationCreatePayload())
                .sourceRepositoryName(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("sourceRepositoryName");
    }

    @Test
    void constructor_shouldThrow_whenTargetOrganizationLoginNull() {
        final GitHubLiveMigrationCreatePayloadBuilder builder = GitHubLiveMigrationCreatePayloadBuilder.builder(
                        EnterpriseAdminCatalogFixtures.liveMigrationCreatePayload())
                .targetOrganizationLogin(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("targetOrganizationLogin");
    }

    @Test
    void constructor_shouldThrow_whenTargetRepositoryNameNull() {
        final GitHubLiveMigrationCreatePayloadBuilder builder = GitHubLiveMigrationCreatePayloadBuilder.builder(
                        EnterpriseAdminCatalogFixtures.liveMigrationCreatePayload())
                .targetRepositoryName(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("targetRepositoryName");
    }

    @Test
    void constructor_shouldThrow_whenTargetApiEndpointNull() {
        final GitHubLiveMigrationCreatePayloadBuilder builder = GitHubLiveMigrationCreatePayloadBuilder.builder(
                        EnterpriseAdminCatalogFixtures.liveMigrationCreatePayload())
                .targetApiEndpoint(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("targetApiEndpoint");
    }

    @Test
    void constructor_shouldThrow_whenPatNameNull() {
        final GitHubLiveMigrationCreatePayloadBuilder builder = GitHubLiveMigrationCreatePayloadBuilder.builder(
                        EnterpriseAdminCatalogFixtures.liveMigrationCreatePayload())
                .patName(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("patName");
    }
}
