package io.github.piotr_janczukowicz.github.api.branches;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateBranchProtectionRequiredStatusCheckTest {

    @Test
    void constructor_shouldThrow_whenContextNull() {
        final GitHubUpdateBranchProtectionRequiredStatusCheckBuilder builder =
                GitHubUpdateBranchProtectionRequiredStatusCheckBuilder.builder().context(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("context");
    }

    @Test
    void constructor_shouldAcceptWhitespace_whenContextNotNull() {
        final GitHubUpdateBranchProtectionRequiredStatusCheck payload =
                GitHubUpdateBranchProtectionRequiredStatusCheckBuilder.builder()
                        .context(" ")
                        .build();

        assertThat(payload.context()).isEqualTo(" ");
    }

    @Test
    void constructor_shouldStoreAppId_whenAppIdProvided() {
        final GitHubUpdateBranchProtectionRequiredStatusCheck payload =
                GitHubUpdateBranchProtectionRequiredStatusCheckBuilder.builder()
                        .context("ci")
                        .appId(37L)
                        .build();

        assertThat(payload.appId()).isEqualTo(37);
    }
}
