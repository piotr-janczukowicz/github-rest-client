package io.github.piotr_janczukowicz.github.api.branches;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.apps.GitHubIntegration;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeam;
import io.github.piotr_janczukowicz.github.api.teams.TeamCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubBranchesBranchProtectionApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String BRANCH = "main";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubBranchesBranchProtectionApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void getBranchProtection_shouldReturnProtection_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("branch-protection.json")));

        final Optional<GitHubBranchProtection> protection = api().getBranchProtection(OWNER, REPO, BRANCH);

        assertThat(protection).contains(BranchesCatalogFixtures.branchProtection());
    }

    @Test
    void getBranchProtection_shouldPercentEncodeSlashInOwner_whenOwnerContainsSlash() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("branch-protection.json")));

        api().getBranchProtection("a/b", REPO, BRANCH);

        assertThat(captureSingleRequest().uri().getRawPath())
                .isEqualTo("/repos/a%2Fb/Hello-World/branches/main/protection");
    }

    @Test
    void updateBranchProtection_shouldReturnProtectedBranch_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("protected-branch.json")));

        final GitHubProtectedBranch protectedBranch = api().updateBranchProtection(
                        OWNER, REPO, BRANCH, BranchesCatalogFixtures.updateBranchProtectionPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(protectedBranch).isEqualTo(BranchesCatalogFixtures.protectedBranch());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
        });
    }

    @Test
    void deleteBranchProtection_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteBranchProtection(OWNER, REPO, BRANCH);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/branches/main/protection");
        });
    }

    @Test
    void getAdminBranchProtection_shouldReturnEnforced_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("protected-branch-admin-enforced.json")));

        final Optional<GitHubProtectedBranchAdminEnforced> enforced =
                api().getAdminBranchProtection(OWNER, REPO, BRANCH);

        assertThat(enforced).contains(BranchesCatalogFixtures.protectedBranchAdminEnforced());
    }

    @Test
    void getAdminBranchProtection_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubProtectedBranchAdminEnforced> enforced =
                api().getAdminBranchProtection(OWNER, REPO, BRANCH);

        assertThat(enforced).isEmpty();
    }

    @Test
    void setAdminBranchProtection_shouldReturnEnforced_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("protected-branch-admin-enforced.json")));

        final GitHubProtectedBranchAdminEnforced enforced = api().setAdminBranchProtection(OWNER, REPO, BRANCH);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(enforced).isEqualTo(BranchesCatalogFixtures.protectedBranchAdminEnforced());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
        });
    }

    @Test
    void deleteAdminBranchProtection_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteAdminBranchProtection(OWNER, REPO, BRANCH);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri())
                .hasToString(
                        "https://api.github.com/repos/octocat/Hello-World/branches/main/protection/enforce_admins");
    }

    @Test
    void getPullRequestReviewProtection_shouldReturnReview_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("protected-branch-pull-request-review.json")));

        final Optional<GitHubProtectedBranchPullRequestReview> review =
                api().getPullRequestReviewProtection(OWNER, REPO, BRANCH);

        assertThat(review).contains(BranchesCatalogFixtures.protectedBranchPullRequestReview());
    }

    @Test
    void getPullRequestReviewProtection_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubProtectedBranchPullRequestReview> review =
                api().getPullRequestReviewProtection(OWNER, REPO, BRANCH);

        assertThat(review).isEmpty();
    }

    @Test
    void updatePullRequestReviewProtection_shouldReturnReview_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("protected-branch-pull-request-review.json")));

        final GitHubProtectedBranchPullRequestReview review = api().updatePullRequestReviewProtection(
                        OWNER, REPO, BRANCH, BranchesCatalogFixtures.updatePullRequestReviewProtectionPayload());

        assertThat(review).isEqualTo(BranchesCatalogFixtures.protectedBranchPullRequestReview());
    }

    @Test
    void updatePullRequestReviewProtection_shouldReturnReview_whenPayloadNull() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("protected-branch-pull-request-review.json")));

        final GitHubProtectedBranchPullRequestReview review =
                api().updatePullRequestReviewProtection(OWNER, REPO, BRANCH, null);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(review).isEqualTo(BranchesCatalogFixtures.protectedBranchPullRequestReview());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
        });
    }

    @Test
    void deletePullRequestReviewProtection_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deletePullRequestReviewProtection(OWNER, REPO, BRANCH);

        assertThat(captureSingleRequest().method()).isEqualTo(HttpMethod.DELETE);
    }

    @Test
    void getCommitSignatureProtection_shouldReturnEnforced_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("protected-branch-admin-enforced.json")));

        final Optional<GitHubProtectedBranchAdminEnforced> enforced =
                api().getCommitSignatureProtection(OWNER, REPO, BRANCH);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(enforced).contains(BranchesCatalogFixtures.protectedBranchAdminEnforced());
            softly.assertThat(request.uri())
                    .hasToString(
                            "https://api.github.com/repos/octocat/Hello-World/branches/main/protection/required_signatures");
        });
    }

    @Test
    void getCommitSignatureProtection_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubProtectedBranchAdminEnforced> enforced =
                api().getCommitSignatureProtection(OWNER, REPO, BRANCH);

        assertThat(enforced).isEmpty();
    }

    @Test
    void createCommitSignatureProtection_shouldReturnEnforced_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("protected-branch-admin-enforced.json")));

        final GitHubProtectedBranchAdminEnforced enforced = api().createCommitSignatureProtection(OWNER, REPO, BRANCH);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(enforced).isEqualTo(BranchesCatalogFixtures.protectedBranchAdminEnforced());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
        });
    }

    @Test
    void deleteCommitSignatureProtection_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteCommitSignatureProtection(OWNER, REPO, BRANCH);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri())
                .hasToString(
                        "https://api.github.com/repos/octocat/Hello-World/branches/main/protection/required_signatures");
    }

    @Test
    void getStatusChecksProtection_shouldReturnPolicy_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("status-check-policy.json")));

        final Optional<GitHubStatusCheckPolicy> policy = api().getStatusChecksProtection(OWNER, REPO, BRANCH);

        assertThat(policy).contains(BranchesCatalogFixtures.statusCheckPolicy());
    }

    @Test
    void updateStatusCheckProtection_shouldReturnPolicy_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("status-check-policy.json")));

        final GitHubStatusCheckPolicy policy = api().updateStatusCheckProtection(
                        OWNER, REPO, BRANCH, BranchesCatalogFixtures.updateStatusCheckProtectionPayload());

        assertThat(policy).isEqualTo(BranchesCatalogFixtures.statusCheckPolicy());
    }

    @Test
    void updateStatusCheckProtection_shouldReturnPolicy_whenPayloadNull() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("status-check-policy.json")));

        final GitHubStatusCheckPolicy policy = api().updateStatusCheckProtection(OWNER, REPO, BRANCH, null);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(policy).isEqualTo(BranchesCatalogFixtures.statusCheckPolicy());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
        });
    }

    @Test
    void removeStatusCheckProtection_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().removeStatusCheckProtection(OWNER, REPO, BRANCH);

        assertThat(captureSingleRequest().method()).isEqualTo(HttpMethod.DELETE);
    }

    @Test
    void getAllStatusCheckContexts_shouldReturnContexts_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[\"continuous-integration/travis-ci\"]"));

        final List<String> contexts = api().getAllStatusCheckContexts(OWNER, REPO, BRANCH);

        assertThat(contexts).containsExactly("continuous-integration/travis-ci");
    }

    @Test
    void getAllStatusCheckContexts_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final GitHubBranchesBranchProtectionApi api = api();

        final Throwable thrown = catchThrowable(() -> api.getAllStatusCheckContexts(OWNER, REPO, BRANCH));

        assertThat(thrown).isInstanceOf(GitHubApiException.class);
    }

    @Test
    void addStatusCheckContexts_shouldReturnContexts_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[\"continuous-integration/travis-ci\"]"));

        final List<String> contexts = api().addStatusCheckContexts(
                        OWNER,
                        REPO,
                        BRANCH,
                        GitHubStatusCheckContextsPayload.withContexts(List.of("continuous-integration/travis-ci")));

        assertThat(contexts).containsExactly("continuous-integration/travis-ci");
    }

    @Test
    void addStatusCheckContexts_shouldReturnContexts_whenPayloadNull() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[\"continuous-integration/travis-ci\"]"));

        api().addStatusCheckContexts(OWNER, REPO, BRANCH, null);

        assertThat(captureSingleRequest().method()).isEqualTo(HttpMethod.POST);
    }

    @Test
    void setStatusCheckContexts_shouldReturnContexts_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[\"continuous-integration/travis-ci\"]"));

        api().setStatusCheckContexts(
                        OWNER,
                        REPO,
                        BRANCH,
                        GitHubStatusCheckContextsPayload.withContextList(List.of("continuous-integration/travis-ci")));
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.PUT);
    }

    @Test
    void setStatusCheckContexts_shouldReturnContexts_whenPayloadNull() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[\"continuous-integration/travis-ci\"]"));

        api().setStatusCheckContexts(OWNER, REPO, BRANCH, null);

        assertThat(captureSingleRequest().method()).isEqualTo(HttpMethod.PUT);
    }

    @Test
    void removeStatusCheckContexts_shouldReturnContexts_whenPayloadNull() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[\"continuous-integration/travis-ci\"]"));

        api().removeStatusCheckContexts(OWNER, REPO, BRANCH, null);

        assertThat(captureSingleRequest().method()).isEqualTo(HttpMethod.DELETE);
    }

    @Test
    void removeStatusCheckContexts_shouldReturnContexts_whenPayloadProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[\"continuous-integration/travis-ci\"]"));

        api().removeStatusCheckContexts(
                        OWNER,
                        REPO,
                        BRANCH,
                        GitHubStatusCheckContextsPayload.withContexts(List.of("continuous-integration/travis-ci")));
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
    }

    @Test
    void getAccessRestrictions_shouldReturnPolicy_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("branch-restriction-policy.json")));

        final Optional<GitHubBranchRestrictionPolicy> policy = api().getAccessRestrictions(OWNER, REPO, BRANCH);

        assertThat(policy).contains(BranchesCatalogFixtures.branchRestrictionPolicy());
    }

    @Test
    void deleteAccessRestrictions_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteAccessRestrictions(OWNER, REPO, BRANCH);

        assertThat(captureSingleRequest().uri().getPath())
                .isEqualTo("/repos/octocat/Hello-World/branches/main/protection/restrictions");
    }

    @Test
    void getAppsWithAccessToProtectedBranch_shouldReturnApps_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("integration.json") + "]"));

        final List<GitHubIntegration> apps = api().getAppsWithAccessToProtectedBranch(OWNER, REPO, BRANCH);

        assertThat(apps).containsExactly(BranchesCatalogFixtures.integrationFromStub());
    }

    @Test
    void getAppsWithAccessToProtectedBranch_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final GitHubBranchesBranchProtectionApi api = api();

        final Throwable thrown = catchThrowable(() -> api.getAppsWithAccessToProtectedBranch(OWNER, REPO, BRANCH));

        assertThat(thrown).isInstanceOf(GitHubApiException.class);
    }

    @Test
    void setAppAccessRestrictions_shouldReturnApps_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("integration.json") + "]"));

        final List<GitHubIntegration> apps = api().setAppAccessRestrictions(
                        OWNER, REPO, BRANCH, BranchesCatalogFixtures.appAccessRestrictionsPayload());

        assertThat(apps).containsExactly(BranchesCatalogFixtures.integrationFromStub());
    }

    @Test
    void addAppAccessRestrictions_shouldReturnApps_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("integration.json") + "]"));

        final List<GitHubIntegration> apps = api().addAppAccessRestrictions(
                        OWNER, REPO, BRANCH, BranchesCatalogFixtures.appAccessRestrictionsPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(apps).containsExactly(BranchesCatalogFixtures.integrationFromStub());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
        });
    }

    @Test
    void removeAppAccessRestrictions_shouldReturnApps_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("integration.json") + "]"));

        final List<GitHubIntegration> apps = api().removeAppAccessRestrictions(
                        OWNER, REPO, BRANCH, BranchesCatalogFixtures.appAccessRestrictionsPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(apps).containsExactly(BranchesCatalogFixtures.integrationFromStub());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
        });
    }

    @Test
    void getTeamsWithAccessToProtectedBranch_shouldReturnTeams_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json(GitHubTeam.class, "team.json") + "]"));

        final List<GitHubTeam> teams = api().getTeamsWithAccessToProtectedBranch(OWNER, REPO, BRANCH);

        assertThat(teams).containsExactly(TeamCatalogFixtures.team());
    }

    @Test
    void getTeamsWithAccessToProtectedBranch_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final GitHubBranchesBranchProtectionApi api = api();

        final Throwable thrown = catchThrowable(() -> api.getTeamsWithAccessToProtectedBranch(OWNER, REPO, BRANCH));

        assertThat(thrown).isInstanceOf(GitHubApiException.class);
    }

    @Test
    void setTeamAccessRestrictions_shouldReturnTeams_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json(GitHubTeam.class, "team.json") + "]"));

        final List<GitHubTeam> teams = api().setTeamAccessRestrictions(
                        OWNER, REPO, BRANCH, GitHubTeamAccessRestrictionsPayload.withTeams(List.of("justice-league")));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(teams).containsExactly(TeamCatalogFixtures.team());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
        });
    }

    @Test
    void setTeamAccessRestrictions_shouldReturnTeams_whenPayloadNull() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json(GitHubTeam.class, "team.json") + "]"));

        final List<GitHubTeam> teams = api().setTeamAccessRestrictions(OWNER, REPO, BRANCH, null);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(teams).containsExactly(TeamCatalogFixtures.team());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
        });
    }

    @Test
    void addTeamAccessRestrictions_shouldReturnTeams_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json(GitHubTeam.class, "team.json") + "]"));

        final List<GitHubTeam> teams = api().addTeamAccessRestrictions(
                        OWNER,
                        REPO,
                        BRANCH,
                        GitHubTeamAccessRestrictionsPayload.withTeamSlugs(List.of("justice-league")));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(teams).containsExactly(TeamCatalogFixtures.team());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
        });
    }

    @Test
    void addTeamAccessRestrictions_shouldReturnTeams_whenPayloadNull() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json(GitHubTeam.class, "team.json") + "]"));

        final List<GitHubTeam> teams = api().addTeamAccessRestrictions(OWNER, REPO, BRANCH, null);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(teams).containsExactly(TeamCatalogFixtures.team());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
        });
    }

    @Test
    void removeTeamAccessRestrictions_shouldReturnTeams_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json(GitHubTeam.class, "team.json") + "]"));

        final List<GitHubTeam> teams = api().removeTeamAccessRestrictions(
                        OWNER, REPO, BRANCH, GitHubTeamAccessRestrictionsPayload.withTeams(List.of("justice-league")));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(teams).containsExactly(TeamCatalogFixtures.team());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
        });
    }

    @Test
    void removeTeamAccessRestrictions_shouldReturnTeams_whenPayloadNull() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json(GitHubTeam.class, "team.json") + "]"));

        final List<GitHubTeam> teams = api().removeTeamAccessRestrictions(OWNER, REPO, BRANCH, null);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(teams).containsExactly(TeamCatalogFixtures.team());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
        });
    }

    @Test
    void getUsersWithAccessToProtectedBranch_shouldReturnUsers_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(
                        HttpStatus.OK.code(), "[" + json(GitHubSimpleUser.class, "simple-user-octocat.json") + "]"));

        final List<GitHubSimpleUser> users = api().getUsersWithAccessToProtectedBranch(OWNER, REPO, BRANCH);

        assertThat(users).containsExactly(UsersCatalogFixtures.octocat());
    }

    @Test
    void getUsersWithAccessToProtectedBranch_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final GitHubBranchesBranchProtectionApi api = api();

        final Throwable thrown = catchThrowable(() -> api.getUsersWithAccessToProtectedBranch(OWNER, REPO, BRANCH));

        assertThat(thrown).isInstanceOf(GitHubApiException.class);
    }

    @Test
    void setUserAccessRestrictions_shouldReturnUsers_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(
                        HttpStatus.OK.code(), "[" + json(GitHubSimpleUser.class, "simple-user-octocat.json") + "]"));

        final List<GitHubSimpleUser> users = api().setUserAccessRestrictions(
                        OWNER, REPO, BRANCH, BranchesCatalogFixtures.userAccessRestrictionsPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(users).containsExactly(UsersCatalogFixtures.octocat());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
        });
    }

    @Test
    void addUserAccessRestrictions_shouldReturnUsers_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(
                        HttpStatus.OK.code(), "[" + json(GitHubSimpleUser.class, "simple-user-octocat.json") + "]"));

        final List<GitHubSimpleUser> users = api().addUserAccessRestrictions(
                        OWNER, REPO, BRANCH, BranchesCatalogFixtures.userAccessRestrictionsPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(users).containsExactly(UsersCatalogFixtures.octocat());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
        });
    }

    @Test
    void removeUserAccessRestrictions_shouldReturnUsers_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(
                        HttpStatus.OK.code(), "[" + json(GitHubSimpleUser.class, "simple-user-octocat.json") + "]"));

        final List<GitHubSimpleUser> users = api().removeUserAccessRestrictions(
                        OWNER, REPO, BRANCH, BranchesCatalogFixtures.userAccessRestrictionsPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(users).containsExactly(UsersCatalogFixtures.octocat());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
        });
    }

    private GitHubBranchesBranchProtectionApi api() {
        return new GitHubBranchesBranchProtectionApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubBranchesBranchProtectionApiTest.class, fileName);
    }

    private static String json(final Class<?> type, final String fileName) {
        return FileUtils.readString(type, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
