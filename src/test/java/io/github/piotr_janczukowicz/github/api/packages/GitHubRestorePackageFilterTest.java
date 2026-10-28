package io.github.piotr_janczukowicz.github.api.packages;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRestorePackageFilterTest {

    @Test
    void empty_shouldReturnNoQueryParameters_whenCalled() {
        final GitHubRestorePackageFilter filter = GitHubRestorePackageFilter.empty();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeToken_whenSet() {
        final GitHubRestorePackageFilter filter = GitHubRestorePackageFilterBuilder.builder()
                .token("restore-token")
                .build();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query).containsEntry("token", "restore-token");
    }

    @Test
    void toString_shouldShowNull_whenTokenNotSet() {
        final GitHubRestorePackageFilter value = GitHubRestorePackageFilter.empty();

        final String text = value.toString();

        assertThat(text).isEqualTo("GitHubRestorePackageFilter[token=null]");
    }

    @Test
    void toString_shouldHideSecrets() {
        final GitHubRestorePackageFilter value = GitHubRestorePackageFilterBuilder.builder()
                .token("secret-token")
                .build();

        final String text = value.toString();

        assertSoftly(softly -> {
            softly.assertThat(text).contains("token=<redacted>");
            softly.assertThat(text).doesNotContain(value.token());
        });
    }
}
