package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRunnerJitConfigForEnterpriseTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenRunnerNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubRunnerJitConfigForEnterprise(null, "encoded"));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("runner");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenEncodedJitConfigNull() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubRunnerJitConfigForEnterprise(ActionsCatalogFixtures.runner(), null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("encodedJitConfig");
    }

    @Test
    void accessors_shouldReturnComponents_whenBuilt() {
        final GitHubRunnerJitConfigForEnterprise value = ActionsCatalogFixtures.runnerJitConfigForEnterprise();

        assertThat(value.runner()).isEqualTo(ActionsCatalogFixtures.runner());
        assertThat(value.encodedJitConfig()).isEqualTo("encodedJitConfig-sample");
    }

    @Test
    void toString_shouldRedactEncodedJitConfig_whenDescribed() {
        final GitHubRunnerJitConfigForEnterprise value = ActionsCatalogFixtures.runnerJitConfigForEnterprise();

        assertThat(value.toString()).contains("<redacted>").doesNotContain("encodedJitConfig-sample");
    }
}
