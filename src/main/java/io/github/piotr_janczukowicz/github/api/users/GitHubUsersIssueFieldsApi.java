package io.github.piotr_janczukowicz.github.api.users;

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

/** GitHub authenticated user issue fields REST resource for API version {@code 2026-03-10}. */
public final class GitHubUsersIssueFieldsApi extends GitHubResourceApi {

    private static final PathTemplate ISSUE_FIELDS = PathTemplate.of("/user/issue-fields");
    private static final PathTemplate ISSUE_FIELD = PathTemplate.of("/user/issue-fields/{issue_field_id}");

    private static final JsonType<List<GitHubUserIssueField>> ISSUE_FIELDS_TYPE =
            JsonTypes.listOf(GitHubUserIssueField.class);

    /**
     * Issue fields resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubUsersIssueFieldsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List issue fields for the authenticated user ({@code GET /user/issue-fields}).
     *
     * @return issue fields
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public List<GitHubUserIssueField> list() throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(ISSUE_FIELDS), ISSUE_FIELDS_TYPE);
    }

    /**
     * Create an issue field for the authenticated user ({@code POST /user/issue-fields}).
     *
     * @param payload issue field to create
     * @return created issue field
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubUserIssueField create(final GitHubCreateUserIssueFieldPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(ISSUE_FIELDS), payload), GitHubUserIssueField.class);
    }

    /**
     * Update an issue field for the authenticated user ({@code PATCH /user/issue-fields/{issue_field_id}}).
     *
     * @param issueFieldId issue field id
     * @param payload issue field fields to update
     * @return updated issue field
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubUserIssueField update(final long issueFieldId, final GitHubUpdateUserIssueFieldPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(patch(ISSUE_FIELD, issueFieldId), payload), GitHubUserIssueField.class);
    }

    /**
     * Delete an issue field for the authenticated user ({@code DELETE /user/issue-fields/{issue_field_id}}).
     *
     * @param issueFieldId issue field id
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void delete(final long issueFieldId) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(ISSUE_FIELD, issueFieldId));
    }
}
