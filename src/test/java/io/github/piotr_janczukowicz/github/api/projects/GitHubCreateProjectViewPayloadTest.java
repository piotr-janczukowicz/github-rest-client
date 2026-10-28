package io.github.piotr_janczukowicz.github.api.projects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateProjectViewPayloadTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final Throwable thrown = catchThrowable(() ->
                new GitHubCreateProjectViewPayload(null, GitHubProjectViewLayout.TABLE, null, null, null, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenVisibleFieldsOnRoadmap() {
        final Throwable thrown = catchThrowable(() -> new GitHubCreateProjectViewPayload(
                "Roadmap", GitHubProjectViewLayout.ROADMAP, null, List.of(123L), null, null, null));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("visibleFields");
    }

    @Test
    void constructor_shouldThrow_whenGroupByExceedsMaxItems() {
        final Throwable thrown = catchThrowable(() -> new GitHubCreateProjectViewPayload(
                "Board", GitHubProjectViewLayout.BOARD, null, null, null, List.of(1L, 2L), null));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("groupBy");
    }

    @Test
    void constructor_shouldThrow_whenVerticalGroupByExceedsMaxItems() {
        final Throwable thrown = catchThrowable(() -> new GitHubCreateProjectViewPayload(
                "Board", GitHubProjectViewLayout.BOARD, null, null, null, null, List.of(1L, 2L)));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("verticalGroupBy");
    }

    @Test
    void constructor_shouldThrow_whenLayoutNull() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubCreateProjectViewPayload("Board", null, null, null, null, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("layout");
    }

    @Test
    void constructor_shouldThrow_whenSortDirectionIsUnknown() {
        final List<GitHubProjectViewSort> sortBy =
                List.of(new GitHubProjectViewSort(123, GitHubProjectViewSortDirection.UNKNOWN));

        final Throwable thrown = catchThrowable(() -> new GitHubCreateProjectViewPayload(
                "Board", GitHubProjectViewLayout.BOARD, null, null, sortBy, null, null));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("sortBy");
    }

    @Test
    void constructor_shouldAcceptRoadmap_whenVisibleFieldsAbsent() {
        final GitHubCreateProjectViewPayload payload = new GitHubCreateProjectViewPayload(
                "Roadmap", GitHubProjectViewLayout.ROADMAP, null, null, null, null, null);

        assertThat(payload.layout()).isEqualTo(GitHubProjectViewLayout.ROADMAP);
    }

    @Test
    void constructor_shouldCopyOptionalLists_whenPresent() {
        final GitHubProjectViewSort sort = new GitHubProjectViewSort(123, GitHubProjectViewSortDirection.ASC);

        final GitHubCreateProjectViewPayload payload = new GitHubCreateProjectViewPayload(
                "Board",
                GitHubProjectViewLayout.BOARD,
                "is:open",
                List.of(1L),
                List.of(sort),
                List.of(2L),
                List.of(3L));

        assertSoftly(softly -> {
            softly.assertThat(payload.visibleFields()).containsExactly(1L);
            softly.assertThat(payload.sortBy()).containsExactly(sort);
            softly.assertThat(payload.groupBy()).containsExactly(2L);
            softly.assertThat(payload.verticalGroupBy()).containsExactly(3L);
            softly.assertThat(payload.filter()).isEqualTo("is:open");
        });
    }
}
