package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAuditLogIncludeTest {

    @Test
    void fromWire_shouldReturnWeb_whenKnownValue() {
        assertThat(GitHubAuditLogInclude.fromWire("web")).isEqualTo(GitHubAuditLogInclude.WEB);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnWeb() {
        assertThat(GitHubAuditLogInclude.WEB.wireValue()).isEqualTo("web");
    }

    @Test
    void fromWire_shouldReturnGit_whenKnownValue() {
        assertThat(GitHubAuditLogInclude.fromWire("git")).isEqualTo(GitHubAuditLogInclude.GIT);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnGit() {
        assertThat(GitHubAuditLogInclude.GIT.wireValue()).isEqualTo("git");
    }

    @Test
    void fromWire_shouldReturnAll_whenKnownValue() {
        assertThat(GitHubAuditLogInclude.fromWire("all")).isEqualTo(GitHubAuditLogInclude.ALL);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnAll() {
        assertThat(GitHubAuditLogInclude.ALL.wireValue()).isEqualTo("all");
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown = catchThrowable(() -> GitHubAuditLogInclude.fromWire("not-a-known-value"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void fromWire_shouldThrowNullPointerException_whenNull() {
        final Throwable thrown = catchThrowable(() -> GitHubAuditLogInclude.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }
}
