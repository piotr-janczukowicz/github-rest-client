package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetAuditLogOrderTest {

    @Test
    void fromWire_shouldReturnDesc_whenKnownValue() {
        assertThat(GitHubGetAuditLogOrder.fromWire("desc")).isEqualTo(GitHubGetAuditLogOrder.DESC);
    }

    @Test
    void fromWire_shouldReturnAsc_whenKnownValue() {
        assertThat(GitHubGetAuditLogOrder.fromWire("asc")).isEqualTo(GitHubGetAuditLogOrder.ASC);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown = catchThrowable(() -> GitHubGetAuditLogOrder.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }
}
