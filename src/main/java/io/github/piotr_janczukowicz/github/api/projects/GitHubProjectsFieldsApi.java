package io.github.piotr_janczukowicz.github.api.projects;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.client.pagination.GitHubListSpec;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequestKindValidators;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** GitHub Projects field REST resource for API version {@code 2026-03-10}. */
public final class GitHubProjectsFieldsApi extends GitHubResourceApi {

    private static final PathTemplate ORG_PROJECT_FIELDS =
            PathTemplate.of("/orgs/{org}/projectsV2/{project_number}/fields");
    private static final PathTemplate ORG_PROJECT_FIELD =
            PathTemplate.of("/orgs/{org}/projectsV2/{project_number}/fields/{field_id}");
    private static final PathTemplate USERS_PROJECT_FIELDS =
            PathTemplate.of("/users/{username}/projectsV2/{project_number}/fields");
    private static final PathTemplate USERS_PROJECT_FIELD =
            PathTemplate.of("/users/{username}/projectsV2/{project_number}/fields/{field_id}");

    private static final JsonType<List<GitHubProjectField>> FIELDS_TYPE = JsonTypes.listOf(GitHubProjectField.class);

    /**
     * Project field resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubProjectsFieldsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List fields in an organization project ({@code GET /orgs/{org}/projectsV2/{project_number}/fields}).
     *
     * @param org organization login
     * @param projectNumber project number
     * @return first HTTP page of project fields
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubProjectField> listForOrg(final String org, final long projectNumber) throws IOException {
        return listForOrg(org, projectNumber, GitHubPageRequest.byCursor());
    }

    /**
     * List fields in an organization project ({@code GET /orgs/{org}/projectsV2/{project_number}/fields}).
     *
     * @param org organization login
     * @param projectNumber project number
     * @param request pagination token
     * @return first HTTP page of project fields
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a page or since variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubProjectField> listForOrg(
            final String org, final long projectNumber, final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return list(
                ORG_PROJECT_FIELDS,
                Map.of(),
                request,
                GitHubListSpec.array(GitHubPageRequestKindValidators.CURSOR, FIELDS_TYPE),
                org,
                projectNumber);
    }

    /**
     * Add a field to an organization project ({@code POST /orgs/{org}/projectsV2/{project_number}/fields}).
     *
     * @param org organization login
     * @param projectNumber project number
     * @param payload field to add
     * @return created project field
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubProjectField addForOrg(
            final String org, final long projectNumber, final GitHubAddProjectFieldPayload payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(ORG_PROJECT_FIELDS, org, projectNumber), payload), GitHubProjectField.class);
    }

    /**
     * Get a field from an organization project ({@code GET /orgs/{org}/projectsV2/{project_number}/fields/{field_id}}).
     *
     * @param org organization login
     * @param projectNumber project number
     * @param fieldId field id
     * @return project field, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubProjectField> getForOrg(final String org, final long projectNumber, final long fieldId)
            throws IOException {

        return caller.fetchOrEmpty(get(ORG_PROJECT_FIELD, org, projectNumber, fieldId), GitHubProjectField.class);
    }

    /**
     * List fields in a user project ({@code GET /users/{username}/projectsV2/{project_number}/fields}).
     *
     * @param username user login
     * @param projectNumber project number
     * @return first HTTP page of project fields
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubProjectField> listForUser(final String username, final long projectNumber)
            throws IOException {
        return listForUser(username, projectNumber, GitHubPageRequest.byCursor());
    }

    /**
     * List fields in a user project ({@code GET /users/{username}/projectsV2/{project_number}/fields}).
     *
     * @param username user login
     * @param projectNumber project number
     * @param request pagination token
     * @return first HTTP page of project fields
     * @throws IllegalArgumentException if {@code username} is blank, or {@code request} is a page or since variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubProjectField> listForUser(
            final String username, final long projectNumber, final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return list(
                USERS_PROJECT_FIELDS,
                Map.of(),
                request,
                GitHubListSpec.array(GitHubPageRequestKindValidators.CURSOR, FIELDS_TYPE),
                username,
                projectNumber);
    }

    /**
     * Add a field to a user project ({@code POST /users/{username}/projectsV2/{project_number}/fields}).
     *
     * @param username user login
     * @param projectNumber project number
     * @param payload field to add
     * @return created project field
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubProjectField addForUser(
            final String username, final long projectNumber, final GitHubAddFieldForUserPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(USERS_PROJECT_FIELDS, username, projectNumber), payload), GitHubProjectField.class);
    }

    /**
     * Get a field from a user project ({@code GET /users/{username}/projectsV2/{project_number}/fields/{field_id}}).
     *
     * @param username user login
     * @param projectNumber project number
     * @param fieldId field id
     * @return project field, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubProjectField> getForUser(final String username, final long projectNumber, final long fieldId)
            throws IOException {

        return caller.fetchOrEmpty(
                get(USERS_PROJECT_FIELD, username, projectNumber, fieldId), GitHubProjectField.class);
    }
}
