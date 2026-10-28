package io.github.piotr_janczukowicz.github.api.checks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCheckSuitePreferenceTest {

    @Test
    void constructor_shouldThrow_whenPreferencesNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCheckSuitePreferenceBuilder.builder(ChecksCatalogFixtures.checkSuitePreference())
                        .preferences(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("preferences");
    }

    @Test
    void constructor_shouldThrow_whenRepositoryNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCheckSuitePreferenceBuilder.builder(ChecksCatalogFixtures.checkSuitePreference())
                        .repository(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repository");
    }
}
