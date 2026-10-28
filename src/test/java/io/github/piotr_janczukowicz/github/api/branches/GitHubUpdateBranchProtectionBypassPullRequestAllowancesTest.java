package io.github.piotr_janczukowicz.github.api.branches;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateBranchProtectionBypassPullRequestAllowancesTest {

    @Test
    void constructor_shouldLeaveListsNull_whenListsNull() {
        final GitHubUpdateBranchProtectionBypassPullRequestAllowances payload =
                new GitHubUpdateBranchProtectionBypassPullRequestAllowances(null, null, null);

        assertSoftly(softly -> {
            softly.assertThat(payload.users()).isNull();
            softly.assertThat(payload.teams()).isNull();
            softly.assertThat(payload.apps()).isNull();
        });
    }

    @Test
    void constructor_shouldCopyLists_whenListsProvided() {
        final GitHubUpdateBranchProtectionBypassPullRequestAllowances payload =
                GitHubUpdateBranchProtectionBypassPullRequestAllowancesBuilder.builder()
                        .users(List.of("octocat"))
                        .teams(List.of("justice-league"))
                        .apps(List.of("octoapp"))
                        .build();

        assertSoftly(softly -> {
            softly.assertThat(payload.users()).containsExactly("octocat");
            softly.assertThat(payload.teams()).containsExactly("justice-league");
            softly.assertThat(payload.apps()).containsExactly("octoapp");
        });
    }
}
