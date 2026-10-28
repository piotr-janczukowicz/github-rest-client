package io.github.piotr_janczukowicz.github.api.copilotspaces;

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
class GitHubCopilotSpaceResourceTypeTest {

    @ParameterizedTest
    @CsvSource({
        "repository,REPOSITORY",
        "github_file,GITHUB_FILE",
        "free_text,FREE_TEXT",
        "github_issue,GITHUB_ISSUE",
        "github_pull_request,GITHUB_PULL_REQUEST",
        "media_content,MEDIA_CONTENT",
        "uploaded_text_file,UPLOADED_TEXT_FILE"
    })
    void fromWire_shouldReturnValue_whenKnownValue(final String wire, final GitHubCopilotSpaceResourceType value) {
        final GitHubCopilotSpaceResourceType decoded = GitHubCopilotSpaceResourceType.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @ParameterizedTest
    @MethodSource("invalidWire")
    void fromWire_shouldThrow_whenInvalid(
            final String wire, final Class<? extends Throwable> type, final String message) {
        final Throwable thrown = catchThrowable(() -> GitHubCopilotSpaceResourceType.fromWire(wire));

        assertThat(thrown).isInstanceOf(type).hasMessageContaining(message);
    }

    private static Stream<Arguments> invalidWire() {
        return Stream.of(
                Arguments.of("unknown", IllegalArgumentException.class, "unknown"),
                Arguments.of(null, NullPointerException.class, "value"));
    }
}
