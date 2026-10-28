package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class GitHubUpdateSecretScanningCustomPatternPayloadTest {

    private static final String VERSION = "0ujsswThIGTUYm2K8FjOOfXtY1K";

    @Test
    void builder_shouldCopyLists_whenMatchListsProvided() {
        final GitHubUpdateSecretScanningCustomPatternPayload payload =
                GitHubUpdateSecretScanningCustomPatternPayloadBuilder.builder()
                        .pattern("token-[0-9]+")
                        .mustMatch(List.of("prefix"))
                        .mustNotMatch(List.of("suffix"))
                        .customPatternVersion(OptionalNull.of(VERSION))
                        .build();

        assertSoftly(softly -> {
            softly.assertThat(payload.mustMatch()).containsExactly("prefix");
            softly.assertThat(payload.mustNotMatch()).containsExactly("suffix");
        });
    }

    @Test
    void constructor_shouldThrow_whenOnlyVersionSet() {
        final GitHubUpdateSecretScanningCustomPatternPayloadBuilder builder =
                GitHubUpdateSecretScanningCustomPatternPayloadBuilder.builder()
                        .customPatternVersion(OptionalNull.of(VERSION));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("at least one of the updatable fields is required");
    }

    @Test
    void constructor_shouldThrow_whenCustomPatternVersionWrapperNull() {
        final Throwable thrown = catchThrowable(
                () -> new GitHubUpdateSecretScanningCustomPatternPayload("token-[0-9]+", null, null, null, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("customPatternVersion");
    }

    @Test
    void constructor_shouldAccept_whenCustomPatternVersionIsJsonNull() {
        final GitHubUpdateSecretScanningCustomPatternPayload payload =
                GitHubUpdateSecretScanningCustomPatternPayloadBuilder.builder()
                        .pattern("token-[0-9]+")
                        .customPatternVersion(OptionalNull.ofNull())
                        .build();

        assertThat(payload.customPatternVersion().value()).isNull();
    }

    @Test
    void builder_shouldLeaveListsNull_whenMatchListsOmitted() {
        final GitHubUpdateSecretScanningCustomPatternPayload payload =
                GitHubUpdateSecretScanningCustomPatternPayloadBuilder.builder()
                        .pattern("token-[0-9]+")
                        .customPatternVersion(OptionalNull.of(VERSION))
                        .build();

        assertSoftly(softly -> {
            softly.assertThat(payload.pattern()).isEqualTo("token-[0-9]+");
            softly.assertThat(payload.mustMatch()).isNull();
            softly.assertThat(payload.mustNotMatch()).isNull();
        });
    }

    @ParameterizedTest
    @MethodSource("singleUpdatableFields")
    void constructor_shouldAccept_whenSingleUpdatableFieldSet(
            final GitHubUpdateSecretScanningCustomPatternPayload payload) {
        final String version = payload.customPatternVersion().value();

        assertThat(version).isEqualTo(VERSION);
    }

    private static Stream<GitHubUpdateSecretScanningCustomPatternPayload> singleUpdatableFields() {
        return Stream.of(
                GitHubUpdateSecretScanningCustomPatternPayloadBuilder.builder()
                        .pattern("token-[0-9]+")
                        .customPatternVersion(OptionalNull.of(VERSION))
                        .build(),
                GitHubUpdateSecretScanningCustomPatternPayloadBuilder.builder()
                        .startDelimiter("^")
                        .customPatternVersion(OptionalNull.of(VERSION))
                        .build(),
                GitHubUpdateSecretScanningCustomPatternPayloadBuilder.builder()
                        .endDelimiter("$")
                        .customPatternVersion(OptionalNull.of(VERSION))
                        .build(),
                GitHubUpdateSecretScanningCustomPatternPayloadBuilder.builder()
                        .mustMatch(List.of("prefix"))
                        .customPatternVersion(OptionalNull.of(VERSION))
                        .build(),
                GitHubUpdateSecretScanningCustomPatternPayloadBuilder.builder()
                        .mustNotMatch(List.of("suffix"))
                        .customPatternVersion(OptionalNull.of(VERSION))
                        .build());
    }
}
