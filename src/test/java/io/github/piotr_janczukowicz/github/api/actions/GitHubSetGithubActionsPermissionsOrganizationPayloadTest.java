package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSetGithubActionsPermissionsOrganizationPayloadTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenEnabledRepositoriesNull() {
        final GitHubAllowedActions allowedActions = GitHubAllowedActions.ALL;
        final GitHubShaPinningRequired shaPinningRequired = ActionsCatalogFixtures.shaPinningRequired();

        final Throwable thrown = catchThrowable(() ->
                new GitHubSetGithubActionsPermissionsOrganizationPayload(null, allowedActions, shaPinningRequired));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("enabledRepositories");
    }
}
