package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.client.pagination.GitHubListSpec;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequestKindValidators;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/** GitHub Actions self-hosted runners REST resource for API version {@code 2026-03-10}. */
public final class GitHubActionsSelfHostedRunnersApi extends GitHubResourceApi {

    private static final PathTemplate ORG_RUNNERS = PathTemplate.of("/orgs/{org}/actions/runners");
    private static final PathTemplate ORG_RUNNER_APPLICATIONS =
            PathTemplate.of("/orgs/{org}/actions/runners/downloads");
    private static final PathTemplate ORG_GENERATE_JITCONFIG =
            PathTemplate.of("/orgs/{org}/actions/runners/generate-jitconfig");
    private static final PathTemplate ORG_REGISTRATION_TOKEN =
            PathTemplate.of("/orgs/{org}/actions/runners/registration-token");
    private static final PathTemplate ORG_REMOVE_TOKEN = PathTemplate.of("/orgs/{org}/actions/runners/remove-token");
    private static final PathTemplate ORG_RUNNER = PathTemplate.of("/orgs/{org}/actions/runners/{runner_id}");
    private static final PathTemplate ORG_RUNNER_LABELS =
            PathTemplate.of("/orgs/{org}/actions/runners/{runner_id}/labels");
    private static final PathTemplate ORG_RUNNER_LABEL =
            PathTemplate.of("/orgs/{org}/actions/runners/{runner_id}/labels/{name}");
    private static final PathTemplate ORG_RUNNER_VERSION_DEPRECATION =
            PathTemplate.of("/orgs/{org}/actions/runners/deprecations/{version}");

    private static final PathTemplate REPO_RUNNERS = PathTemplate.of("/repos/{owner}/{repo}/actions/runners");
    private static final PathTemplate REPO_RUNNER_APPLICATIONS =
            PathTemplate.of("/repos/{owner}/{repo}/actions/runners/downloads");
    private static final PathTemplate REPO_GENERATE_JITCONFIG =
            PathTemplate.of("/repos/{owner}/{repo}/actions/runners/generate-jitconfig");
    private static final PathTemplate REPO_REGISTRATION_TOKEN =
            PathTemplate.of("/repos/{owner}/{repo}/actions/runners/registration-token");
    private static final PathTemplate REPO_REMOVE_TOKEN =
            PathTemplate.of("/repos/{owner}/{repo}/actions/runners/remove-token");
    private static final PathTemplate REPO_RUNNER =
            PathTemplate.of("/repos/{owner}/{repo}/actions/runners/{runner_id}");
    private static final PathTemplate REPO_RUNNER_LABELS =
            PathTemplate.of("/repos/{owner}/{repo}/actions/runners/{runner_id}/labels");
    private static final PathTemplate REPO_RUNNER_LABEL =
            PathTemplate.of("/repos/{owner}/{repo}/actions/runners/{runner_id}/labels/{name}");
    private static final PathTemplate REPO_RUNNER_VERSION_DEPRECATION =
            PathTemplate.of("/repos/{owner}/{repo}/actions/runners/deprecations/{version}");

    private static final PathTemplate ENTERPRISE_RUNNERS = PathTemplate.of("/enterprises/{enterprise}/actions/runners");
    private static final PathTemplate ENTERPRISE_RUNNER_APPLICATIONS =
            PathTemplate.of("/enterprises/{enterprise}/actions/runners/downloads");
    private static final PathTemplate ENTERPRISE_GENERATE_JITCONFIG =
            PathTemplate.of("/enterprises/{enterprise}/actions/runners/generate-jitconfig");
    private static final PathTemplate ENTERPRISE_REGISTRATION_TOKEN =
            PathTemplate.of("/enterprises/{enterprise}/actions/runners/registration-token");
    private static final PathTemplate ENTERPRISE_REMOVE_TOKEN =
            PathTemplate.of("/enterprises/{enterprise}/actions/runners/remove-token");
    private static final PathTemplate ENTERPRISE_RUNNER =
            PathTemplate.of("/enterprises/{enterprise}/actions/runners/{runner_id}");
    private static final PathTemplate ENTERPRISE_RUNNER_LABELS =
            PathTemplate.of("/enterprises/{enterprise}/actions/runners/{runner_id}/labels");
    private static final PathTemplate ENTERPRISE_RUNNER_LABEL =
            PathTemplate.of("/enterprises/{enterprise}/actions/runners/{runner_id}/labels/{name}");
    private static final PathTemplate ENTERPRISE_RUNNER_VERSION_DEPRECATION =
            PathTemplate.of("/enterprises/{enterprise}/actions/runners/deprecations/{version}");

