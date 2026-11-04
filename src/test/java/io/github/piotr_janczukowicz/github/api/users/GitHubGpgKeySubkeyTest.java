package io.github.piotr_janczukowicz.github.api.users;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGpgKeySubkeyTest {

    @Test
    void constructor_shouldKeepNullCollections_whenAbsent() {
        final GitHubGpgKeySubkey subkey = new GitHubGpgKeySubkey(
                4L,
                3L,
                "key",
                "public",
                null,
                null,
                false,
                true,
                true,
                false,
                Instant.parse("2026-01-01T00:00:00Z"),
                "expires",
                null,
                false);

        assertSoftly(softly -> {
            softly.assertThat(subkey.emails()).isNull();
            softly.assertThat(subkey.subkeys()).isNull();
        });
    }
}
