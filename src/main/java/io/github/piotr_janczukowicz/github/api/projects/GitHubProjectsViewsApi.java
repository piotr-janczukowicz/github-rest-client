package io.github.piotr_janczukowicz.github.api.projects;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;

/** GitHub Projects view REST resource for API version {@code 2026-03-10}. */
public final class GitHubProjectsViewsApi extends GitHubResourceApi {

    private static final PathTemplate ORG_PROJECT_VIEWS =
            PathTemplate.of("/orgs/{org}/projectsV2/{project_number}/views");
    private static final PathTemplate USERS_PROJECT_VIEWS =
            PathTemplate.of("/users/{user_id}/projectsV2/{project_number}/views");

    /**
     * Project view resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubProjectsViewsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Create a view in an organization project ({@code POST /orgs/{org}/projectsV2/{project_number}/views}).
     *
     * @param org organization login
     * @param projectNumber project number
     * @param payload view details
     * @return created project view
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubProjectView createForOrg(
            final String org, final long projectNumber, final GitHubCreateProjectViewPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(ORG_PROJECT_VIEWS, org, projectNumber), payload), GitHubProjectView.class);
    }

    /**
     * Create a view in a user-owned project ({@code POST /users/{user_id}/projectsV2/{project_number}/views}).
     *
     * @param userId user id
     * @param projectNumber project number
     * @param payload view details
     * @return created project view
     * @throws IllegalArgumentException if {@code userId} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubProjectView createForUser(
            final String userId, final long projectNumber, final GitHubCreateProjectViewPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(USERS_PROJECT_VIEWS, userId, projectNumber), payload), GitHubProjectView.class);
    }
}
