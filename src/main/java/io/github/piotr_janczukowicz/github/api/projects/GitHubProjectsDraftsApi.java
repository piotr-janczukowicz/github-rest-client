package io.github.piotr_janczukowicz.github.api.projects;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;

/** GitHub Projects draft item REST resource for API version {@code 2026-03-10}. */
public final class GitHubProjectsDraftsApi extends GitHubResourceApi {

    private static final PathTemplate ORG_PROJECT_DRAFTS =
            PathTemplate.of("/orgs/{org}/projectsV2/{project_number}/drafts");
    private static final PathTemplate USER_PROJECT_DRAFTS =
            PathTemplate.of("/user/{user_id}/projectsV2/{project_number}/drafts");

    /**
     * Project draft item resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubProjectsDraftsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Create a draft item in an organization project ({@code POST /orgs/{org}/projectsV2/{project_number}/drafts}).
     *
     * @param org organization login
     * @param projectNumber project number
     * @param payload draft item details
     * @return created project item
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubProjectItemSimple createForOrg(
            final String org, final long projectNumber, final GitHubCreateDraftItemPayload payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(ORG_PROJECT_DRAFTS, org, projectNumber), payload), GitHubProjectItemSimple.class);
    }

    /**
     * Create a draft item in a user-owned project ({@code POST /user/{user_id}/projectsV2/{project_number}/drafts}).
     *
     * @param userId authenticated user id
     * @param projectNumber project number
     * @param payload draft item details
     * @return created project item
     * @throws IllegalArgumentException if {@code userId} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubProjectItemSimple createForAuthenticatedUser(
            final String userId, final long projectNumber, final GitHubCreateDraftItemPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(USER_PROJECT_DRAFTS, userId, projectNumber), payload), GitHubProjectItemSimple.class);
    }
}
