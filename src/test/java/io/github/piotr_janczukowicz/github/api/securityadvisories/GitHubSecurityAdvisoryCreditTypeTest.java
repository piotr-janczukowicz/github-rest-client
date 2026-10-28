package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class GitHubSecurityAdvisoryCreditTypeTest {

    @ParameterizedTest
    @CsvSource({
        "analyst,ANALYST",
        "finder,FINDER",
        "reporter,REPORTER",
        "coordinator,COORDINATOR",
        "remediation_developer,REMEDIATION_DEVELOPER",
        "remediation_reviewer,REMEDIATION_REVIEWER",
        "remediation_verifier,REMEDIATION_VERIFIER",
        "tool,TOOL",
        "sponsor,SPONSOR",
        "other,OTHER"
    })
    void fromWire_shouldReturnType_whenKnownValue(final String wire, final GitHubSecurityAdvisoryCreditType value) {
        final GitHubSecurityAdvisoryCreditType decoded = GitHubSecurityAdvisoryCreditType.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @ParameterizedTest
    @MethodSource("invalidWire")
    void fromWire_shouldThrow_whenInvalid(
            final String wire, final Class<? extends Throwable> type, final String message) {
        final Throwable thrown = catchThrowable(() -> GitHubSecurityAdvisoryCreditType.fromWire(wire));

        assertThat(thrown).isInstanceOf(type).hasMessageContaining(message);
    }

    private static Stream<Arguments> invalidWire() {
        return Stream.of(
                Arguments.of("unknown", IllegalArgumentException.class, "unknown"),
                Arguments.of(null, NullPointerException.class, "value"));
    }
}
