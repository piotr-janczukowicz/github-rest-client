package io.github.piotr_janczukowicz.github.api.privateregistries;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class GitHubOrgPrivateRegistryConfigurationTest {

    private static final Instant CREATED_AT = Instant.parse("2024-01-01T00:00:00Z");
    private static final Instant UPDATED_AT = Instant.parse("2024-01-01T00:00:00Z");

    @ParameterizedTest
    @MethodSource("invalidConstructors")
    void constructor_shouldThrow_whenRequiredFieldNull(final ConstructorCall call, final String message) {
        final Throwable thrown = catchThrowable(call::run);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining(message);
    }

    private static Stream<Arguments> invalidConstructors() {
        return Stream.of(
                Arguments.of(
                        (ConstructorCall) () -> configuration(null, type(), visibility(), CREATED_AT, UPDATED_AT),
                        "name"),
                Arguments.of(
                        (ConstructorCall) () ->
                                configuration("MAVEN_REPOSITORY_SECRET", null, visibility(), CREATED_AT, UPDATED_AT),
                        "registryType"),
                Arguments.of(
                        (ConstructorCall)
                                () -> configuration("MAVEN_REPOSITORY_SECRET", type(), null, CREATED_AT, UPDATED_AT),
                        "visibility"),
                Arguments.of(
                        (ConstructorCall)
                                () -> configuration("MAVEN_REPOSITORY_SECRET", type(), visibility(), null, UPDATED_AT),
                        "createdAt"),
                Arguments.of(
                        (ConstructorCall)
                                () -> configuration("MAVEN_REPOSITORY_SECRET", type(), visibility(), CREATED_AT, null),
                        "updatedAt"));
    }

    private static GitHubPrivateRegistryType type() {
        return GitHubPrivateRegistryType.MAVEN_REPOSITORY;
    }

    private static GitHubPrivateRegistryVisibility visibility() {
        return GitHubPrivateRegistryVisibility.PRIVATE;
    }

    private static GitHubOrgPrivateRegistryConfiguration configuration(
            final String name,
            final GitHubPrivateRegistryType registryType,
            final GitHubPrivateRegistryVisibility visibility,
            final Instant createdAt,
            final Instant updatedAt) {
        return new GitHubOrgPrivateRegistryConfiguration(
                name,
                registryType,
                null,
                null,
                "monalisa",
                null,
                visibility,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                createdAt,
                updatedAt);
    }

    @FunctionalInterface
    interface ConstructorCall {
        void run();
    }
}
