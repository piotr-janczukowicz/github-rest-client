package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@UnitTest
class GitHubListCacheInfoItemGitSyncStatusTest {

    @ParameterizedTest
    @CsvSource({"offline,OFFLINE", "inactive,INACTIVE", "in_sync,IN_SYNC", "not_in_sync,NOT_IN_SYNC"})
    void fromWire_shouldReturnStatus_whenKnownValue(
            final String wire, final GitHubListCacheInfoItemGitSyncStatus value) {
        final GitHubListCacheInfoItemGitSyncStatus decoded = GitHubListCacheInfoItemGitSyncStatus.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        final GitHubListCacheInfoItemGitSyncStatus decoded = GitHubListCacheInfoItemGitSyncStatus.fromWire("new_state");

        assertThat(decoded).isEqualTo(GitHubListCacheInfoItemGitSyncStatus.UNKNOWN);
    }

    @Test
    void fromWire_shouldThrow_whenValueNull() {
        final Throwable thrown = catchThrowable(() -> GitHubListCacheInfoItemGitSyncStatus.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }
}
