package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAuditLogOrderTest {

    @Test
    void fromWire_shouldReturnDesc_whenKnownValue() {
        assertThat(GitHubAuditLogOrder.fromWire("desc")).isEqualTo(GitHubAuditLogOrder.DESC);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnDesc() {
        assertThat(GitHubAuditLogOrder.DESC.wireValue()).isEqualTo("desc");
    }

    @Test
    void fromWire_shouldReturnAsc_whenKnownValue() {
        assertThat(GitHubAuditLogOrder.fromWire("asc")).isEqualTo(GitHubAuditLogOrder.ASC);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnAsc() {
        assertThat(GitHubAuditLogOrder.ASC.wireValue()).isEqualTo("asc");
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown = catchThrowable(() -> GitHubAuditLogOrder.fromWire("not-a-known-value"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void fromWire_shouldThrowNullPointerException_whenNull() {
        final Throwable thrown = catchThrowable(() -> GitHubAuditLogOrder.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }
}
