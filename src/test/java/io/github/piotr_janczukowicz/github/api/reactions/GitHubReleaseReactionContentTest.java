package io.github.piotr_janczukowicz.github.api.reactions;

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
class GitHubReleaseReactionContentTest {

    @ParameterizedTest
    @CsvSource({"+1,PLUS_ONE", "laugh,LAUGH", "heart,HEART", "hooray,HOORAY", "rocket,ROCKET", "eyes,EYES"})
    void fromWire_shouldReturnType_whenKnownValue(final String wire, final GitHubReleaseReactionContent value) {
        final GitHubReleaseReactionContent decoded = GitHubReleaseReactionContent.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @ParameterizedTest
    @MethodSource("invalidWire")
    void fromWire_shouldThrow_whenInvalid(
            final String wire, final Class<? extends Throwable> type, final String message) {
        final Throwable thrown = catchThrowable(() -> GitHubReleaseReactionContent.fromWire(wire));

        assertThat(thrown).isInstanceOf(type).hasMessageContaining(message);
    }

    private static Stream<Arguments> invalidWire() {
        return Stream.of(
                Arguments.of("confused", IllegalArgumentException.class, "confused"),
                Arguments.of("-1", IllegalArgumentException.class, "-1"),
                Arguments.of(null, NullPointerException.class, "value"));
    }
}
