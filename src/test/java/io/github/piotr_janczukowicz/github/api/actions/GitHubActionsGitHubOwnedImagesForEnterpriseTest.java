package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubActionsGitHubOwnedImagesForEnterpriseTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenImagesNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubActionsGitHubOwnedImagesForEnterprise(1, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("images");
    }

    @Test
    void accessors_shouldReturnComponents_whenBuilt() {
        final GitHubActionsGitHubOwnedImagesForEnterprise value =
                ActionsCatalogFixtures.actionsGitHubOwnedImagesForEnterprise();

        assertThat(value.totalCount()).isEqualTo(1);
        assertThat(value.images()).containsExactly(ActionsCatalogFixtures.actionsHostedRunnerCuratedImage());
    }
}
