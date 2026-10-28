package io.github.piotr_janczukowicz.github.api.projects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListProjectItemsFilterTest {

    @Test
    void empty_shouldReturnEmptyQueryParameters_whenCalled() {
        final Map<String, String> query = GitHubListProjectItemsFilter.empty().toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldJoinFields_whenPresent() {
        final GitHubListProjectItemsFilter filter = GitHubListProjectItemsFilterBuilder.builder()
                .fields(List.of("123", "456"))
                .build();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query).containsEntry("fields", "123,456");
    }

    @Test
    void constructor_shouldThrow_whenFieldsExceedMax() {
        final List<String> fields = Collections.nCopies(51, "1");

        final Throwable thrown = catchThrowable(() -> new GitHubListProjectItemsFilter(null, fields));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("fields");
    }

    @Test
    void toQueryParameters_shouldPutQ_whenFieldsAbsent() {
        final GitHubListProjectItemsFilter filter =
                GitHubListProjectItemsFilterBuilder.builder().q("is:open").build();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query).containsEntry("q", "is:open").doesNotContainKey("fields");
    }

    @Test
    void toQueryParameters_shouldIncludeQAndFields_whenBothSet() {
        final GitHubListProjectItemsFilter filter = GitHubListProjectItemsFilterBuilder.builder()
                .q("is:open")
                .fields(List.of("1"))
                .build();

        final Map<String, String> query = filter.toQueryParameters();

        assertSoftly(softly -> {
            softly.assertThat(query).containsEntry("q", "is:open");
            softly.assertThat(query).containsEntry("fields", "1");
        });
    }
}
