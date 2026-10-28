package io.github.piotr_janczukowicz.github.api.migrations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSetLfsPreferencePayloadTest {

    @Test
    void constructor_shouldThrow_whenUseLfsNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubSetLfsPreferencePayload(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("useLfs");
    }

    @Test
    void builder_shouldCreatePayload_whenCalled() {
        final GitHubSetLfsPreferencePayload payload = GitHubSetLfsPreferencePayloadBuilder.builder()
                .useLfs(GitHubImportUseLfs.OPT_IN)
                .build();

        assertThat(payload.useLfs()).isEqualTo(GitHubImportUseLfs.OPT_IN);
    }
}
