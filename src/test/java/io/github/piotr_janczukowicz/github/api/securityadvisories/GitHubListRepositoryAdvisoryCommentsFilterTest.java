package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListRepositoryAdvisoryCommentsFilterTest {

    @Test
    void empty_shouldReturnNoQueryParameters_whenCalled() {
        assertThat(GitHubListRepositoryAdvisoryCommentsFilter.empty().toQueryParameters())
                .isEmpty();
    }

    @Test
    void toQueryParameters_shouldEncodeField_whenSet() {
        final GitHubListRepositoryAdvisoryCommentsFilter filter =
                GitHubListRepositoryAdvisoryCommentsFilterBuilder.builder()
                        .since(Instant.parse("2026-01-01T00:00:00Z"))
                        .build();

        assertThat(filter.toQueryParameters()).containsEntry("since", "2026-01-01T00:00:00Z");
    }
}
