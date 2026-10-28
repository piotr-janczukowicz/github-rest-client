package io.github.piotr_janczukowicz.github.api.campaigns;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.net.URI;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubCampaignsApiTest {

    private static final String ORG = "octocat";
    private static final int CAMPAIGN_NUMBER = 1;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCampaignsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listOrgCampaigns_shouldReturnCampaigns_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("campaign-summary.json") + "]"));

        final GitHubPage<GitHubCampaignSummary> page = api().listOrgCampaigns(ORG);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(CampaignsCatalogFixtures.campaignSummary());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/orgs/octocat/campaigns?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void listOrgCampaigns_shouldReturnCampaigns_whenFilterProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("campaign-summary.json") + "]"));

        final GitHubPage<GitHubCampaignSummary> page =
                api().listOrgCampaigns(ORG, CampaignsCatalogFixtures.listOrgCampaignsFilter());
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(CampaignsCatalogFixtures.campaignSummary());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/orgs/octocat/campaigns?direction=desc&state=open"
                            + "&sort=ends_at&page=1&per_page=" + 100);
        });
    }

    @Test
    void listOrgCampaigns_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("campaign-summary.json") + "]"));

        final GitHubPage<GitHubCampaignSummary> page = api().listOrgCampaigns(
                        ORG, CampaignsCatalogFixtures.listOrgCampaignsFilter(), GitHubPageNumberRequest.of(2, 10));
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(CampaignsCatalogFixtures.campaignSummary());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/orgs/octocat/campaigns?direction=desc&state=open"
                            + "&sort=ends_at&page=2&per_page=10");
        });
    }

    @Test
    void createCampaign_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("campaign-summary.json")));

        final GitHubCampaignSummary campaign =
                api().createCampaign(ORG, CampaignsCatalogFixtures.createCampaignPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(campaign).isEqualTo(CampaignsCatalogFixtures.campaignSummary());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/orgs/octocat/campaigns");
        });
    }

    @Test
    void getCampaignSummary_shouldReturnCampaign_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("campaign-summary.json")));

        final GitHubCampaignSummary campaign =
                api().getCampaignSummary(ORG, CAMPAIGN_NUMBER).orElseThrow();
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(campaign).isEqualTo(CampaignsCatalogFixtures.campaignSummary());
            softly.assertThat(requestUri).hasToString("https://api.github.com/orgs/octocat/campaigns/1");
        });
    }

    @Test
    void getCampaignSummary_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubCampaignSummary> result = api().getCampaignSummary(ORG, CAMPAIGN_NUMBER);

        assertThat(result).isEmpty();
    }

    @Test
    void updateCampaign_shouldPatchPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("campaign-summary.json")));

        final GitHubCampaignSummary campaign =
                api().updateCampaign(ORG, CAMPAIGN_NUMBER, CampaignsCatalogFixtures.updateCampaignPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(campaign).isEqualTo(CampaignsCatalogFixtures.campaignSummary());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/orgs/octocat/campaigns/1");
        });
    }

    @Test
    void deleteCampaign_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteCampaign(ORG, CAMPAIGN_NUMBER);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/orgs/octocat/campaigns/1");
        });
    }

    private GitHubCampaignsApi api() {
        return new GitHubCampaignsApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubCampaignsApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
