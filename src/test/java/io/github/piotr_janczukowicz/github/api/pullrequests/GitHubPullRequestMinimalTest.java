package io.github.piotr_janczukowicz.github.api.pullrequests;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPullRequestMinimalTest {

    @Test
    void constructor_shouldRetainFields_whenProvided() {
        final GitHubPullRequestMinimalRef head = ref("user-model", "aaa1111aaa1111aaa1111aaa1111aaa1111aaa11");
        final GitHubPullRequestMinimalRef base = ref("main", "6dcb09b5b57875f334f61aebed695e2e4193db5e");

        final GitHubPullRequestMinimal pullRequest = new GitHubPullRequestMinimal(
                100_001L, 101, "https://api.github.com/repos/octocat/hello-world/pulls/101", head, base);

        assertSoftly(softly -> {
            softly.assertThat(pullRequest).hasNoNullFieldsOrProperties();
            softly.assertThat(pullRequest.id()).isEqualTo(100_001L);
            softly.assertThat(pullRequest.number()).isEqualTo(101);
            softly.assertThat(pullRequest.head()).isEqualTo(head);
            softly.assertThat(pullRequest.base()).isEqualTo(base);
        });
    }

    private static GitHubPullRequestMinimalRef ref(final String name, final String sha) {
        return new GitHubPullRequestMinimalRef(name, sha, PullrequestsCatalogFixtures.pullRequestMinimalRepo());
    }
}
