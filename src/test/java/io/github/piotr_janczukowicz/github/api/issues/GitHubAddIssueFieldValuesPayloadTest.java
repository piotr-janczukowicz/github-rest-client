package io.github.piotr_janczukowicz.github.api.issues;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAddIssueFieldValuesPayloadTest {

    @Test
    void constructor_shouldCopyValues_whenPresent() {
        final List<GitHubCreateIssueFieldValue> values =
                new ArrayList<>(List.of(new GitHubCreateIssueFieldValue(1, GitHubIssueFieldValueWire.text("a"))));

        final GitHubAddIssueFieldValuesPayload payload = new GitHubAddIssueFieldValuesPayload(values);
        values.clear();

        assertThat(payload.issueFieldValues()).hasSize(1);
    }

    @Test
    void constructor_shouldLeaveValuesNull_whenNull() {
        final GitHubAddIssueFieldValuesPayload payload = new GitHubAddIssueFieldValuesPayload(null);

        assertThat(payload.issueFieldValues()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenMoreThanMaxItems() {
        final List<GitHubCreateIssueFieldValue> values = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            values.add(new GitHubCreateIssueFieldValue(i, GitHubIssueFieldValueWire.text("a")));
        }

        final Throwable thrown = catchThrowable(() -> new GitHubAddIssueFieldValuesPayload(values));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("issueFieldValues");
    }
}
