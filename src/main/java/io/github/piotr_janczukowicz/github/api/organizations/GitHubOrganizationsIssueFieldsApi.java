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

/** GitHub organization issue fields REST resource for API version {@code 2026-03-10}. */
public final class GitHubOrganizationsIssueFieldsApi extends GitHubResourceApi {

    private static final PathTemplate ISSUE_FIELDS = PathTemplate.of("/orgs/{org}/issue-fields");
    private static final PathTemplate ISSUE_FIELD = PathTemplate.of("/orgs/{org}/issue-fields/{issue_field_id}");

    private static final JsonType<List<GitHubIssueField>> ISSUE_FIELDS_TYPE = JsonTypes.listOf(GitHubIssueField.class);

    /**
     * Issue fields resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubOrganizationsIssueFieldsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List issue fields for an organization ({@code GET /orgs/{org}/issue-fields}).
     *
     * @param org organization name
     * @return issue fields
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public List<GitHubIssueField> list(final String org) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(ISSUE_FIELDS, org), ISSUE_FIELDS_TYPE);
    }

    /**
     * Create an issue field for an organization ({@code POST /orgs/{org}/issue-fields}).
     *
     * @param org organization name
     * @param payload issue field to create
     * @return created issue field
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubIssueField create(final String org, final GitHubCreateIssueFieldPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(ISSUE_FIELDS, org), payload), GitHubIssueField.class);
    }

    /**
     * Update an issue field for an organization ({@code PATCH /orgs/{org}/issue-fields/{issue_field_id}}).
     *
     * @param org organization name
     * @param issueFieldId issue field id
     * @param payload issue field fields to update
     * @return updated issue field
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubIssueField update(
            final String org, final long issueFieldId, final GitHubUpdateIssueFieldPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(patch(ISSUE_FIELD, org, issueFieldId), payload), GitHubIssueField.class);
    }

    /**
     * Delete an issue field for an organization ({@code DELETE /orgs/{org}/issue-fields/{issue_field_id}}).
     *
     * @param org organization name
     * @param issueFieldId issue field id
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void delete(final String org, final long issueFieldId) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(ISSUE_FIELD, org, issueFieldId));
    }
}
