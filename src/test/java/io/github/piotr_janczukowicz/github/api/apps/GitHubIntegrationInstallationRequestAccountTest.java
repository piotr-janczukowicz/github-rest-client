package io.github.piotr_janczukowicz.github.api.apps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.organizations.OrganizationsCatalogFixtures;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubIntegrationInstallationRequestAccountTest {

    @Test
    void constructor_shouldThrow_whenSimpleUserUserNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubIntegrationInstallationRequestAccount.SimpleUser(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("user");
    }

    @Test
    void constructor_shouldThrow_whenEnterpriseNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubIntegrationInstallationRequestAccount.Enterprise(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("enterprise");
    }

    @Test
    void enterprise_shouldKeepEnterprise_whenNonNull() {
        final GitHubIntegrationInstallationRequestAccount.Enterprise account =
                new GitHubIntegrationInstallationRequestAccount.Enterprise(OrganizationsCatalogFixtures.enterprise());

        assertThat(account.enterprise()).isEqualTo(OrganizationsCatalogFixtures.enterprise());
    }
}
