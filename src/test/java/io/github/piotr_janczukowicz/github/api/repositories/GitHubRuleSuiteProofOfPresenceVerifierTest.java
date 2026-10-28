package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRuleSuiteProofOfPresenceVerifierTest {

    @Test
    void constructor_shouldStoreType_whenBuilt() {
        final GitHubRuleSuiteProofOfPresenceVerifier verifier =
                new GitHubRuleSuiteProofOfPresenceVerifier(1L, "octocat", "User");

        assertThat(verifier.type()).isEqualTo("User");
    }
}
