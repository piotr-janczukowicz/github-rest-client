package io.github.piotr_janczukowicz.github.api.activity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubFeedTest {

    @Test
    void constructor_shouldThrow_whenTimelineUrlNull() {
        final GitHubFeedBuilder builder =
                GitHubFeedBuilder.builder(ActivityCatalogFixtures.feed()).timelineUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("timelineUrl");
    }

    @Test
    void constructor_shouldLeaveCurrentUserOrganizationUrlsNull_whenNull() {
        final GitHubFeed feed = GitHubFeedBuilder.builder(ActivityCatalogFixtures.feed())
                .currentUserOrganizationUrls(null)
                .build();

        assertThat(feed.currentUserOrganizationUrls()).isNull();
    }
}
