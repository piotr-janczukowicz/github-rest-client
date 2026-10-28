package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRepositoryFineGrainedPermissionTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubRepositoryFineGrainedPermission value =
                OrganizationsCatalogFixtures.repositoryFineGrainedPermission();
        final GitHubRepositoryFineGrainedPermission expected =
                OrganizationsCatalogFixtures.repositoryFineGrainedPermission();

        assertThat(value.name()).isEqualTo(expected.name());
        assertThat(value.description()).isEqualTo(expected.description());
    }

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubRepositoryFineGrainedPermission(null, "d"));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }
}
