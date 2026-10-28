package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubBulkCreateSecretScanningCustomPatternsPayloadTest {

    @Test
    void constructor_shouldAcceptEmptyPatterns_whenPresent() {
        final GitHubBulkCreateSecretScanningCustomPatternsPayload payload =
                new GitHubBulkCreateSecretScanningCustomPatternsPayload(List.of());

        assertThat(payload.patterns()).isEmpty();
    }

    @Test
    void constructor_shouldAcceptPatterns_whenAtMax() {
        final GitHubSecretScanningCustomPatternToCreate pattern =
                SecretScanningCatalogFixtures.bulkCreateSecretScanningCustomPatternsPayload()
                        .patterns()
                        .getFirst();

        final GitHubBulkCreateSecretScanningCustomPatternsPayload payload =
                new GitHubBulkCreateSecretScanningCustomPatternsPayload(Collections.nCopies(100, pattern));

        assertThat(payload.patterns()).hasSize(100);
    }

    @Test
    void constructor_shouldThrow_whenPatternsExceedMax() {
        final GitHubSecretScanningCustomPatternToCreate pattern =
                SecretScanningCatalogFixtures.bulkCreateSecretScanningCustomPatternsPayload()
                        .patterns()
                        .getFirst();
        final List<GitHubSecretScanningCustomPatternToCreate> patterns = Collections.nCopies(101, pattern);

        final Throwable thrown =
                catchThrowable(() -> new GitHubBulkCreateSecretScanningCustomPatternsPayload(patterns));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("patterns");
    }

    @Test
    void constructor_shouldThrow_whenPatternsIsNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubBulkCreateSecretScanningCustomPatternsPayload(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("patterns");
    }
}
