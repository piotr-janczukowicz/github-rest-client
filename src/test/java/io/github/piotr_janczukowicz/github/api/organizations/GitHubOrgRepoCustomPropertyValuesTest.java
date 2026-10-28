package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubCustomPropertyValue;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubOrgRepoCustomPropertyValuesTest {

    @Test
    void constructor_shouldThrow_whenRepositoryNameNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubOrgRepoCustomPropertyValues(
                1L, null, "octocat/Hello-World", List.of(GitHubCustomPropertyValue.of("team", "core"))));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repositoryName");
    }

    @Test
    void constructor_shouldThrow_whenRepositoryFullNameNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubOrgRepoCustomPropertyValues(
                1L, "Hello-World", null, List.of(GitHubCustomPropertyValue.of("team", "core"))));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repositoryFullName");
    }

    @Test
    void constructor_shouldThrow_whenPropertiesNull() {
        final Throwable thrown = catchThrowable(
                () -> new GitHubOrgRepoCustomPropertyValues(1L, "Hello-World", "octocat/Hello-World", null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("properties");
    }
}
