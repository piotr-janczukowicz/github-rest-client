package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.organizations.OrganizationsCatalogFixtures;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRunnerGroupOrganizationsForEnterpriseTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenOrganizationsNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubRunnerGroupOrganizationsForEnterprise(1, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("organizations");
    }

    @Test
    void accessors_shouldReturnComponents_whenBuilt() {
        final GitHubRunnerGroupOrganizationsForEnterprise value =
                ActionsCatalogFixtures.runnerGroupOrganizationsForEnterprise();

        assertThat(value.totalCount()).isEqualTo(1);
        assertThat(value.organizations()).containsExactly(OrganizationsCatalogFixtures.organizationSimple());
    }
}
