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
class GitHubOrganizationSecurityProductEnablementTest {

    @ParameterizedTest
    @CsvSource({"enable_all,ENABLE_ALL", "disable_all,DISABLE_ALL"})
    void fromWire_shouldReturnEnablement_whenKnownValue(
            final String wire, final GitHubOrganizationSecurityProductEnablement type) {
        final GitHubOrganizationSecurityProductEnablement decoded =
                GitHubOrganizationSecurityProductEnablement.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(type);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @ParameterizedTest
    @MethodSource("invalidWire")
    void fromWire_shouldThrow_whenInvalid(
            final String wire, final Class<? extends Throwable> type, final String message) {
        final Throwable thrown = catchThrowable(() -> GitHubOrganizationSecurityProductEnablement.fromWire(wire));

        assertThat(thrown).isInstanceOf(type).hasMessageContaining(message);
    }

    private static Stream<Arguments> invalidWire() {
        return Stream.of(
                Arguments.of("unknown", IllegalArgumentException.class, "unknown"),
                Arguments.of(null, NullPointerException.class, "value"));
    }
}
