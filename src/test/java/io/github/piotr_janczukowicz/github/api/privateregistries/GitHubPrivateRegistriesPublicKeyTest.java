package io.github.piotr_janczukowicz.github.api.privateregistries;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class GitHubPrivateRegistriesPublicKeyTest {

    @ParameterizedTest
    @MethodSource("invalidConstructors")
    void constructor_shouldThrow_whenRequiredFieldNull(final ConstructorCall call, final String message) {
        final Throwable thrown = catchThrowable(call::run);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining(message);
    }

    private static Stream<Arguments> invalidConstructors() {
        return Stream.of(
                Arguments.of((ConstructorCall) () -> new GitHubPrivateRegistriesPublicKey(null, "key"), "keyId"),
                Arguments.of((ConstructorCall) () -> new GitHubPrivateRegistriesPublicKey("key-id", null), "key"));
    }

    @FunctionalInterface
    interface ConstructorCall {
        void run();
    }
}
