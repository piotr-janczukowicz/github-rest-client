package io.github.piotr_janczukowicz.github.api.branches;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.apps.GitHubIntegration;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeam;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/** GitHub repository branch protection REST resource for API version {@code 2026-03-10}. */
@SuppressWarnings("PMD.LinguisticNaming")
public final class GitHubBranchesBranchProtectionApi extends GitHubResourceApi {

    private static final PathTemplate BRANCH_PROTECTION =
            PathTemplate.of("/repos/{owner}/{repo}/branches/{+branch}/protection");
    private static final PathTemplate BRANCH_PROTECTION_ENFORCE_ADMINS =
            PathTemplate.of("/repos/{owner}/{repo}/branches/{+branch}/protection/enforce_admins");
    private static final PathTemplate REQUIRED_PULL_REQUEST_REVIEWS =
            PathTemplate.of("/repos/{owner}/{repo}/branches/{+branch}/protection/required_pull_request_reviews");
    private static final PathTemplate BRANCH_PROTECTION_REQUIRED_SIGNATURES =
            PathTemplate.of("/repos/{owner}/{repo}/branches/{+branch}/protection/required_signatures");
    private static final PathTemplate BRANCH_PROTECTION_REQUIRED_STATUS_CHECKS =
            PathTemplate.of("/repos/{owner}/{repo}/branches/{+branch}/protection/required_status_checks");
    private static final PathTemplate REQUIRED_STATUS_CHECKS_CONTEXTS =
            PathTemplate.of("/repos/{owner}/{repo}/branches/{+branch}/protection/required_status_checks/contexts");
    private static final PathTemplate BRANCH_PROTECTION_RESTRICTIONS =
            PathTemplate.of("/repos/{owner}/{repo}/branches/{+branch}/protection/restrictions");
    private static final PathTemplate BRANCH_PROTECTION_RESTRICTIONS_APPS =
            PathTemplate.of("/repos/{owner}/{repo}/branches/{+branch}/protection/restrictions/apps");
    private static final PathTemplate BRANCH_PROTECTION_RESTRICTIONS_TEAMS =
            PathTemplate.of("/repos/{owner}/{repo}/branches/{+branch}/protection/restrictions/teams");
    private static final PathTemplate BRANCH_PROTECTION_RESTRICTIONS_USERS =
            PathTemplate.of("/repos/{owner}/{repo}/branches/{+branch}/protection/restrictions/users");

    private static final String BODY_CONTEXTS = "contexts";
    private static final String BODY_TEAMS = "teams";

    private static final JsonType<List<String>> STRING_LIST_TYPE = JsonTypes.listOf(String.class);
    private static final JsonType<List<GitHubIntegration>> INTEGRATIONS_TYPE =
            JsonTypes.listOf(GitHubIntegration.class);
    private static final JsonType<List<GitHubTeam>> TEAMS_TYPE = JsonTypes.listOf(GitHubTeam.class);
    private static final JsonType<List<GitHubSimpleUser>> SIMPLE_USERS_TYPE = JsonTypes.listOf(GitHubSimpleUser.class);

