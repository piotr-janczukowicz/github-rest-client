package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListWorkflowRunArtifactsFilterTest {

    @Test
    void empty_shouldReturnFilterWithoutQueryParameters() {
        final GitHubListWorkflowRunArtifactsFilter sut = GitHubListWorkflowRunArtifactsFilter.empty();

        final Map<String, String> query = sut.toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeNameAndDirection_whenSet() {
        final GitHubListWorkflowRunArtifactsFilter sut =
                new GitHubListWorkflowRunArtifactsFilter("artifact-1", GitHubDirection.ASC);

        final Map<String, String> query = sut.toQueryParameters();

        assertThat(query).containsExactlyInAnyOrderEntriesOf(Map.of("name", "artifact-1", "direction", "asc"));
    }
}
