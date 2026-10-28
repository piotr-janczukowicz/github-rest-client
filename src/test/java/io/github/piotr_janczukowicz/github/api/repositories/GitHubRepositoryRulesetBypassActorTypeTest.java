package io.github.piotr_janczukowicz.github.api.repositories;

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
class GitHubRepositoryRulesetBypassActorTypeTest {

    @ParameterizedTest
    @CsvSource({
        "Integration,INTEGRATION",
        "OrganizationAdmin,ORGANIZATION_ADMIN",
        "RepositoryRole,REPOSITORY_ROLE",
        "Team,TEAM",
        "DeployKey,DEPLOY_KEY",
        "User,USER",
        "EnterpriseOwner,ENTERPRISE_OWNER",
        "EnterpriseRole,ENTERPRISE_ROLE"
    })
    void fromWire_shouldReturnType_whenKnownValue(
            final String wire, final GitHubRepositoryRulesetBypassActorType value) {
        final GitHubRepositoryRulesetBypassActorType decoded = GitHubRepositoryRulesetBypassActorType.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @ParameterizedTest
    @MethodSource("invalidWire")
    void fromWire_shouldThrow_whenInvalid(
            final String wire, final Class<? extends Throwable> type, final String message) {
        final Throwable thrown = catchThrowable(() -> GitHubRepositoryRulesetBypassActorType.fromWire(wire));

        assertThat(thrown).isInstanceOf(type).hasMessageContaining(message);
    }

    private static Stream<Arguments> invalidWire() {
        return Stream.of(
                Arguments.of("Bot", IllegalArgumentException.class, "Bot"),
                Arguments.of(null, NullPointerException.class, "value"));
    }
}
