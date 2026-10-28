package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

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
import java.util.Map;

/** GitHub user social accounts REST resource for API version {@code 2026-03-10}. */
public final class GitHubUsersSocialAccountsApi extends GitHubResourceApi {

    private static final PathTemplate USERS_SOCIAL_ACCOUNTS = PathTemplate.of("/users/{username}/social_accounts");
    private static final PathTemplate USER_SOCIAL_ACCOUNTS = PathTemplate.of("/user/social_accounts");

    private static final JsonType<List<GitHubSocialAccount>> SOCIAL_ACCOUNTS_TYPE =
            JsonTypes.listOf(GitHubSocialAccount.class);

    /**
     * Users resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubUsersSocialAccountsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List social accounts for the authenticated user ({@code GET /user/social_accounts}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listSocialAccounts(GitHubPageRequest)}.
     *
     * @return first HTTP page of social accounts
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubSocialAccount> listSocialAccounts() throws IOException {
        return listSocialAccounts(GitHubPageRequest.byPage());
    }

    /**
     * List social accounts for the authenticated user ({@code GET /user/social_accounts}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param request pagination token
     * @return first HTTP page of social accounts
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code request} is a since or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubSocialAccount> listSocialAccounts(final GitHubPageRequest request) throws IOException {
        return pageList(USER_SOCIAL_ACCOUNTS, Map.of(), request, SOCIAL_ACCOUNTS_TYPE);
    }

    /**
     * List social accounts for a user ({@code GET /users/{username}/social_accounts}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listSocialAccounts(String, GitHubPageRequest)}.
     *
     * @param username GitHub username
     * @return first HTTP page of social accounts
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the user is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubSocialAccount> listSocialAccounts(final String username) throws IOException {
        return listSocialAccounts(username, GitHubPageRequest.byPage());
    }

    /**
     * List social accounts for a user ({@code GET /users/{username}/social_accounts}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param username GitHub username
     * @param request pagination token
     * @return first HTTP page of social accounts
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code username} is blank, or {@code request} is a since or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the user is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubSocialAccount> listSocialAccounts(final String username, final GitHubPageRequest request)
            throws IOException {
        return pageList(USERS_SOCIAL_ACCOUNTS, Map.of(), request, SOCIAL_ACCOUNTS_TYPE, username);
    }

    /**
     * Add social accounts for the authenticated user ({@code POST /user/social_accounts}).
     *
     * @param payload social accounts to add
     * @return social accounts after the update
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     */
    public List<GitHubSocialAccount> addSocialAccounts(final GitHubUpdateSocialAccountsPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(USER_SOCIAL_ACCOUNTS), payload), SOCIAL_ACCOUNTS_TYPE);
    }

    /**
     * Delete social accounts for the authenticated user ({@code DELETE /user/social_accounts}).
     *
     * @param payload social accounts to delete
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteSocialAccounts(final GitHubUpdateSocialAccountsPayload payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(delete(USER_SOCIAL_ACCOUNTS), payload));
    }
}
