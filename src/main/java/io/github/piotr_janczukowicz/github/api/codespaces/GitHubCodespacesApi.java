package io.github.piotr_janczukowicz.github.api.codespaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.util.LazyValue;

/**
 * GitHub Codespaces REST resource aggregator for API version {@code 2026-03-10}.
 *
 * <p>Codespaces, organizations, organization secrets, machines, repository secrets, and user secrets.
 */
public final class GitHubCodespacesApi {

    private final LazyValue<GitHubCodespaceApi> codespace;
    private final LazyValue<GitHubCodespacesOrganizationsApi> organizations;
    private final LazyValue<GitHubCodespacesOrganizationSecretsApi> organizationSecrets;
    private final LazyValue<GitHubCodespacesMachinesApi> machines;
    private final LazyValue<GitHubCodespacesRepositorySecretsApi> repositorySecrets;
    private final LazyValue<GitHubCodespacesSecretsApi> secrets;

    /**
     * Codespaces resource aggregator over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubCodespacesApi(final GitHubCaller caller) {
        requireNonNull(caller, "caller");
        this.codespace = new LazyValue<>(() -> new GitHubCodespaceApi(caller));
        this.organizations = new LazyValue<>(() -> new GitHubCodespacesOrganizationsApi(caller));
        this.organizationSecrets = new LazyValue<>(() -> new GitHubCodespacesOrganizationSecretsApi(caller));
        this.machines = new LazyValue<>(() -> new GitHubCodespacesMachinesApi(caller));
        this.repositorySecrets = new LazyValue<>(() -> new GitHubCodespacesRepositorySecretsApi(caller));
        this.secrets = new LazyValue<>(() -> new GitHubCodespacesSecretsApi(caller));
    }

    /**
     * Codespace operations ({@code subcategory=codespaces}).
     *
     * @return codespace API
     */
    public GitHubCodespaceApi codespace() {
        return codespace.get();
    }

    /**
     * Codespaces organization operations ({@code subcategory=organizations}).
     *
     * @return organizations API
     */
    public GitHubCodespacesOrganizationsApi organizations() {
        return organizations.get();
    }

    /**
     * Organization secret operations ({@code subcategory=organization-secrets}).
     *
     * @return organization secrets API
     */
    public GitHubCodespacesOrganizationSecretsApi organizationSecrets() {
        return organizationSecrets.get();
    }

    /**
     * Machine type operations ({@code subcategory=machines}).
     *
     * @return machines API
     */
    public GitHubCodespacesMachinesApi machines() {
        return machines.get();
    }

    /**
     * Repository secret operations ({@code subcategory=repository-secrets}).
     *
     * @return repository secrets API
     */
    public GitHubCodespacesRepositorySecretsApi repositorySecrets() {
        return repositorySecrets.get();
    }

    /**
     * User secret operations ({@code subcategory=secrets}).
     *
     * @return user secrets API
     */
    public GitHubCodespacesSecretsApi secrets() {
        return secrets.get();
    }
}
