package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubActionsHostedRunnerPlatformsForEnterpriseTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenPlatformsNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubActionsHostedRunnerPlatformsForEnterprise(1, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("platforms");
    }

    @Test
    void accessors_shouldReturnComponents_whenBuilt() {
        final GitHubActionsHostedRunnerPlatformsForEnterprise value =
                ActionsCatalogFixtures.actionsHostedRunnerPlatformsForEnterprise();

        assertThat(value.totalCount()).isEqualTo(1);
        assertThat(value.platforms()).containsExactly("platforms-sample");
    }
}
