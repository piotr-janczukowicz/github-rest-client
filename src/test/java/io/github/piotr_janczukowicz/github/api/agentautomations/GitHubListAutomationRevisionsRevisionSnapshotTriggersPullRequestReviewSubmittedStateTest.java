package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@UnitTest
class GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewSubmittedStateTest {

    @ParameterizedTest
    @CsvSource({"approved,APPROVED", "changes_requested,CHANGES_REQUESTED", "commented,COMMENTED"})
    void fromWire_shouldReturnConstant_whenKnownValue(
            final String wire,
            final GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewSubmittedState value) {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewSubmittedState decoded =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewSubmittedState.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @Test
    void fromWire_shouldThrow_whenValueNull() {
        final Throwable thrown = catchThrowable(() ->
                GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewSubmittedState.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }

    @Test
    void fromWire_shouldReturnUnrecognized_whenValueNotKnown() {
        final String wire = "brand_new_value";

        final GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewSubmittedState decoded =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewSubmittedState.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded)
                    .isEqualTo(
                            GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewSubmittedState
                                    .UNRECOGNIZED);
            softly.assertThat(decoded.wireValue()).isEqualTo("unrecognized");
        });
    }
}
