package io.github.piotr_janczukowicz.github.api.teams;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateTeamLegacyPayloadTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubUpdateTeamLegacyPayload(null, null, null, null, null, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }
}
