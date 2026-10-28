package io.github.piotr_janczukowicz.github.api.projects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListProjectViewItemsFilterTest {

    @Test
    void empty_shouldReturnEmptyQueryParameters_whenCalled() {
        final Map<String, String> query =
                GitHubListProjectViewItemsFilter.empty().toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void constructor_shouldThrow_whenFieldsExceedMax() {
        final List<String> fields = Collections.nCopies(51, "1");

        final Throwable thrown = catchThrowable(() -> new GitHubListProjectViewItemsFilter(fields));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("fields");
    }

    @Test
    void toQueryParameters_shouldJoinFields_whenPresent() {
        final GitHubListProjectViewItemsFilter filter = GitHubListProjectViewItemsFilterBuilder.builder()
                .fields(List.of("123", "456"))
                .build();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query).containsEntry("fields", "123,456");
    }
}
