package io.github.piotr_janczukowicz.github.api.announcementbanners;

import java.time.Instant;

public final class AnnouncementBannersCatalogFixtures {

    private AnnouncementBannersCatalogFixtures() {}

    static GitHubAnnouncementBanner announcementBanner() {
        return GitHubAnnouncementBannerBuilder.builder()
                .announcement("Very **important** announcement about _something_.")
                .expiresAt(Instant.parse("2021-01-01T07:00:00Z"))
                .userDismissible(false)
                .build();
    }

    public static GitHubAnnouncement announcement() {
        return GitHubAnnouncementBuilder.builder()
                .announcement("Very **important** announcement about _something_.")
                .expiresAt(Instant.parse("2021-01-01T07:00:00Z"))
                .userDismissible(false)
                .build();
    }
}
