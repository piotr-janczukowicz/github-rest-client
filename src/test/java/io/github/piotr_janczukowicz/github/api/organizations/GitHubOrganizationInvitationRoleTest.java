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
class GitHubOrganizationInvitationRoleTest {

    @ParameterizedTest
    @CsvSource({"admin,ADMIN", "direct_member,DIRECT_MEMBER", "billing_manager,BILLING_MANAGER", "reinstate,REINSTATE"})
    void fromWire_shouldReturnRole_whenKnownValue(final String wire, final GitHubOrganizationInvitationRole role) {
        final GitHubOrganizationInvitationRole decoded = GitHubOrganizationInvitationRole.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(role);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @ParameterizedTest
    @MethodSource("invalidWire")
    void fromWire_shouldThrow_whenInvalid(
            final String wire, final Class<? extends Throwable> type, final String message) {
        final Throwable thrown = catchThrowable(() -> GitHubOrganizationInvitationRole.fromWire(wire));

        assertThat(thrown).isInstanceOf(type).hasMessageContaining(message);
    }

    private static Stream<Arguments> invalidWire() {
        return Stream.of(
                Arguments.of("unknown", IllegalArgumentException.class, "unknown"),
                Arguments.of(null, NullPointerException.class, "value"));
    }
}
