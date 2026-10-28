package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetAuditLogIncludeTest {

    @Test
    void fromWire_shouldReturnWeb_whenKnownValue() {
        assertThat(GitHubGetAuditLogInclude.fromWire("web")).isEqualTo(GitHubGetAuditLogInclude.WEB);
    }

    @Test
    void fromWire_shouldReturnGit_whenKnownValue() {
        assertThat(GitHubGetAuditLogInclude.fromWire("git")).isEqualTo(GitHubGetAuditLogInclude.GIT);
    }

    @Test
    void fromWire_shouldReturnAll_whenKnownValue() {
        assertThat(GitHubGetAuditLogInclude.fromWire("all")).isEqualTo(GitHubGetAuditLogInclude.ALL);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown = catchThrowable(() -> GitHubGetAuditLogInclude.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }
}
