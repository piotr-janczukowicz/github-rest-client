package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateInstallationPayloadTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubCreateInstallationPayload value = EnterpriseAdminCatalogFixtures.createInstallationPayload();

        assertThat(GitHubCreateInstallationPayloadBuilder.builder(value).build())
                .isEqualTo(value);
    }

    @Test
    void constructor_shouldLeaveRepositoriesNull_whenNull() {
        final GitHubCreateInstallationPayload value = GitHubCreateInstallationPayloadBuilder.builder(
                        EnterpriseAdminCatalogFixtures.createInstallationPayload())
                .repositories(null)
                .build();

        assertThat(value.repositories()).isNull();
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenClientIdNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCreateInstallationPayloadBuilder.builder(
                        EnterpriseAdminCatalogFixtures.createInstallationPayload())
                .clientId(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }
}
