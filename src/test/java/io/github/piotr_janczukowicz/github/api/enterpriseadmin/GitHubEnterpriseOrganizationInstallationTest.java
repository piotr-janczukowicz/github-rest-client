package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnterpriseOrganizationInstallationTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubEnterpriseOrganizationInstallation value =
                EnterpriseAdminCatalogFixtures.enterpriseOrganizationInstallation();

        assertThat(GitHubEnterpriseOrganizationInstallationBuilder.builder(value)
                        .build())
                .isEqualTo(value);
    }

    @Test
    void constructor_shouldLeaveEventsNull_whenNull() {
        final GitHubEnterpriseOrganizationInstallation value = GitHubEnterpriseOrganizationInstallationBuilder.builder(
                        EnterpriseAdminCatalogFixtures.enterpriseOrganizationInstallation())
                .events(null)
                .build();

        assertThat(value.events()).isNull();
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenClientIdNull() {
        final Throwable thrown = catchThrowable(() -> GitHubEnterpriseOrganizationInstallationBuilder.builder(
                        EnterpriseAdminCatalogFixtures.enterpriseOrganizationInstallation())
                .clientId(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }
}
