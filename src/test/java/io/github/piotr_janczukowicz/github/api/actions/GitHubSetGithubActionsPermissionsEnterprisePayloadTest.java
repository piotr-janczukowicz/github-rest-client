package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSetGithubActionsPermissionsEnterprisePayloadTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenEnabledOrganizationsNull() {
        final GitHubAllowedActions allowedActions = GitHubAllowedActions.ALL;
        final GitHubShaPinningRequired shaPinningRequired = ActionsCatalogFixtures.shaPinningRequired();

        final Throwable thrown = catchThrowable(
                () -> new GitHubSetGithubActionsPermissionsEnterprisePayload(null, allowedActions, shaPinningRequired));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("enabledOrganizations");
    }

    @Test
    void accessors_shouldReturnValues_whenConstructed() {
        final GitHubSetGithubActionsPermissionsEnterprisePayload payload =
                ActionsCatalogFixtures.setGithubActionsPermissionsEnterprisePayload();

        assertSoftly(softly -> {
            softly.assertThat(payload.enabledOrganizations()).isEqualTo(GitHubEnabledOrganizations.ALL);
            softly.assertThat(payload.allowedActions()).isEqualTo(GitHubAllowedActions.ALL);
            softly.assertThat(payload.shaPinningRequired()).isEqualTo(ActionsCatalogFixtures.shaPinningRequired());
        });
    }
}
