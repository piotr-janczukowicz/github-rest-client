package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;

/** GitHub organization issue types REST resource for API version {@code 2026-03-10}. */
public final class GitHubOrganizationsIssueTypesApi extends GitHubResourceApi {

    private static final PathTemplate ISSUE_TYPES = PathTemplate.of("/orgs/{org}/issue-types");
    private static final PathTemplate ISSUE_TYPE = PathTemplate.of("/orgs/{org}/issue-types/{issue_type_id}");

    private static final JsonType<List<GitHubIssueType>> ISSUE_TYPES_TYPE = JsonTypes.listOf(GitHubIssueType.class);

    /**
     * Issue types resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubOrganizationsIssueTypesApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List issue types for an organization ({@code GET /orgs/{org}/issue-types}).
     *
     * @param org organization name
     * @return issue types
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public List<GitHubIssueType> list(final String org) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(ISSUE_TYPES, org), ISSUE_TYPES_TYPE);
    }

    /**
     * Create an issue type for an organization ({@code POST /orgs/{org}/issue-types}).
     *
     * @param org organization name
     * @param payload issue type to create
     * @return created issue type
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubIssueType create(final String org, final GitHubCreateIssueTypePayload payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(ISSUE_TYPES, org), payload), GitHubIssueType.class);
    }

    /**
     * Update an issue type for an organization ({@code PUT /orgs/{org}/issue-types/{issue_type_id}}).
     *
     * @param org organization name
     * @param issueTypeId issue type id
     * @param payload issue type to replace
     * @return updated issue type
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubIssueType update(final String org, final long issueTypeId, final GitHubUpdateIssueTypePayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(put(ISSUE_TYPE, org, issueTypeId), payload), GitHubIssueType.class);
    }

    /**
     * Delete an issue type for an organization ({@code DELETE /orgs/{org}/issue-types/{issue_type_id}}).
     *
     * @param org organization name
     * @param issueTypeId issue type id
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void delete(final String org, final long issueTypeId) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(ISSUE_TYPE, org, issueTypeId));
    }
}
