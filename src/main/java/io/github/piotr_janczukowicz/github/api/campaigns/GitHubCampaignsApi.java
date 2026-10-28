package io.github.piotr_janczukowicz.github.api.campaigns;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/** GitHub security campaigns REST resource for API version {@code 2026-03-10}. */
public final class GitHubCampaignsApi extends GitHubResourceApi {

    private static final PathTemplate CAMPAIGNS = PathTemplate.of("/orgs/{org}/campaigns");
    private static final PathTemplate CAMPAIGN = PathTemplate.of("/orgs/{org}/campaigns/{campaign_number}");

    private static final JsonType<List<GitHubCampaignSummary>> CAMPAIGNS_TYPE =
            JsonTypes.listOf(GitHubCampaignSummary.class);

    /**
     * Security campaigns resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubCampaignsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List campaigns for an organization ({@code GET /orgs/{org}/campaigns}).
     *
     * @param org organization name
     * @return first HTTP page of campaigns
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubCampaignSummary> listOrgCampaigns(final String org) throws IOException {
        return listOrgCampaigns(org, GitHubListOrgCampaignsFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List campaigns for an organization ({@code GET /orgs/{org}/campaigns}).
     *
     * @param org organization name
     * @param filter query filters
     * @return first HTTP page of campaigns
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubCampaignSummary> listOrgCampaigns(
            final String org, final GitHubListOrgCampaignsFilter filter) throws IOException {
        return listOrgCampaigns(org, filter, GitHubPageRequest.byPage());
    }

    /**
     * List campaigns for an organization ({@code GET /orgs/{org}/campaigns}).
     *
     * @param org organization name
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of campaigns
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubCampaignSummary> listOrgCampaigns(
            final String org, final GitHubListOrgCampaignsFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(CAMPAIGNS, filter.toQueryParameters(), request, CAMPAIGNS_TYPE, org);
    }

    /**
     * Create a campaign for an organization ({@code POST /orgs/{org}/campaigns}).
     *
     * @param org organization name
     * @param payload campaign to create
     * @return created campaign
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCampaignSummary createCampaign(final String org, final GitHubCreateCampaignPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(CAMPAIGNS, org), payload), GitHubCampaignSummary.class);
    }

    /**
     * Get a campaign for an organization ({@code GET /orgs/{org}/campaigns/{campaign_number}}).
     *
     * @param org organization name
     * @param campaignNumber campaign number
     * @return campaign summary, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubCampaignSummary> getCampaignSummary(final String org, final int campaignNumber)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(get(CAMPAIGN, org, campaignNumber), GitHubCampaignSummary.class);
    }

    /**
     * Update a campaign ({@code PATCH /orgs/{org}/campaigns/{campaign_number}}).
     *
     * @param org organization name
     * @param campaignNumber campaign number
     * @param payload fields to update
     * @return updated campaign
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCampaignSummary updateCampaign(
            final String org, final int campaignNumber, final GitHubUpdateCampaignPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(patch(CAMPAIGN, org, campaignNumber), payload), GitHubCampaignSummary.class);
    }

    /**
     * Delete a campaign for an organization ({@code DELETE /orgs/{org}/campaigns/{campaign_number}}).
     *
     * @param org organization name
     * @param campaignNumber campaign number
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void deleteCampaign(final String org, final int campaignNumber) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(CAMPAIGN, org, campaignNumber));
    }
}
