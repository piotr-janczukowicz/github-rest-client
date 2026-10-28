package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.search.GitHubIssueFieldValue;
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
import java.util.Map;

/** GitHub issue field values REST resource for API version {@code 2026-03-10}. */
public final class GitHubIssuesIssueFieldValuesApi extends GitHubResourceApi {

    private static final PathTemplate ISSUE_FIELD_VALUES =
            PathTemplate.of("/repos/{owner}/{repo}/issues/{issue_number}/issue-field-values");
    private static final PathTemplate ISSUE_FIELD_VALUE =
            PathTemplate.of("/repos/{owner}/{repo}/issues/{issue_number}/issue-field-values/{issue_field_id}");

    private static final JsonType<List<GitHubIssueFieldValue>> FIELD_VALUES_TYPE =
            JsonTypes.listOf(GitHubIssueFieldValue.class);

    /**
     * Issue field values resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubIssuesIssueFieldValuesApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List issue field values for an issue ({@code GET
     * /repos/{owner}/{repo}/issues/{issue_number}/issue-field-values}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @return first HTTP page of field values
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubIssueFieldValue> listIssueFieldValuesForIssue(
            final String owner, final String repo, final int issueNumber) throws IOException {
        return listIssueFieldValuesForIssue(owner, repo, issueNumber, GitHubPageRequest.byPage());
    }

    /**
     * List issue field values for an issue ({@code GET
     * /repos/{owner}/{repo}/issues/{issue_number}/issue-field-values}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @param request pagination token
     * @return first HTTP page of field values
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubIssueFieldValue> listIssueFieldValuesForIssue(
            final String owner, final String repo, final int issueNumber, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(ISSUE_FIELD_VALUES, Map.of(), request, FIELD_VALUES_TYPE, owner, repo, issueNumber);
    }

    /**
     * Add issue field values to an issue ({@code POST /repos/{owner}/{repo}/issues/{issue_number}/issue-field-values}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @param payload field values to add
     * @return field values on the issue
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public List<GitHubIssueFieldValue> addIssueFieldValues(
            final String owner,
            final String repo,
            final int issueNumber,
            final GitHubAddIssueFieldValuesPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(ISSUE_FIELD_VALUES, owner, repo, issueNumber), payload), FIELD_VALUES_TYPE);
    }

    /**
     * Set issue field values for an issue ({@code PUT /repos/{owner}/{repo}/issues/{issue_number}/issue-field-values}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @param payload field values to set
     * @return field values on the issue
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @SuppressWarnings("PMD.LinguisticNaming")
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public List<GitHubIssueFieldValue> setIssueFieldValues(
            final String owner,
            final String repo,
            final int issueNumber,
            final GitHubSetIssueFieldValuesPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(put(ISSUE_FIELD_VALUES, owner, repo, issueNumber), payload), FIELD_VALUES_TYPE);
    }

    /**
     * Delete an issue field value from an issue ({@code DELETE
     * /repos/{owner}/{repo}/issues/{issue_number}/issue-field-values/{issue_field_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @param issueFieldId issue field id
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void deleteIssueFieldValue(
            final String owner, final String repo, final int issueNumber, final long issueFieldId) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);

        caller.send(delete(ISSUE_FIELD_VALUE, owner, repo, issueNumber, issueFieldId));
    }
}
