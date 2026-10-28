package io.github.piotr_janczukowicz.github.api.secretscanning;

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
class GitHubBulkDeleteEnterpriseCustomPatternsPostDeleteActionTest {

    @ParameterizedTest
    @CsvSource({"delete_alerts,DELETE_ALERTS", "resolve_alerts,RESOLVE_ALERTS"})
    void fromWire_shouldReturnAction_whenKnownValue(
            final String wire, final GitHubBulkDeleteEnterpriseCustomPatternsPostDeleteAction value) {
        final GitHubBulkDeleteEnterpriseCustomPatternsPostDeleteAction decoded =
                GitHubBulkDeleteEnterpriseCustomPatternsPostDeleteAction.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @ParameterizedTest
    @MethodSource("invalidWire")
    void fromWire_shouldThrow_whenInvalid(
            final String wire, final Class<? extends Throwable> type, final String message) {
        final Throwable thrown =
                catchThrowable(() -> GitHubBulkDeleteEnterpriseCustomPatternsPostDeleteAction.fromWire(wire));

        assertThat(thrown).isInstanceOf(type).hasMessageContaining(message);
    }

    private static Stream<Arguments> invalidWire() {
        return Stream.of(
                Arguments.of("unknown", IllegalArgumentException.class, "unknown"),
                Arguments.of(null, NullPointerException.class, "value"));
    }
}
