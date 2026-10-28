package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubOrganizationExternalPropertyInstallationTest {

    @Test
    void constructor_shouldThrow_whenDisplayNameNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubOrganizationExternalPropertyInstallation(
                null, OrganizationsCatalogFixtures.organizationExternalPropertyInstallationInstallation()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("displayName");
    }

    @Test
    void constructor_shouldThrow_whenInstallationNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubOrganizationExternalPropertyInstallation("Acme", null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("installation");
    }
}
