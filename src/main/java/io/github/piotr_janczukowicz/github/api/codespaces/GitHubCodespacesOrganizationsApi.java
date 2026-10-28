package io.github.piotr_janczukowicz.github.api.codespaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.client.pagination.GitHubListSpec;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequestKindValidators;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.Map;

/** GitHub Codespaces organizations REST resource for API version {@code 2026-03-10}. */
public final class GitHubCodespacesOrganizationsApi extends GitHubResourceApi {

    private static final PathTemplate ORG_CODESPACES = PathTemplate.of("/orgs/{org}/codespaces");
    private static final PathTemplate ORG_CODESPACES_ACCESS = PathTemplate.of("/orgs/{org}/codespaces/access");
    private static final PathTemplate ORG_CODESPACES_ACCESS_SELECTED_USERS =
            PathTemplate.of("/orgs/{org}/codespaces/access/selected_users");
    private static final PathTemplate ORG_MEMBER_CODESPACES =
            PathTemplate.of("/orgs/{org}/members/{username}/codespaces");
    private static final PathTemplate ORG_MEMBER_CODESPACE =
            PathTemplate.of("/orgs/{org}/members/{username}/codespaces/{codespace_name}");
    private static final PathTemplate ORG_MEMBER_CODESPACE_STOP =
            PathTemplate.of("/orgs/{org}/members/{username}/codespaces/{codespace_name}/stop");

    /**
     * Codespaces organizations resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubCodespacesOrganizationsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List codespaces for the organization ({@code GET /orgs/{org}/codespaces}).
     *
     * @param org organization name
     * @return first HTTP page of codespaces
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubCodespace> listInOrganization(final String org) throws IOException {
        return listInOrganization(org, GitHubPageRequest.byPage());
    }

    /**
     * List codespaces for the organization ({@code GET /orgs/{org}/codespaces}).
     *
     * @param org organization name
     * @param request pagination token
     * @return first HTTP page of codespaces
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubCodespace> listInOrganization(final String org, final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(request, PARAM_REQUEST);

        return list(
                ORG_CODESPACES,
                Map.of(),
                request,
                GitHubListSpec.wrapper(
                        GitHubPageRequestKindValidators.PAGE,
                        GitHubCodespacesPage.class,
                        GitHubCodespacesPage::codespaces,
                        GitHubCodespacesPage::totalCount),
                org);
    }

    /**
     * Manage access control for organization codespaces ({@code PUT /orgs/{org}/codespaces/access}).
     *
     * @deprecated GitHub marks this operation deprecated in the REST API description.
     * @param org organization name
     * @param payload visibility and selected members
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Deprecated
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void setCodespacesAccess(final String org, final GitHubSetCodespacesAccessPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(ORG_CODESPACES_ACCESS, org), payload));
    }

    /**
     * Add users to Codespaces access for an organization ({@code POST /orgs/{org}/codespaces/access/selected_users}).
     *
     * @deprecated GitHub marks this operation deprecated in the REST API description.
     * @param org organization name
     * @param payload usernames to add
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Deprecated
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void setCodespacesAccessUsers(final String org, final GitHubSetCodespacesAccessUsersPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(post(ORG_CODESPACES_ACCESS_SELECTED_USERS, org), payload));
    }

    /**
     * Remove users from Codespaces access for an organization ({@code DELETE
     * /orgs/{org}/codespaces/access/selected_users}).
     *
     * @deprecated GitHub marks this operation deprecated in the REST API description.
     * @param org organization name
     * @param payload usernames to remove
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Deprecated
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void deleteCodespacesAccessUsers(final String org, final GitHubDeleteCodespacesAccessUsersPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(delete(ORG_CODESPACES_ACCESS_SELECTED_USERS, org), payload));
    }

    /**
     * List codespaces for a user in organization ({@code GET /orgs/{org}/members/{username}/codespaces}).
     *
     * @param org organization name
     * @param username user login
     * @return first HTTP page of codespaces
     * @throws IllegalArgumentException if {@code org} or {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubCodespace> listForUserInOrganization(final String org, final String username)
            throws IOException {
        return listForUserInOrganization(org, username, GitHubPageRequest.byPage());
    }

    /**
     * List codespaces for a user in organization ({@code GET /orgs/{org}/members/{username}/codespaces}).
     *
     * @param org organization name
     * @param username user login
     * @param request pagination token
     * @return first HTTP page of codespaces
     * @throws IllegalArgumentException if {@code org} or {@code username} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubCodespace> listForUserInOrganization(
            final String org, final String username, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(request, PARAM_REQUEST);

        return list(
                ORG_MEMBER_CODESPACES,
                Map.of(),
                request,
                GitHubListSpec.wrapper(
                        GitHubPageRequestKindValidators.PAGE,
                        GitHubCodespacesPage.class,
                        GitHubCodespacesPage::codespaces,
                        GitHubCodespacesPage::totalCount),
                org,
                username);
    }

    /**
     * Delete a codespace from the organization ({@code DELETE
     * /orgs/{org}/members/{username}/codespaces/{codespace_name}} ).
     *
     * @param org organization name
     * @param username user login
     * @param codespaceName codespace name
     * @throws IllegalArgumentException if {@code org}, {@code username}, or {@code codespaceName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void deleteFromOrganization(final String org, final String username, final String codespaceName)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(ORG_MEMBER_CODESPACE, org, username, codespaceName));
    }

    /**
     * Stop a codespace for an organization user ({@code POST
     * /orgs/{org}/members/{username}/codespaces/{codespace_name}/stop}).
     *
     * @param org organization name
     * @param username user login
     * @param codespaceName codespace name
     * @return stopped codespace
     * @throws IllegalArgumentException if {@code org}, {@code username}, or {@code codespaceName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCodespace stopInOrganization(final String org, final String username, final String codespaceName)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(post(ORG_MEMBER_CODESPACE_STOP, org, username, codespaceName), GitHubCodespace.class);
    }
}
