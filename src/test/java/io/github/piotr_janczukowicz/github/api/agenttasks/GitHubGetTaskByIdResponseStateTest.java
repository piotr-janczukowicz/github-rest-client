package io.github.piotr_janczukowicz.github.api.agenttasks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@UnitTest
class GitHubGetTaskByIdResponseStateTest {

    @ParameterizedTest
    @CsvSource({
        "queued,QUEUED",
        "in_progress,IN_PROGRESS",
        "completed,COMPLETED",
        "failed,FAILED",
        "idle,IDLE",
        "waiting_for_user,WAITING_FOR_USER",
        "timed_out,TIMED_OUT",
        "cancelled,CANCELLED"
    })
    void fromWire_shouldReturnValue_whenKnownValue(final String wire, final GitHubGetTaskByIdResponseState value) {
        final GitHubGetTaskByIdResponseState decoded = GitHubGetTaskByIdResponseState.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @Test
    void fromWire_shouldThrow_whenNull() {
        final Throwable thrown = catchThrowable(() -> GitHubGetTaskByIdResponseState.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        final String wire = "brand_new_value";

        final GitHubGetTaskByIdResponseState decoded = GitHubGetTaskByIdResponseState.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(GitHubGetTaskByIdResponseState.UNKNOWN);
            softly.assertThat(decoded.wireValue()).isEqualTo("unknown");
        });
    }
}
