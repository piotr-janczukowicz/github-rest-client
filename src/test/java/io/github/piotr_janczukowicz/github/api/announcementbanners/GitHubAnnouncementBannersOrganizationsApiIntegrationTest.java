package io.github.piotr_janczukowicz.github.api.announcementbanners;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.announcementBannersOrganizationsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubAnnouncementBannersOrganizationsApiIntegrationTest {

    private static final String ORG = "octo-org";
    private static final String PATH = "/orgs/octo-org/announcement";
    private static final String BANNER_JSON = "announcement-banner.json";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getAnnouncementBannerForOrg_shouldReturnBanner_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                PATH,
                okJson(jsonBytes(GitHubAnnouncementBannersOrganizationsApiIntegrationTest.class, BANNER_JSON)));

        final GitHubAnnouncementBanner banner;
        try (final GitHubApiWireMockSupport.AnnouncementBannersOrganizationsApiClient api =
                announcementBannersOrganizationsApi(HTTP)) {
            banner = api.api().getAnnouncementBannerForOrg(ORG);
        }

        assertThat(banner).isEqualTo(AnnouncementBannersCatalogFixtures.announcementBanner());
        HTTP.verifyRequested(GET, PATH);
    }

    @Test
    void setAnnouncementBannerForOrg_shouldReturnBanner_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PATCH,
                PATH,
                okJson(jsonBytes(GitHubAnnouncementBannersOrganizationsApiIntegrationTest.class, BANNER_JSON)));
        final GitHubAnnouncement payload = AnnouncementBannersCatalogFixtures.announcement();

        final GitHubAnnouncementBanner banner;
        try (final GitHubApiWireMockSupport.AnnouncementBannersOrganizationsApiClient api =
                announcementBannersOrganizationsApi(HTTP)) {
            banner = api.api().setAnnouncementBannerForOrg(ORG, payload);
        }

        assertThat(banner).isEqualTo(AnnouncementBannersCatalogFixtures.announcementBanner());
        HTTP.verifyRequested(PATCH, PATH);
    }

    @Test
    void removeAnnouncementBannerForOrg_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, PATH, noContent());

        try (final GitHubApiWireMockSupport.AnnouncementBannersOrganizationsApiClient api =
                announcementBannersOrganizationsApi(HTTP)) {
            api.api().removeAnnouncementBannerForOrg(ORG);
        }

        HTTP.verifyRequested(DELETE, PATH);
    }
}
