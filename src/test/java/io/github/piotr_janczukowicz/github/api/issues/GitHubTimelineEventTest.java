package io.github.piotr_janczukowicz.github.api.issues;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubLink;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubTimelineEventTest {

    @Test
    void gitUserConstructor_shouldThrow_whenDateNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubTimelineGitUser(null, "a@b.c", "n"));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("date");
    }

    @Test
    void linksConstructor_shouldThrow_whenHtmlNull() {
        final GitHubLink pullRequest = new GitHubLink("https://api.github.com/pr");

        final Throwable thrown = catchThrowable(() -> new GitHubTimelineEventLinks(null, pullRequest));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("html");
    }

    @Test
    void parentConstructor_shouldThrow_whenShaNull() {
        final Throwable thrown = catchThrowable(
                () -> new GitHubTimelineCommitParent(null, "https://api.github.com/sha", "https://github.com/sha"));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("sha");
    }

    @Test
    void treeConstructor_shouldThrow_whenShaNull() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubTimelineCommitTree(null, "https://api.github.com/tree"));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("sha");
    }

    @Test
    void verificationConstructor_shouldThrow_whenReasonNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubTimelineVerification(true, null, null, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("reason");
    }

    @Test
    void gitUserConstructor_shouldStoreValues_whenCalled() {
        final Instant date = Instant.parse("2014-08-09T08:02:04Z");

        final GitHubTimelineGitUser user = new GitHubTimelineGitUser(date, "a@b.c", "n");

        assertThat(user.date()).isEqualTo(date);
    }
}
