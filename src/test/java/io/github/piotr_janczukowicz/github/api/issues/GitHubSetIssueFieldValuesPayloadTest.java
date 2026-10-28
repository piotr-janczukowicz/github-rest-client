package io.github.piotr_janczukowicz.github.api.issues;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSetIssueFieldValuesPayloadTest {

    @Test
    void constructor_shouldCopyValues_whenPresent() {
        final List<GitHubSetIssueFieldValue> values = new ArrayList<>(List.of(new GitHubSetIssueFieldValue(1, "a")));

        final GitHubSetIssueFieldValuesPayload payload = new GitHubSetIssueFieldValuesPayload(values);
        values.clear();

        assertThat(payload.issueFieldValues()).hasSize(1);
    }

    @Test
    void constructor_shouldLeaveValuesNull_whenNull() {
        final GitHubSetIssueFieldValuesPayload payload = new GitHubSetIssueFieldValuesPayload(null);

        assertThat(payload.issueFieldValues()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenMoreThanMaxItems() {
        final List<GitHubSetIssueFieldValue> values = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            values.add(new GitHubSetIssueFieldValue(i, "a"));
        }

        final Throwable thrown = catchThrowable(() -> new GitHubSetIssueFieldValuesPayload(values));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("issueFieldValues");
    }
}
