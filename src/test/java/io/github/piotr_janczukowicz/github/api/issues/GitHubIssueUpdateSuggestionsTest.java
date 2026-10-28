package io.github.piotr_janczukowicz.github.api.issues;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubIssueUpdateSuggestionsTest {

    @Test
    void constructor_shouldLeaveListsNull_whenNull() {
        final GitHubIssueUpdateSuggestions suggestions = new GitHubIssueUpdateSuggestions(null, null, null, null, null);

        assertSoftly(softly -> {
            softly.assertThat(suggestions.type()).isNull();
            softly.assertThat(suggestions.issueFieldValues()).isNull();
            softly.assertThat(suggestions.labels()).isNull();
            softly.assertThat(suggestions.assignees()).isNull();
            softly.assertThat(suggestions.state()).isNull();
        });
    }
}
