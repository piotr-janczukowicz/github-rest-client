package io.github.piotr_janczukowicz.github.api.checks;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSetSuitesPreferencesPayloadTest {

    @Test
    void constructor_shouldLeaveAutoTriggerChecksNull_whenNull() {
        final GitHubSetSuitesPreferencesPayload payload = GitHubSetSuitesPreferencesPayloadBuilder.builder(
                        ChecksCatalogFixtures.setSuitesPreferencesPayload())
                .autoTriggerChecks(null)
                .build();

        assertThat(payload.autoTriggerChecks()).isNull();
    }
}
