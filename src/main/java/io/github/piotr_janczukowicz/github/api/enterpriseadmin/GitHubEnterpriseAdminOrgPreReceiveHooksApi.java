package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
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
import java.util.Optional;

/** GitHub Enterprise Server organization pre-receive hook REST resource for API version {@code 2026-03-10}. */
public final class GitHubEnterpriseAdminOrgPreReceiveHooksApi extends GitHubResourceApi {

    private static final PathTemplate HOOKS = PathTemplate.of("/orgs/{org}/pre-receive-hooks");
    private static final PathTemplate HOOK = PathTemplate.of("/orgs/{org}/pre-receive-hooks/{pre_receive_hook_id}");

    private static final JsonType<List<GitHubOrgPreReceiveHook>> HOOKS_TYPE =
            JsonTypes.listOf(GitHubOrgPreReceiveHook.class);

    /**
     * Resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubEnterpriseAdminOrgPreReceiveHooksApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List the pre-receive hooks of an organization ({@code GET /orgs/{org}/pre-receive-hooks}).
     *
     * @param org organization login
     * @return first HTTP page
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubPage<GitHubOrgPreReceiveHook> listPreReceiveHooksForOrg(final String org) throws IOException {
        return listPreReceiveHooksForOrg(org, GitHubListPreReceiveHooksForOrgFilter.empty());
    }

    /**
     * List the pre-receive hooks of an organization ({@code GET /orgs/{org}/pre-receive-hooks}).
     *
     * @param org organization login
     * @param filter sort and direction
     * @return first HTTP page
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubPage<GitHubOrgPreReceiveHook> listPreReceiveHooksForOrg(
            final String org, final GitHubListPreReceiveHooksForOrgFilter filter) throws IOException {
        return listPreReceiveHooksForOrg(org, filter, GitHubPageRequest.byPage());
    }

    /**
     * List the pre-receive hooks of an organization ({@code GET /orgs/{org}/pre-receive-hooks}).
     *
     * @param org organization login
     * @param filter sort and direction
     * @param request pagination token
     * @return first HTTP page
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubPage<GitHubOrgPreReceiveHook> listPreReceiveHooksForOrg(
            final String org, final GitHubListPreReceiveHooksForOrgFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(HOOKS, filter.toQueryParameters(), request, HOOKS_TYPE, org);
    }

    /**
     * Get a pre-receive hook of an organization ({@code GET /orgs/{org}/pre-receive-hooks/{pre_receive_hook_id}}).
     *
     * @param org organization login
     * @param preReceiveHookId pre-receive hook id
     * @return the resource, or empty if it does not exist
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public Optional<GitHubOrgPreReceiveHook> getPreReceiveHookForOrg(final String org, final long preReceiveHookId)
            throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        return caller.fetchOrEmpty(get(HOOK, org, preReceiveHookId), GitHubOrgPreReceiveHook.class);
    }

    /**
     * Update the enforcement of a pre-receive hook for an organization ({@code PATCH
     * /orgs/{org}/pre-receive-hooks/{pre_receive_hook_id}}).
     *
     * @param org organization login
     * @param preReceiveHookId pre-receive hook id
     * @param payload request body
     * @return the resource
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubOrgPreReceiveHook updatePreReceiveHookEnforcementForOrg(
            final String org,
            final long preReceiveHookId,
            final GitHubUpdatePreReceiveHookEnforcementForOrgPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(patch(HOOK, org, preReceiveHookId), payload), GitHubOrgPreReceiveHook.class);
    }

    /**
     * Remove the enforcement of a pre-receive hook for an organization ({@code DELETE
     * /orgs/{org}/pre-receive-hooks/{pre_receive_hook_id}}).
     *
     * @param org organization login
     * @param preReceiveHookId pre-receive hook id
     * @return the hook
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubOrgPreReceiveHook removePreReceiveHookEnforcementForOrg(final String org, final long preReceiveHookId)
            throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        return caller.fetch(delete(HOOK, org, preReceiveHookId), GitHubOrgPreReceiveHook.class);
    }
}
