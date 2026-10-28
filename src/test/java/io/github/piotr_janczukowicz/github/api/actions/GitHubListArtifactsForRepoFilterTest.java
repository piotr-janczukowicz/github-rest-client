package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListArtifactsForRepoFilterTest {

    @Test
    void empty_shouldReturnFilterWithoutQueryParameters() {
        final GitHubListArtifactsForRepoFilter sut = GitHubListArtifactsForRepoFilter.empty();

        final Map<String, String> query = sut.toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeName_whenSet() {
        final GitHubListArtifactsForRepoFilter sut = new GitHubListArtifactsForRepoFilter("artifact-1");

        final Map<String, String> query = sut.toQueryParameters();

        assertThat(query).containsExactlyEntriesOf(Map.of("name", "artifact-1"));
    }
}
