package io.github.piotr_janczukowicz.github.api.issues;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreatePayloadTest {

    @Test
    void constructor_shouldThrow_whenTitleNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCreatePayloadBuilder.builder().title(null).build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("title");
    }

    @Test
    void constructor_shouldCopyLabels_whenPresent() {
        final List<GitHubCreateIssueLabel> labels = new ArrayList<>(List.of(GitHubCreateIssueLabel.ofName("bug")));

        final GitHubCreatePayload payload =
                GitHubCreatePayloadBuilder.builder().title("Bug").labels(labels).build();
        labels.add(GitHubCreateIssueLabel.ofName("enhancement"));

        assertThat(payload.labels()).containsExactly(GitHubCreateIssueLabel.ofName("bug"));
    }

    @Test
    void constructor_shouldCopyAssignees_whenPresent() {
        final List<String> assignees = new ArrayList<>(List.of("octocat"));

        final GitHubCreatePayload payload = GitHubCreatePayloadBuilder.builder()
                .title("Bug")
                .assignees(assignees)
                .build();
        assignees.add("hubot");

        assertThat(payload.assignees()).containsExactly("octocat");
    }

    @Test
    void constructor_shouldCopyIssueFieldValues_whenPresent() {
        final List<GitHubCreateIssueFieldValue> fieldValues =
                new ArrayList<>(List.of(GitHubCreateIssueFieldValueBuilder.builder()
                        .fieldId(1)
                        .value(GitHubIssueFieldValueWire.text("high"))
                        .build()));

        final GitHubCreatePayload payload = GitHubCreatePayloadBuilder.builder()
                .title("Bug")
                .issueFieldValues(fieldValues)
                .build();
        fieldValues.clear();

        assertThat(payload.issueFieldValues()).hasSize(1);
    }
}
