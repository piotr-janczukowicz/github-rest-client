package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubRuleSuite;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRuleSuiteListItem;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/** GitHub organization rule suites REST resource for API version {@code 2026-03-10}. */
public final class GitHubOrganizationsRuleSuitesApi extends GitHubResourceApi {

    private static final PathTemplate RULE_SUITE = PathTemplate.of("/orgs/{org}/rulesets/rule-suites/{rule_suite_id}");
    private static final PathTemplate RULE_SUITES = PathTemplate.of("/orgs/{org}/rulesets/rule-suites");

    private static final JsonType<List<GitHubRuleSuiteListItem>> RULE_SUITES_TYPE =
            JsonTypes.listOf(GitHubRuleSuiteListItem.class);

    /**
     * Rule suites resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubOrganizationsRuleSuitesApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List rule suites ({@code GET /orgs/{org}/rulesets/rule-suites}).
     *
     * @param org organization login
     * @return first HTTP page of rule suites
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRuleSuiteListItem> list(final String org) throws IOException {
        return list(org, GitHubListOrgRuleSuitesFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List rule suites ({@code GET /orgs/{org}/rulesets/rule-suites}).
     *
     * @param org organization login
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of rule suites
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRuleSuiteListItem> list(
            final String org, final GitHubListOrgRuleSuitesFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(RULE_SUITES, filter.toQueryParameters(), request, RULE_SUITES_TYPE, org);
    }

    /**
     * Get a rule suite ({@code GET /orgs/{org}/rulesets/rule-suites/{rule_suite_id}}).
     *
     * @param org organization login
     * @param ruleSuiteId rule suite ID
     * @return rule suite, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubRuleSuite> get(final String org, final long ruleSuiteId) throws IOException {
        return get(org, ruleSuiteId, GitHubGetOrgRuleSuiteFilter.empty());
    }

    /**
     * Get a rule suite ({@code GET /orgs/{org}/rulesets/rule-suites/{rule_suite_id}}).
     *
     * @param org organization login
     * @param ruleSuiteId rule suite ID
     * @param filter query filters
     * @return rule suite, or empty on HTTP 404
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubRuleSuite> get(
            final String org, final long ruleSuiteId, final GitHubGetOrgRuleSuiteFilter filter) throws IOException {
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetchOrEmpty(
                get(RULE_SUITE, org, ruleSuiteId).withQuery(filter.toQueryParameters()), GitHubRuleSuite.class);
    }
}
