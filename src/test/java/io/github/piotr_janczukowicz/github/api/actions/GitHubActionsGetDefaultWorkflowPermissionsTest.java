package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubActionsGetDefaultWorkflowPermissionsTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenDefaultWorkflowPermissionsNull() {
        final GitHubActionsCanApprovePullRequestReviews canApprovePullRequestReviews =
                ActionsCatalogFixtures.actionsCanApprovePullRequestReviews();

        final Throwable thrown = catchThrowable(
                () -> new GitHubActionsGetDefaultWorkflowPermissions(null, canApprovePullRequestReviews));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("defaultWorkflowPermissions");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenCanApprovePullRequestReviewsNull() {
        final GitHubActionsDefaultWorkflowPermissions defaultWorkflowPermissions =
                GitHubActionsDefaultWorkflowPermissions.READ;

        final Throwable thrown =
                catchThrowable(() -> new GitHubActionsGetDefaultWorkflowPermissions(defaultWorkflowPermissions, null));

        assertThat(thrown)
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("canApprovePullRequestReviews");
    }
}
