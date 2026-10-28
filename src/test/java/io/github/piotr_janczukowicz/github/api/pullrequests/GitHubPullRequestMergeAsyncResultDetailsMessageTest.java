package io.github.piotr_janczukowicz.github.api.pullrequests;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPullRequestMergeAsyncResultDetailsMessageTest {

    @Test
    void constructor_shouldRetainMessage_whenCreated() {
        final GitHubPullRequestMergeAsyncResultDetailsMessage details =
                new GitHubPullRequestMergeAsyncResultDetailsMessage("Merge failed.");

        assertThat(details.message()).isEqualTo("Merge failed.");
    }
}
