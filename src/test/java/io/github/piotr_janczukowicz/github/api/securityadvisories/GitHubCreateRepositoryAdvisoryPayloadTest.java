package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class GitHubCreateRepositoryAdvisoryPayloadTest {

    @ParameterizedTest
    @MethodSource("invalidConstructors")
    void constructor_shouldThrow_whenInvalid(
            final ConstructorCall call, final Class<? extends Throwable> type, final String message) {
        final Throwable thrown = catchThrowable(call::run);

        assertThat(thrown).isInstanceOf(type).hasMessageContaining(message);
    }

    private static Stream<Arguments> invalidConstructors() {
        final List<GitHubRepositoryAdvisoryVulnerabilityPayload> vulnerabilities =
                List.of(SecurityAdvisoriesCatalogFixtures.repositoryAdvisoryVulnerabilityPayload());
        return Stream.of(
                Arguments.of(
                        (ConstructorCall) () -> new GitHubCreateRepositoryAdvisoryPayload(
                                "a".repeat(1025), "description", vulnerabilities, null, null, null, null, null, null),
                        IllegalArgumentException.class,
                        "summary"),
                Arguments.of(
                        (ConstructorCall) () -> new GitHubCreateRepositoryAdvisoryPayload(
                                "summary", "a".repeat(65_536), vulnerabilities, null, null, null, null, null, null),
                        IllegalArgumentException.class,
                        "description"),
                Arguments.of(
                        (ConstructorCall) () -> new GitHubCreateRepositoryAdvisoryPayload(
                                null, "description", vulnerabilities, null, null, null, null, null, null),
                        NullPointerException.class,
                        "summary"),
                Arguments.of(
                        (ConstructorCall) () -> new GitHubCreateRepositoryAdvisoryPayload(
                                "summary", null, vulnerabilities, null, null, null, null, null, null),
                        NullPointerException.class,
                        "description"),
                Arguments.of(
                        (ConstructorCall) () -> new GitHubCreateRepositoryAdvisoryPayload(
                                "summary", "description", null, null, null, null, null, null, null),
                        NullPointerException.class,
                        "vulnerabilities"));
    }

    @FunctionalInterface
    interface ConstructorCall {
        void run();
    }
}
