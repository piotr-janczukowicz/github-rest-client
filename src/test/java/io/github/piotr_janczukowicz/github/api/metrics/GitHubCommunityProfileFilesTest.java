package io.github.piotr_janczukowicz.github.api.metrics;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class GitHubCommunityProfileFilesTest {

    @Test
    void constructor_shouldAcceptExplicitNullFiles_whenWrappersPresent() {
        final GitHubCommunityProfileFiles files = new GitHubCommunityProfileFiles(
                OptionalNull.ofNull(),
                OptionalNull.ofNull(),
                OptionalNull.ofNull(),
                OptionalNull.ofNull(),
                OptionalNull.ofNull(),
                OptionalNull.ofNull(),
                OptionalNull.ofNull());

        assertThat(files.codeOfConduct().value()).isNull();
    }

    @ParameterizedTest
    @MethodSource("invalidConstructors")
    void constructor_shouldThrow_whenRequiredFieldNull(final ConstructorCall call, final String message) {
        final Throwable thrown = catchThrowable(call::run);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining(message);
    }

    private static Stream<Arguments> invalidConstructors() {
        final OptionalNull<GitHubCommunityHealthFile> file = OptionalNull.ofNull();
        return Stream.of(
                Arguments.of(
                        (ConstructorCall) () -> new GitHubCommunityProfileFiles(
                                null, file, OptionalNull.ofNull(), file, file, file, file),
                        "codeOfConduct"),
                Arguments.of(
                        (ConstructorCall) () -> new GitHubCommunityProfileFiles(
                                OptionalNull.ofNull(), null, OptionalNull.ofNull(), file, file, file, file),
                        "codeOfConductFile"),
                Arguments.of(
                        (ConstructorCall) () -> new GitHubCommunityProfileFiles(
                                OptionalNull.ofNull(), file, null, file, file, file, file),
                        "license"),
                Arguments.of(
                        (ConstructorCall) () -> new GitHubCommunityProfileFiles(
                                OptionalNull.ofNull(), file, OptionalNull.ofNull(), null, file, file, file),
                        "contributing"),
                Arguments.of(
                        (ConstructorCall) () -> new GitHubCommunityProfileFiles(
                                OptionalNull.ofNull(), file, OptionalNull.ofNull(), file, null, file, file),
                        "readme"),
                Arguments.of(
                        (ConstructorCall) () -> new GitHubCommunityProfileFiles(
                                OptionalNull.ofNull(), file, OptionalNull.ofNull(), file, file, null, file),
                        "issueTemplate"),
                Arguments.of(
                        (ConstructorCall) () -> new GitHubCommunityProfileFiles(
                                OptionalNull.ofNull(), file, OptionalNull.ofNull(), file, file, file, null),
                        "pullRequestTemplate"));
    }

    @FunctionalInterface
    interface ConstructorCall {
        void run();
    }
}
