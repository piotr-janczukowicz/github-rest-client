package io.github.piotr_janczukowicz.github.api.copilotspaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.Optional;

/** GitHub Copilot Space resources REST resource for API version {@code 2026-03-10}. */
public final class GitHubCopilotSpacesResourcesApi extends GitHubResourceApi {

    private static final PathTemplate ORG_COPILOT_SPACE_RESOURCES =
            PathTemplate.of("/orgs/{org}/copilot-spaces/{space_number}/resources");
    private static final PathTemplate ORG_COPILOT_SPACE_RESOURCE =
            PathTemplate.of("/orgs/{org}/copilot-spaces/{space_number}/resources/{space_resource_id}");
    private static final PathTemplate USERS_COPILOT_SPACE_RESOURCES =
            PathTemplate.of("/users/{username}/copilot-spaces/{space_number}/resources");
    private static final PathTemplate USERS_COPILOT_SPACE_RESOURCE =
            PathTemplate.of("/users/{username}/copilot-spaces/{space_number}/resources/{space_resource_id}");

    /**
     * Copilot Space resources resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubCopilotSpacesResourcesApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List resources for an organization Copilot Space ({@code GET
     * /orgs/{org}/copilot-spaces/{space_number}/resources}).
     *
     * @param org organization name
     * @param spaceNumber Copilot Space number
     * @return resources attached to the space
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCopilotSpaceResourcesPage listResourcesForOrg(final String org, final long spaceNumber)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(ORG_COPILOT_SPACE_RESOURCES, org, spaceNumber), GitHubCopilotSpaceResourcesPage.class);
    }

    /**
     * Create a resource for an organization Copilot Space ({@code POST
     * /orgs/{org}/copilot-spaces/{space_number}/resources}).
     *
     * <p>GitHub answers HTTP 201 when the resource is created and HTTP 200 when it already exists.
     *
     * @param org organization name
     * @param spaceNumber Copilot Space number
     * @param payload resource type and metadata
     * @return the resource
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCopilotSpaceResource createResourceForOrg(
            final String org, final long spaceNumber, final GitHubCreateResourcePayload payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(ORG_COPILOT_SPACE_RESOURCES, org, spaceNumber), payload),
                GitHubCopilotSpaceResource.class);
    }

    /**
     * Get a resource for an organization Copilot Space ({@code GET
     * /orgs/{org}/copilot-spaces/{space_number}/resources/{space_resource_id}}).
     *
     * @param org organization name
     * @param spaceNumber Copilot Space number
     * @param spaceResourceId resource ID
     * @return the resource, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubCopilotSpaceResource> getResourceForOrg(
            final String org, final long spaceNumber, final long spaceResourceId) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(
                get(ORG_COPILOT_SPACE_RESOURCE, org, spaceNumber, spaceResourceId), GitHubCopilotSpaceResource.class);
    }

    /**
     * Set a resource for an organization Copilot Space ({@code PUT
     * /orgs/{org}/copilot-spaces/{space_number}/resources/{space_resource_id}}).
     *
     * @param org organization name
     * @param spaceNumber Copilot Space number
     * @param spaceResourceId resource ID
     * @param payload updated metadata
     * @return the updated resource
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCopilotSpaceResource updateResourceForOrg(
            final String org,
            final long spaceNumber,
            final long spaceResourceId,
            final GitHubUpdateResourcePayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(put(ORG_COPILOT_SPACE_RESOURCE, org, spaceNumber, spaceResourceId), payload),
                GitHubCopilotSpaceResource.class);
    }

    /**
     * Delete a resource from an organization Copilot Space ({@code DELETE
     * /orgs/{org}/copilot-spaces/{space_number}/resources/{space_resource_id}}).
     *
     * @param org organization name
     * @param spaceNumber Copilot Space number
     * @param spaceResourceId resource ID
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void deleteResourceForOrg(final String org, final long spaceNumber, final long spaceResourceId)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(ORG_COPILOT_SPACE_RESOURCE, org, spaceNumber, spaceResourceId));
    }

    /**
     * List resources for a Copilot Space for a user ({@code GET
     * /users/{username}/copilot-spaces/{space_number}/resources}).
     *
     * @param username user login
     * @param spaceNumber Copilot Space number
     * @return resources attached to the space
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCopilotSpaceResourcesPage listResourcesForUser(final String username, final long spaceNumber)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(
                get(USERS_COPILOT_SPACE_RESOURCES, username, spaceNumber), GitHubCopilotSpaceResourcesPage.class);
    }

    /**
     * Create a resource for a Copilot Space for a user ({@code POST
     * /users/{username}/copilot-spaces/{space_number}/resources}).
     *
     * <p>GitHub answers HTTP 201 when the resource is created and HTTP 200 when it already exists.
     *
     * @param username user login
     * @param spaceNumber Copilot Space number
     * @param payload resource type and metadata
     * @return the resource
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCopilotSpaceResource createResourceForUser(
            final String username, final long spaceNumber, final GitHubCreateResourcePayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(USERS_COPILOT_SPACE_RESOURCES, username, spaceNumber), payload),
                GitHubCopilotSpaceResource.class);
    }

    /**
     * Get a resource for a Copilot Space for a user ({@code GET
     * /users/{username}/copilot-spaces/{space_number}/resources/{space_resource_id}}).
     *
     * @param username user login
     * @param spaceNumber Copilot Space number
     * @param spaceResourceId resource ID
     * @return the resource, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubCopilotSpaceResource> getResourceForUser(
            final String username, final long spaceNumber, final long spaceResourceId) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(
                get(USERS_COPILOT_SPACE_RESOURCE, username, spaceNumber, spaceResourceId),
                GitHubCopilotSpaceResource.class);
    }

    /**
     * Set a resource for a Copilot Space for a user ({@code PUT
     * /users/{username}/copilot-spaces/{space_number}/resources/{space_resource_id}}).
     *
     * @param username user login
     * @param spaceNumber Copilot Space number
     * @param spaceResourceId resource ID
     * @param payload updated metadata
     * @return the updated resource
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCopilotSpaceResource updateResourceForUser(
            final String username,
            final long spaceNumber,
            final long spaceResourceId,
            final GitHubUpdateResourcePayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(put(USERS_COPILOT_SPACE_RESOURCE, username, spaceNumber, spaceResourceId), payload),
                GitHubCopilotSpaceResource.class);
    }

    /**
     * Delete a resource from a Copilot Space for a user ({@code DELETE
     * /users/{username}/copilot-spaces/{space_number}/resources/{space_resource_id}}).
     *
     * @param username user login
     * @param spaceNumber Copilot Space number
     * @param spaceResourceId resource ID
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void deleteResourceForUser(final String username, final long spaceNumber, final long spaceResourceId)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(USERS_COPILOT_SPACE_RESOURCE, username, spaceNumber, spaceResourceId));
    }
}
