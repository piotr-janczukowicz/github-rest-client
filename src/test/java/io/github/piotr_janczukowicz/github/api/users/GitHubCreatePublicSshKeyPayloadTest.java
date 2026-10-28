package io.github.piotr_janczukowicz.github.api.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class GitHubCreatePublicSshKeyPayloadTest {

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
                        (ConstructorCall) () -> new GitHubCreatePublicSshKeyPayload(null, null),
                        NullPointerException.class,
                        "key"),
                Arguments.of(
                        (ConstructorCall) () -> new GitHubCreatePublicSshKeyPayload(null, "not-a-valid-key"),
                        IllegalArgumentException.class,
                        "key"));
    }

    @FunctionalInterface
    interface ConstructorCall {
        void run();
    }
}
