package io.github.piotr_janczukowicz.github.api.meta;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSshKeyFingerprintsTest {

    @Test
    void constructor_shouldKeepNullFingerprints_whenAbsent() {
        final GitHubSshKeyFingerprints fingerprints =
                GitHubSshKeyFingerprintsBuilder.builder().build();

        assertSoftly(softly -> {
            softly.assertThat(fingerprints.sha256Rsa()).isNull();
            softly.assertThat(fingerprints.sha256Dsa()).isNull();
            softly.assertThat(fingerprints.sha256Ecdsa()).isNull();
            softly.assertThat(fingerprints.sha256Ed25519()).isNull();
        });
    }
}
