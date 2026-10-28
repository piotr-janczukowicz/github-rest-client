package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetSecretScanningAlertFilterTest {

    @Test
    void empty_shouldOmitAllParameters_whenBuilt() {
        final GitHubGetSecretScanningAlertFilter filter = GitHubGetSecretScanningAlertFilter.empty();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeHideSecret_whenSet() {
        final GitHubGetSecretScanningAlertFilter filter = GitHubGetSecretScanningAlertFilterBuilder.builder()
                .hideSecret(true)
                .build();

        final Map<String, String> query = filter.toQueryParameters();

        assertSoftly(softly -> {
            softly.assertThat(query).containsEntry("hide_secret", "true");
            softly.assertThat(query).hasSize(1);
        });
    }
}
