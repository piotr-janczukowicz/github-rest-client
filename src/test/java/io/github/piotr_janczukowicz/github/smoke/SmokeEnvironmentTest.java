package io.github.piotr_janczukowicz.github.smoke;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class SmokeEnvironmentTest {

    @Test
    void of_shouldDefaultTheRepo_whenNoRepoVariable() {
        final Map<String, String> variables = Map.of(SmokeEnvironment.TOKEN_VARIABLE, "t");

        final SmokeEnvironment env = SmokeEnvironment.of(variables);

        assertSoftly(softly -> {
            softly.assertThat(env.owner()).isEqualTo("piotr-janczukowicz");
            softly.assertThat(env.repo()).isEqualTo("github-api-smoke");
        });
    }

    @Test
    void of_shouldSplitOwnerAndRepo_whenVariableSet() {
        final Map<String, String> variables = Map.of(SmokeEnvironment.REPO_VARIABLE, "acme/box-smoke");

        final SmokeEnvironment env = SmokeEnvironment.of(variables);

        assertSoftly(softly -> {
            softly.assertThat(env.owner()).isEqualTo("acme");
            softly.assertThat(env.repo()).isEqualTo("box-smoke");
        });
    }

    @Test
    void of_shouldRejectMalformedRepo_whenNoSlash() {
        final Map<String, String> variables = Map.of(SmokeEnvironment.REPO_VARIABLE, "noslash");

        final Throwable thrown = catchThrowable(() -> SmokeEnvironment.of(variables));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("SMOKE_GITHUB_REPO");
    }

    @Test
    void of_shouldRejectMalformedRepo_whenExtraSlash() {
        final Map<String, String> variables = Map.of(SmokeEnvironment.REPO_VARIABLE, "a/b/c");

        final Throwable thrown = catchThrowable(() -> SmokeEnvironment.of(variables));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("SMOKE_GITHUB_REPO");
    }

    @Test
    void configured_shouldBeFalse_whenTokenMissing() {
        final SmokeEnvironment env = SmokeEnvironment.of(Map.of());

        final boolean configured = env.configured();

        assertThat(configured).isFalse();
    }

    @Test
    void configured_shouldBeFalse_whenTokenBlank() {
        final SmokeEnvironment env = SmokeEnvironment.of(Map.of(SmokeEnvironment.TOKEN_VARIABLE, "  "));

        final boolean configured = env.configured();

        assertThat(configured).isFalse();
    }

    @Test
    void configured_shouldBeTrue_whenTokenSet() {
        final SmokeEnvironment env = SmokeEnvironment.of(Map.of(SmokeEnvironment.TOKEN_VARIABLE, "t"));

        final boolean configured = env.configured();

        assertThat(configured).isTrue();
    }

    @Test
    void name_shouldPrefixWithRunId() {
        final SmokeEnvironment env = SmokeEnvironment.of(Map.of());

        final String name = env.name("issue");

        assertThat(name).isEqualTo("smoke-" + env.runId() + "-issue");
    }

    @Test
    void toString_shouldNotContainTheToken() {
        final SmokeEnvironment env = SmokeEnvironment.of(Map.of(SmokeEnvironment.TOKEN_VARIABLE, "secret-value"));

        final String text = env.toString();

        assertThat(text).doesNotContain("secret-value").contains("github-api-smoke");
    }
}
