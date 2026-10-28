package io.github.piotr_janczukowicz.github.api.organizations;

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
class GitHubCreateNetworkConfigurationComputeServiceTest {

    @ParameterizedTest
    @CsvSource({"none,NONE", "actions,ACTIONS"})
    void fromWire_shouldReturnComputeService_whenKnownValue(
            final String wire, final GitHubCreateNetworkConfigurationComputeService computeService) {
        final GitHubCreateNetworkConfigurationComputeService decoded =
                GitHubCreateNetworkConfigurationComputeService.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(computeService);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @ParameterizedTest
    @MethodSource("invalidWire")
    void fromWire_shouldThrow_whenInvalid(
            final String wire, final Class<? extends Throwable> type, final String message) {
        final Throwable thrown = catchThrowable(() -> GitHubCreateNetworkConfigurationComputeService.fromWire(wire));

        assertThat(thrown).isInstanceOf(type).hasMessageContaining(message);
    }

    private static Stream<Arguments> invalidWire() {
        return Stream.of(
                Arguments.of("codespaces", IllegalArgumentException.class, "codespaces"),
                Arguments.of("invalid", IllegalArgumentException.class, "invalid"),
                Arguments.of(null, NullPointerException.class, "value"));
    }
}
