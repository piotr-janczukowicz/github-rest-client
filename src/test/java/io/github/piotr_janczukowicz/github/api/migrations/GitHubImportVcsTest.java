package io.github.piotr_janczukowicz.github.api.migrations;

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
class GitHubImportVcsTest {

    @ParameterizedTest
    @CsvSource({"subversion,SUBVERSION", "git,GIT", "mercurial,MERCURIAL", "tfvc,TFVC"})
    void fromWire_shouldReturnVcs_whenKnownValue(final String wire, final GitHubImportVcs vcs) {
        final GitHubImportVcs decoded = GitHubImportVcs.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(vcs);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @ParameterizedTest
    @MethodSource("invalidWire")
    void fromWire_shouldThrow_whenInvalid(
            final String wire, final Class<? extends Throwable> type, final String message) {
        final Throwable thrown = catchThrowable(() -> GitHubImportVcs.fromWire(wire));

        assertThat(thrown).isInstanceOf(type).hasMessageContaining(message);
    }

    private static Stream<Arguments> invalidWire() {
        return Stream.of(
                Arguments.of("cvs", IllegalArgumentException.class, "cvs"),
                Arguments.of(null, NullPointerException.class, "value"));
    }
}
