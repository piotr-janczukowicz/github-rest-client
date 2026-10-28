package io.github.piotr_janczukowicz.github.api.checks;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCheckSuitePreferencesTest {

    @Test
    void constructor_shouldLeaveAutoTriggerChecksNull_whenNull() {
        final GitHubCheckSuitePreferences preferences = GitHubCheckSuitePreferencesBuilder.builder(
                        ChecksCatalogFixtures.checkSuitePreferences())
                .autoTriggerChecks(null)
                .build();

        assertThat(preferences.autoTriggerChecks()).isNull();
    }
}
