package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubActionsHostedRunnerMachineSpecsForEnterpriseTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenMachineSpecsNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubActionsHostedRunnerMachineSpecsForEnterprise(1, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("machineSpecs");
    }

    @Test
    void accessors_shouldReturnComponents_whenBuilt() {
        final GitHubActionsHostedRunnerMachineSpecsForEnterprise value =
                ActionsCatalogFixtures.actionsHostedRunnerMachineSpecsForEnterprise();

        assertThat(value.totalCount()).isEqualTo(1);
        assertThat(value.machineSpecs()).containsExactly(ActionsCatalogFixtures.actionsHostedRunnerMachineSpec());
    }
}
