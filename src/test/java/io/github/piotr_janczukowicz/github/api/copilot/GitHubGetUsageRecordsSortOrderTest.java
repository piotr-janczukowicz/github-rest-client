package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetUsageRecordsSortOrderTest {

    @Test
    void fromWire_shouldReturnDesc_whenKnownValue() {
        assertThat(GitHubGetUsageRecordsSortOrder.fromWire("desc")).isEqualTo(GitHubGetUsageRecordsSortOrder.DESC);
    }

    @Test
    void fromWire_shouldReturnAsc_whenKnownValue() {
        assertThat(GitHubGetUsageRecordsSortOrder.fromWire("asc")).isEqualTo(GitHubGetUsageRecordsSortOrder.ASC);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown = catchThrowable(() -> GitHubGetUsageRecordsSortOrder.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }
}
