package io.github.piotr_janczukowicz.github.api.privateregistries;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class GitHubOrgPrivateRegistryConfigurationWithSelectedRepositoriesTest {

    @Test
    void constructor_shouldKeepSelectedRepositoryIdsNull_whenIdsNull() {
        final GitHubOrgPrivateRegistryConfigurationWithSelectedRepositories configuration = configuration(
                "MAVEN_REPOSITORY_SECRET",
                type(),
                visibility(),
                Instant.parse("2024-01-01T00:00:00Z"),
                Instant.parse("2024-01-01T00:00:00Z"));

        assertThat(configuration.selectedRepositoryIds()).isNull();
    }

    @ParameterizedTest
    @MethodSource("invalidConstructors")
    void constructor_shouldThrow_whenRequiredFieldNull(final ConstructorCall call, final String message) {
        final Throwable thrown = catchThrowable(call::run);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining(message);
    }

    private static Stream<Arguments> invalidConstructors() {
        final Instant createdAt = Instant.parse("2024-01-01T00:00:00Z");
        final Instant updatedAt = Instant.parse("2024-01-01T00:00:00Z");
        return Stream.of(
                Arguments.of(
                        (ConstructorCall) () -> configuration(null, type(), visibility(), createdAt, updatedAt),
                        "name"),
                Arguments.of(
                        (ConstructorCall) () ->
                                configuration("MAVEN_REPOSITORY_SECRET", null, visibility(), createdAt, updatedAt),
                        "registryType"),
                Arguments.of(
                        (ConstructorCall)
                                () -> configuration("MAVEN_REPOSITORY_SECRET", type(), null, createdAt, updatedAt),
                        "visibility"),
                Arguments.of(
                        (ConstructorCall)
                                () -> configuration("MAVEN_REPOSITORY_SECRET", type(), visibility(), null, updatedAt),
                        "createdAt"),
                Arguments.of(
                        (ConstructorCall)
                                () -> configuration("MAVEN_REPOSITORY_SECRET", type(), visibility(), createdAt, null),
                        "updatedAt"));
    }

    private static GitHubPrivateRegistryType type() {
        return GitHubPrivateRegistryType.MAVEN_REPOSITORY;
    }

    private static GitHubPrivateRegistryVisibility visibility() {
        return GitHubPrivateRegistryVisibility.SELECTED;
    }

    private static GitHubOrgPrivateRegistryConfigurationWithSelectedRepositories configuration(
            final String name,
            final GitHubPrivateRegistryType registryType,
            final GitHubPrivateRegistryVisibility visibility,
            final Instant createdAt,
            final Instant updatedAt) {
        return new GitHubOrgPrivateRegistryConfigurationWithSelectedRepositories(
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
                null,
                createdAt,
                updatedAt);
    }

    @FunctionalInterface
    interface ConstructorCall {
        void run();
    }
}
