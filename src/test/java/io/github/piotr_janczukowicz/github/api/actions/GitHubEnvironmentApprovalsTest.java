package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnvironmentApprovalsTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenEnvironmentsNull() {
        final GitHubEnvironmentApprovalState state = GitHubEnvironmentApprovalState.APPROVED;
        final GitHubSimpleUser user = UsersCatalogFixtures.octocat();
        final String comment = "comment-sample";

        final Throwable thrown = catchThrowable(() -> new GitHubEnvironmentApprovals(null, state, user, comment));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("environments");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenStateNull() {
        final List<GitHubEnvironmentApprovalsEnvironment> environments =
                List.of(ActionsCatalogFixtures.environmentApprovalsEnvironment());
        final GitHubSimpleUser user = UsersCatalogFixtures.octocat();
        final String comment = "comment-sample";

        final Throwable thrown =
                catchThrowable(() -> new GitHubEnvironmentApprovals(environments, null, user, comment));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("state");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenUserNull() {
        final List<GitHubEnvironmentApprovalsEnvironment> environments =
                List.of(ActionsCatalogFixtures.environmentApprovalsEnvironment());
        final GitHubEnvironmentApprovalState state = GitHubEnvironmentApprovalState.APPROVED;
        final String comment = "comment-sample";

        final Throwable thrown =
                catchThrowable(() -> new GitHubEnvironmentApprovals(environments, state, null, comment));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("user");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenCommentNull() {
        final List<GitHubEnvironmentApprovalsEnvironment> environments =
                List.of(ActionsCatalogFixtures.environmentApprovalsEnvironment());
        final GitHubEnvironmentApprovalState state = GitHubEnvironmentApprovalState.APPROVED;
        final GitHubSimpleUser user = UsersCatalogFixtures.octocat();

        final Throwable thrown = catchThrowable(() -> new GitHubEnvironmentApprovals(environments, state, user, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("comment");
    }
}
