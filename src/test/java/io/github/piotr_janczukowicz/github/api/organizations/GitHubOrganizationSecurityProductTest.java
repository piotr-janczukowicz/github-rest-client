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
class GitHubOrganizationSecurityProductTest {

    @ParameterizedTest
    @CsvSource({
        "dependency_graph,DEPENDENCY_GRAPH",
        "dependabot_alerts,DEPENDABOT_ALERTS",
        "dependabot_security_updates,DEPENDABOT_SECURITY_UPDATES",
        "advanced_security,ADVANCED_SECURITY",
        "code_scanning_default_setup,CODE_SCANNING_DEFAULT_SETUP",
        "secret_scanning,SECRET_SCANNING",
        "secret_scanning_push_protection,SECRET_SCANNING_PUSH_PROTECTION"
    })
    void fromWire_shouldReturnProduct_whenKnownValue(final String wire, final GitHubOrganizationSecurityProduct type) {
        final GitHubOrganizationSecurityProduct decoded = GitHubOrganizationSecurityProduct.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(type);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @ParameterizedTest
    @MethodSource("invalidWire")
    void fromWire_shouldThrow_whenInvalid(
            final String wire, final Class<? extends Throwable> type, final String message) {
        final Throwable thrown = catchThrowable(() -> GitHubOrganizationSecurityProduct.fromWire(wire));

        assertThat(thrown).isInstanceOf(type).hasMessageContaining(message);
    }

    private static Stream<Arguments> invalidWire() {
        return Stream.of(
                Arguments.of("unknown", IllegalArgumentException.class, "unknown"),
                Arguments.of(null, NullPointerException.class, "value"));
    }
}
