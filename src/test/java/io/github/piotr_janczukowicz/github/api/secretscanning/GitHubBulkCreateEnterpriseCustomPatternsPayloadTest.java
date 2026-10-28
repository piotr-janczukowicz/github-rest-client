package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubBulkCreateEnterpriseCustomPatternsPayloadTest {

    @Test
    void constructor_shouldAcceptEmptyPatterns_whenPresent() {
        final GitHubBulkCreateEnterpriseCustomPatternsPayload payload =
                new GitHubBulkCreateEnterpriseCustomPatternsPayload(List.of());

        assertThat(payload.patterns()).isEmpty();
    }

    @Test
    void constructor_shouldAcceptPatterns_whenAtMax() {
        final GitHubSecretScanningCustomPatternToCreate pattern =
                SecretScanningCatalogFixtures.bulkCreateEnterpriseCustomPatternsPayload()
                        .patterns()
                        .getFirst();

        final GitHubBulkCreateEnterpriseCustomPatternsPayload payload =
                new GitHubBulkCreateEnterpriseCustomPatternsPayload(Collections.nCopies(100, pattern));

        assertThat(payload.patterns()).hasSize(100);
    }

    @Test
    void constructor_shouldThrow_whenPatternsExceedMax() {
        final GitHubSecretScanningCustomPatternToCreate pattern =
                SecretScanningCatalogFixtures.bulkCreateEnterpriseCustomPatternsPayload()
                        .patterns()
                        .getFirst();
        final List<GitHubSecretScanningCustomPatternToCreate> patterns = Collections.nCopies(101, pattern);

        final Throwable thrown = catchThrowable(() -> new GitHubBulkCreateEnterpriseCustomPatternsPayload(patterns));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("patterns");
    }

    @Test
    void constructor_shouldThrow_whenPatternsIsNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubBulkCreateEnterpriseCustomPatternsPayload(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("patterns");
    }
}
