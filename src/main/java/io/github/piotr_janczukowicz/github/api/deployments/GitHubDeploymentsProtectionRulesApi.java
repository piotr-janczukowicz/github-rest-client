package io.github.piotr_janczukowicz.github.api.deployments;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
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

/** GitHub deployment protection rules REST resource for API version {@code 2026-03-10}. */
public final class GitHubDeploymentsProtectionRulesApi extends GitHubResourceApi {

    private static final PathTemplate ENVIRONMENT_PROTECTION_RULE_APPS =
            PathTemplate.of("/repos/{owner}/{repo}/environments/{environment_name}/deployment_protection_rules/apps");
    private static final PathTemplate ENVIRONMENT_PROTECTION_RULE = PathTemplate.of(
            "/repos/{owner}/{repo}/environments/{environment_name}/deployment_protection_rules/{protection_rule_id}");
    private static final PathTemplate ENVIRONMENT_PROTECTION_RULES =
            PathTemplate.of("/repos/{owner}/{repo}/environments/{environment_name}/deployment_protection_rules");

    /**
     * Deployment protection rules resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubDeploymentsProtectionRulesApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get all deployment protection rules ({@code GET
     * /repos/{owner}/{repo}/environments/{environment_name}/deployment_protection_rules}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param environmentName environment name
     * @return deployment protection rules (single unpaged response)
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubDeploymentProtectionRule> getAllDeploymentProtectionRules(
            final String owner, final String repo, final String environmentName) throws IOException {

        return list(
                ENVIRONMENT_PROTECTION_RULES,
                Map.of(),
                GitHubPageRequest.none(),
                GitHubListSpec.wrapper(
                        GitHubPageRequestKindValidators.PAGE,
                        GitHubDeploymentProtectionRulesPage.class,
                        GitHubDeploymentProtectionRulesPage::customDeploymentProtectionRules,
                        GitHubDeploymentProtectionRulesPage::totalCount),
                owner,
                repo,
                environmentName);
    }

    /**
     * Get a custom deployment protection rule ({@code GET
     * /repos/{owner}/{repo}/environments/{environment_name}/deployment_protection_rules/{protection_rule_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param environmentName environment name
     * @param protectionRuleId protection rule id
     * @return deployment protection rule, or empty on HTTP 404
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubDeploymentProtectionRule> getCustomDeploymentProtectionRule(
            final String owner, final String repo, final String environmentName, final long protectionRuleId)
            throws IOException {

        return caller.fetchOrEmpty(
                get(ENVIRONMENT_PROTECTION_RULE, owner, repo, environmentName, protectionRuleId),
                GitHubDeploymentProtectionRule.class);
    }

    /**
     * Create a deployment protection rule ({@code POST
     * /repos/{owner}/{repo}/environments/{environment_name}/deployment_protection_rules}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param environmentName environment name
     * @param payload protection rule to create
     * @return created deployment protection rule
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubDeploymentProtectionRule createDeploymentProtectionRule(
            final String owner,
            final String repo,
            final String environmentName,
            final GitHubCreateDeploymentProtectionRulePayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(ENVIRONMENT_PROTECTION_RULES, owner, repo, environmentName), payload),
                GitHubDeploymentProtectionRule.class);
    }

    /**
     * Disable a deployment protection rule ({@code DELETE
     * /repos/{owner}/{repo}/environments/{environment_name}/deployment_protection_rules/{protection_rule_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param environmentName environment name
     * @param protectionRuleId protection rule id
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void disableDeploymentProtectionRule(
            final String owner, final String repo, final String environmentName, final long protectionRuleId)
            throws IOException {

        caller.send(delete(ENVIRONMENT_PROTECTION_RULE, owner, repo, environmentName, protectionRuleId));
    }

    /**
     * List custom deployment rule integrations ({@code GET
     * /repos/{owner}/{repo}/environments/{environment_name}/deployment_protection_rules/apps}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param environmentName environment name
     * @return first HTTP page of custom deployment rule integrations
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubCustomDeploymentRuleApp> listCustomDeploymentRuleIntegrations(
            final String owner, final String repo, final String environmentName) throws IOException {
        return listCustomDeploymentRuleIntegrations(owner, repo, environmentName, GitHubPageRequest.byPage());
    }

    /**
     * List custom deployment rule integrations ({@code GET
     * /repos/{owner}/{repo}/environments/{environment_name}/deployment_protection_rules/apps}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param environmentName environment name
     * @param request pagination token
     * @return first HTTP page of custom deployment rule integrations
     * @throws IllegalArgumentException if any argument is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubCustomDeploymentRuleApp> listCustomDeploymentRuleIntegrations(
            final String owner, final String repo, final String environmentName, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return list(
                ENVIRONMENT_PROTECTION_RULE_APPS,
                Map.of(),
                request,
                GitHubListSpec.wrapper(
                        GitHubPageRequestKindValidators.PAGE,
                        GitHubCustomDeploymentRuleIntegrationsPage.class,
                        GitHubCustomDeploymentRuleIntegrationsPage::availableCustomDeploymentRuleIntegrations,
                        GitHubCustomDeploymentRuleIntegrationsPage::totalCount),
                owner,
                repo,
                environmentName);
    }
}
