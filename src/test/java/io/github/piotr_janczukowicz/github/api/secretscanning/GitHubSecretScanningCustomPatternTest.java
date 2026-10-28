package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSecretScanningCustomPatternTest {

    @Test
    void constructor_shouldAcceptNullCreatedPatterns_whenResponseEmpty() {
        final GitHubBulkCreateSecretScanningCustomPatternsResult result =
                new GitHubBulkCreateSecretScanningCustomPatternsResult(null);

        assertThat(result.createdPatterns()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubSecretScanningCustomPattern(
                1,
                null,
                "pattern",
                "slug",
                GitHubSecretScanningCustomPatternState.PUBLISHED,
                true,
                null,
                null,
                null,
                null,
                null,
                null,
                null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenPatternNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubSecretScanningCustomPattern(
                1,
                "name",
                null,
                "slug",
                GitHubSecretScanningCustomPatternState.PUBLISHED,
                true,
                null,
                null,
                null,
                null,
                null,
                null,
                null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("pattern");
    }

    @Test
    void constructor_shouldThrow_whenSlugNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubSecretScanningCustomPattern(
                1,
                "name",
                "pattern",
                null,
                GitHubSecretScanningCustomPatternState.PUBLISHED,
                true,
                null,
                null,
                null,
                null,
                null,
                null,
                null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("slug");
    }

    @Test
    void constructor_shouldThrow_whenStateNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubSecretScanningCustomPattern(
                1, "name", "pattern", "slug", null, true, null, null, null, null, null, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("state");
    }

    @Test
    void constructor_shouldLeaveMatchListsNull_whenListsNull() {
        final GitHubSecretScanningCustomPattern pattern = new GitHubSecretScanningCustomPattern(
                1,
                "name",
                "pattern",
                "slug",
                GitHubSecretScanningCustomPatternState.PUBLISHED,
                false,
                null,
                null,
                null,
                null,
                null,
                null,
                null);

        assertSoftly(softly -> {
            softly.assertThat(pattern.mustMatch()).isNull();
            softly.assertThat(pattern.mustNotMatch()).isNull();
        });
    }
}
