package io.github.piotr_janczukowicz.github.api.branches;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.api.apps.GitHubIntegration;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeam;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubProtectedBranchPullRequestReviewBypassPullRequestAllowancesTest {

    @Test
    void constructor_shouldLeaveListsNull_whenListsNull() {
        final GitHubProtectedBranchPullRequestReviewBypassPullRequestAllowances allowances =
                new GitHubProtectedBranchPullRequestReviewBypassPullRequestAllowances(null, null, null);

        assertSoftly(softly -> {
            softly.assertThat(allowances.users()).isNull();
            softly.assertThat(allowances.teams()).isNull();
            softly.assertThat(allowances.apps()).isNull();
        });
    }

    @Test
    void constructor_shouldCopyLists_whenListsProvided() {
        final List<GitHubSimpleUser> users = new ArrayList<>();
        final List<GitHubTeam> teams = new ArrayList<>();
        final List<GitHubIntegration> apps = new ArrayList<>();

        final GitHubProtectedBranchPullRequestReviewBypassPullRequestAllowances allowances =
                new GitHubProtectedBranchPullRequestReviewBypassPullRequestAllowances(users, teams, apps);
        users.clear();
        teams.clear();
        apps.clear();

        assertSoftly(softly -> {
            softly.assertThat(allowances.users()).isEmpty();
            softly.assertThat(allowances.teams()).isEmpty();
            softly.assertThat(allowances.apps()).isEmpty();
        });
    }
}
