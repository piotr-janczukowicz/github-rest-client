package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubOrganizationFineGrainedPermissionTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubOrganizationFineGrainedPermission value =
                OrganizationsCatalogFixtures.organizationFineGrainedPermission();
        final GitHubOrganizationFineGrainedPermission expected =
                OrganizationsCatalogFixtures.organizationFineGrainedPermission();

        assertThat(value.name()).isEqualTo(expected.name());
        assertThat(value.description()).isEqualTo(expected.description());
    }

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubOrganizationFineGrainedPermission(null, "d"));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }
}
