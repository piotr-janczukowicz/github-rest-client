package io.github.piotr_janczukowicz.github.api.collaborators;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.util.LazyValue;

/** GitHub Collaborators REST resource aggregator for API version {@code 2026-03-10}. */
public final class GitHubCollaboratorsApi {

    private final LazyValue<GitHubCollaboratorApi> collaborator;
    private final LazyValue<GitHubCollaboratorsInvitationsApi> invitations;

    /**
     * Collaborators resource aggregator over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubCollaboratorsApi(final GitHubCaller caller) {
        requireNonNull(caller, "caller");
        this.collaborator = new LazyValue<>(() -> new GitHubCollaboratorApi(caller));
        this.invitations = new LazyValue<>(() -> new GitHubCollaboratorsInvitationsApi(caller));
    }

    /**
     * Repository collaborator operations ({@code subcategory=collaborators}).
     *
     * @return collaborator API
     */
    public GitHubCollaboratorApi collaborator() {
        return collaborator.get();
    }

    /**
     * Repository invitation operations ({@code subcategory=invitations}).
     *
     * @return invitations API
     */
    public GitHubCollaboratorsInvitationsApi invitations() {
        return invitations.get();
    }
}
