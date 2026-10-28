package io.github.piotr_janczukowicz.github.api.agents;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.util.LazyValue;

/**
 * GitHub Agents REST resource aggregator for API version {@code 2026-03-10}.
 *
 * <p>Agent secrets and agent variables, for organizations and repositories.
 */
public final class GitHubAgentsApi {

    private final LazyValue<GitHubAgentsSecretsApi> secrets;
    private final LazyValue<GitHubAgentsVariablesApi> variables;

    /**
     * Agents resource aggregator over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubAgentsApi(final GitHubCaller caller) {
        requireNonNull(caller, "caller");
        this.secrets = new LazyValue<>(() -> new GitHubAgentsSecretsApi(caller));
        this.variables = new LazyValue<>(() -> new GitHubAgentsVariablesApi(caller));
    }

    /**
     * Agent secret operations ({@code subcategory=secrets}).
     *
     * @return secrets API
     */
    public GitHubAgentsSecretsApi secrets() {
        return secrets.get();
    }

    /**
     * Agent variable operations ({@code subcategory=variables}).
     *
     * @return variables API
     */
    public GitHubAgentsVariablesApi variables() {
        return variables.get();
    }
}
