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

/** GitHub Copilot Space collaborators REST resource for API version {@code 2026-03-10}. */
public final class GitHubCopilotSpacesCollaboratorsApi extends GitHubResourceApi {

    private static final PathTemplate ORG_COPILOT_SPACE_COLLABORATORS =
            PathTemplate.of("/orgs/{org}/copilot-spaces/{space_number}/collaborators");
    private static final PathTemplate ORG_COPILOT_SPACE_COLLABORATOR =
            PathTemplate.of("/orgs/{org}/copilot-spaces/{space_number}/collaborators/{actor_type}/{actor_identifier}");
    private static final PathTemplate USERS_COPILOT_SPACE_COLLABORATORS =
            PathTemplate.of("/users/{username}/copilot-spaces/{space_number}/collaborators");
    private static final PathTemplate USERS_COPILOT_SPACE_COLLABORATOR = PathTemplate.of(
            "/users/{username}/copilot-spaces/{space_number}/collaborators/{actor_type}/{actor_identifier}");
    private static final String PARAM_ACTOR_TYPE = "actor_type";

    /**
     * Copilot Space collaborators resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubCopilotSpacesCollaboratorsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List collaborators for an organization Copilot Space ({@code GET
     * /orgs/{org}/copilot-spaces/{space_number}/collaborators}).
     *
     * @param org organization name
     * @param spaceNumber Copilot Space number
     * @return collaborators of the space
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCopilotSpaceCollaboratorsPage listCollaboratorsForOrg(final String org, final long spaceNumber)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(
                get(ORG_COPILOT_SPACE_COLLABORATORS, org, spaceNumber), GitHubCopilotSpaceCollaboratorsPage.class);
    }

    /**
     * Add a collaborator to an organization Copilot Space ({@code POST
     * /orgs/{org}/copilot-spaces/{space_number}/collaborators}).
     *
     * @param org organization name
     * @param spaceNumber Copilot Space number
     * @param payload actor and role to add
     * @return the added collaborator
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCopilotSpaceCollaborator addCollaboratorForOrg(
            final String org, final long spaceNumber, final GitHubAddCollaboratorForOrgPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(ORG_COPILOT_SPACE_COLLABORATORS, org, spaceNumber), payload),
                GitHubCopilotSpaceCollaborator.class);
    }

    /**
     * Set a collaborator role for an organization Copilot Space ({@code PUT
     * /orgs/{org}/copilot-spaces/{space_number}/collaborators/{actor_type}/{actor_identifier}}).
     *
     * @param org organization name
     * @param spaceNumber Copilot Space number
     * @param actorType type of the collaborator
     * @param actorIdentifier username or team slug (a numeric ID is also accepted)
     * @param payload new role; {@code no_access} removes the collaborator
     * @return the updated collaborator, or empty on HTTP 204
     * @throws IllegalArgumentException if {@code org} or {@code actorIdentifier} is blank
     * @throws NullPointerException if {@code actorType} or {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubCopilotSpaceCollaborator> updateCollaboratorForOrg(
            final String org,
            final long spaceNumber,
            final GitHubCopilotSpaceActorType actorType,
            final String actorIdentifier,
            final GitHubUpdateCollaboratorPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(actorType, PARAM_ACTOR_TYPE);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetchOptional(
                caller.json(
                        put(ORG_COPILOT_SPACE_COLLABORATOR, org, spaceNumber, actorType.wireValue(), actorIdentifier),
                        payload),
                GitHubCopilotSpaceCollaborator.class);
    }

    /**
     * Remove a collaborator from an organization Copilot Space ({@code DELETE
     * /orgs/{org}/copilot-spaces/{space_number}/collaborators/{actor_type}/{actor_identifier}}).
     *
     * @param org organization name
     * @param spaceNumber Copilot Space number
     * @param actorType type of the collaborator
     * @param actorIdentifier username or team slug (a numeric ID is also accepted)
     * @throws IllegalArgumentException if {@code org} or {@code actorIdentifier} is blank
     * @throws NullPointerException if {@code actorType} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void removeCollaboratorForOrg(
            final String org,
            final long spaceNumber,
            final GitHubCopilotSpaceActorType actorType,
            final String actorIdentifier)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(actorType, PARAM_ACTOR_TYPE);

        caller.send(delete(ORG_COPILOT_SPACE_COLLABORATOR, org, spaceNumber, actorType.wireValue(), actorIdentifier));
    }

    /**
     * List collaborators for a Copilot Space for a user ({@code GET
     * /users/{username}/copilot-spaces/{space_number}/collaborators}).
     *
     * @param username user login
     * @param spaceNumber Copilot Space number
     * @return collaborators of the space
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCopilotSpaceCollaboratorsPage listCollaboratorsForUser(final String username, final long spaceNumber)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(
                get(USERS_COPILOT_SPACE_COLLABORATORS, username, spaceNumber),
                GitHubCopilotSpaceCollaboratorsPage.class);
    }

    /**
     * Add a collaborator to a Copilot Space for a user ({@code POST
     * /users/{username}/copilot-spaces/{space_number}/collaborators}).
     *
     * @param username user login
     * @param spaceNumber Copilot Space number
     * @param payload actor and role to add
     * @return the added collaborator
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCopilotSpaceCollaborator addCollaboratorForUser(
            final String username, final long spaceNumber, final GitHubAddCollaboratorForUserPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(USERS_COPILOT_SPACE_COLLABORATORS, username, spaceNumber), payload),
                GitHubCopilotSpaceCollaborator.class);
    }

    /**
     * Set a collaborator role for a Copilot Space for a user ({@code PUT
     * /users/{username}/copilot-spaces/{space_number}/collaborators/{actor_type}/{actor_identifier}}).
     *
     * @param username user login
     * @param spaceNumber Copilot Space number
     * @param actorType type of the collaborator
     * @param actorIdentifier username or team slug (a numeric ID is also accepted)
     * @param payload new role; {@code no_access} removes the collaborator
     * @return the updated collaborator, or empty on HTTP 204
     * @throws IllegalArgumentException if {@code username} or {@code actorIdentifier} is blank
     * @throws NullPointerException if {@code actorType} or {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubCopilotSpaceCollaborator> updateCollaboratorForUser(
            final String username,
            final long spaceNumber,
            final GitHubCopilotSpaceActorType actorType,
            final String actorIdentifier,
            final GitHubUpdateCollaboratorPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(actorType, PARAM_ACTOR_TYPE);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetchOptional(
                caller.json(
                        put(
                                USERS_COPILOT_SPACE_COLLABORATOR,
                                username,
                                spaceNumber,
                                actorType.wireValue(),
                                actorIdentifier),
                        payload),
                GitHubCopilotSpaceCollaborator.class);
    }

    /**
     * Remove a collaborator from a Copilot Space for a user ({@code DELETE
     * /users/{username}/copilot-spaces/{space_number}/collaborators/{actor_type}/{actor_identifier}}).
     *
     * @param username user login
     * @param spaceNumber Copilot Space number
     * @param actorType type of the collaborator
     * @param actorIdentifier username or team slug (a numeric ID is also accepted)
     * @throws IllegalArgumentException if {@code username} or {@code actorIdentifier} is blank
     * @throws NullPointerException if {@code actorType} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void removeCollaboratorForUser(
            final String username,
            final long spaceNumber,
            final GitHubCopilotSpaceActorType actorType,
            final String actorIdentifier)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(actorType, PARAM_ACTOR_TYPE);

        caller.send(delete(
                USERS_COPILOT_SPACE_COLLABORATOR, username, spaceNumber, actorType.wireValue(), actorIdentifier));
    }
}
