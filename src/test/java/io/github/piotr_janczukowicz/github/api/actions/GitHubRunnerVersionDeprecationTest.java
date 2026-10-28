package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRunnerVersionDeprecationTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenRunnerVersionNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubRunnerVersionDeprecationBuilder.builder(ActionsCatalogFixtures.runnerVersionDeprecation())
                        .runnerVersion(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("runnerVersion");
    }
}
