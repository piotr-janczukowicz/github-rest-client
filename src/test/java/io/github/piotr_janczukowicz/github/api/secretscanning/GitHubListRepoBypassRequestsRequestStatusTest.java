package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@UnitTest
class GitHubListRepoBypassRequestsRequestStatusTest {

    @ParameterizedTest
    @CsvSource({
        "completed,COMPLETED",
        "cancelled,CANCELLED",
        "approved,APPROVED",
        "expired,EXPIRED",
        "deleted,DELETED",
        "denied,DENIED",
        "open,OPEN",
        "all,ALL"
    })
    void fromWire_shouldReturnValue_whenKnownValue(
            final String wire, final GitHubListRepoBypassRequestsRequestStatus value) {
        final GitHubListRepoBypassRequestsRequestStatus decoded =
                GitHubListRepoBypassRequestsRequestStatus.fromWire(wire);

        assertThat(decoded).isEqualTo(value);
        assertThat(decoded.wireValue()).isEqualTo(wire);
    }

    @Test
    void fromWire_shouldThrow_whenUnknownValue() {
        final Throwable thrown = catchThrowable(() -> GitHubListRepoBypassRequestsRequestStatus.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }

    @Test
    void fromWire_shouldThrow_whenNull() {
        final Throwable thrown = catchThrowable(() -> GitHubListRepoBypassRequestsRequestStatus.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }
}
