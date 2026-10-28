package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequestKindValidators;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;

/** GitHub billing advanced security REST resource for API version {@code 2026-03-10}. */
public final class GitHubBillingBillingApi extends GitHubResourceApi {

    private static final PathTemplate ORG_ADVANCED_SECURITY =
            PathTemplate.of("/orgs/{org}/settings/billing/advanced-security");

    /**
     * Billing advanced security resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubBillingBillingApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get GitHub Advanced Security active committers of an organization ({@code GET
     * /orgs/{org}/settings/billing/advanced-security}).
     *
     * @param org organization name
     * @return active committers with the first page of repositories
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubAdvancedSecurityActiveCommitters getAdvancedSecurityBilling(final String org) throws IOException {
        return getAdvancedSecurityBilling(
                org, GitHubGetGithubAdvancedSecurityBillingOrgFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * Get GitHub Advanced Security active committers of an organization ({@code GET
     * /orgs/{org}/settings/billing/advanced-security}).
     *
     * @param org organization name
     * @param filter query filters
     * @param request pagination token
     * @return active committers with the requested page of repositories
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubAdvancedSecurityActiveCommitters getAdvancedSecurityBilling(
            final String org,
            final GitHubGetGithubAdvancedSecurityBillingOrgFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return caller.fetch(
                caller.pageParameters(
                        get(ORG_ADVANCED_SECURITY, org).withQuery(filter.toQueryParameters()),
                        request,
                        GitHubPageRequestKindValidators.PAGE),
                GitHubAdvancedSecurityActiveCommitters.class);
    }
}
