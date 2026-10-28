package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubActionsEnterprisePermissionsTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenEnabledOrganizationsNull() {
        final String selectedOrganizationsUrl = "https://api.github.com/example";
        final GitHubAllowedActions allowedActions = GitHubAllowedActions.ALL;
        final GitHubSelectedActionsUrl selectedActionsUrl = ActionsCatalogFixtures.selectedActionsUrl();
        final GitHubShaPinningRequired shaPinningRequired = ActionsCatalogFixtures.shaPinningRequired();

        final Throwable thrown = catchThrowable(() -> new GitHubActionsEnterprisePermissions(
                null, selectedOrganizationsUrl, allowedActions, selectedActionsUrl, shaPinningRequired));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("enabledOrganizations");
    }

    @Test
    void accessors_shouldReturnValues_whenConstructed() {
        final GitHubActionsEnterprisePermissions permissions = ActionsCatalogFixtures.actionsEnterprisePermissions();

        assertSoftly(softly -> {
            softly.assertThat(permissions.enabledOrganizations()).isEqualTo(GitHubEnabledOrganizations.SELECTED);
            softly.assertThat(permissions.selectedOrganizationsUrl()).isEqualTo("https://api.github.com/example");
            softly.assertThat(permissions.allowedActions()).isEqualTo(GitHubAllowedActions.ALL);
            softly.assertThat(permissions.selectedActionsUrl()).isEqualTo(ActionsCatalogFixtures.selectedActionsUrl());
            softly.assertThat(permissions.shaPinningRequired()).isEqualTo(ActionsCatalogFixtures.shaPinningRequired());
        });
    }

    @Test
    void constructor_shouldAllowNullOptionalValues_whenOnlyEnabledOrganizationsSet() {
        final GitHubActionsEnterprisePermissions permissions =
                new GitHubActionsEnterprisePermissions(GitHubEnabledOrganizations.NONE, null, null, null, null);

        assertSoftly(softly -> {
            softly.assertThat(permissions.enabledOrganizations()).isEqualTo(GitHubEnabledOrganizations.NONE);
            softly.assertThat(permissions.selectedOrganizationsUrl()).isNull();
            softly.assertThat(permissions.allowedActions()).isNull();
            softly.assertThat(permissions.selectedActionsUrl()).isNull();
            softly.assertThat(permissions.shaPinningRequired()).isNull();
        });
    }
}
