package io.github.piotr_janczukowicz.github.api.announcementbanners;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAnnouncementTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubAnnouncement announcement = AnnouncementBannersCatalogFixtures.announcement();

        assertThat(announcement.announcement()).isEqualTo("Very **important** announcement about _something_.");
        assertThat(announcement.expiresAt()).isEqualTo(Instant.parse("2021-01-01T07:00:00Z"));
        assertThat(announcement.userDismissible()).isFalse();
    }

    @Test
    void constructor_shouldAcceptNullFields_whenAnnouncementNeverExpires() {
        final GitHubAnnouncement announcement = new GitHubAnnouncement(null, null, null);

        assertThat(announcement.announcement()).isNull();
        assertThat(announcement.expiresAt()).isNull();
        assertThat(announcement.userDismissible()).isNull();
    }
}
