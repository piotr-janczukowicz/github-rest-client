package io.github.piotr_janczukowicz.github.api.users;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAttestationsTest {

    @Test
    void constructor_shouldKeepNullAttestations_whenAbsent() {
        final GitHubAttestations attestations = new GitHubAttestations(null);

        assertThat(attestations.attestations()).isNull();
    }
}
