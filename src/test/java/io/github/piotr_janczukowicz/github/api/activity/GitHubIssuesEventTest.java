package io.github.piotr_janczukowicz.github.api.activity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubIssuesEventTest {

    @Test
    void constructor_shouldThrow_whenActionNull() {
        final GitHubIssuesEventBuilder builder = GitHubIssuesEventBuilder.builder(ActivityCatalogFixtures.issuesEvent())
                .action(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("action");
    }

    @Test
    void constructor_shouldLeaveAssigneesNull_whenNull() {
        final GitHubIssuesEvent event = GitHubIssuesEventBuilder.builder(ActivityCatalogFixtures.issuesEvent())
                .assignees(null)
                .build();

        assertThat(event.assignees()).isNull();
    }

    @Test
    void constructor_shouldLeaveLabelsNull_whenNull() {
        final GitHubIssuesEvent event = GitHubIssuesEventBuilder.builder(ActivityCatalogFixtures.issuesEvent())
                .labels(null)
                .build();

        assertThat(event.labels()).isNull();
    }
}
