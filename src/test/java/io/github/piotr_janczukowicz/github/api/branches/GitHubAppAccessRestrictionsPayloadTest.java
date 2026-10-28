package io.github.piotr_janczukowicz.github.api.branches;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAppAccessRestrictionsPayloadTest {

    @Test
    void constructor_shouldThrow_whenAppsNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubAppAccessRestrictionsPayload(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("apps");
    }

    @Test
    void constructor_shouldCopyApps_whenAppsProvided() {
        final GitHubAppAccessRestrictionsPayload payload = new GitHubAppAccessRestrictionsPayload(List.of("octoapp"));

        assertThat(payload.apps()).containsExactly("octoapp");
    }
}
