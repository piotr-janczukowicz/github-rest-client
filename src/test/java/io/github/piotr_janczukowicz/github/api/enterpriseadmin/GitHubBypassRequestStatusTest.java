package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubBypassRequestStatusTest {

    @Test
    void fromWire_shouldReturnCompleted_whenKnownValue() {
        assertThat(GitHubBypassRequestStatus.fromWire("completed")).isEqualTo(GitHubBypassRequestStatus.COMPLETED);
    }

    @Test
    void fromWire_shouldReturnCancelled_whenKnownValue() {
        assertThat(GitHubBypassRequestStatus.fromWire("cancelled")).isEqualTo(GitHubBypassRequestStatus.CANCELLED);
    }

    @Test
    void fromWire_shouldReturnApproved_whenKnownValue() {
        assertThat(GitHubBypassRequestStatus.fromWire("approved")).isEqualTo(GitHubBypassRequestStatus.APPROVED);
    }

    @Test
    void fromWire_shouldReturnExpired_whenKnownValue() {
        assertThat(GitHubBypassRequestStatus.fromWire("expired")).isEqualTo(GitHubBypassRequestStatus.EXPIRED);
    }

    @Test
    void fromWire_shouldReturnDeleted_whenKnownValue() {
        assertThat(GitHubBypassRequestStatus.fromWire("deleted")).isEqualTo(GitHubBypassRequestStatus.DELETED);
    }

    @Test
    void fromWire_shouldReturnDenied_whenKnownValue() {
        assertThat(GitHubBypassRequestStatus.fromWire("denied")).isEqualTo(GitHubBypassRequestStatus.DENIED);
    }

    @Test
    void fromWire_shouldReturnOpen_whenKnownValue() {
        assertThat(GitHubBypassRequestStatus.fromWire("open")).isEqualTo(GitHubBypassRequestStatus.OPEN);
    }

    @Test
    void fromWire_shouldReturnAll_whenKnownValue() {
        assertThat(GitHubBypassRequestStatus.fromWire("all")).isEqualTo(GitHubBypassRequestStatus.ALL);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown = catchThrowable(() -> GitHubBypassRequestStatus.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }
}
