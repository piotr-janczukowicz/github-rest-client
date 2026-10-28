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
class GitHubPrivateRegistryTypeTest {

    @ParameterizedTest
    @CsvSource({
        "maven_repository,MAVEN_REPOSITORY",
        "nuget_feed,NUGET_FEED",
        "goproxy_server,GOPROXY_SERVER",
        "npm_registry,NPM_REGISTRY",
        "rubygems_server,RUBYGEMS_SERVER",
        "cargo_registry,CARGO_REGISTRY",
        "composer_repository,COMPOSER_REPOSITORY",
        "docker_registry,DOCKER_REGISTRY",
        "git_source,GIT_SOURCE",
        "helm_registry,HELM_REGISTRY",
        "hex_organization,HEX_ORGANIZATION",
        "hex_repository,HEX_REPOSITORY",
        "pub_repository,PUB_REPOSITORY",
        "python_index,PYTHON_INDEX",
        "terraform_registry,TERRAFORM_REGISTRY"
    })
    void fromWire_shouldReturnType_whenKnownValue(final String wire, final GitHubPrivateRegistryType value) {
        final GitHubPrivateRegistryType decoded = GitHubPrivateRegistryType.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @ParameterizedTest
    @MethodSource("invalidWire")
    void fromWire_shouldThrow_whenInvalid(
            final String wire, final Class<? extends Throwable> type, final String message) {
        final Throwable thrown = catchThrowable(() -> GitHubPrivateRegistryType.fromWire(wire));

        assertThat(thrown).isInstanceOf(type).hasMessageContaining(message);
    }

    private static Stream<Arguments> invalidWire() {
        return Stream.of(
                Arguments.of("unknown", IllegalArgumentException.class, "unknown"),
                Arguments.of(null, NullPointerException.class, "value"));
    }
}
