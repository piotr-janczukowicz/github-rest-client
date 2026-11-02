package io.github.piotr_janczukowicz.github.api.issues;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListIssueArtifactsFilterTest {

    @Test
    void empty_shouldReturnNoQueryParameters_whenCalled() {
        assertThat(GitHubListIssueArtifactsFilter.empty().toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldEncodeField_whenSet() {
        final GitHubListIssueArtifactsFilter filter = GitHubListIssueArtifactsFilterBuilder.builder()
                .type(GitHubListIssueArtifactsType.GENERIC)
                .build();

        assertThat(filter.toQueryParameters()).containsEntry("type", "generic");
    }
}
