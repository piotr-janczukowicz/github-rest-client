package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class GitHubRepositoryRuleCodeQualitySeverityTest {

    @ParameterizedTest
    @MethodSource("knownWire")
    void fromWire_shouldReturnType_whenKnownValue(
            final String wire, final GitHubRepositoryRuleCodeQualitySeverity value) {
        final GitHubRepositoryRuleCodeQualitySeverity decoded = GitHubRepositoryRuleCodeQualitySeverity.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @ParameterizedTest
    @MethodSource("invalidWire")
    void fromWire_shouldThrow_whenInvalid(
            final String wire, final Class<? extends Throwable> type, final String message) {
        final Throwable thrown = catchThrowable(() -> GitHubRepositoryRuleCodeQualitySeverity.fromWire(wire));

        assertThat(thrown).isInstanceOf(type).hasMessageContaining(message);
    }

    private static Stream<Arguments> knownWire() {
        return Stream.of(
                Arguments.of("errors", GitHubRepositoryRuleCodeQualitySeverity.ERRORS),
                Arguments.of("warnings", GitHubRepositoryRuleCodeQualitySeverity.WARNINGS),
                Arguments.of("notes", GitHubRepositoryRuleCodeQualitySeverity.NOTES),
                Arguments.of("all", GitHubRepositoryRuleCodeQualitySeverity.ALL));
    }

    private static Stream<Arguments> invalidWire() {
        return Stream.of(
                Arguments.of("not-a-severity", IllegalArgumentException.class, "not-a-severity"),
                Arguments.of(null, NullPointerException.class, "value"));
    }
}
