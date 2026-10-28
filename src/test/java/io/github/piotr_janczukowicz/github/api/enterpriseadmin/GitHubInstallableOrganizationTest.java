package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubInstallableOrganizationTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubInstallableOrganization value = EnterpriseAdminCatalogFixtures.installableOrganization();

        assertThat(GitHubInstallableOrganizationBuilder.builder(value).build()).isEqualTo(value);
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenLoginNull() {
        final Throwable thrown = catchThrowable(() -> GitHubInstallableOrganizationBuilder.builder(
                        EnterpriseAdminCatalogFixtures.installableOrganization())
                .login(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }
}
