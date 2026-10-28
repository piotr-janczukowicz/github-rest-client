package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSecretScanningCustomPatternValidationErrorTest {

    @Test
    void constructor_shouldHoldValues_whenFieldsProvided() {
        final GitHubSecretScanningCustomPatternValidationError error =
                new GitHubSecretScanningCustomPatternValidationError(
                        GitHubSecretScanningCustomPatternValidationErrorCode.INVALID, "pattern is invalid");

        assertSoftly(softly -> {
            softly.assertThat(error).hasNoNullFieldsOrProperties();
            softly.assertThat(error.code()).isEqualTo(GitHubSecretScanningCustomPatternValidationErrorCode.INVALID);
            softly.assertThat(error.message()).isEqualTo("pattern is invalid");
        });
    }
}
