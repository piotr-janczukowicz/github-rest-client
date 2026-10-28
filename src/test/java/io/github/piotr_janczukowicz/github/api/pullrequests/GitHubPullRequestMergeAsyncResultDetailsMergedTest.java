package io.github.piotr_janczukowicz.github.api.pullrequests;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPullRequestMergeAsyncResultDetailsMergedTest {

    @Test
    void constructor_shouldRetainFields_whenCreated() {
        final GitHubPullRequestMergeAsyncResultDetailsMerged details =
                new GitHubPullRequestMergeAsyncResultDetailsMerged(
                        "Pull Request successfully merged", "6dcb09b5b57875f334f61aebed695e2e4193db5e");

        assertSoftly(softly -> {
            softly.assertThat(details).hasNoNullFieldsOrProperties();
            softly.assertThat(details.message()).isEqualTo("Pull Request successfully merged");
            softly.assertThat(details.sha()).isEqualTo("6dcb09b5b57875f334f61aebed695e2e4193db5e");
        });
    }
}
