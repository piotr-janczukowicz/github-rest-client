package io.github.piotr_janczukowicz.github.api.copilot;

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
class GitHubCodingAgentPolicyStateTest {

    @ParameterizedTest
    @CsvSource({
        "enabled_for_all_orgs,ENABLED_FOR_ALL_ORGS,disabled_for_all_orgs,DISABLED_FOR_ALL_ORGS,enabled_for_selected_orgs,ENABLED_FOR_SELECTED_ORGS,configured_by_org_admins,CONFIGURED_BY_ORG_ADMINS"
    })
    void fromWire_shouldReturnType_whenKnownValue(final String wire, final GitHubCodingAgentPolicyState value) {
        final GitHubCodingAgentPolicyState decoded = GitHubCodingAgentPolicyState.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @ParameterizedTest
    @MethodSource("invalidWire")
    void fromWire_shouldThrow_whenInvalid(
            final String wire, final Class<? extends Throwable> type, final String message) {
        final Throwable thrown = catchThrowable(() -> GitHubCodingAgentPolicyState.fromWire(wire));

        assertThat(thrown).isInstanceOf(type).hasMessageContaining(message);
    }

    private static Stream<Arguments> invalidWire() {
        return Stream.of(
                Arguments.of("unknown", IllegalArgumentException.class, "unknown"),
                Arguments.of(null, NullPointerException.class, "value"));
    }
}
