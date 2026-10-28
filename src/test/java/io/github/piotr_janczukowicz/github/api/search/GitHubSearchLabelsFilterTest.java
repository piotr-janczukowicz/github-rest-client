package io.github.piotr_janczukowicz.github.api.search;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSearchLabelsFilterTest {

    @Test
    void constructor_shouldThrow_whenQNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubSearchLabelsFilter(0L, null, null, null, false));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("q");
    }

    @Test
    void constructor_shouldThrow_whenQBlank() {
        final Throwable thrown = catchThrowable(() -> new GitHubSearchLabelsFilter(0L, " ", null, null, false));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("q");
    }

    @Test
    void toQueryParameters_shouldKeepDeclarationOrder_whenAllParametersSet() {
        final GitHubSearchLabelsFilter filter =
                new GitHubSearchLabelsFilter(1L, "bug", GitHubLabelSearchSort.CREATED, GitHubSearchOrder.DESC, false);

        final List<String> names = List.copyOf(filter.toQueryParameters().keySet());

        assertThat(names).containsExactly("repository_id", "q", "sort", "order");
    }
}
