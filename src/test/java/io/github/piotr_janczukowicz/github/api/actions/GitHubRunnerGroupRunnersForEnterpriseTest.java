package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRunnerGroupRunnersForEnterpriseTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenRunnersNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubRunnerGroupRunnersForEnterprise(1, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("runners");
    }

    @Test
    void accessors_shouldReturnComponents_whenBuilt() {
        final GitHubRunnerGroupRunnersForEnterprise value = ActionsCatalogFixtures.runnerGroupRunnersForEnterprise();

        assertThat(value.totalCount()).isEqualTo(1);
        assertThat(value.runners()).containsExactly(ActionsCatalogFixtures.runner());
    }
}
