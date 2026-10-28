package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRunnerVersionDeprecationForEnterpriseTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenRunnerVersionNull() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubRunnerVersionDeprecationForEnterprise(null, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("runnerVersion");
    }

    @Test
    void accessors_shouldReturnComponents_whenBuilt() {
        final GitHubRunnerVersionDeprecationForEnterprise value =
                ActionsCatalogFixtures.runnerVersionDeprecationForEnterprise();

        assertThat(value.runnerVersion()).isEqualTo("2.300.0");
        assertThat(value.registrationDeprecatesAt()).isEqualTo(Instant.parse("2026-08-01T00:00:00Z"));
        assertThat(value.runtimeDeprecatesAt()).isEqualTo(Instant.parse("2026-09-01T00:00:00Z"));
    }

    @Test
    void constructor_shouldAllowNullDates_whenNotScheduled() {
        final GitHubRunnerVersionDeprecationForEnterprise value =
                new GitHubRunnerVersionDeprecationForEnterprise("2.300.0", null, null);

        assertThat(value.registrationDeprecatesAt()).isNull();
        assertThat(value.runtimeDeprecatesAt()).isNull();
    }
}
