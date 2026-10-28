package io.github.piotr_janczukowicz.github.api.copilotspaces;

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
import java.util.Optional;

/** GitHub Copilot Spaces REST resource for API version {@code 2026-03-10}. */
public final class GitHubCopilotSpaceApi extends GitHubResourceApi {

    private static final PathTemplate ORG_COPILOT_SPACES = PathTemplate.of("/orgs/{org}/copilot-spaces");
    private static final PathTemplate ORG_COPILOT_SPACE = PathTemplate.of("/orgs/{org}/copilot-spaces/{space_number}");
    private static final PathTemplate USERS_COPILOT_SPACES = PathTemplate.of("/users/{username}/copilot-spaces");
    private static final PathTemplate USERS_COPILOT_SPACE =
            PathTemplate.of("/users/{username}/copilot-spaces/{space_number}");

    /**
     * Copilot Spaces resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubCopilotSpaceApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List organization Copilot Spaces ({@code GET /orgs/{org}/copilot-spaces}).
     *
     * <p>Uses {@link GitHubPageRequest#byCursor()}. See {@link #listForOrg(String, GitHubPageRequest)}.
     *
     * @param org organization name
     * @return first HTTP page of Copilot Spaces
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubCopilotSpace> listForOrg(final String org) throws IOException {
        return listForOrg(org, GitHubPageRequest.byCursor());
    }

    /**
     * List organization Copilot Spaces ({@code GET /orgs/{org}/copilot-spaces}).
     *
     * @param org organization name
     * @param request pagination token
     * @return first HTTP page of Copilot Spaces
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a page or since variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubCopilotSpace> listForOrg(final String org, final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(request, PARAM_REQUEST);

        return list(
                ORG_COPILOT_SPACES,
                Map.of(),
                request,
                GitHubListSpec.wrapper(
                        GitHubPageRequestKindValidators.CURSOR,
                        GitHubCopilotSpacesPage.class,
                        GitHubCopilotSpacesPage::spaces,
                        null),
                org);
    }

    /**
     * Create an organization Copilot Space ({@code POST /orgs/{org}/copilot-spaces}).
     *
     * @param org organization name
     * @param payload space to create
     * @return the created space
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCopilotSpace createForOrg(final String org, final GitHubCreateForOrgPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(ORG_COPILOT_SPACES, org), payload), GitHubCopilotSpace.class);
    }

    /**
     * Get an organization Copilot Space ({@code GET /orgs/{org}/copilot-spaces/{space_number}}).
     *
     * @param org organization name
     * @param spaceNumber Copilot Space number
     * @return the space, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubCopilotSpace> getForOrg(final String org, final long spaceNumber) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(get(ORG_COPILOT_SPACE, org, spaceNumber), GitHubCopilotSpace.class);
    }

    /**
     * Set an organization Copilot Space ({@code PUT /orgs/{org}/copilot-spaces/{space_number}}).
     *
     * @param org organization name
     * @param spaceNumber Copilot Space number
     * @param payload updated space fields
     * @return the updated space
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCopilotSpace updateForOrg(
            final String org, final long spaceNumber, final GitHubUpdateForOrgPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(put(ORG_COPILOT_SPACE, org, spaceNumber), payload), GitHubCopilotSpace.class);
    }

    /**
     * Delete an organization Copilot Space ({@code DELETE /orgs/{org}/copilot-spaces/{space_number}}).
     *
     * @param org organization name
     * @param spaceNumber Copilot Space number
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void deleteForOrg(final String org, final long spaceNumber) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(ORG_COPILOT_SPACE, org, spaceNumber));
    }

    /**
     * List Copilot Spaces for a user ({@code GET /users/{username}/copilot-spaces}).
     *
     * <p>Uses {@link GitHubPageRequest#byCursor()}. See {@link #listForUser(String, GitHubPageRequest)}.
     *
     * @param username user login
     * @return first HTTP page of Copilot Spaces
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubCopilotSpace> listForUser(final String username) throws IOException {
        return listForUser(username, GitHubPageRequest.byCursor());
    }

    /**
     * List Copilot Spaces for a user ({@code GET /users/{username}/copilot-spaces}).
     *
     * @param username user login
     * @param request pagination token
     * @return first HTTP page of Copilot Spaces
     * @throws IllegalArgumentException if {@code username} is blank, or {@code request} is a page or since variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubCopilotSpace> listForUser(final String username, final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(request, PARAM_REQUEST);

        return list(
                USERS_COPILOT_SPACES,
                Map.of(),
                request,
                GitHubListSpec.wrapper(
                        GitHubPageRequestKindValidators.CURSOR,
                        GitHubCopilotSpacesPage.class,
                        GitHubCopilotSpacesPage::spaces,
                        null),
                username);
    }

    /**
     * Create a Copilot Space for a user ({@code POST /users/{username}/copilot-spaces}).
     *
     * @param username user login
     * @param payload space to create
     * @return the created space
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCopilotSpace createForUser(final String username, final GitHubCreateForUserPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(USERS_COPILOT_SPACES, username), payload), GitHubCopilotSpace.class);
    }

    /**
     * Get a Copilot Space for a user ({@code GET /users/{username}/copilot-spaces/{space_number}}).
     *
     * @param username user login
     * @param spaceNumber Copilot Space number
     * @return the space, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubCopilotSpace> getForUser(final String username, final long spaceNumber) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(get(USERS_COPILOT_SPACE, username, spaceNumber), GitHubCopilotSpace.class);
    }

    /**
     * Set a Copilot Space for a user ({@code PUT /users/{username}/copilot-spaces/{space_number}}).
     *
     * @param username user login
     * @param spaceNumber Copilot Space number
     * @param payload updated space fields
     * @return the updated space
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCopilotSpace updateForUser(
            final String username, final long spaceNumber, final GitHubUpdateForUserPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(put(USERS_COPILOT_SPACE, username, spaceNumber), payload), GitHubCopilotSpace.class);
    }

    /**
     * Delete a Copilot Space for a user ({@code DELETE /users/{username}/copilot-spaces/{space_number}}).
     *
     * @param username user login
     * @param spaceNumber Copilot Space number
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void deleteForUser(final String username, final long spaceNumber) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(USERS_COPILOT_SPACE, username, spaceNumber));
    }
}
