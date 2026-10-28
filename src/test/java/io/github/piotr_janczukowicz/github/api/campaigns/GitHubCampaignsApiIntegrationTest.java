package io.github.piotr_janczukowicz.github.api.campaigns;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LINK;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.campaignsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.linkNext;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubCampaignsApiIntegrationTest {

    private static final String ORG = "octocat";
    private static final int CAMPAIGN_NUMBER = 1;
    private static final String COLLECTION_PATH = "/orgs/octocat/campaigns";
    private static final String ITEM_PATH = "/orgs/octocat/campaigns/1";

    private static final JsonType<List<GitHubCampaignSummary>> CAMPAIGNS_TYPE =
            JsonTypes.listOf(GitHubCampaignSummary.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listOrgCampaigns_shouldReturnCampaigns_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                COLLECTION_PATH,
                okJson(listJsonBytes(GitHubCampaignsApiIntegrationTest.class, "campaign-summary.json")));

        final GitHubPage<GitHubCampaignSummary> page;
        try (final GitHubApiWireMockSupport.CampaignsApiClient api = campaignsApi(HTTP)) {
            page = api.api().listOrgCampaigns(ORG);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedCampaigns());
        HTTP.verifyRequested(GET, COLLECTION_PATH);
        HTTP.verifyQueryParam(GET, COLLECTION_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, COLLECTION_PATH, "per_page", "100");
    }

    @Test
    void listOrgCampaigns_shouldFollowLinkNext_whenNextPagePresent() throws Exception {
        HTTP.stub(
                GET,
                COLLECTION_PATH,
                okJson(listJsonBytes(GitHubCampaignsApiIntegrationTest.class, "campaign-summary.json"))
                        .header(LINK, linkNext(HTTP.uri(COLLECTION_PATH + "?page=2&" + TestClients.perPageQuery()))),
                okJson(listJsonBytes(GitHubCampaignsApiIntegrationTest.class, "campaign-summary.json")));

        final GitHubPage<GitHubCampaignSummary> first;
        final Optional<GitHubPage<GitHubCampaignSummary>> second;
        try (final GitHubApiWireMockSupport.CampaignsApiClient api = campaignsApi(HTTP)) {
            first = api.api().listOrgCampaigns(ORG);
            second = first.next();
        }

        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactlyElementsOf(expectedCampaigns());
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items()).containsExactlyElementsOf(expectedCampaigns());
        });
        HTTP.verifyRequested(GET, COLLECTION_PATH, 2);
    }

    @Test
    void listOrgCampaigns_shouldSendFilterAndPage_whenFilterAndCustomPageRequest() throws Exception {
        HTTP.stub(
                GET,
                COLLECTION_PATH,
                okJson(listJsonBytes(GitHubCampaignsApiIntegrationTest.class, "campaign-summary.json")));

        try (final GitHubApiWireMockSupport.CampaignsApiClient api = campaignsApi(HTTP)) {
            api.api()
                    .listOrgCampaigns(
                            ORG, CampaignsCatalogFixtures.listOrgCampaignsFilter(), GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, COLLECTION_PATH, "direction", "desc");
        HTTP.verifyQueryParam(GET, COLLECTION_PATH, "state", "open");
        HTTP.verifyQueryParam(GET, COLLECTION_PATH, "sort", "ends_at");
        HTTP.verifyQueryParam(GET, COLLECTION_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, COLLECTION_PATH, "per_page", "10");
    }

    @Test
    void createCampaign_shouldReturnCampaign_whenResponseIsOk() throws Exception {
        HTTP.stub(
                POST,
                COLLECTION_PATH,
                okJson(jsonBytes(GitHubCampaignsApiIntegrationTest.class, "campaign-summary.json")));
        final GitHubCreateCampaignPayload payload = CampaignsCatalogFixtures.createCampaignPayload();

        final GitHubCampaignSummary campaign;
        try (final GitHubApiWireMockSupport.CampaignsApiClient api = campaignsApi(HTTP)) {
            campaign = api.api().createCampaign(ORG, payload);
        }

        assertThat(campaign).isEqualTo(expectedCampaign());
        HTTP.verifyRequested(POST, COLLECTION_PATH);
        HTTP.verifyHeader(POST, COLLECTION_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, COLLECTION_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void getCampaignSummary_shouldReturnCampaign_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, ITEM_PATH, okJson(jsonBytes(GitHubCampaignsApiIntegrationTest.class, "campaign-summary.json")));

        final GitHubCampaignSummary campaign;
        try (final GitHubApiWireMockSupport.CampaignsApiClient api = campaignsApi(HTTP)) {
            campaign = api.api().getCampaignSummary(ORG, CAMPAIGN_NUMBER).orElseThrow();
        }

        assertThat(campaign).isEqualTo(expectedCampaign());
        HTTP.verifyRequested(GET, ITEM_PATH);
    }

    @Test
    void updateCampaign_shouldReturnCampaign_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PATCH, ITEM_PATH, okJson(jsonBytes(GitHubCampaignsApiIntegrationTest.class, "campaign-summary.json")));
        final GitHubUpdateCampaignPayload payload = CampaignsCatalogFixtures.updateCampaignPayload();

        final GitHubCampaignSummary campaign;
        try (final GitHubApiWireMockSupport.CampaignsApiClient api = campaignsApi(HTTP)) {
            campaign = api.api().updateCampaign(ORG, CAMPAIGN_NUMBER, payload);
        }

        assertThat(campaign).isEqualTo(expectedCampaign());
        HTTP.verifyRequested(PATCH, ITEM_PATH);
        HTTP.verifyHeader(PATCH, ITEM_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PATCH, ITEM_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void deleteCampaign_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, ITEM_PATH, noContent());

        try (final GitHubApiWireMockSupport.CampaignsApiClient api = campaignsApi(HTTP)) {
            api.api().deleteCampaign(ORG, CAMPAIGN_NUMBER);
        }

        HTTP.verifyRequested(DELETE, ITEM_PATH);
    }

    private static GitHubCampaignSummary expectedCampaign() {
        return CODEC.decode(
                jsonBytes(GitHubCampaignsApiIntegrationTest.class, "campaign-summary.json"),
                GitHubCampaignSummary.class);
    }

    private static List<GitHubCampaignSummary> expectedCampaigns() {
        return CODEC.decode(
                listJsonBytes(GitHubCampaignsApiIntegrationTest.class, "campaign-summary.json"), CAMPAIGNS_TYPE);
    }
}
