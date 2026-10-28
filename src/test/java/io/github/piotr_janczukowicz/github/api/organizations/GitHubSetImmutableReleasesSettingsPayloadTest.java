package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSetImmutableReleasesSettingsPayloadTest {

    @Test
    void constructor_shouldThrow_whenEnforcedRepositoriesNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubSetImmutableReleasesSettingsPayload(null, List.of(1L)));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("enforcedRepositories");
    }

    @Test
    void constructor_shouldKeepNullSelectedRepositoryIds_whenAbsent() {
        final GitHubSetImmutableReleasesSettingsPayload payload =
                new GitHubSetImmutableReleasesSettingsPayload(GitHubImmutableReleasesEnforcedRepositories.ALL, null);

        assertThat(payload.selectedRepositoryIds()).isNull();
    }

    @Test
    void constructor_shouldCopySelectedRepositoryIds_whenPresent() {
        final GitHubSetImmutableReleasesSettingsPayload payload = new GitHubSetImmutableReleasesSettingsPayload(
                GitHubImmutableReleasesEnforcedRepositories.SELECTED, List.of(1L));

        assertThat(payload.selectedRepositoryIds()).containsExactly(1L);
    }
}