    private static final GitHubListSpec<GitHubRunner> ENTERPRISE_RUNNERS_SPEC = GitHubListSpec.wrapper(
            GitHubPageRequestKindValidators.PAGE,
            GitHubRunnersForEnterprise.class,
            GitHubRunnersForEnterprise::runners,
            GitHubRunnersForEnterprise::totalCount);
    private static final JsonType<List<GitHubRunnerApplication>> APPLICATIONS_TYPE =
            JsonTypes.listOf(GitHubRunnerApplication.class);

    private static final GitHubListSpec<GitHubRunner> RUNNERS_SPEC = GitHubListSpec.wrapper(
            GitHubPageRequestKindValidators.PAGE,
            GitHubRunners.class,
            GitHubRunners::runners,
            GitHubRunners::totalCount);

    /**
     * Self-hosted runners resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubActionsSelfHostedRunnersApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List self-hosted runners of an organization ({@code GET /orgs/{org}/actions/runners}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()} and an empty filter.
     *
     * @param org organization name
     * @return first HTTP page of runners
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRunner> listSelfHostedRunnersForOrg(final String org) throws IOException {
        return listSelfHostedRunnersForOrg(org, GitHubPageRequest.byPage());
    }

    /**
     * List self-hosted runners of an organization ({@code GET /orgs/{org}/actions/runners}).
     *
     * @param org organization name
     * @param request pagination token
     * @return first HTTP page of runners
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRunner> listSelfHostedRunnersForOrg(final String org, final GitHubPageRequest request)
            throws IOException {
        return listSelfHostedRunnersForOrg(org, GitHubListSelfHostedRunnersForOrgFilter.empty(), request);
    }

    /**
     * List self-hosted runners of an organization ({@code GET /orgs/{org}/actions/runners}).
     *
     * @param org organization name
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of runners
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRunner> listSelfHostedRunnersForOrg(
            final String org, final GitHubListSelfHostedRunnersForOrgFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(ORG_RUNNERS, filter.toQueryParameters(), request, RUNNERS_SPEC, org);
    }

    /**
     * List runner application downloads for an organization ({@code GET /orgs/{org}/actions/runners/downloads}).
     *
     * @param org organization name
     * @return runner application downloads
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public List<GitHubRunnerApplication> listRunnerApplicationsForOrg(final String org) throws IOException {
        return caller.fetch(get(ORG_RUNNER_APPLICATIONS, org), APPLICATIONS_TYPE);
    }

    /**
     * Create a just-in-time runner configuration for an organization ({@code POST
     * /orgs/{org}/actions/runners/generate-jitconfig}).
     *
     * @param org organization name
     * @param payload runner name, group, labels and work folder
     * @return runner and encoded configuration
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubRunnerJitConfig generateRunnerJitconfigForOrg(
            final String org, final GitHubGenerateRunnerJitconfigForOrgPayload payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(ORG_GENERATE_JITCONFIG, org), payload), GitHubRunnerJitConfig.class);
    }

    /**
     * Create a registration token for an organization ({@code POST /orgs/{org}/actions/runners/registration-token}).
     *
     * @param org organization name
     * @return registration token
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubAuthenticationToken createRegistrationTokenForOrg(final String org) throws IOException {
        return caller.fetch(post(ORG_REGISTRATION_TOKEN, org), GitHubAuthenticationToken.class);
    }

    /**
     * Create a remove token for an organization ({@code POST /orgs/{org}/actions/runners/remove-token}).
     *
     * @param org organization name
     * @return remove token
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubAuthenticationToken createRemoveTokenForOrg(final String org) throws IOException {
        return caller.fetch(post(ORG_REMOVE_TOKEN, org), GitHubAuthenticationToken.class);
    }

    /**
     * Get a self-hosted runner of an organization ({@code GET /orgs/{org}/actions/runners/{runner_id}}).
     *
     * @param org organization name
     * @param runnerId runner ID
     * @return runner, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubRunner> getSelfHostedRunnerForOrg(final String org, final long runnerId) throws IOException {
        return caller.fetchOrEmpty(get(ORG_RUNNER, org, runnerId), GitHubRunner.class);
    }

    /**
     * Delete a self-hosted runner of an organization ({@code DELETE /orgs/{org}/actions/runners/{runner_id}}).
     *
     * @param org organization name
     * @param runnerId runner ID
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteSelfHostedRunnerFromOrg(final String org, final long runnerId) throws IOException {
        caller.send(delete(ORG_RUNNER, org, runnerId));
    }

    /**
     * Get the end-of-life schedule of a runner version for an organization ({@code GET
     * /orgs/{org}/actions/runners/deprecations/{version}}).
     *
     * <p>Returns the runner version and the dates when registration and runtime support will end.
     *
     * @param org organization name
     * @param version runner version to look up
     * @return end-of-life schedule of the runner version, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code org} or {@code version} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubRunnerVersionDeprecation> getRunnerVersionDeprecationForOrg(
            final String org, final String version) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(
                get(ORG_RUNNER_VERSION_DEPRECATION, org, version), GitHubRunnerVersionDeprecation.class);
    }

    /**
     * List labels of a self-hosted runner of an organization ({@code GET
     * /orgs/{org}/actions/runners/{runner_id}/labels}).
     *
     * @param org organization name
     * @param runnerId runner ID
     * @return labels, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubRunnerLabels> listLabelsForSelfHostedRunnerForOrg(final String org, final long runnerId)
            throws IOException {
        return caller.fetchOrEmpty(get(ORG_RUNNER_LABELS, org, runnerId), GitHubRunnerLabels.class);
    }

    /**
     * Add custom labels to a self-hosted runner of an organization ({@code POST
     * /orgs/{org}/actions/runners/{runner_id}/labels}).
     *
     * @param org organization name
     * @param runnerId runner ID
     * @param payload labels to add
     * @return labels of the runner
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubRunnerLabels addCustomLabelsToSelfHostedRunnerForOrg(
            final String org, final long runnerId, final GitHubAddCustomLabelsToSelfHostedRunnerForOrgPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(ORG_RUNNER_LABELS, org, runnerId), payload), GitHubRunnerLabels.class);
    }

    /**
     * Set custom labels of a self-hosted runner of an organization ({@code PUT
     * /orgs/{org}/actions/runners/{runner_id}/labels}).
     *
     * @param org organization name
     * @param runnerId runner ID
     * @param payload labels that replace the current ones
     * @return labels of the runner
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubRunnerLabels replaceCustomLabelsForSelfHostedRunnerForOrg(
            final String org, final long runnerId, final GitHubSetCustomLabelsForSelfHostedRunnerForOrgPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(put(ORG_RUNNER_LABELS, org, runnerId), payload), GitHubRunnerLabels.class);
    }

    /**
     * Remove all custom labels from a self-hosted runner of an organization ({@code DELETE
     * /orgs/{org}/actions/runners/{runner_id}/labels}).
     *
     * @param org organization name
     * @param runnerId runner ID
     * @return remaining labels of the runner
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubRunnerLabels removeAllCustomLabelsFromSelfHostedRunnerForOrg(final String org, final long runnerId)
            throws IOException {
        return caller.fetch(delete(ORG_RUNNER_LABELS, org, runnerId), GitHubRunnerLabels.class);
    }

    /**
     * Remove a custom label from a self-hosted runner of an organization ({@code DELETE
     * /orgs/{org}/actions/runners/{runner_id}/labels/{name}}).
     *
     * @param org organization name
     * @param runnerId runner ID
     * @param name label name
     * @return remaining labels of the runner
     * @throws IllegalArgumentException if {@code org} or {@code name} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubRunnerLabels removeCustomLabelFromSelfHostedRunnerForOrg(
            final String org, final long runnerId, final String name) throws IOException {
        return caller.fetch(delete(ORG_RUNNER_LABEL, org, runnerId, name), GitHubRunnerLabels.class);
    }

    /**
     * List self-hosted runners of a repository ({@code GET /repos/{owner}/{repo}/actions/runners}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()} and an empty filter.
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of runners
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRunner> listSelfHostedRunnersForRepo(final String owner, final String repo)
            throws IOException {
        return listSelfHostedRunnersForRepo(owner, repo, GitHubPageRequest.byPage());
    }

    /**
     * List self-hosted runners of a repository ({@code GET /repos/{owner}/{repo}/actions/runners}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return first HTTP page of runners
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRunner> listSelfHostedRunnersForRepo(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        return listSelfHostedRunnersForRepo(owner, repo, GitHubListSelfHostedRunnersForRepoFilter.empty(), request);
    }

    /**
     * List self-hosted runners of a repository ({@code GET /repos/{owner}/{repo}/actions/runners}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of runners
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRunner> listSelfHostedRunnersForRepo(
            final String owner,
            final String repo,
            final GitHubListSelfHostedRunnersForRepoFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(REPO_RUNNERS, filter.toQueryParameters(), request, RUNNERS_SPEC, owner, repo);
    }

    /**
     * List runner application downloads for a repository ({@code GET /repos/{owner}/{repo}/actions/runners/downloads}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return runner application downloads
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public List<GitHubRunnerApplication> listRunnerApplicationsForRepo(final String owner, final String repo)
            throws IOException {
        return caller.fetch(get(REPO_RUNNER_APPLICATIONS, owner, repo), APPLICATIONS_TYPE);
    }

    /**
     * Create a just-in-time runner configuration for a repository ({@code POST
     * /repos/{owner}/{repo}/actions/runners/generate-jitconfig}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload runner name, group, labels and work folder
     * @return runner and encoded configuration
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubRunnerJitConfig generateRunnerJitconfigForRepo(
            final String owner, final String repo, final GitHubGenerateRunnerJitconfigForRepoPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(REPO_GENERATE_JITCONFIG, owner, repo), payload), GitHubRunnerJitConfig.class);
    }

    /**
     * Create a registration token for a repository ({@code POST
     * /repos/{owner}/{repo}/actions/runners/registration-token}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return registration token
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubAuthenticationToken createRegistrationTokenForRepo(final String owner, final String repo)
            throws IOException {
        return caller.fetch(post(REPO_REGISTRATION_TOKEN, owner, repo), GitHubAuthenticationToken.class);
    }

    /**
     * Create a remove token for a repository ({@code POST /repos/{owner}/{repo}/actions/runners/remove-token}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return remove token
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubAuthenticationToken createRemoveTokenForRepo(final String owner, final String repo)
            throws IOException {
        return caller.fetch(post(REPO_REMOVE_TOKEN, owner, repo), GitHubAuthenticationToken.class);
    }

    /**
     * Get a self-hosted runner of a repository ({@code GET /repos/{owner}/{repo}/actions/runners/{runner_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param runnerId runner ID
     * @return runner, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubRunner> getSelfHostedRunnerForRepo(final String owner, final String repo, final long runnerId)
            throws IOException {
        return caller.fetchOrEmpty(get(REPO_RUNNER, owner, repo, runnerId), GitHubRunner.class);
    }

    /**
     * Delete a self-hosted runner of a repository ({@code DELETE /repos/{owner}/{repo}/actions/runners/{runner_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param runnerId runner ID
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteSelfHostedRunnerFromRepo(final String owner, final String repo, final long runnerId)
            throws IOException {
        caller.send(delete(REPO_RUNNER, owner, repo, runnerId));
    }

    /**
     * Get the end-of-life schedule of a runner version for a repository ({@code GET
     * /repos/{owner}/{repo}/actions/runners/deprecations/{version}}).
     *
     * <p>Returns the runner version and the dates when registration and runtime support will end.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param version runner version to look up
     * @return end-of-life schedule of the runner version, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner}, {@code repo} or {@code version} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubRunnerVersionDeprecation> getRunnerVersionDeprecationForRepo(
            final String owner, final String repo, final String version) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(
                get(REPO_RUNNER_VERSION_DEPRECATION, owner, repo, version), GitHubRunnerVersionDeprecation.class);
    }

    /**
     * List labels of a self-hosted runner of a repository ({@code GET
     * /repos/{owner}/{repo}/actions/runners/{runner_id}/labels}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param runnerId runner ID
     * @return labels, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubRunnerLabels> listLabelsForSelfHostedRunnerForRepo(
            final String owner, final String repo, final long runnerId) throws IOException {
        return caller.fetchOrEmpty(get(REPO_RUNNER_LABELS, owner, repo, runnerId), GitHubRunnerLabels.class);
    }

    /**
     * Add custom labels to a self-hosted runner of a repository ({@code POST
     * /repos/{owner}/{repo}/actions/runners/{runner_id}/labels}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param runnerId runner ID
     * @param payload labels to add
     * @return labels of the runner
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubRunnerLabels addCustomLabelsToSelfHostedRunnerForRepo(
            final String owner,
            final String repo,
            final long runnerId,
            final GitHubAddCustomLabelsToSelfHostedRunnerForRepoPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(REPO_RUNNER_LABELS, owner, repo, runnerId), payload), GitHubRunnerLabels.class);
    }

    /**
     * Set custom labels of a self-hosted runner of a repository ({@code PUT
     * /repos/{owner}/{repo}/actions/runners/{runner_id}/labels}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param runnerId runner ID
     * @param payload labels that replace the current ones
     * @return labels of the runner
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubRunnerLabels replaceCustomLabelsForSelfHostedRunnerForRepo(
            final String owner,
            final String repo,
            final long runnerId,
            final GitHubSetCustomLabelsForSelfHostedRunnerForRepoPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(put(REPO_RUNNER_LABELS, owner, repo, runnerId), payload), GitHubRunnerLabels.class);
    }

    /**
     * Remove all custom labels from a self-hosted runner of a repository ({@code DELETE
     * /repos/{owner}/{repo}/actions/runners/{runner_id}/labels}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param runnerId runner ID
     * @return remaining labels of the runner
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubRunnerLabels removeAllCustomLabelsFromSelfHostedRunnerForRepo(
            final String owner, final String repo, final long runnerId) throws IOException {
        return caller.fetch(delete(REPO_RUNNER_LABELS, owner, repo, runnerId), GitHubRunnerLabels.class);
    }

    /**
     * Remove a custom label from a self-hosted runner of a repository ({@code DELETE
     * /repos/{owner}/{repo}/actions/runners/{runner_id}/labels/{name}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param runnerId runner ID
     * @param name label name
     * @return remaining labels of the runner
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code name} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubRunnerLabels removeCustomLabelFromSelfHostedRunnerForRepo(
            final String owner, final String repo, final long runnerId, final String name) throws IOException {
        return caller.fetch(delete(REPO_RUNNER_LABEL, owner, repo, runnerId, name), GitHubRunnerLabels.class);
    }

    /**
     * List self-hosted runners of an enterprise ({@code GET /enterprises/{enterprise}/actions/runners}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()} and an empty filter.
     *
     * @param enterprise enterprise slug
     * @return first HTTP page of runners
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubRunner> listSelfHostedRunnersForEnterprise(final String enterprise) throws IOException {
        return listSelfHostedRunnersForEnterprise(enterprise, GitHubPageRequest.byPage());
    }

    /**
     * List self-hosted runners of an enterprise ({@code GET /enterprises/{enterprise}/actions/runners}).
     *
     * @param enterprise enterprise slug
     * @param request pagination token
     * @return first HTTP page of runners
     * @throws IllegalArgumentException if {@code enterprise} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubRunner> listSelfHostedRunnersForEnterprise(
            final String enterprise, final GitHubPageRequest request) throws IOException {
        return listSelfHostedRunnersForEnterprise(
                enterprise, GitHubListSelfHostedRunnersForEnterpriseFilter.empty(), request);
    }

    /**
     * List self-hosted runners of an enterprise ({@code GET /enterprises/{enterprise}/actions/runners}).
     *
     * @param enterprise enterprise slug
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of runners
     * @throws IllegalArgumentException if {@code enterprise} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubRunner> listSelfHostedRunnersForEnterprise(
            final String enterprise,
            final GitHubListSelfHostedRunnersForEnterpriseFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(ENTERPRISE_RUNNERS, filter.toQueryParameters(), request, ENTERPRISE_RUNNERS_SPEC, enterprise);
    }

    /**
     * List runner application downloads for an enterprise ({@code GET
     * /enterprises/{enterprise}/actions/runners/downloads}).
     *
     * @param enterprise enterprise slug
     * @return runner application downloads
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public List<GitHubRunnerApplication> listRunnerApplicationsForEnterprise(final String enterprise)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        return caller.fetch(get(ENTERPRISE_RUNNER_APPLICATIONS, enterprise), APPLICATIONS_TYPE);
    }

    /**
     * Create a just-in-time runner configuration for an enterprise ({@code POST
     * /enterprises/{enterprise}/actions/runners/generate-jitconfig}).
     *
     * @param enterprise enterprise slug
     * @param payload runner name, group, labels and work folder
     * @return runner and encoded configuration
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubRunnerJitConfigForEnterprise generateRunnerJitconfigForEnterprise(
            final String enterprise, final GitHubGenerateRunnerJitconfigForEnterprisePayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(ENTERPRISE_GENERATE_JITCONFIG, enterprise), payload),
                GitHubRunnerJitConfigForEnterprise.class);
    }

    /**
     * Create a registration token for an enterprise ({@code POST
     * /enterprises/{enterprise}/actions/runners/registration-token}).
     *
     * @param enterprise enterprise slug
     * @return registration token
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubAuthenticationToken createRegistrationTokenForEnterprise(final String enterprise) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        return caller.fetch(post(ENTERPRISE_REGISTRATION_TOKEN, enterprise), GitHubAuthenticationToken.class);
    }

    /**
     * Create a remove token for an enterprise ({@code POST /enterprises/{enterprise}/actions/runners/remove-token}).
     *
     * @param enterprise enterprise slug
     * @return remove token
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubAuthenticationToken createRemoveTokenForEnterprise(final String enterprise) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        return caller.fetch(post(ENTERPRISE_REMOVE_TOKEN, enterprise), GitHubAuthenticationToken.class);
    }

    /**
     * Get a self-hosted runner of an enterprise ({@code GET /enterprises/{enterprise}/actions/runners/{runner_id}}).
     *
     * @param enterprise enterprise slug
     * @param runnerId runner ID
     * @return runner, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public Optional<GitHubRunner> getSelfHostedRunnerForEnterprise(final String enterprise, final long runnerId)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        return caller.fetchOrEmpty(get(ENTERPRISE_RUNNER, enterprise, runnerId), GitHubRunner.class);
    }

    /**
     * Delete a self-hosted runner of an enterprise ({@code DELETE
     * /enterprises/{enterprise}/actions/runners/{runner_id}}).
     *
     * @param enterprise enterprise slug
     * @param runnerId runner ID
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public void deleteSelfHostedRunnerFromEnterprise(final String enterprise, final long runnerId) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        caller.send(delete(ENTERPRISE_RUNNER, enterprise, runnerId));
    }

    /**
     * Get the end-of-life schedule of a runner version for an enterprise ({@code GET
     * /enterprises/{enterprise}/actions/runners/deprecations/{version}}).
     *
     * <p>Returns the runner version and the dates when registration and runtime support will end.
     *
     * @param enterprise enterprise slug
     * @param version runner version to look up
     * @return end-of-life schedule of the runner version, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code enterprise} or {@code version} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public Optional<GitHubRunnerVersionDeprecationForEnterprise> getRunnerVersionDeprecationForEnterprise(
            final String enterprise, final String version) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(
                get(ENTERPRISE_RUNNER_VERSION_DEPRECATION, enterprise, version),
                GitHubRunnerVersionDeprecationForEnterprise.class);
    }

    /**
     * List labels of a self-hosted runner of an enterprise ({@code GET
     * /enterprises/{enterprise}/actions/runners/{runner_id}/labels}).
     *
     * @param enterprise enterprise slug
     * @param runnerId runner ID
     * @return labels, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public Optional<GitHubRunnerLabelsForEnterprise> listLabelsForSelfHostedRunnerForEnterprise(
            final String enterprise, final long runnerId) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        return caller.fetchOrEmpty(
                get(ENTERPRISE_RUNNER_LABELS, enterprise, runnerId), GitHubRunnerLabelsForEnterprise.class);
    }

    /**
     * Add custom labels to a self-hosted runner of an enterprise ({@code POST
     * /enterprises/{enterprise}/actions/runners/{runner_id}/labels}).
     *
     * @param enterprise enterprise slug
     * @param runnerId runner ID
     * @param payload labels to add
     * @return labels of the runner
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubRunnerLabelsForEnterprise addCustomLabelsToSelfHostedRunnerForEnterprise(
            final String enterprise,
            final long runnerId,
            final GitHubAddCustomLabelsToSelfHostedRunnerForEnterprisePayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(ENTERPRISE_RUNNER_LABELS, enterprise, runnerId), payload),
                GitHubRunnerLabelsForEnterprise.class);
    }

    /**
     * Set custom labels of a self-hosted runner of an enterprise ({@code PUT
     * /enterprises/{enterprise}/actions/runners/{runner_id}/labels}).
     *
     * @param enterprise enterprise slug
     * @param runnerId runner ID
     * @param payload labels that replace the current ones
     * @return labels of the runner
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubRunnerLabelsForEnterprise replaceCustomLabelsForSelfHostedRunnerForEnterprise(
            final String enterprise,
            final long runnerId,
            final GitHubSetCustomLabelsForSelfHostedRunnerForEnterprisePayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(put(ENTERPRISE_RUNNER_LABELS, enterprise, runnerId), payload),
                GitHubRunnerLabelsForEnterprise.class);
    }

    /**
     * Remove all custom labels from a self-hosted runner of an enterprise ({@code DELETE
     * /enterprises/{enterprise}/actions/runners/{runner_id}/labels}).
     *
     * @param enterprise enterprise slug
     * @param runnerId runner ID
     * @return remaining labels of the runner
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubRunnerLabelsForEnterprise removeAllCustomLabelsFromSelfHostedRunnerForEnterprise(
            final String enterprise, final long runnerId) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        return caller.fetch(
                delete(ENTERPRISE_RUNNER_LABELS, enterprise, runnerId), GitHubRunnerLabelsForEnterprise.class);
    }

    /**
     * Remove a custom label from a self-hosted runner of an enterprise ({@code DELETE
     * /enterprises/{enterprise}/actions/runners/{runner_id}/labels/{name}}).
     *
     * @param enterprise enterprise slug
     * @param runnerId runner ID
     * @param name label name
     * @return remaining labels of the runner
     * @throws IllegalArgumentException if {@code enterprise} or {@code name} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubRunnerLabelsForEnterprise removeCustomLabelFromSelfHostedRunnerForEnterprise(
            final String enterprise, final long runnerId, final String name) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        return caller.fetch(
                delete(ENTERPRISE_RUNNER_LABEL, enterprise, runnerId, name), GitHubRunnerLabelsForEnterprise.class);
    }
}
