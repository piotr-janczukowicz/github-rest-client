package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRunnerJitConfigTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenRunnerNull() {
        final String encodedJitConfig = "encodedJitConfig-sample";

        final Throwable thrown = catchThrowable(() -> new GitHubRunnerJitConfig(null, encodedJitConfig));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("runner");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenEncodedJitConfigNull() {
        final GitHubRunner runner = ActionsCatalogFixtures.runner();

        final Throwable thrown = catchThrowable(() -> new GitHubRunnerJitConfig(runner, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("encodedJitConfig");
    }

    @Test
    void toString_shouldHideSecrets() {
        final GitHubRunnerJitConfig value = ActionsCatalogFixtures.runnerJitConfig();

        final String text = value.toString();

        assertSoftly(softly -> {
            softly.assertThat(text).contains("encodedJitConfig=<redacted>");
            softly.assertThat(text).doesNotContain(value.encodedJitConfig());
        });
    }
}
