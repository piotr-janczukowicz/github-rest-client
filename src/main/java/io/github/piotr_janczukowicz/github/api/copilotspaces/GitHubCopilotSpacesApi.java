package io.github.piotr_janczukowicz.github.api.copilotspaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.util.LazyValue;

/** GitHub Copilot Spaces REST resource aggregator for API version {@code 2026-03-10}. */
public final class GitHubCopilotSpacesApi {

    private final LazyValue<GitHubCopilotSpacesCollaboratorsApi> collaborators;
    private final LazyValue<GitHubCopilotSpaceApi> copilotSpace;
    private final LazyValue<GitHubCopilotSpacesResourcesApi> resources;

    /**
     * Copilot Spaces resource aggregator over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubCopilotSpacesApi(final GitHubCaller caller) {
        requireNonNull(caller, "caller");
        this.collaborators = new LazyValue<>(() -> new GitHubCopilotSpacesCollaboratorsApi(caller));
        this.copilotSpace = new LazyValue<>(() -> new GitHubCopilotSpaceApi(caller));
        this.resources = new LazyValue<>(() -> new GitHubCopilotSpacesResourcesApi(caller));
    }

    /**
     * Copilot Space collaborator operations ({@code subcategory=collaborators}).
     *
     * @return collaborators API
     */
    public GitHubCopilotSpacesCollaboratorsApi collaborators() {
        return collaborators.get();
    }

    /**
     * Copilot Space operations ({@code subcategory=copilot-spaces}).
     *
     * @return Copilot Space API
     */
    public GitHubCopilotSpaceApi copilotSpace() {
        return copilotSpace.get();
    }

    /**
     * Copilot Space resource operations ({@code subcategory=resources}).
     *
     * @return resources API
     */
    public GitHubCopilotSpacesResourcesApi resources() {
        return resources.get();
    }
}
