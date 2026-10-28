package io.github.piotr_janczukowicz.github.api.branches;

import io.github.piotr_janczukowicz.github.api.apps.GitHubIntegration;
import io.github.piotr_janczukowicz.github.api.apps.GitHubIntegrationOwner;
import io.github.piotr_janczukowicz.github.api.apps.GitHubIntegrationPermissions;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryPermissions;
import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeam;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeamAccessSource;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeamOwnershipType;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeamSimple;
import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import io.github.piotr_janczukowicz.json.OptionalNull;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public final class BranchesCatalogFixtures {

    private static final String PROTECTION_URL =
            "https://api.github.com/repos/octocat/Hello-World/branches/main/protection";

    private BranchesCatalogFixtures() {}

    static GitHubAppAccessRestrictionsPayload appAccessRestrictionsPayload() {
        return new GitHubAppAccessRestrictionsPayload(List.of("my-app"));
    }

    static GitHubBranchProtection branchProtection() {
        return new GitHubBranchProtection(
                PROTECTION_URL,
                true,
                protectedBranchRequiredStatusCheck(),
                protectedBranchAdminEnforced(),
                protectedBranchPullRequestReview(),
                branchRestrictionPolicy(),
                branchProtectionSettingOff(),
                branchProtectionSettingOff(),
                branchProtectionSettingOff(),
                branchProtectionSettingOff(),
                branchProtectionSetting(),
                "main",
                PROTECTION_URL,
                protectedBranchRequiredSignatures(),
                branchProtectionSettingOff(),
                branchProtectionSetting());
    }

    static GitHubBranchProtectionSetting branchProtectionSetting() {
        return new GitHubBranchProtectionSetting(true);
    }

    static GitHubBranchRestrictionPolicy branchRestrictionPolicy() {
        return new GitHubBranchRestrictionPolicy(
                PROTECTION_URL + "/restrictions",
                PROTECTION_URL + "/restrictions/users",
                PROTECTION_URL + "/restrictions/teams",
                PROTECTION_URL + "/restrictions/apps",
                List.of(branchRestrictionPolicyUser()),
                List.of(team()),
                List.of(branchRestrictionPolicyApp()));
    }

    static GitHubBranchRestrictionPolicyApp branchRestrictionPolicyApp() {
        return new GitHubBranchRestrictionPolicyApp(
                1L,
                "octoapp",
                "MDExOkludGVncmF0aW9uMQ==",
                branchRestrictionPolicyAppOwner(),
                "Octocat App",
                "Iv1.25b5d1e65ffc4022",
                "A GitHub App",
                "https://example.com",
                "https://github.com/apps/octoapp",
                "2017-07-08T16:18:44-04:00",
                "2017-07-08T16:18:44-04:00",
                branchRestrictionPolicyAppPermissions(),
                List.of("push", "pull_request"));
    }

    static GitHubBranchRestrictionPolicyAppOwner branchRestrictionPolicyAppOwner() {
        return new GitHubBranchRestrictionPolicyAppOwner(
                "github",
                1L,
                "MDEyOk9yZ2FuaXphdGlvbjE=",
                "https://api.github.com/orgs/github",
                "https://api.github.com/orgs/github/repos",
                "https://api.github.com/orgs/github/events",
                "https://api.github.com/orgs/github/hooks",
                "https://api.github.com/orgs/github/issues",
                "https://api.github.com/orgs/github/members{/member}",
                "https://api.github.com/orgs/github/public_members{/member}",
                "https://github.com/images/error/octocat_happy.gif",
                "A great organization",
                "",
                "https://github.com/github",
                "https://api.github.com/users/github/followers",
                "https://api.github.com/users/github/following{/other_user}",
                "https://api.github.com/users/github/gists{/gist_id}",
                "https://api.github.com/users/github/starred{/owner}{/repo}",
                "https://api.github.com/users/github/subscriptions",
                "https://api.github.com/users/github/orgs",
                "https://api.github.com/users/github/received_events",
                "Organization",
                false,
                "public");
    }

    static GitHubBranchRestrictionPolicyAppPermissions branchRestrictionPolicyAppPermissions() {
        return new GitHubBranchRestrictionPolicyAppPermissions("read", "read", "write", "write");
    }

    static GitHubBranchRestrictionPolicyUser branchRestrictionPolicyUser() {
        return new GitHubBranchRestrictionPolicyUser(
                "octocat",
                1L,
                "MDQ6VXNlcjE=",
                "https://github.com/images/error/octocat_happy.gif",
                "",
                "https://api.github.com/users/octocat",
                "https://github.com/octocat",
                "https://api.github.com/users/octocat/followers",
                "https://api.github.com/users/octocat/following{/other_user}",
                "https://api.github.com/users/octocat/gists{/gist_id}",
                "https://api.github.com/users/octocat/starred{/owner}{/repo}",
                "https://api.github.com/users/octocat/subscriptions",
                "https://api.github.com/users/octocat/orgs",
                "https://api.github.com/users/octocat/repos",
                "https://api.github.com/users/octocat/events{/privacy}",
                "https://api.github.com/users/octocat/received_events",
                "User",
                false,
                "public");
    }

    static GitHubBranchWithProtection branchWithProtection() {
        return new GitHubBranchWithProtection(
                "main",
                RepositoriesCatalogFixtures.commit(),
                branchWithProtectionLinks(),
                true,
                branchProtection(),
                PROTECTION_URL,
                "main",
                2);
    }

    static GitHubBranchWithProtection branchWithProtectionFromStub() {
        return new GitHubBranchWithProtection(
                "main",
                RepositoriesCatalogFixtures.commitFromStub(),
                branchWithProtectionLinks(),
                true,
                new GitHubBranchProtection(
                        null,
                        true,
                        new GitHubProtectedBranchRequiredStatusCheck(
                                null,
                                null,
                                List.of("continuous-integration/travis-ci"),
                                List.of(new GitHubStatusCheckPolicyCheck(
                                        "continuous-integration/travis-ci", OptionalNull.ofNull())),
                                null,
                                null),
                        protectedBranchAdminEnforced(),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null),
                PROTECTION_URL,
                null,
                null);
    }

    static GitHubIntegration integrationFromStub() {
        return new GitHubIntegration(
                37L,
                "probot-owners",
                "MDExOkludGVncmF0aW9uMQ==",
                "Iv1.25b5d1e65ffc4022",
                GitHubIntegrationOwner.ofUser(UsersCatalogFixtures.octocatWithoutOptionalFields()),
                "Probot Owners",
                "The description of the app.",
                "https://example.com",
                "https://github.com/apps/super-ci",
                Instant.parse("2017-07-08T20:18:44Z"),
                Instant.parse("2017-07-08T20:18:44Z"),
                new GitHubIntegrationPermissions("read", null, null, null, "write", Map.of()),
                List.of("label", "deployment"),
                null);
    }

    static GitHubBranchWithProtectionLinks branchWithProtectionLinks() {
        return new GitHubBranchWithProtectionLinks(
                "https://github.com/octocat/Hello-World/tree/main",
                "https://api.github.com/repos/octocat/Hello-World/branches/main");
    }

    static GitHubListBranchesFilter listBranchesFilter() {
        return new GitHubListBranchesFilter(true);
    }

    static GitHubMergeBranchPayload mergeBranchPayload() {
        return new GitHubMergeBranchPayload("main", "feature", "Merge feature into main");
    }

    static GitHubMergeUpstreamPayload mergeUpstreamPayload() {
        return GitHubMergeUpstreamPayloadBuilder.builder().branch("main").build();
    }

    static GitHubProtectedBranch protectedBranch() {
        return new GitHubProtectedBranch(
                PROTECTION_URL,
                statusCheckPolicy(),
                protectedBranchRequiredPullRequestReviews(),
                protectedBranchRequiredSignatures(),
                protectedBranchEnforceAdmins(),
                branchProtectionSettingOff(),
                branchProtectionSettingOff(),
                branchProtectionSettingOff(),
                branchRestrictionPolicy(),
                branchProtectionSetting(),
                branchProtectionSettingOff(),
                branchProtectionSettingOff(),
                branchProtectionSetting());
    }

    static GitHubProtectedBranchAdminEnforced protectedBranchAdminEnforced() {
        return new GitHubProtectedBranchAdminEnforced(PROTECTION_URL + "/enforce_admins", true);
    }

    static GitHubProtectedBranchBypassPullRequestAllowances protectedBranchBypassPullRequestAllowances() {
        return new GitHubProtectedBranchBypassPullRequestAllowances(
                List.of(UsersCatalogFixtures.octocat()), List.of(team()), List.of(integration()));
    }

    static GitHubProtectedBranchDismissalRestrictions protectedBranchDismissalRestrictions() {
        return new GitHubProtectedBranchDismissalRestrictions(
                List.of(UsersCatalogFixtures.octocat()),
                List.of(team()),
                List.of(integration()),
                PROTECTION_URL + "/dismissal_restrictions",
                PROTECTION_URL + "/dismissal_restrictions/users",
                PROTECTION_URL + "/dismissal_restrictions/teams");
    }

    static GitHubProtectedBranchEnforceAdmins protectedBranchEnforceAdmins() {
        return new GitHubProtectedBranchEnforceAdmins(PROTECTION_URL + "/enforce_admins", true);
    }

    static GitHubProtectedBranchPullRequestReview protectedBranchPullRequestReview() {
        return new GitHubProtectedBranchPullRequestReview(
                PROTECTION_URL + "/required_pull_request_reviews",
                protectedBranchPullRequestReviewDismissalRestrictions(),
                protectedBranchPullRequestReviewBypassPullRequestAllowances(),
                true,
                true,
                2,
                true);
    }

    static GitHubProtectedBranchPullRequestReviewBypassPullRequestAllowances
            protectedBranchPullRequestReviewBypassPullRequestAllowances() {
        return new GitHubProtectedBranchPullRequestReviewBypassPullRequestAllowances(
                List.of(UsersCatalogFixtures.octocat()), List.of(team()), List.of(integration()));
    }

    static GitHubProtectedBranchPullRequestReviewDismissalRestrictions
            protectedBranchPullRequestReviewDismissalRestrictions() {
        return new GitHubProtectedBranchPullRequestReviewDismissalRestrictions(
                List.of(UsersCatalogFixtures.octocat()),
                List.of(team()),
                List.of(integration()),
                PROTECTION_URL + "/dismissal_restrictions",
                PROTECTION_URL + "/dismissal_restrictions/users",
                PROTECTION_URL + "/dismissal_restrictions/teams");
    }

    static GitHubProtectedBranchRequiredPullRequestReviews protectedBranchRequiredPullRequestReviews() {
        return new GitHubProtectedBranchRequiredPullRequestReviews(
                PROTECTION_URL + "/required_pull_request_reviews",
                protectedBranchDismissalRestrictions(),
                true,
                true,
                2,
                true,
                protectedBranchBypassPullRequestAllowances());
    }

    static GitHubProtectedBranchRequiredSignatures protectedBranchRequiredSignatures() {
        return new GitHubProtectedBranchRequiredSignatures(PROTECTION_URL + "/required_signatures", true);
    }

    static GitHubProtectedBranchRequiredStatusCheck protectedBranchRequiredStatusCheck() {
        return new GitHubProtectedBranchRequiredStatusCheck(
                PROTECTION_URL + "/required_status_checks",
                "everyone",
                List.of("continuous-integration/travis-ci"),
                List.of(statusCheckPolicyCheck()),
                PROTECTION_URL + "/required_status_checks/contexts",
                true);
    }

    static GitHubRenameBranchPayload renameBranchPayload() {
        return new GitHubRenameBranchPayload("my_renamed_branch");
    }

    static GitHubShortBranch shortBranch() {
        return new GitHubShortBranch("main", shortBranchCommit(), true, branchProtection(), PROTECTION_URL);
    }

    static GitHubShortBranchCommit shortBranchCommit() {
        return new GitHubShortBranchCommit(
                "6dcb09b5b57875f334f61aebed695e2e4193db5e",
                "https://api.github.com/repos/octocat/Hello-World/commits/6dcb09b5b57875f334f61aebed695e2e4193db5e");
    }

    static GitHubStatusCheckPolicy statusCheckPolicy() {
        return new GitHubStatusCheckPolicy(
                PROTECTION_URL + "/required_status_checks",
                true,
                List.of("continuous-integration/travis-ci"),
                List.of(statusCheckPolicyCheck()),
                PROTECTION_URL + "/required_status_checks/contexts");
    }

    static GitHubStatusCheckPolicyCheck statusCheckPolicyCheck() {
        return new GitHubStatusCheckPolicyCheck("continuous-integration/travis-ci", OptionalNull.of(1L));
    }

    static GitHubUpdateBranchProtectionBypassPullRequestAllowances updateBranchProtectionBypassPullRequestAllowances() {
        return new GitHubUpdateBranchProtectionBypassPullRequestAllowances(
                List.of("octocat"), List.of("justice-league"), List.of("my-app"));
    }

    static GitHubUpdateBranchProtectionDismissalRestrictions updateBranchProtectionDismissalRestrictions() {
        return new GitHubUpdateBranchProtectionDismissalRestrictions(
                List.of("octocat"), List.of("justice-league"), List.of("my-app"));
    }

    static GitHubUpdateBranchProtectionPayload updateBranchProtectionPayload() {
        return new GitHubUpdateBranchProtectionPayload(
                OptionalNull.of(updateBranchProtectionRequiredStatusChecks()),
                OptionalNull.of(true),
                OptionalNull.of(updateBranchProtectionRequiredPullRequestReviews()),
                OptionalNull.of(updateBranchProtectionRestrictions()),
                true,
                OptionalNull.of(false),
                false,
                true,
                true,
                false,
                true);
    }

    static GitHubUpdateBranchProtectionRequiredPullRequestReviews updateBranchProtectionRequiredPullRequestReviews() {
        return new GitHubUpdateBranchProtectionRequiredPullRequestReviews(
                updateBranchProtectionDismissalRestrictions(),
                true,
                true,
                2,
                true,
                updateBranchProtectionBypassPullRequestAllowances());
    }

    static GitHubUpdateBranchProtectionRequiredStatusCheck updateBranchProtectionRequiredStatusCheck() {
        return new GitHubUpdateBranchProtectionRequiredStatusCheck("continuous-integration/travis-ci", 1L);
    }

    static GitHubUpdateBranchProtectionRequiredStatusChecks updateBranchProtectionRequiredStatusChecks() {
        return new GitHubUpdateBranchProtectionRequiredStatusChecks(
                true,
                List.of("continuous-integration/travis-ci"),
                List.of(updateBranchProtectionRequiredStatusCheck()));
    }

    static GitHubUpdateBranchProtectionRestrictions updateBranchProtectionRestrictions() {
        return new GitHubUpdateBranchProtectionRestrictions(
                List.of("octocat"), List.of("justice-league"), List.of("my-app"));
    }

    static GitHubUpdatePullRequestReviewProtectionPayload updatePullRequestReviewProtectionPayload() {
        return new GitHubUpdatePullRequestReviewProtectionPayload(
                updateBranchProtectionDismissalRestrictions(),
                true,
                true,
                2,
                true,
                updateBranchProtectionBypassPullRequestAllowances());
    }

    static GitHubUpdateStatusCheckProtectionPayload updateStatusCheckProtectionPayload() {
        return new GitHubUpdateStatusCheckProtectionPayload(
                true,
                List.of("continuous-integration/travis-ci"),
                List.of(updateBranchProtectionRequiredStatusCheck()));
    }

    static GitHubUserAccessRestrictionsPayload userAccessRestrictionsPayload() {
        return new GitHubUserAccessRestrictionsPayload(List.of("octocat"));
    }

    private static GitHubBranchProtectionSetting branchProtectionSettingOff() {
        return new GitHubBranchProtectionSetting(false);
    }

    public static GitHubIntegration integration() {
        return new GitHubIntegration(
                37L,
                "probot-owners",
                "MDExOkludGVncmF0aW9uMQ==",
                "Iv1.25b5d1e65ffc4022",
                GitHubIntegrationOwner.ofUser(UsersCatalogFixtures.octocat()),
                "Probot Owners",
                "The description of the app.",
                "https://example.com",
                "https://github.com/apps/super-ci",
                Instant.parse("2017-07-08T20:18:44Z"),
                Instant.parse("2017-07-08T20:18:44Z"),
                new GitHubIntegrationPermissions("read", "read", "read", "read", "write", Map.of()),
                List.of("label", "deployment"),
                5);
    }

    private static GitHubTeam team() {
        return new GitHubTeam(
                1L,
                "MDQ6VGVhbTE=",
                "Justice League",
                "justice-league",
                "A great team.",
                "closed",
                "notifications_enabled",
                "push",
                new GitHubRepositoryPermissions(true, false, true, false, true),
                "https://api.github.com/organizations/1/team/1",
                "https://github.com/orgs/rails/teams/core",
                "https://api.github.com/organizations/1/team/1/members{/member}",
                "https://api.github.com/organizations/1/team/1/repos",
                GitHubTeamOwnershipType.ORGANIZATION,
                GitHubTeamAccessSource.ORGANIZATION,
                1L,
                2L,
                teamParent());
    }

    private static GitHubTeamSimple teamParent() {
        return new GitHubTeamSimple(
                2L,
                "MDQ6VGVhbTI=",
                "https://api.github.com/organizations/1/team/2",
                "https://api.github.com/organizations/1/team/2/members{/member}",
                "Parent Team",
                "Parent team.",
                "admin",
                "closed",
                "notifications_enabled",
                "https://github.com/orgs/rails/teams/parent",
                "https://api.github.com/organizations/1/team/2/repos",
                "parent-team",
                "cn=parent-team,ou=groups,dc=github,dc=com",
                GitHubTeamOwnershipType.ORGANIZATION,
                1L,
                2L);
    }
}
