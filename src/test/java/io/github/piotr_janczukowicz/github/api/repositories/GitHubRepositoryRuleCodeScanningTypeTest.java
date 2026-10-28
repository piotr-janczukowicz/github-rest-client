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
class GitHubRepositoryRuleCodeScanningTypeTest {

    @ParameterizedTest
    @MethodSource("knownWire")
    void fromWire_shouldReturnType_whenKnownValue(final String wire, final GitHubRepositoryRuleCodeScanningType value) {
        final GitHubRepositoryRuleCodeScanningType decoded = GitHubRepositoryRuleCodeScanningType.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @ParameterizedTest
    @MethodSource("invalidWire")
    void fromWire_shouldThrow_whenInvalid(
            final String wire, final Class<? extends Throwable> type, final String message) {
        final Throwable thrown = catchThrowable(() -> GitHubRepositoryRuleCodeScanningType.fromWire(wire));

        assertThat(thrown).isInstanceOf(type).hasMessageContaining(message);
    }

    private static Stream<Arguments> knownWire() {
        return Stream.of(Arguments.of("code_scanning", GitHubRepositoryRuleCodeScanningType.CODE_SCANNING));
    }

    private static Stream<Arguments> invalidWire() {
        return Stream.of(
                Arguments.of("not-a-type", IllegalArgumentException.class, "not-a-type"),
                Arguments.of(null, NullPointerException.class, "value"));
    }
}
