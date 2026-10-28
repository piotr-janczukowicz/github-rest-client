package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubActionsRepositoryPermissionsTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenEnabledNull() {
        final GitHubAllowedActions allowedActions = GitHubAllowedActions.ALL;
        final GitHubSelectedActionsUrl selectedActionsUrl = ActionsCatalogFixtures.selectedActionsUrl();
        final GitHubShaPinningRequired shaPinningRequired = ActionsCatalogFixtures.shaPinningRequired();

        final Throwable thrown = catchThrowable(() ->
                new GitHubActionsRepositoryPermissions(null, allowedActions, selectedActionsUrl, shaPinningRequired));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("enabled");
    }
}
