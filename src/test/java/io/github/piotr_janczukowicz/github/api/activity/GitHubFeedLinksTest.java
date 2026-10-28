package io.github.piotr_janczukowicz.github.api.activity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubFeedLinksTest {

    @Test
    void constructor_shouldThrow_whenTimelineNull() {
        final GitHubFeedLinksBuilder builder = GitHubFeedLinksBuilder.builder(ActivityCatalogFixtures.feedLinks())
                .timeline(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("timeline");
    }

    @Test
    void constructor_shouldLeaveCurrentUserOrganizationsNull_whenNull() {
        final GitHubFeedLinks links = GitHubFeedLinksBuilder.builder(ActivityCatalogFixtures.feedLinks())
                .currentUserOrganizations(null)
                .build();

        assertThat(links.currentUserOrganizations()).isNull();
    }
}
