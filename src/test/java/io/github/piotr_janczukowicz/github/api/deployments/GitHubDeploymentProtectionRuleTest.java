package io.github.piotr_janczukowicz.github.api.deployments;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubDeploymentProtectionRuleTest {

    @Test
    void constructor_shouldThrow_whenAppNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubDeploymentProtectionRule(1, "n", true, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("app");
    }
}
