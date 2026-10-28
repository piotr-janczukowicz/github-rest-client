package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRunnerLabelsForEnterpriseTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenLabelsNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubRunnerLabelsForEnterprise(1, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("labels");
    }

    @Test
    void accessors_shouldReturnComponents_whenBuilt() {
        final GitHubRunnerLabelsForEnterprise value = ActionsCatalogFixtures.runnerLabelsForEnterprise();

        assertThat(value.totalCount()).isEqualTo(1);
        assertThat(value.labels()).containsExactly(ActionsCatalogFixtures.runnerLabel());
    }
}
