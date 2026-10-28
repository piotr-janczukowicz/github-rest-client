package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.secretscanning.GitHubPushRuleBypassRequest;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;

/** GitHub Enterprise Cloud push rule bypass request REST resource for API version {@code 2026-03-10}. */
public final class GitHubEnterpriseAdminBypassRequestsApi extends GitHubResourceApi {

    private static final PathTemplate PUSH_RULE_BYPASS_REQUESTS =
            PathTemplate.of("/enterprises/{enterprise}/bypass-requests/push-rules");

    private static final JsonType<List<GitHubPushRuleBypassRequest>> PUSH_RULE_BYPASS_REQUESTS_TYPE =
            JsonTypes.listOf(GitHubPushRuleBypassRequest.class);

    /**
     * Bypass request resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubEnterpriseAdminBypassRequestsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List the push rule bypass requests of an enterprise ({@code GET
     * /enterprises/{enterprise}/bypass-requests/push-rules}) with no filter.
     *
     * <p>Uses {@link GitHubPageRequest#byPage()} and an empty filter. See {@link #listPushBypassRequests(String,
     * GitHubListPushBypassRequestsFilter, GitHubPageRequest)}.
     *
     * @param enterprise the slug version of the enterprise name
     * @return first HTTP page of push rule bypass requests
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubPushRuleBypassRequest> listPushBypassRequests(final String enterprise) throws IOException {
        return listPushBypassRequests(
                enterprise, GitHubListPushBypassRequestsFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List the push rule bypass requests of an enterprise ({@code GET
     * /enterprises/{enterprise}/bypass-requests/push-rules}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param filter query filters
     * @param request page pagination token
     * @return page of push rule bypass requests
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubPushRuleBypassRequest> listPushBypassRequests(
            final String enterprise, final GitHubListPushBypassRequestsFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(
                PUSH_RULE_BYPASS_REQUESTS,
                filter.toQueryParameters(),
                request,
                PUSH_RULE_BYPASS_REQUESTS_TYPE,
                enterprise);
    }
}
