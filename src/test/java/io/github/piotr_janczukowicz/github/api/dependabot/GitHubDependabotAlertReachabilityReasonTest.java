package io.github.piotr_janczukowicz.github.api.dependabot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@UnitTest
class GitHubDependabotAlertReachabilityReasonTest {

    @ParameterizedTest
    @CsvSource({
        "unknown,UNKNOWN",
        "unavailable_unsupported_ecosystem,UNAVAILABLE_UNSUPPORTED_ECOSYSTEM",
        "unavailable_analysis_timed_out,UNAVAILABLE_ANALYSIS_TIMED_OUT",
        "potentially_reachable_invalid_manifest_path,POTENTIALLY_REACHABLE_INVALID_MANIFEST_PATH",
        "potentially_reachable_missing_vulnerable_function_annotation,POTENTIALLY_REACHABLE_MISSING_VULNERABLE_FUNCTION_ANNOTATION",
        "potentially_reachable_dependency_not_found,POTENTIALLY_REACHABLE_DEPENDENCY_NOT_FOUND",
        "potentially_reachable_dependency_analysis_incomplete,POTENTIALLY_REACHABLE_DEPENDENCY_ANALYSIS_INCOMPLETE",
        "potentially_reachable_repository_analysis_incomplete,POTENTIALLY_REACHABLE_REPOSITORY_ANALYSIS_INCOMPLETE",
        "potentially_reachable_call_found_outside_manifest_scope,POTENTIALLY_REACHABLE_CALL_FOUND_OUTSIDE_MANIFEST_SCOPE",
        "potentially_reachable_unclassified_analysis_state,POTENTIALLY_REACHABLE_UNCLASSIFIED_ANALYSIS_STATE",
        "reachable_vulnerable_function_called,REACHABLE_VULNERABLE_FUNCTION_CALLED",
        "unreachable_vulnerable_function_not_called,UNREACHABLE_VULNERABLE_FUNCTION_NOT_CALLED",
        "potentially_reachable_unresolved_dynamic_call,POTENTIALLY_REACHABLE_UNRESOLVED_DYNAMIC_CALL"
    })
    void fromWire_shouldReturnConstant_whenKnownValue(
            final String wire, final GitHubDependabotAlertReachabilityReason value) {
        final GitHubDependabotAlertReachabilityReason decoded = GitHubDependabotAlertReachabilityReason.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @Test
    void fromWire_shouldThrow_whenValueNull() {
        final Throwable thrown = catchThrowable(() -> GitHubDependabotAlertReachabilityReason.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }

    @Test
    void fromWire_shouldReturnUnrecognized_whenValueNotKnown() {
        final String wire = "brand_new_value";

        final GitHubDependabotAlertReachabilityReason decoded = GitHubDependabotAlertReachabilityReason.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(GitHubDependabotAlertReachabilityReason.UNRECOGNIZED);
            softly.assertThat(decoded.wireValue()).isEqualTo("unrecognized");
        });
    }
}
