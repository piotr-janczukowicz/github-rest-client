package io.github.piotr_janczukowicz.github.api.announcementbanners;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAnnouncementBannerTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubAnnouncementBanner banner = AnnouncementBannersCatalogFixtures.announcementBanner();

        assertThat(banner.announcement()).isEqualTo("Very **important** announcement about _something_.");
        assertThat(banner.expiresAt()).isEqualTo(Instant.parse("2021-01-01T07:00:00Z"));
        assertThat(banner.userDismissible()).isFalse();
    }

    @Test
    void constructor_shouldAcceptNullFields_whenAnnouncementNeverExpires() {
        final GitHubAnnouncementBanner banner = new GitHubAnnouncementBanner(null, null, null);

        assertThat(banner.announcement()).isNull();
        assertThat(banner.expiresAt()).isNull();
        assertThat(banner.userDismissible()).isNull();
    }
}
