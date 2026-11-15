package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubIssueType;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubIssueTypeRequest;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;

/** GitHub authenticated user issue types REST resource for API version {@code 2026-03-10}. */
public final class GitHubUsersIssueTypesApi extends GitHubResourceApi {

    private static final PathTemplate ISSUE_TYPES = PathTemplate.of("/user/issue-types");
    private static final PathTemplate ISSUE_TYPE = PathTemplate.of("/user/issue-types/{issue_type_id}");

    private static final JsonType<List<GitHubIssueType>> ISSUE_TYPES_TYPE = JsonTypes.listOf(GitHubIssueType.class);

    /**
     * Issue types resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubUsersIssueTypesApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List issue types for the authenticated user ({@code GET /user/issue-types}).
     *
     * @return issue types
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public List<GitHubIssueType> list() throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(ISSUE_TYPES), ISSUE_TYPES_TYPE);
    }

    /**
     * Create an issue type for the authenticated user ({@code POST /user/issue-types}).
     *
     * @param payload issue type to create
     * @return created issue type
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubIssueType create(final GitHubIssueTypeRequest payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(ISSUE_TYPES), payload), GitHubIssueType.class);
    }

    /**
     * Update an issue type for the authenticated user ({@code PUT /user/issue-types/{issue_type_id}}).
     *
     * @param issueTypeId issue type id
     * @param payload issue type to replace
     * @return updated issue type
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubIssueType update(final long issueTypeId, final GitHubIssueTypeRequest payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(put(ISSUE_TYPE, issueTypeId), payload), GitHubIssueType.class);
    }

    /**
     * Delete an issue type for the authenticated user ({@code DELETE /user/issue-types/{issue_type_id}}).
     *
     * @param issueTypeId issue type id
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void delete(final long issueTypeId) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(ISSUE_TYPE, issueTypeId));
    }
}