    /**
     * Branch protection resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubBranchesBranchProtectionApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get branch protection ({@code GET /repos/{owner}/{repo}/branches/{branch}/protection}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param branch branch name
     * @return branch protection, or empty on HTTP 404
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubBranchProtection> getBranchProtection(
            final String owner, final String repo, final String branch) throws IOException {

        return caller.fetchOrEmpty(get(BRANCH_PROTECTION, owner, repo, branch), GitHubBranchProtection.class);
    }

    /**
     * Update branch protection ({@code PUT /repos/{owner}/{repo}/branches/{branch}/protection}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param branch branch name
     * @param payload protection settings
     * @return updated protected branch
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubProtectedBranch updateBranchProtection(
            final String owner,
            final String repo,
            final String branch,
            final GitHubUpdateBranchProtectionPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(put(BRANCH_PROTECTION, owner, repo, branch), payload), GitHubProtectedBranch.class);
    }

    /**
     * Delete branch protection ({@code DELETE /repos/{owner}/{repo}/branches/{branch}/protection}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param branch branch name
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteBranchProtection(final String owner, final String repo, final String branch) throws IOException {
        caller.send(delete(BRANCH_PROTECTION, owner, repo, branch));
    }

    /**
     * Get admin branch protection ({@code GET /repos/{owner}/{repo}/branches/{branch}/protection/enforce_admins}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param branch branch name
     * @return admin enforcement status, or empty if not found (HTTP 404)
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubProtectedBranchAdminEnforced> getAdminBranchProtection(
            final String owner, final String repo, final String branch) throws IOException {

        return caller.fetchOrEmpty(
                get(BRANCH_PROTECTION_ENFORCE_ADMINS, owner, repo, branch), GitHubProtectedBranchAdminEnforced.class);
    }

    /**
     * Set admin branch protection ({@code POST /repos/{owner}/{repo}/branches/{branch}/protection/enforce_admins}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param branch branch name
     * @return admin enforcement status
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubProtectedBranchAdminEnforced setAdminBranchProtection(
            final String owner, final String repo, final String branch) throws IOException {

        return caller.fetch(
                post(BRANCH_PROTECTION_ENFORCE_ADMINS, owner, repo, branch), GitHubProtectedBranchAdminEnforced.class);
    }

    /**
     * Delete admin branch protection ({@code DELETE
     * /repos/{owner}/{repo}/branches/{branch}/protection/enforce_admins}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param branch branch name
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteAdminBranchProtection(final String owner, final String repo, final String branch)
            throws IOException {

        caller.send(delete(BRANCH_PROTECTION_ENFORCE_ADMINS, owner, repo, branch));
    }

    /**
     * Get pull request review protection ({@code GET
     * /repos/{owner}/{repo}/branches/{branch}/protection/required_pull_request_reviews}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param branch branch name
     * @return pull request review protection, or empty if not found (HTTP 404)
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubProtectedBranchPullRequestReview> getPullRequestReviewProtection(
            final String owner, final String repo, final String branch) throws IOException {

        return caller.fetchOrEmpty(
                get(REQUIRED_PULL_REQUEST_REVIEWS, owner, repo, branch), GitHubProtectedBranchPullRequestReview.class);
    }

    /**
     * Update pull request review protection ({@code PATCH
     * /repos/{owner}/{repo}/branches/{branch}/protection/required_pull_request_reviews}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param branch branch name
     * @param payload optional review protection settings
     * @return updated pull request review protection
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubProtectedBranchPullRequestReview updatePullRequestReviewProtection(
            final String owner,
            final String repo,
            final String branch,
            final @Nullable GitHubUpdatePullRequestReviewProtectionPayload payload)
            throws IOException {

        return caller.fetch(
                caller.jsonIfPresent(patch(REQUIRED_PULL_REQUEST_REVIEWS, owner, repo, branch), payload),
                GitHubProtectedBranchPullRequestReview.class);
    }

    /**
     * Delete pull request review protection ({@code DELETE
     * /repos/{owner}/{repo}/branches/{branch}/protection/required_pull_request_reviews}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param branch branch name
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deletePullRequestReviewProtection(final String owner, final String repo, final String branch)
            throws IOException {

        caller.send(delete(REQUIRED_PULL_REQUEST_REVIEWS, owner, repo, branch));
    }

    /**
     * Get commit signature protection ({@code GET
     * /repos/{owner}/{repo}/branches/{branch}/protection/required_signatures}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param branch branch name
     * @return commit signature protection, or empty on HTTP 404
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubProtectedBranchAdminEnforced> getCommitSignatureProtection(
            final String owner, final String repo, final String branch) throws IOException {

        return caller.fetchOrEmpty(
                get(BRANCH_PROTECTION_REQUIRED_SIGNATURES, owner, repo, branch),
                GitHubProtectedBranchAdminEnforced.class);
    }

    /**
     * Create commit signature protection ({@code POST
     * /repos/{owner}/{repo}/branches/{branch}/protection/required_signatures}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param branch branch name
     * @return commit signature protection
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubProtectedBranchAdminEnforced createCommitSignatureProtection(
            final String owner, final String repo, final String branch) throws IOException {

        return caller.fetch(
                post(BRANCH_PROTECTION_REQUIRED_SIGNATURES, owner, repo, branch),
                GitHubProtectedBranchAdminEnforced.class);
    }

    /**
     * Delete commit signature protection ({@code DELETE
     * /repos/{owner}/{repo}/branches/{branch}/protection/required_signatures}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param branch branch name
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteCommitSignatureProtection(final String owner, final String repo, final String branch)
            throws IOException {

        caller.send(delete(BRANCH_PROTECTION_REQUIRED_SIGNATURES, owner, repo, branch));
    }

    /**
     * Get status checks protection ({@code GET
     * /repos/{owner}/{repo}/branches/{branch}/protection/required_status_checks}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param branch branch name
     * @return status check protection, or empty on HTTP 404
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubStatusCheckPolicy> getStatusChecksProtection(
            final String owner, final String repo, final String branch) throws IOException {

        return caller.fetchOrEmpty(
                get(BRANCH_PROTECTION_REQUIRED_STATUS_CHECKS, owner, repo, branch), GitHubStatusCheckPolicy.class);
    }

    /**
     * Update status check protection ({@code PATCH
     * /repos/{owner}/{repo}/branches/{branch}/protection/required_status_checks}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param branch branch name
     * @param payload optional status check settings
     * @return updated status check protection
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubStatusCheckPolicy updateStatusCheckProtection(
            final String owner,
            final String repo,
            final String branch,
            final @Nullable GitHubUpdateStatusCheckProtectionPayload payload)
            throws IOException {

        return caller.fetch(
                caller.jsonIfPresent(patch(BRANCH_PROTECTION_REQUIRED_STATUS_CHECKS, owner, repo, branch), payload),
                GitHubStatusCheckPolicy.class);
    }

    /**
     * Remove status check protection ({@code DELETE
     * /repos/{owner}/{repo}/branches/{branch}/protection/required_status_checks}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param branch branch name
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void removeStatusCheckProtection(final String owner, final String repo, final String branch)
            throws IOException {

        caller.send(delete(BRANCH_PROTECTION_REQUIRED_STATUS_CHECKS, owner, repo, branch));
    }

    /**
     * Get all status check contexts ({@code GET
     * /repos/{owner}/{repo}/branches/{branch}/protection/required_status_checks/contexts}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param branch branch name
     * @return status check contexts
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public List<String> getAllStatusCheckContexts(final String owner, final String repo, final String branch)
            throws IOException {

        return caller.fetch(get(REQUIRED_STATUS_CHECKS_CONTEXTS, owner, repo, branch), STRING_LIST_TYPE);
    }

    /**
     * Add status check contexts ({@code POST
     * /repos/{owner}/{repo}/branches/{branch}/protection/required_status_checks/contexts}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param branch branch name
     * @param payload optional contexts to add
     * @return updated status check contexts
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public List<String> addStatusCheckContexts(
            final String owner,
            final String repo,
            final String branch,
            final @Nullable GitHubStatusCheckContextsPayload payload)
            throws IOException {

        return caller.fetch(
                caller.jsonIfPresent(
                        post(REQUIRED_STATUS_CHECKS_CONTEXTS, owner, repo, branch),
                        payload == null ? null : statusCheckContextsBody(payload)),
                STRING_LIST_TYPE);
    }

    /**
     * Set status check contexts ({@code PUT
     * /repos/{owner}/{repo}/branches/{branch}/protection/required_status_checks/contexts}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param branch branch name
     * @param payload optional contexts to set
     * @return updated status check contexts
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public List<String> setStatusCheckContexts(
            final String owner,
            final String repo,
            final String branch,
            final @Nullable GitHubStatusCheckContextsPayload payload)
            throws IOException {

        return caller.fetch(
                caller.jsonIfPresent(
                        put(REQUIRED_STATUS_CHECKS_CONTEXTS, owner, repo, branch),
                        payload == null ? null : statusCheckContextsBody(payload)),
                STRING_LIST_TYPE);
    }

    /**
     * Remove status check contexts ({@code DELETE
     * /repos/{owner}/{repo}/branches/{branch}/protection/required_status_checks/contexts}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param branch branch name
     * @param payload optional contexts to remove
     * @return updated status check contexts
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public List<String> removeStatusCheckContexts(
            final String owner,
            final String repo,
            final String branch,
            final @Nullable GitHubStatusCheckContextsPayload payload)
            throws IOException {

        return caller.fetch(
                caller.jsonIfPresent(
                        delete(REQUIRED_STATUS_CHECKS_CONTEXTS, owner, repo, branch),
                        payload == null ? null : statusCheckContextsBody(payload)),
                STRING_LIST_TYPE);
    }

    /**
     * Get access restrictions ({@code GET /repos/{owner}/{repo}/branches/{branch}/protection/restrictions}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param branch branch name
     * @return access restrictions, or empty on HTTP 404
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubBranchRestrictionPolicy> getAccessRestrictions(
            final String owner, final String repo, final String branch) throws IOException {

        return caller.fetchOrEmpty(
                get(BRANCH_PROTECTION_RESTRICTIONS, owner, repo, branch), GitHubBranchRestrictionPolicy.class);
    }

    /**
     * Delete access restrictions ({@code DELETE /repos/{owner}/{repo}/branches/{branch}/protection/restrictions}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param branch branch name
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteAccessRestrictions(final String owner, final String repo, final String branch)
            throws IOException {

        caller.send(delete(BRANCH_PROTECTION_RESTRICTIONS, owner, repo, branch));
    }

    /**
     * Get apps with access to the protected branch ({@code GET
     * /repos/{owner}/{repo}/branches/{branch}/protection/restrictions/apps}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param branch branch name
     * @return apps with push access
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public List<GitHubIntegration> getAppsWithAccessToProtectedBranch(
            final String owner, final String repo, final String branch) throws IOException {

        return caller.fetch(get(BRANCH_PROTECTION_RESTRICTIONS_APPS, owner, repo, branch), INTEGRATIONS_TYPE);
    }

    /**
     * Set app access restrictions ({@code PUT /repos/{owner}/{repo}/branches/{branch}/protection/restrictions/apps}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param branch branch name
     * @param payload apps to allow
     * @return apps with push access
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public List<GitHubIntegration> setAppAccessRestrictions(
            final String owner,
            final String repo,
            final String branch,
            final GitHubAppAccessRestrictionsPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(put(BRANCH_PROTECTION_RESTRICTIONS_APPS, owner, repo, branch), payload), INTEGRATIONS_TYPE);
    }

    /**
     * Add app access restrictions ({@code POST /repos/{owner}/{repo}/branches/{branch}/protection/restrictions/apps}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param branch branch name
     * @param payload apps to add
     * @return apps with push access
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public List<GitHubIntegration> addAppAccessRestrictions(
            final String owner,
            final String repo,
            final String branch,
            final GitHubAppAccessRestrictionsPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(BRANCH_PROTECTION_RESTRICTIONS_APPS, owner, repo, branch), payload),
                INTEGRATIONS_TYPE);
    }

    /**
     * Remove app access restrictions ({@code DELETE
     * /repos/{owner}/{repo}/branches/{branch}/protection/restrictions/apps}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param branch branch name
     * @param payload apps to remove
     * @return apps with push access
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public List<GitHubIntegration> removeAppAccessRestrictions(
            final String owner,
            final String repo,
            final String branch,
            final GitHubAppAccessRestrictionsPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(delete(BRANCH_PROTECTION_RESTRICTIONS_APPS, owner, repo, branch), payload),
                INTEGRATIONS_TYPE);
    }

    /**
     * Get teams with access to the protected branch ({@code GET
     * /repos/{owner}/{repo}/branches/{branch}/protection/restrictions/teams}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param branch branch name
     * @return teams with push access
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public List<GitHubTeam> getTeamsWithAccessToProtectedBranch(
            final String owner, final String repo, final String branch) throws IOException {

        return caller.fetch(get(BRANCH_PROTECTION_RESTRICTIONS_TEAMS, owner, repo, branch), TEAMS_TYPE);
    }

    /**
     * Set team access restrictions ({@code PUT /repos/{owner}/{repo}/branches/{branch}/protection/restrictions/teams}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param branch branch name
     * @param payload optional teams to set
     * @return teams with push access
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public List<GitHubTeam> setTeamAccessRestrictions(
            final String owner,
            final String repo,
            final String branch,
            final @Nullable GitHubTeamAccessRestrictionsPayload payload)
            throws IOException {

        return caller.fetch(
                caller.jsonIfPresent(
                        put(BRANCH_PROTECTION_RESTRICTIONS_TEAMS, owner, repo, branch),
                        payload == null ? null : teamAccessRestrictionsBody(payload)),
                TEAMS_TYPE);
    }

    /**
     * Add team access restrictions ({@code POST
     * /repos/{owner}/{repo}/branches/{branch}/protection/restrictions/teams}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param branch branch name
     * @param payload optional teams to add
     * @return teams with push access
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public List<GitHubTeam> addTeamAccessRestrictions(
            final String owner,
            final String repo,
            final String branch,
            final @Nullable GitHubTeamAccessRestrictionsPayload payload)
            throws IOException {

        return caller.fetch(
                caller.jsonIfPresent(
                        post(BRANCH_PROTECTION_RESTRICTIONS_TEAMS, owner, repo, branch),
                        payload == null ? null : teamAccessRestrictionsBody(payload)),
                TEAMS_TYPE);
    }

    /**
     * Remove team access restrictions ({@code DELETE
     * /repos/{owner}/{repo}/branches/{branch}/protection/restrictions/teams}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param branch branch name
     * @param payload optional teams to remove
     * @return teams with push access
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public List<GitHubTeam> removeTeamAccessRestrictions(
            final String owner,
            final String repo,
            final String branch,
            final @Nullable GitHubTeamAccessRestrictionsPayload payload)
            throws IOException {

        return caller.fetch(
                caller.jsonIfPresent(
                        delete(BRANCH_PROTECTION_RESTRICTIONS_TEAMS, owner, repo, branch),
                        payload == null ? null : teamAccessRestrictionsBody(payload)),
                TEAMS_TYPE);
    }

    /**
     * Get users with access to the protected branch ({@code GET
     * /repos/{owner}/{repo}/branches/{branch}/protection/restrictions/users}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param branch branch name
     * @return users with push access
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public List<GitHubSimpleUser> getUsersWithAccessToProtectedBranch(
            final String owner, final String repo, final String branch) throws IOException {

        return caller.fetch(get(BRANCH_PROTECTION_RESTRICTIONS_USERS, owner, repo, branch), SIMPLE_USERS_TYPE);
    }

    /**
     * Set user access restrictions ({@code PUT /repos/{owner}/{repo}/branches/{branch}/protection/restrictions/users}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param branch branch name
     * @param payload users to set
     * @return users with push access
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public List<GitHubSimpleUser> setUserAccessRestrictions(
            final String owner,
            final String repo,
            final String branch,
            final GitHubUserAccessRestrictionsPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(put(BRANCH_PROTECTION_RESTRICTIONS_USERS, owner, repo, branch), payload),
                SIMPLE_USERS_TYPE);
    }

    /**
     * Add user access restrictions ({@code POST
     * /repos/{owner}/{repo}/branches/{branch}/protection/restrictions/users}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param branch branch name
     * @param payload users to add
     * @return users with push access
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public List<GitHubSimpleUser> addUserAccessRestrictions(
            final String owner,
            final String repo,
            final String branch,
            final GitHubUserAccessRestrictionsPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(BRANCH_PROTECTION_RESTRICTIONS_USERS, owner, repo, branch), payload),
                SIMPLE_USERS_TYPE);
    }

    /**
     * Remove user access restrictions ({@code DELETE
     * /repos/{owner}/{repo}/branches/{branch}/protection/restrictions/users}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param branch branch name
     * @param payload users to remove
     * @return users with push access
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public List<GitHubSimpleUser> removeUserAccessRestrictions(
            final String owner,
            final String repo,
            final String branch,
            final GitHubUserAccessRestrictionsPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(delete(BRANCH_PROTECTION_RESTRICTIONS_USERS, owner, repo, branch), payload),
                SIMPLE_USERS_TYPE);
    }

    private static Object statusCheckContextsBody(final GitHubStatusCheckContextsPayload payload) {
        final List<String> contextList = payload.contextList();
        if (contextList != null) {
            return contextList;
        }
        return Map.of(BODY_CONTEXTS, requireNonNull(payload.contexts(), BODY_CONTEXTS));
    }

    private static Object teamAccessRestrictionsBody(final GitHubTeamAccessRestrictionsPayload payload) {
        final List<String> teamSlugs = payload.teamSlugs();
        if (teamSlugs != null) {
            return teamSlugs;
        }
        return Map.of(BODY_TEAMS, requireNonNull(payload.teams(), BODY_TEAMS));
    }
}
