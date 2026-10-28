package io.github.piotr_janczukowicz.github.api.privateregistries;

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
class GitHubPrivateRegistryAuthTypeTest {

    @ParameterizedTest
    @CsvSource({
        "token,TOKEN",
        "username_password,USERNAME_PASSWORD",
        "oidc_azure,OIDC_AZURE",
        "oidc_aws,OIDC_AWS",
        "oidc_jfrog,OIDC_JFROG",
        "oidc_cloudsmith,OIDC_CLOUDSMITH",
        "oidc_gcp,OIDC_GCP",
        "anonymous,ANONYMOUS"
    })
    void fromWire_shouldReturnType_whenKnownValue(final String wire, final GitHubPrivateRegistryAuthType value) {
        final GitHubPrivateRegistryAuthType decoded = GitHubPrivateRegistryAuthType.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @ParameterizedTest
    @MethodSource("invalidWire")
    void fromWire_shouldThrow_whenInvalid(
            final String wire, final Class<? extends Throwable> type, final String message) {
        final Throwable thrown = catchThrowable(() -> GitHubPrivateRegistryAuthType.fromWire(wire));

        assertThat(thrown).isInstanceOf(type).hasMessageContaining(message);
    }

    private static Stream<Arguments> invalidWire() {
        return Stream.of(
                Arguments.of("unknown", IllegalArgumentException.class, "unknown"),
                Arguments.of(null, NullPointerException.class, "value"));
    }
}
