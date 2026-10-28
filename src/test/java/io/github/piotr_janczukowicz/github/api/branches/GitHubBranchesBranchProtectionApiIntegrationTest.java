package io.github.piotr_janczukowicz.github.api.branches;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.branchesApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.apps.GitHubIntegration;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeam;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.client.exception.GitHubNotFoundException;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubBranchesBranchProtectionApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String BRANCH = "main";
    private static final String CONTEXT = "continuous-integration/travis-ci";

    private static final String PROTECTION_PATH = "/repos/octocat/Hello-World/branches/main/protection";
    private static final String ENFORCE_ADMINS_PATH =
            "/repos/octocat/Hello-World/branches/main/protection/enforce_admins";
    private static final String PR_REVIEWS_PATH =
            "/repos/octocat/Hello-World/branches/main/protection/required_pull_request_reviews";
    private static final String REQUIRED_SIGNATURES_PATH =
            "/repos/octocat/Hello-World/branches/main/protection/required_signatures";
    private static final String STATUS_CHECKS_PATH =
            "/repos/octocat/Hello-World/branches/main/protection/required_status_checks";
    private static final String CONTEXTS_PATH =
            "/repos/octocat/Hello-World/branches/main/protection/required_status_checks/contexts";
    private static final String RESTRICTIONS_PATH = "/repos/octocat/Hello-World/branches/main/protection/restrictions";
    private static final String APPS_PATH = "/repos/octocat/Hello-World/branches/main/protection/restrictions/apps";
    private static final String TEAMS_PATH = "/repos/octocat/Hello-World/branches/main/protection/restrictions/teams";
    private static final String USERS_PATH = "/repos/octocat/Hello-World/branches/main/protection/restrictions/users";

    private static final byte[] CONTEXTS_JSON = ("[\"" + CONTEXT + "\"]").getBytes(StandardCharsets.UTF_8);

    private static final JsonType<List<GitHubIntegration>> INTEGRATIONS_TYPE =
            JsonTypes.listOf(GitHubIntegration.class);
    private static final JsonType<List<GitHubTeam>> TEAMS_TYPE = JsonTypes.listOf(GitHubTeam.class);
    private static final JsonType<List<GitHubSimpleUser>> USERS_TYPE = JsonTypes.listOf(GitHubSimpleUser.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getBranchProtection_shouldReturnProtection_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                PROTECTION_PATH,
                okJson(jsonBytes(GitHubBranchesBranchProtectionApiIntegrationTest.class, "branch-protection.json")));

        final Optional<GitHubBranchProtection> protection;
        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            protection = api.api().branchProtection().getBranchProtection(OWNER, REPO, BRANCH);
        }

        assertThat(protection).contains(expectedBranchProtection());
        HTTP.verifyRequested(GET, PROTECTION_PATH);
    }

    @Test
    void getBranchProtection_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, PROTECTION_PATH, notFound());

        final Optional<GitHubBranchProtection> protection;
        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            protection = api.api().branchProtection().getBranchProtection(OWNER, REPO, BRANCH);
        }

        assertThat(protection).isEmpty();
        HTTP.verifyRequested(GET, PROTECTION_PATH);
    }

    @Test
    void updateBranchProtection_shouldReturnProtectedBranch_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PUT,
                PROTECTION_PATH,
                okJson(jsonBytes(GitHubBranchesBranchProtectionApiIntegrationTest.class, "protected-branch.json")));
        final GitHubUpdateBranchProtectionPayload payload = BranchesCatalogFixtures.updateBranchProtectionPayload();

        final GitHubProtectedBranch protectedBranch;
        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            protectedBranch = api.api().branchProtection().updateBranchProtection(OWNER, REPO, BRANCH, payload);
        }

        assertThat(protectedBranch).isEqualTo(expectedProtectedBranch());
        HTTP.verifyRequested(PUT, PROTECTION_PATH);
        HTTP.verifyHeader(PUT, PROTECTION_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PUT, PROTECTION_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void deleteBranchProtection_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, PROTECTION_PATH, noContent());

        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            api.api().branchProtection().deleteBranchProtection(OWNER, REPO, BRANCH);
        }

        HTTP.verifyRequested(DELETE, PROTECTION_PATH);
    }

    @Test
    void getAdminBranchProtection_shouldReturnEnforced_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ENFORCE_ADMINS_PATH,
                okJson(jsonBytes(
                        GitHubBranchesBranchProtectionApiIntegrationTest.class,
                        "protected-branch-admin-enforced.json")));

        final GitHubProtectedBranchAdminEnforced enforced;
        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            enforced = api.api()
                    .branchProtection()
                    .getAdminBranchProtection(OWNER, REPO, BRANCH)
                    .orElseThrow();
        }

        assertThat(enforced).isEqualTo(expectedAdminEnforced());
        HTTP.verifyRequested(GET, ENFORCE_ADMINS_PATH);
    }

    @Test
    void setAdminBranchProtection_shouldReturnEnforced_whenResponseIsOk() throws Exception {
        HTTP.stub(
                POST,
                ENFORCE_ADMINS_PATH,
                okJson(jsonBytes(
                        GitHubBranchesBranchProtectionApiIntegrationTest.class,
                        "protected-branch-admin-enforced.json")));

        final GitHubProtectedBranchAdminEnforced enforced;
        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            enforced = api.api().branchProtection().setAdminBranchProtection(OWNER, REPO, BRANCH);
        }

        assertThat(enforced).isEqualTo(expectedAdminEnforced());
        HTTP.verifyRequested(POST, ENFORCE_ADMINS_PATH);
    }

    @Test
    void deleteAdminBranchProtection_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, ENFORCE_ADMINS_PATH, noContent());

        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            api.api().branchProtection().deleteAdminBranchProtection(OWNER, REPO, BRANCH);
        }

        HTTP.verifyRequested(DELETE, ENFORCE_ADMINS_PATH);
    }

    @Test
    void getPullRequestReviewProtection_shouldReturnReview_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                PR_REVIEWS_PATH,
                okJson(jsonBytes(
                        GitHubBranchesBranchProtectionApiIntegrationTest.class,
                        "protected-branch-pull-request-review.json")));

        final GitHubProtectedBranchPullRequestReview review;
        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            review = api.api()
                    .branchProtection()
                    .getPullRequestReviewProtection(OWNER, REPO, BRANCH)
                    .orElseThrow();
        }

        assertThat(review).isEqualTo(expectedPullRequestReview());
        HTTP.verifyRequested(GET, PR_REVIEWS_PATH);
    }

    @Test
    void updatePullRequestReviewProtection_shouldReturnReview_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PATCH,
                PR_REVIEWS_PATH,
                okJson(jsonBytes(
                        GitHubBranchesBranchProtectionApiIntegrationTest.class,
                        "protected-branch-pull-request-review.json")));
        final GitHubUpdatePullRequestReviewProtectionPayload payload =
                BranchesCatalogFixtures.updatePullRequestReviewProtectionPayload();

        final GitHubProtectedBranchPullRequestReview review;
        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            review = api.api().branchProtection().updatePullRequestReviewProtection(OWNER, REPO, BRANCH, payload);
        }

        assertThat(review).isEqualTo(expectedPullRequestReview());
        HTTP.verifyRequested(PATCH, PR_REVIEWS_PATH);
        HTTP.verifyHeader(PATCH, PR_REVIEWS_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PATCH, PR_REVIEWS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void updatePullRequestReviewProtection_shouldPatchWithoutBody_whenPayloadNull() throws Exception {
        HTTP.stub(
                PATCH,
                PR_REVIEWS_PATH,
                okJson(jsonBytes(
                        GitHubBranchesBranchProtectionApiIntegrationTest.class,
                        "protected-branch-pull-request-review.json")));

        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            api.api().branchProtection().updatePullRequestReviewProtection(OWNER, REPO, BRANCH, null);
        }

        HTTP.verifyRequested(PATCH, PR_REVIEWS_PATH);
    }

    @Test
    void deletePullRequestReviewProtection_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, PR_REVIEWS_PATH, noContent());

        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            api.api().branchProtection().deletePullRequestReviewProtection(OWNER, REPO, BRANCH);
        }

        HTTP.verifyRequested(DELETE, PR_REVIEWS_PATH);
    }

    @Test
    void getCommitSignatureProtection_shouldReturnEnforced_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REQUIRED_SIGNATURES_PATH,
                okJson(jsonBytes(
                        GitHubBranchesBranchProtectionApiIntegrationTest.class,
                        "protected-branch-admin-enforced.json")));

        final Optional<GitHubProtectedBranchAdminEnforced> enforced;
        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            enforced = api.api().branchProtection().getCommitSignatureProtection(OWNER, REPO, BRANCH);
        }

        assertThat(enforced).contains(expectedAdminEnforced());
        HTTP.verifyRequested(GET, REQUIRED_SIGNATURES_PATH);
    }

    @Test
    void getCommitSignatureProtection_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, REQUIRED_SIGNATURES_PATH, notFound());

        final Optional<GitHubProtectedBranchAdminEnforced> enforced;
        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            enforced = api.api().branchProtection().getCommitSignatureProtection(OWNER, REPO, BRANCH);
        }

        assertThat(enforced).isEmpty();
        HTTP.verifyRequested(GET, REQUIRED_SIGNATURES_PATH);
    }

    @Test
    void createCommitSignatureProtection_shouldReturnEnforced_whenResponseIsOk() throws Exception {
        HTTP.stub(
                POST,
                REQUIRED_SIGNATURES_PATH,
                okJson(jsonBytes(
                        GitHubBranchesBranchProtectionApiIntegrationTest.class,
                        "protected-branch-admin-enforced.json")));

        final GitHubProtectedBranchAdminEnforced enforced;
        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            enforced = api.api().branchProtection().createCommitSignatureProtection(OWNER, REPO, BRANCH);
        }

        assertThat(enforced).isEqualTo(expectedAdminEnforced());
        HTTP.verifyRequested(POST, REQUIRED_SIGNATURES_PATH);
    }

    @Test
    void deleteCommitSignatureProtection_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, REQUIRED_SIGNATURES_PATH, noContent());

        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            api.api().branchProtection().deleteCommitSignatureProtection(OWNER, REPO, BRANCH);
        }

        HTTP.verifyRequested(DELETE, REQUIRED_SIGNATURES_PATH);
    }

    @Test
    void getStatusChecksProtection_shouldReturnPolicy_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                STATUS_CHECKS_PATH,
                okJson(jsonBytes(GitHubBranchesBranchProtectionApiIntegrationTest.class, "status-check-policy.json")));

        final Optional<GitHubStatusCheckPolicy> policy;
        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            policy = api.api().branchProtection().getStatusChecksProtection(OWNER, REPO, BRANCH);
        }

        assertThat(policy).contains(expectedStatusCheckPolicy());
        HTTP.verifyRequested(GET, STATUS_CHECKS_PATH);
    }

    @Test
    void getStatusChecksProtection_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, STATUS_CHECKS_PATH, notFound());

        final Optional<GitHubStatusCheckPolicy> policy;
        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            policy = api.api().branchProtection().getStatusChecksProtection(OWNER, REPO, BRANCH);
        }

        assertThat(policy).isEmpty();
        HTTP.verifyRequested(GET, STATUS_CHECKS_PATH);
    }

    @Test
    void updateStatusCheckProtection_shouldReturnPolicy_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PATCH,
                STATUS_CHECKS_PATH,
                okJson(jsonBytes(GitHubBranchesBranchProtectionApiIntegrationTest.class, "status-check-policy.json")));
        final GitHubUpdateStatusCheckProtectionPayload payload =
                BranchesCatalogFixtures.updateStatusCheckProtectionPayload();

        final GitHubStatusCheckPolicy policy;
        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            policy = api.api().branchProtection().updateStatusCheckProtection(OWNER, REPO, BRANCH, payload);
        }

        assertThat(policy).isEqualTo(expectedStatusCheckPolicy());
        HTTP.verifyRequested(PATCH, STATUS_CHECKS_PATH);
        HTTP.verifyHeader(PATCH, STATUS_CHECKS_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PATCH, STATUS_CHECKS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void updateStatusCheckProtection_shouldPatchWithoutBody_whenPayloadNull() throws Exception {
        HTTP.stub(
                PATCH,
                STATUS_CHECKS_PATH,
                okJson(jsonBytes(GitHubBranchesBranchProtectionApiIntegrationTest.class, "status-check-policy.json")));

        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            api.api().branchProtection().updateStatusCheckProtection(OWNER, REPO, BRANCH, null);
        }

        HTTP.verifyRequested(PATCH, STATUS_CHECKS_PATH);
    }

    @Test
    void removeStatusCheckProtection_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, STATUS_CHECKS_PATH, noContent());

        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            api.api().branchProtection().removeStatusCheckProtection(OWNER, REPO, BRANCH);
        }

        HTTP.verifyRequested(DELETE, STATUS_CHECKS_PATH);
    }

    @Test
    void getAllStatusCheckContexts_shouldReturnContexts_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, CONTEXTS_PATH, okJson(CONTEXTS_JSON));

        final List<String> contexts;
        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            contexts = api.api().branchProtection().getAllStatusCheckContexts(OWNER, REPO, BRANCH);
        }

        assertThat(contexts).containsExactly(CONTEXT);
        HTTP.verifyRequested(GET, CONTEXTS_PATH);
    }

    @Test
    void getAllStatusCheckContexts_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, CONTEXTS_PATH, notFound());

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            final GitHubBranchesBranchProtectionApi branchProtection = api.api().branchProtection();

            thrown = catchThrowable(() -> branchProtection.getAllStatusCheckContexts(OWNER, REPO, BRANCH));
        }

        assertThat(thrown).isExactlyInstanceOf(GitHubNotFoundException.class);
        HTTP.verifyRequested(GET, CONTEXTS_PATH);
    }

    @Test
    void addStatusCheckContexts_shouldReturnContexts_whenResponseIsOk() throws Exception {
        HTTP.stub(POST, CONTEXTS_PATH, okJson(CONTEXTS_JSON));
        final GitHubStatusCheckContextsPayload payload =
                GitHubStatusCheckContextsPayload.withContexts(List.of(CONTEXT));

        final List<String> contexts;
        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            contexts = api.api().branchProtection().addStatusCheckContexts(OWNER, REPO, BRANCH, payload);
        }

        assertThat(contexts).containsExactly(CONTEXT);
        HTTP.verifyRequested(POST, CONTEXTS_PATH);
        HTTP.verifyHeader(POST, CONTEXTS_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(
                POST,
                CONTEXTS_PATH,
                new String(CODEC.encode(Map.of("contexts", List.of(CONTEXT))), StandardCharsets.UTF_8));
    }

    @Test
    void addStatusCheckContexts_shouldPostWithoutBody_whenPayloadNull() throws Exception {
        HTTP.stub(POST, CONTEXTS_PATH, okJson(CONTEXTS_JSON));

        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            api.api().branchProtection().addStatusCheckContexts(OWNER, REPO, BRANCH, null);
        }

        HTTP.verifyRequested(POST, CONTEXTS_PATH);
    }

    @Test
    void setStatusCheckContexts_shouldReturnContexts_whenResponseIsOk() throws Exception {
        HTTP.stub(PUT, CONTEXTS_PATH, okJson(CONTEXTS_JSON));
        final GitHubStatusCheckContextsPayload payload =
                GitHubStatusCheckContextsPayload.withContextList(List.of(CONTEXT));

        final List<String> contexts;
        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            contexts = api.api().branchProtection().setStatusCheckContexts(OWNER, REPO, BRANCH, payload);
        }

        assertThat(contexts).containsExactly(CONTEXT);
        HTTP.verifyRequested(PUT, CONTEXTS_PATH);
        HTTP.verifyHeader(PUT, CONTEXTS_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PUT, CONTEXTS_PATH, new String(CODEC.encode(List.of(CONTEXT)), StandardCharsets.UTF_8));
    }

    @Test
    void removeStatusCheckContexts_shouldReturnContexts_whenResponseIsOk() throws Exception {
        HTTP.stub(DELETE, CONTEXTS_PATH, okJson(CONTEXTS_JSON));
        final GitHubStatusCheckContextsPayload payload =
                GitHubStatusCheckContextsPayload.withContexts(List.of(CONTEXT));

        final List<String> contexts;
        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            contexts = api.api().branchProtection().removeStatusCheckContexts(OWNER, REPO, BRANCH, payload);
        }

        assertThat(contexts).containsExactly(CONTEXT);
        HTTP.verifyRequested(DELETE, CONTEXTS_PATH);
        HTTP.verifyHeader(DELETE, CONTEXTS_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(
                DELETE,
                CONTEXTS_PATH,
                new String(CODEC.encode(Map.of("contexts", List.of(CONTEXT))), StandardCharsets.UTF_8));
    }

    @Test
    void getAccessRestrictions_shouldReturnPolicy_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                RESTRICTIONS_PATH,
                okJson(jsonBytes(
                        GitHubBranchesBranchProtectionApiIntegrationTest.class, "branch-restriction-policy.json")));

        final Optional<GitHubBranchRestrictionPolicy> policy;
        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            policy = api.api().branchProtection().getAccessRestrictions(OWNER, REPO, BRANCH);
        }

        assertThat(policy).contains(expectedRestrictionPolicy());
        HTTP.verifyRequested(GET, RESTRICTIONS_PATH);
    }

    @Test
    void getAccessRestrictions_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, RESTRICTIONS_PATH, notFound());

        final Optional<GitHubBranchRestrictionPolicy> policy;
        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            policy = api.api().branchProtection().getAccessRestrictions(OWNER, REPO, BRANCH);
        }

        assertThat(policy).isEmpty();
        HTTP.verifyRequested(GET, RESTRICTIONS_PATH);
    }

    @Test
    void deleteAccessRestrictions_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, RESTRICTIONS_PATH, noContent());

        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            api.api().branchProtection().deleteAccessRestrictions(OWNER, REPO, BRANCH);
        }

        HTTP.verifyRequested(DELETE, RESTRICTIONS_PATH);
    }

    @Test
    void getAppsWithAccessToProtectedBranch_shouldReturnApps_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                APPS_PATH,
                okJson(listJsonBytes(GitHubBranchesBranchProtectionApiIntegrationTest.class, "integration.json")));

        final List<GitHubIntegration> apps;
        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            apps = api.api().branchProtection().getAppsWithAccessToProtectedBranch(OWNER, REPO, BRANCH);
        }

        assertThat(apps).containsExactlyElementsOf(expectedIntegrations());
        HTTP.verifyRequested(GET, APPS_PATH);
    }

    @Test
    void getAppsWithAccessToProtectedBranch_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, APPS_PATH, notFound());

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            final GitHubBranchesBranchProtectionApi branchProtection = api.api().branchProtection();

            thrown = catchThrowable(() -> branchProtection.getAppsWithAccessToProtectedBranch(OWNER, REPO, BRANCH));
        }

        assertThat(thrown).isExactlyInstanceOf(GitHubNotFoundException.class);
        HTTP.verifyRequested(GET, APPS_PATH);
    }

    @Test
    void setAppAccessRestrictions_shouldReturnApps_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PUT,
                APPS_PATH,
                okJson(listJsonBytes(GitHubBranchesBranchProtectionApiIntegrationTest.class, "integration.json")));
        final GitHubAppAccessRestrictionsPayload payload = BranchesCatalogFixtures.appAccessRestrictionsPayload();

        final List<GitHubIntegration> apps;
        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            apps = api.api().branchProtection().setAppAccessRestrictions(OWNER, REPO, BRANCH, payload);
        }

        assertThat(apps).containsExactlyElementsOf(expectedIntegrations());
        HTTP.verifyRequested(PUT, APPS_PATH);
        HTTP.verifyHeader(PUT, APPS_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PUT, APPS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void addAppAccessRestrictions_shouldReturnApps_whenResponseIsOk() throws Exception {
        HTTP.stub(
                POST,
                APPS_PATH,
                okJson(listJsonBytes(GitHubBranchesBranchProtectionApiIntegrationTest.class, "integration.json")));
        final GitHubAppAccessRestrictionsPayload payload = BranchesCatalogFixtures.appAccessRestrictionsPayload();

        final List<GitHubIntegration> apps;
        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            apps = api.api().branchProtection().addAppAccessRestrictions(OWNER, REPO, BRANCH, payload);
        }

        assertThat(apps).containsExactlyElementsOf(expectedIntegrations());
        HTTP.verifyRequested(POST, APPS_PATH);
        HTTP.verifyBody(POST, APPS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void removeAppAccessRestrictions_shouldReturnApps_whenResponseIsOk() throws Exception {
        HTTP.stub(
                DELETE,
                APPS_PATH,
                okJson(listJsonBytes(GitHubBranchesBranchProtectionApiIntegrationTest.class, "integration.json")));
        final GitHubAppAccessRestrictionsPayload payload = BranchesCatalogFixtures.appAccessRestrictionsPayload();

        final List<GitHubIntegration> apps;
        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            apps = api.api().branchProtection().removeAppAccessRestrictions(OWNER, REPO, BRANCH, payload);
        }

        assertThat(apps).containsExactlyElementsOf(expectedIntegrations());
        HTTP.verifyRequested(DELETE, APPS_PATH);
        HTTP.verifyBody(DELETE, APPS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void getTeamsWithAccessToProtectedBranch_shouldReturnTeams_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, TEAMS_PATH, okJson(listJsonBytes(GitHubTeam.class, "team.json")));

        final List<GitHubTeam> teams;
        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            teams = api.api().branchProtection().getTeamsWithAccessToProtectedBranch(OWNER, REPO, BRANCH);
        }

        assertThat(teams).containsExactlyElementsOf(expectedTeams());
        HTTP.verifyRequested(GET, TEAMS_PATH);
    }

    @Test
    void getTeamsWithAccessToProtectedBranch_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, TEAMS_PATH, notFound());

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            final GitHubBranchesBranchProtectionApi branchProtection = api.api().branchProtection();

            thrown = catchThrowable(() -> branchProtection.getTeamsWithAccessToProtectedBranch(OWNER, REPO, BRANCH));
        }

        assertThat(thrown).isExactlyInstanceOf(GitHubNotFoundException.class);
        HTTP.verifyRequested(GET, TEAMS_PATH);
    }

    @Test
    void setTeamAccessRestrictions_shouldReturnTeams_whenResponseIsOk() throws Exception {
        HTTP.stub(PUT, TEAMS_PATH, okJson(listJsonBytes(GitHubTeam.class, "team.json")));
        final GitHubTeamAccessRestrictionsPayload payload =
                GitHubTeamAccessRestrictionsPayload.withTeams(List.of("justice-league"));

        final List<GitHubTeam> teams;
        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            teams = api.api().branchProtection().setTeamAccessRestrictions(OWNER, REPO, BRANCH, payload);
        }

        assertThat(teams).containsExactlyElementsOf(expectedTeams());
        HTTP.verifyRequested(PUT, TEAMS_PATH);
        HTTP.verifyBody(
                PUT,
                TEAMS_PATH,
                new String(CODEC.encode(Map.of("teams", List.of("justice-league"))), StandardCharsets.UTF_8));
    }

    @Test
    void setTeamAccessRestrictions_shouldPutWithoutBody_whenPayloadNull() throws Exception {
        HTTP.stub(PUT, TEAMS_PATH, okJson(listJsonBytes(GitHubTeam.class, "team.json")));

        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            api.api().branchProtection().setTeamAccessRestrictions(OWNER, REPO, BRANCH, null);
        }

        HTTP.verifyRequested(PUT, TEAMS_PATH);
    }

    @Test
    void addTeamAccessRestrictions_shouldReturnTeams_whenResponseIsOk() throws Exception {
        HTTP.stub(POST, TEAMS_PATH, okJson(listJsonBytes(GitHubTeam.class, "team.json")));
        final GitHubTeamAccessRestrictionsPayload payload =
                GitHubTeamAccessRestrictionsPayload.withTeamSlugs(List.of("justice-league"));

        final List<GitHubTeam> teams;
        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            teams = api.api().branchProtection().addTeamAccessRestrictions(OWNER, REPO, BRANCH, payload);
        }

        assertThat(teams).containsExactlyElementsOf(expectedTeams());
        HTTP.verifyRequested(POST, TEAMS_PATH);
        HTTP.verifyBody(POST, TEAMS_PATH, new String(CODEC.encode(List.of("justice-league")), StandardCharsets.UTF_8));
    }

    @Test
    void removeTeamAccessRestrictions_shouldReturnTeams_whenResponseIsOk() throws Exception {
        HTTP.stub(DELETE, TEAMS_PATH, okJson(listJsonBytes(GitHubTeam.class, "team.json")));
        final GitHubTeamAccessRestrictionsPayload payload =
                GitHubTeamAccessRestrictionsPayload.withTeams(List.of("justice-league"));

        final List<GitHubTeam> teams;
        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            teams = api.api().branchProtection().removeTeamAccessRestrictions(OWNER, REPO, BRANCH, payload);
        }

        assertThat(teams).containsExactlyElementsOf(expectedTeams());
        HTTP.verifyRequested(DELETE, TEAMS_PATH);
        HTTP.verifyBody(
                DELETE,
                TEAMS_PATH,
                new String(CODEC.encode(Map.of("teams", List.of("justice-league"))), StandardCharsets.UTF_8));
    }

    @Test
    void getUsersWithAccessToProtectedBranch_shouldReturnUsers_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, USERS_PATH, okJson(listJsonBytes(GitHubSimpleUser.class, "simple-user-octocat.json")));

        final List<GitHubSimpleUser> users;
        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            users = api.api().branchProtection().getUsersWithAccessToProtectedBranch(OWNER, REPO, BRANCH);
        }

        assertThat(users).containsExactlyElementsOf(expectedUsers());
        HTTP.verifyRequested(GET, USERS_PATH);
    }

    @Test
    void getUsersWithAccessToProtectedBranch_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, USERS_PATH, notFound());

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            final GitHubBranchesBranchProtectionApi branchProtection = api.api().branchProtection();

            thrown = catchThrowable(() -> branchProtection.getUsersWithAccessToProtectedBranch(OWNER, REPO, BRANCH));
        }

        assertThat(thrown).isExactlyInstanceOf(GitHubNotFoundException.class);
        HTTP.verifyRequested(GET, USERS_PATH);
    }

    @Test
    void setUserAccessRestrictions_shouldReturnUsers_whenResponseIsOk() throws Exception {
        HTTP.stub(PUT, USERS_PATH, okJson(listJsonBytes(GitHubSimpleUser.class, "simple-user-octocat.json")));
        final GitHubUserAccessRestrictionsPayload payload = BranchesCatalogFixtures.userAccessRestrictionsPayload();

        final List<GitHubSimpleUser> users;
        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            users = api.api().branchProtection().setUserAccessRestrictions(OWNER, REPO, BRANCH, payload);
        }

        assertThat(users).containsExactlyElementsOf(expectedUsers());
        HTTP.verifyRequested(PUT, USERS_PATH);
        HTTP.verifyBody(PUT, USERS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void addUserAccessRestrictions_shouldReturnUsers_whenResponseIsOk() throws Exception {
        HTTP.stub(POST, USERS_PATH, okJson(listJsonBytes(GitHubSimpleUser.class, "simple-user-octocat.json")));
        final GitHubUserAccessRestrictionsPayload payload = BranchesCatalogFixtures.userAccessRestrictionsPayload();

        final List<GitHubSimpleUser> users;
        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            users = api.api().branchProtection().addUserAccessRestrictions(OWNER, REPO, BRANCH, payload);
        }

        assertThat(users).containsExactlyElementsOf(expectedUsers());
        HTTP.verifyRequested(POST, USERS_PATH);
        HTTP.verifyBody(POST, USERS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void removeUserAccessRestrictions_shouldReturnUsers_whenResponseIsOk() throws Exception {
        HTTP.stub(DELETE, USERS_PATH, okJson(listJsonBytes(GitHubSimpleUser.class, "simple-user-octocat.json")));
        final GitHubUserAccessRestrictionsPayload payload = BranchesCatalogFixtures.userAccessRestrictionsPayload();

        final List<GitHubSimpleUser> users;
        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            users = api.api().branchProtection().removeUserAccessRestrictions(OWNER, REPO, BRANCH, payload);
        }

        assertThat(users).containsExactlyElementsOf(expectedUsers());
        HTTP.verifyRequested(DELETE, USERS_PATH);
        HTTP.verifyBody(DELETE, USERS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    private static GitHubBranchProtection expectedBranchProtection() {
        return CODEC.decode(
                jsonBytes(GitHubBranchesBranchProtectionApiIntegrationTest.class, "branch-protection.json"),
                GitHubBranchProtection.class);
    }

    private static GitHubProtectedBranch expectedProtectedBranch() {
        return CODEC.decode(
                jsonBytes(GitHubBranchesBranchProtectionApiIntegrationTest.class, "protected-branch.json"),
                GitHubProtectedBranch.class);
    }

    private static GitHubProtectedBranchAdminEnforced expectedAdminEnforced() {
        return CODEC.decode(
                jsonBytes(
                        GitHubBranchesBranchProtectionApiIntegrationTest.class, "protected-branch-admin-enforced.json"),
                GitHubProtectedBranchAdminEnforced.class);
    }

    private static GitHubProtectedBranchPullRequestReview expectedPullRequestReview() {
        return CODEC.decode(
                jsonBytes(
                        GitHubBranchesBranchProtectionApiIntegrationTest.class,
                        "protected-branch-pull-request-review.json"),
                GitHubProtectedBranchPullRequestReview.class);
    }

    private static GitHubStatusCheckPolicy expectedStatusCheckPolicy() {
        return CODEC.decode(
                jsonBytes(GitHubBranchesBranchProtectionApiIntegrationTest.class, "status-check-policy.json"),
                GitHubStatusCheckPolicy.class);
    }

    private static GitHubBranchRestrictionPolicy expectedRestrictionPolicy() {
        return CODEC.decode(
                jsonBytes(GitHubBranchesBranchProtectionApiIntegrationTest.class, "branch-restriction-policy.json"),
                GitHubBranchRestrictionPolicy.class);
    }

    private static List<GitHubIntegration> expectedIntegrations() {
        return CODEC.decode(
                listJsonBytes(GitHubBranchesBranchProtectionApiIntegrationTest.class, "integration.json"),
                INTEGRATIONS_TYPE);
    }

    private static List<GitHubTeam> expectedTeams() {
        return CODEC.decode(listJsonBytes(GitHubTeam.class, "team.json"), TEAMS_TYPE);
    }

    private static List<GitHubSimpleUser> expectedUsers() {
        return CODEC.decode(listJsonBytes(GitHubSimpleUser.class, "simple-user-octocat.json"), USERS_TYPE);
    }
}
