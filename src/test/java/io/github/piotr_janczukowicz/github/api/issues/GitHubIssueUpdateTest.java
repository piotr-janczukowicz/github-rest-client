package io.github.piotr_janczukowicz.github.api.issues;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubIssueUpdateTest {

    @Test
    void constructor_shouldLeaveIssueFieldValuesNull_whenNull() {
        final GitHubIssueUpdate issue = GitHubIssueUpdateBuilder.builder(IssuesCatalogFixtures.issueUpdate())
                .assignees(null)
                .issueFieldValues(null)
                .build();

        assertSoftly(softly -> {
            softly.assertThat(issue.assignees()).isNull();
            softly.assertThat(issue.issueFieldValues()).isNull();
        });
    }
}
