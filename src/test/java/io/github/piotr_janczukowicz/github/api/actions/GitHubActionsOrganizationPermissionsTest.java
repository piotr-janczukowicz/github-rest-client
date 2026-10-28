package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubActionsOrganizationPermissionsTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenEnabledRepositoriesNull() {
        final String selectedRepositoriesUrl = "https://api.github.com/example";
        final GitHubAllowedActions allowedActions = GitHubAllowedActions.ALL;
        final GitHubSelectedActionsUrl selectedActionsUrl = ActionsCatalogFixtures.selectedActionsUrl();
        final GitHubShaPinningRequired shaPinningRequired = ActionsCatalogFixtures.shaPinningRequired();

        final Throwable thrown = catchThrowable(() -> new GitHubActionsOrganizationPermissions(
                null, selectedRepositoriesUrl, allowedActions, selectedActionsUrl, shaPinningRequired));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("enabledRepositories");
    }
}
