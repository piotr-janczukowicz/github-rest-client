package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubBulkDeleteEnterpriseCustomPatternsPayloadTest {

    @Test
    void constructor_shouldAcceptPatterns_whenWithinLimit() {
        final GitHubBulkDeleteEnterpriseCustomPatternsPayload payload =
                new GitHubBulkDeleteEnterpriseCustomPatternsPayload(
                        List.of(new GitHubSecretScanningCustomPatternToDelete(1L, null)), null);

        assertThat(payload.patterns()).hasSize(1);
    }

    @Test
    void constructor_shouldAcceptEmptyPatterns_whenPresent() {
        final GitHubBulkDeleteEnterpriseCustomPatternsPayload payload =
                new GitHubBulkDeleteEnterpriseCustomPatternsPayload(List.of(), null);

        assertThat(payload.patterns()).isEmpty();
    }

    @Test
    void constructor_shouldThrow_whenPatternsIsNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubBulkDeleteEnterpriseCustomPatternsPayload(null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("patterns");
    }

    @Test
    void constructor_shouldThrow_whenPatternsExceedMaxItems() {
        final List<GitHubSecretScanningCustomPatternToDelete> patterns =
                Collections.nCopies(501, new GitHubSecretScanningCustomPatternToDelete(1L, null));

        final Throwable thrown =
                catchThrowable(() -> new GitHubBulkDeleteEnterpriseCustomPatternsPayload(patterns, null));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("patterns")
                .hasMessageContaining("at most 500");
    }
}
