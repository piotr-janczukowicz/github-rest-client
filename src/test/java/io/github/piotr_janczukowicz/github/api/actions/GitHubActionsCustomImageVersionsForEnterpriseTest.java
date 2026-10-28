package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubActionsCustomImageVersionsForEnterpriseTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenImageVersionsNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubActionsCustomImageVersionsForEnterprise(1, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("imageVersions");
    }

    @Test
    void accessors_shouldReturnComponents_whenBuilt() {
        final GitHubActionsCustomImageVersionsForEnterprise value =
                ActionsCatalogFixtures.actionsCustomImageVersionsForEnterprise();

        assertThat(value.totalCount()).isEqualTo(1);
        assertThat(value.imageVersions())
                .containsExactly(ActionsCatalogFixtures.actionsHostedRunnerCustomImageVersion());
    }
}
