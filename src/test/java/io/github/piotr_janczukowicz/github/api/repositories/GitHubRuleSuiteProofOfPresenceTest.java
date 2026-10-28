package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRuleSuiteProofOfPresenceTest {

    @Test
    void constructor_shouldStoreVerifiedLevel_whenBuilt() {
        final GitHubRuleSuiteProofOfPresence proofOfPresence = new GitHubRuleSuiteProofOfPresence(
                "verified", "verified", null, null, "approve", "approve", null, null);

        assertThat(proofOfPresence.verifiedLevel()).isEqualTo("approve");
    }
}
