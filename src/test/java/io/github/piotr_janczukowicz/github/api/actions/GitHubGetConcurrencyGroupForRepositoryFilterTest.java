package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetConcurrencyGroupForRepositoryFilterTest {

    @Test
    void empty_shouldReturnFilterWithoutQueryParameters() {
        final GitHubGetConcurrencyGroupForRepositoryFilter sut = GitHubGetConcurrencyGroupForRepositoryFilter.empty();

        final Map<String, String> query = sut.toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeAheadCounts_whenSet() {
        final GitHubGetConcurrencyGroupForRepositoryFilter sut = new GitHubGetConcurrencyGroupForRepositoryFilter(2, 3);

        final Map<String, String> query = sut.toQueryParameters();

        assertThat(query).containsExactlyInAnyOrderEntriesOf(Map.of("ahead_of_run", "2", "ahead_of_job", "3"));
    }

    @Test
    void constructor_shouldThrowIllegalArgumentException_whenAheadOfRunBelowOne() {
        final Throwable thrown = catchThrowable(() -> new GitHubGetConcurrencyGroupForRepositoryFilter(0, null));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }
}
