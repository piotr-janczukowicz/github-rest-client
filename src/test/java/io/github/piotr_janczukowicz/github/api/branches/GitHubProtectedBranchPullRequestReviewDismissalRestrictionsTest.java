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
class GitHubProtectedBranchPullRequestReviewDismissalRestrictionsTest {

    @Test
    void constructor_shouldLeaveListsNull_whenListsNull() {
        final GitHubProtectedBranchPullRequestReviewDismissalRestrictions restrictions =
                new GitHubProtectedBranchPullRequestReviewDismissalRestrictions(null, null, null, null, null, null);

        assertSoftly(softly -> {
            softly.assertThat(restrictions.users()).isNull();
            softly.assertThat(restrictions.teams()).isNull();
            softly.assertThat(restrictions.apps()).isNull();
        });
    }

    @Test
    void constructor_shouldCopyLists_whenListsProvided() {
        final List<GitHubSimpleUser> users = new ArrayList<>();
        final List<GitHubTeam> teams = new ArrayList<>();
        final List<GitHubIntegration> apps = new ArrayList<>();

        final GitHubProtectedBranchPullRequestReviewDismissalRestrictions restrictions =
                new GitHubProtectedBranchPullRequestReviewDismissalRestrictions(
                        users, teams, apps, "url", "users-url", "teams-url");
        users.clear();
        teams.clear();
        apps.clear();

        assertSoftly(softly -> {
            softly.assertThat(restrictions.users()).isEmpty();
            softly.assertThat(restrictions.teams()).isEmpty();
            softly.assertThat(restrictions.apps()).isEmpty();
            softly.assertThat(restrictions.url()).isEqualTo("url");
            softly.assertThat(restrictions.usersUrl()).isEqualTo("users-url");
            softly.assertThat(restrictions.teamsUrl()).isEqualTo("teams-url");
        });
    }
}
