package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class GitHubCreatePushProtectionBypassPayloadTest {

    @Test
    void constructor_shouldAcceptRequiredFields_whenPresent() {
        final GitHubCreatePushProtectionBypassPayload payload = new GitHubCreatePushProtectionBypassPayload(
                GitHubSecretScanningPushProtectionBypassReason.FALSE_POSITIVE, "placeholder");

        assertSoftly(softly -> {
            softly.assertThat(payload).hasNoNullFieldsOrProperties();
            softly.assertThat(payload.reason())
                    .isEqualTo(GitHubSecretScanningPushProtectionBypassReason.FALSE_POSITIVE);
            softly.assertThat(payload.placeholderId()).isEqualTo("placeholder");
        });
    }

    @ParameterizedTest
    @MethodSource("nullConstructors")
    void constructor_shouldThrow_whenRequiredArgNull(final ConstructorCall call, final String message) {
        final Throwable thrown = catchThrowable(call::run);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining(message);
    }

    private static Stream<Arguments> nullConstructors() {
        return Stream.of(
                Arguments.of(
                        (ConstructorCall) () -> new GitHubCreatePushProtectionBypassPayload(null, "placeholder"),
                        "reason"),
                Arguments.of(
                        (ConstructorCall) () -> new GitHubCreatePushProtectionBypassPayload(
                                GitHubSecretScanningPushProtectionBypassReason.FALSE_POSITIVE, null),
                        "placeholderId"));
    }

    @FunctionalInterface
    interface ConstructorCall {
        void run();
    }
}
