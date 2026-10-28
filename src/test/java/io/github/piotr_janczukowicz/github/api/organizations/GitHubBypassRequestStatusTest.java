package io.github.piotr_janczukowicz.github.api.organizations;

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
    void wireValue_shouldReturnWire_whenCalledOnCompleted() {
        assertThat(GitHubBypassRequestStatus.COMPLETED.wireValue()).isEqualTo("completed");
    }

    @Test
    void fromWire_shouldReturnCancelled_whenKnownValue() {
        assertThat(GitHubBypassRequestStatus.fromWire("cancelled")).isEqualTo(GitHubBypassRequestStatus.CANCELLED);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnCancelled() {
        assertThat(GitHubBypassRequestStatus.CANCELLED.wireValue()).isEqualTo("cancelled");
    }

    @Test
    void fromWire_shouldReturnApproved_whenKnownValue() {
        assertThat(GitHubBypassRequestStatus.fromWire("approved")).isEqualTo(GitHubBypassRequestStatus.APPROVED);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnApproved() {
        assertThat(GitHubBypassRequestStatus.APPROVED.wireValue()).isEqualTo("approved");
    }

    @Test
    void fromWire_shouldReturnExpired_whenKnownValue() {
        assertThat(GitHubBypassRequestStatus.fromWire("expired")).isEqualTo(GitHubBypassRequestStatus.EXPIRED);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnExpired() {
        assertThat(GitHubBypassRequestStatus.EXPIRED.wireValue()).isEqualTo("expired");
    }

    @Test
    void fromWire_shouldReturnDeleted_whenKnownValue() {
        assertThat(GitHubBypassRequestStatus.fromWire("deleted")).isEqualTo(GitHubBypassRequestStatus.DELETED);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnDeleted() {
        assertThat(GitHubBypassRequestStatus.DELETED.wireValue()).isEqualTo("deleted");
    }

    @Test
    void fromWire_shouldReturnDenied_whenKnownValue() {
        assertThat(GitHubBypassRequestStatus.fromWire("denied")).isEqualTo(GitHubBypassRequestStatus.DENIED);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnDenied() {
        assertThat(GitHubBypassRequestStatus.DENIED.wireValue()).isEqualTo("denied");
    }

    @Test
    void fromWire_shouldReturnOpen_whenKnownValue() {
        assertThat(GitHubBypassRequestStatus.fromWire("open")).isEqualTo(GitHubBypassRequestStatus.OPEN);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnOpen() {
        assertThat(GitHubBypassRequestStatus.OPEN.wireValue()).isEqualTo("open");
    }

    @Test
    void fromWire_shouldReturnAll_whenKnownValue() {
        assertThat(GitHubBypassRequestStatus.fromWire("all")).isEqualTo(GitHubBypassRequestStatus.ALL);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnAll() {
        assertThat(GitHubBypassRequestStatus.ALL.wireValue()).isEqualTo("all");
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown = catchThrowable(() -> GitHubBypassRequestStatus.fromWire("not-a-known-value"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void fromWire_shouldThrowNullPointerException_whenNull() {
        final Throwable thrown = catchThrowable(() -> GitHubBypassRequestStatus.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }
}
