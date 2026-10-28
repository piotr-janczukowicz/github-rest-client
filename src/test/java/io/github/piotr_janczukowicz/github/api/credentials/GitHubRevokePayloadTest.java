package io.github.piotr_janczukowicz.github.api.credentials;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class GitHubRevokePayloadTest {

    @Test
    void constructor_shouldCopyCredentials_whenWithinBounds() {
        final GitHubRevokePayload payload = CredentialsCatalogFixtures.revokePayload();

        assertThat(payload.credentials()).hasSize(5);
    }

    @Test
    void toString_shouldRedactCredentials() {
        final GitHubRevokePayload payload =
                new GitHubRevokePayload(List.of("ghp_secretValueOne", "ghp_secretValueTwo"));

        final String text = payload.toString();

        assertThat(text)
                .isEqualTo("GitHubRevokePayload[credentials=<redacted:2>]")
                .doesNotContain("ghp_");
    }

    @ParameterizedTest
    @MethodSource("invalidConstructors")
    void constructor_shouldThrow_whenInvalid(
            final ConstructorCall call, final Class<? extends Throwable> type, final String message) {
        final Throwable thrown = catchThrowable(call::run);

        assertThat(thrown).isInstanceOf(type).hasMessageContaining(message);
    }

    private static Stream<Arguments> invalidConstructors() {
        return Stream.of(
                Arguments.of(
                        (ConstructorCall) () -> GitHubRevokePayloadBuilder.builder()
                                .credentials(null)
                                .build(),
                        NullPointerException.class,
                        "credentials"),
                Arguments.of(
                        (ConstructorCall) () -> GitHubRevokePayloadBuilder.builder()
                                .credentials(List.of())
                                .build(),
                        IllegalArgumentException.class,
                        "credentials"),
                Arguments.of(
                        (ConstructorCall) () -> GitHubRevokePayloadBuilder.builder()
                                .credentials(tooManyCredentials())
                                .build(),
                        IllegalArgumentException.class,
                        "credentials"));
    }

    private static List<String> tooManyCredentials() {
        return IntStream.rangeClosed(1, 1001).mapToObj(i -> "ghp_" + i).toList();
    }

    @FunctionalInterface
    interface ConstructorCall {
        void run();
    }
}
