package io.github.piotr_janczukowicz.github.api.issues;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdatePayloadTest {

    @Test
    void constructor_shouldCopyLabels_whenPresent() {
        final List<GitHubUpdateIssueLabel> labels = new ArrayList<>(List.of(GitHubUpdateIssueLabel.ofName("bug")));

        final GitHubUpdatePayload payload =
                GitHubUpdatePayloadBuilder.builder().labels(labels).build();
        labels.add(GitHubUpdateIssueLabel.ofName("enhancement"));

        assertThat(payload.labels()).containsExactly(GitHubUpdateIssueLabel.ofName("bug"));
    }

    @Test
    void constructor_shouldCopyAssignees_whenPresent() {
        final List<GitHubUpdateIssueAssignee> assignees =
                new ArrayList<>(List.of(GitHubUpdateIssueAssignee.ofLogin("octocat")));

        final GitHubUpdatePayload payload =
                GitHubUpdatePayloadBuilder.builder().assignees(assignees).build();
        assignees.add(GitHubUpdateIssueAssignee.ofLogin("hubot"));

        assertThat(payload.assignees()).containsExactly(GitHubUpdateIssueAssignee.ofLogin("octocat"));
    }

    @Test
    void constructor_shouldCopyIssueFieldValues_whenPresent() {
        final List<GitHubUpdateIssueFieldValue> fieldValues =
                new ArrayList<>(List.of(IssuesCatalogFixtures.updateIssueFieldValue()));

        final GitHubUpdatePayload payload = GitHubUpdatePayloadBuilder.builder()
                .issueFieldValues(fieldValues)
                .build();
        fieldValues.clear();

        assertThat(payload.issueFieldValues()).hasSize(1);
    }
}
