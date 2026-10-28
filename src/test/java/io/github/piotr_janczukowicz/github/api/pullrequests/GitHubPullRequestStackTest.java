package io.github.piotr_janczukowicz.github.api.pullrequests;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPullRequestStackTest {

    @Test
    void constructor_shouldRetainFields_whenProvided() {
        final GitHubPullRequestStackBase base =
                new GitHubPullRequestStackBase("main", "6dcb09b5b57875f334f61aebed695e2e4193db5e");

        final GitHubPullRequestStack stack = new GitHubPullRequestStack(base, 3, 2, 9_876_543L, 42);

        assertSoftly(softly -> {
            softly.assertThat(stack).hasNoNullFieldsOrProperties();
            softly.assertThat(stack.base()).isEqualTo(base);
            softly.assertThat(stack.size()).isEqualTo(3);
            softly.assertThat(stack.position()).isEqualTo(2);
            softly.assertThat(stack.id()).isEqualTo(9_876_543);
            softly.assertThat(stack.number()).isEqualTo(42);
        });
    }
}
