package io.github.piotr_janczukowicz.github.api.announcementbanners;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.announcementBannersEnterprisesApi;
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
class GitHubAnnouncementBannersEnterprisesApiIntegrationTest {

    private static final String ENTERPRISE = "octo-enterprise";
    private static final String PATH = "/enterprises/octo-enterprise/announcement";
    private static final String BANNER_JSON = "announcement-banner.json";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getAnnouncementBannerForEnterprise_shouldReturnBanner_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                PATH,
                okJson(jsonBytes(GitHubAnnouncementBannersEnterprisesApiIntegrationTest.class, BANNER_JSON)));

        final GitHubAnnouncementBanner banner;
        try (final GitHubApiWireMockSupport.AnnouncementBannersEnterprisesApiClient api =
                announcementBannersEnterprisesApi(HTTP)) {
            banner = api.api().getAnnouncementBannerForEnterprise(ENTERPRISE);
        }

        assertThat(banner).isEqualTo(AnnouncementBannersCatalogFixtures.announcementBanner());
        HTTP.verifyRequested(GET, PATH);
    }

    @Test
    void setAnnouncementBannerForEnterprise_shouldReturnBanner_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PATCH,
                PATH,
                okJson(jsonBytes(GitHubAnnouncementBannersEnterprisesApiIntegrationTest.class, BANNER_JSON)));
        final GitHubAnnouncement payload = AnnouncementBannersCatalogFixtures.announcement();

        final GitHubAnnouncementBanner banner;
        try (final GitHubApiWireMockSupport.AnnouncementBannersEnterprisesApiClient api =
                announcementBannersEnterprisesApi(HTTP)) {
            banner = api.api().setAnnouncementBannerForEnterprise(ENTERPRISE, payload);
        }

        assertThat(banner).isEqualTo(AnnouncementBannersCatalogFixtures.announcementBanner());
        HTTP.verifyRequested(PATCH, PATH);
    }

    @Test
    void removeAnnouncementBannerForEnterprise_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, PATH, noContent());

        try (final GitHubApiWireMockSupport.AnnouncementBannersEnterprisesApiClient api =
                announcementBannersEnterprisesApi(HTTP)) {
            api.api().removeAnnouncementBannerForEnterprise(ENTERPRISE);
        }

        HTTP.verifyRequested(DELETE, PATH);
    }
}
