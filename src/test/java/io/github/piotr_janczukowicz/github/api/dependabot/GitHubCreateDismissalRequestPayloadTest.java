package io.github.piotr_janczukowicz.github.api.dependabot;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateDismissalRequestPayloadTest {

    @Test
    void gitHubCreateDismissalRequestPayload_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubCreateDismissalRequestPayload value = DependabotCatalogFixtures.createDismissalRequestPayload();

        assertThat(value.dismissedReason()).isEqualTo(GitHubCreateDismissalRequestDismissedReason.NOT_USED);
        assertThat(value.dismissedComment()).isEqualTo("Not used in production.");
    }
}
