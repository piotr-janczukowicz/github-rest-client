package io.github.piotr_janczukowicz.github.api.search;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSearchCodeFilterTest {

    @Test
    void constructor_shouldThrow_whenQNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubSearchCodeFilter(null, null, null, false));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("q");
    }

    @Test
    void constructor_shouldThrow_whenQBlank() {
        final Throwable thrown = catchThrowable(() -> new GitHubSearchCodeFilter(" ", null, null, false));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("q");
    }

    @Test
    void toQueryParameters_shouldIncludeSortAndOrder_whenSet() {
        final GitHubSearchCodeFilter filter = GitHubSearchCodeFilterBuilder.builder()
                .q("addClass")
                .sort(GitHubCodeSearchSort.INDEXED)
                .order(GitHubSearchOrder.ASC)
                .build();
        final Map<String, String> query = filter.toQueryParameters();

        assertSoftly(softly -> {
            softly.assertThat(query).containsEntry("q", "addClass");
            softly.assertThat(query).containsEntry("sort", "indexed");
            softly.assertThat(query).containsEntry("order", "asc");
        });
    }
}
