package io.github.piotr_janczukowicz.github.api.projects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubProjectViewSortTest {

    @Test
    void constructor_shouldThrow_whenDirectionNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubProjectViewSort(123, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("direction");
    }

    @Test
    void toJson_shouldReturnTuple_whenCalled() {
        final GitHubProjectViewSort sort = new GitHubProjectViewSort(123, GitHubProjectViewSortDirection.DESC);

        final List<Object> json = sort.toJson();

        assertThat(json).containsExactly(123L, "desc");
    }
}
