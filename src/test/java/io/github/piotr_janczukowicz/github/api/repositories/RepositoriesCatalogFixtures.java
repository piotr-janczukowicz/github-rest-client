package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;

import io.github.piotr_janczukowicz.github.api.codesofconduct.GitHubCodeOfConduct;
import io.github.piotr_janczukowicz.github.api.licenses.GitHubLicenseSimple;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubIssueType;
import io.github.piotr_janczukowicz.github.api.pullrequests.GitHubPullRequestStack;
import io.github.piotr_janczukowicz.github.api.pullrequests.GitHubPullRequestStackBase;
import io.github.piotr_janczukowicz.github.api.search.GitHubAuthorAssociation;
import io.github.piotr_janczukowicz.github.api.search.GitHubGitUser;
import io.github.piotr_janczukowicz.github.api.search.GitHubMilestone;
import io.github.piotr_janczukowicz.github.api.search.GitHubMilestoneState;
import io.github.piotr_janczukowicz.github.api.search.GitHubVerification;
import io.github.piotr_janczukowicz.github.api.securityadvisories.GitHubCodeOfConductSimple;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeam;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeamAccessSource;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeamOwnershipType;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeamSimple;
import io.github.piotr_janczukowicz.github.api.users.GitHubAttestation;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUserBuilder;
import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import io.github.piotr_janczukowicz.json.OptionalNull;
import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public final class RepositoriesCatalogFixtures {

    private static final String REPO_API = "https://api.github.com/repos/octocat/Hello-World";
    private static final String REPO_HTTP = "http://api.github.com/repos/octocat/Hello-World";
    private static final String RULESET_SOURCE_NAME = "my-org/my-repo";
    private static final long RULESET_ID = 42L;

    private RepositoriesCatalogFixtures() {}

    public static GitHubLicenseSimple mitLicenseWithHtmlUrl() {
        return new GitHubLicenseSimple(
                "mit",
                "MIT License",
                "https://api.github.com/licenses/mit",
                "MIT",
                "MDc6TGljZW5zZW1pdA==",
                "https://choosealicense.com/licenses/mit/");
    }

    public static GitHubMinimalRepositoryLicense minimalRepositoryLicense() {
        return new GitHubMinimalRepositoryLicense(
                "mit", "MIT License", "MIT", "https://api.github.com/licenses/mit", "MDc6TGljZW5zZW1pdA==");
    }

    public static GitHubRepositoryPermissions repositoryPermissions() {
        return new GitHubRepositoryPermissions(true, false, true, false, true);
    }

    public static GitHubSecurityAndAnalysis securityAndAnalysis() {
        return new GitHubSecurityAndAnalysis(
                status(GitHubSecurityAndAnalysisEnablement.ENABLED),
                status(GitHubSecurityAndAnalysisEnablement.ENABLED),
                status(GitHubSecurityAndAnalysisEnablement.ENABLED),
                status(GitHubSecurityAndAnalysisEnablement.DISABLED),
                status(GitHubSecurityAndAnalysisEnablement.ENABLED),
                status(GitHubSecurityAndAnalysisEnablement.DISABLED),
                status(GitHubSecurityAndAnalysisEnablement.DISABLED),
                status(GitHubSecurityAndAnalysisEnablement.ENABLED),
                status(GitHubSecurityAndAnalysisEnablement.DISABLED),
                status(GitHubSecurityAndAnalysisEnablement.DISABLED),
                new GitHubSecretScanningDelegatedBypassOptions(List.of(new GitHubSecretScanningDelegatedBypassReviewer(
                        1L,
                        GitHubSecretScanningBypassReviewerType.TEAM,
                        GitHubSecretScanningDelegatedBypassMode.ALWAYS))));
    }

    static GitHubVerification sampleVerification() {
        return new GitHubVerification(
                true,
                "valid",
                "tree 827efc6d56897b048c772eb4087f854f46256132\nparent 6dcb09b5b57875f334f61aebed695e2e4193db5e\nauthor The Octocat <octocat@github.com> 1288184193 -0700\ncommitter The Octocat <octocat@github.com> 1288184193 -0700\n\nFix all the bugs",
                "-----BEGIN PGP SIGNATURE-----\nVersion: GnuPG v1\n\niQEcBAABAgAGBQJQqZiHAAoJEAW5p2yx9jGYH2K+QAiY9G9ub3VzIG1vbnN0ZXIgPG9j\ntb2NhdEBnaXRodWIuY29tPoi5BBMRAgA5BQJQqZiHAwsNCg0ZAwIBAgQvH1tRAAoJEBbk\ndssfYxjqHREAmwRHCplPmvz71ZMKj2hzlY5oy5c0jW/gXTA7hAd35L9XLwqrNAY08b+/\n9tG2x8fkNQ+oKzyUH4SNe/+Sj6gC0V5gZ3IrQzhhWKWI03cMW9PuKFv5pBwZ8xbdV07a\n39UsUp9J63a6i6E8BKR1F0ndsrMf5WYLRB0u7+pp8/Ho98v45H/Ry5Bq87jxh8xGHmv6\nHAgmv6iM+vIFZdWwiw==\n-----END PGP SIGNATURE-----\n",
                Instant.parse("2011-01-26T19:01:12Z"));
    }

    static GitHubRepositoryRuleBranchNamePatternParameters branchNamePatternParameters() {
        return patternParameters(GitHubRepositoryRuleBranchNamePatternParameters::new);
    }

    static GitHubRepositoryRuleTagNamePatternParameters tagNamePatternParameters() {
        return patternParameters(GitHubRepositoryRuleTagNamePatternParameters::new);
    }

    static GitHubRepositoryRuleCommitMessagePatternParameters commitMessagePatternParameters() {
        return patternParameters(GitHubRepositoryRuleCommitMessagePatternParameters::new);
    }

    static GitHubRepositoryRuleCommitAuthorEmailPatternParameters commitAuthorEmailPatternParameters() {
        return patternParameters(GitHubRepositoryRuleCommitAuthorEmailPatternParameters::new);
    }

    static GitHubRepositoryRuleCommitterEmailPatternParameters committerEmailPatternParameters() {
        return patternParameters(GitHubRepositoryRuleCommitterEmailPatternParameters::new);
    }

    static GitHubRepositoryRuleMaxFileSizeParameters maxFileSizeParameters() {
        return new GitHubRepositoryRuleMaxFileSizeParameters(100);
    }

    static GitHubRepositoryRuleMaxFilePathLengthParameters maxFilePathLengthParameters() {
        return new GitHubRepositoryRuleMaxFilePathLengthParameters(255);
    }

    static GitHubRepositoryRuleFilePathRestrictionParameters filePathRestrictionParameters() {
        return new GitHubRepositoryRuleFilePathRestrictionParameters(List.of("docs/"));
    }

    static GitHubRepositoryRuleFileExtensionRestrictionParameters fileExtensionRestrictionParameters() {
        return new GitHubRepositoryRuleFileExtensionRestrictionParameters(List.of(".exe"));
    }

    static GitHubRepositoryRuleRequiredDeploymentsParameters requiredDeploymentsParameters() {
        return new GitHubRepositoryRuleRequiredDeploymentsParameters(List.of("production"));
    }

    static GitHubRepositoryRuleUpdateParameters updateRuleParameters() {
        return new GitHubRepositoryRuleUpdateParameters(true);
    }

    static GitHubRepositoryRuleMergeQueueParameters mergeQueueParameters() {
        return new GitHubRepositoryRuleMergeQueueParameters(
                5,
                GitHubRepositoryRuleMergeQueueGroupingStrategy.ALLGREEN,
                5,
                5,
                GitHubRepositoryRuleMergeQueueMergeMethod.MERGE,
                1,
                0);
    }

    static GitHubRepositoryRuleCodeScanningParameters codeScanningParameters() {
        return new GitHubRepositoryRuleCodeScanningParameters(List.of(new GitHubRepositoryRuleParamsCodeScanningTool(
                GitHubRepositoryRuleCodeScanningAlertsThreshold.NONE,
                GitHubRepositoryRuleCodeScanningSecurityAlertsThreshold.NONE,
                "codeql")));
    }

    static GitHubRepositoryRuleCopilotCodeReviewParameters copilotCodeReviewParameters() {
        return new GitHubRepositoryRuleCopilotCodeReviewParameters(false, true);
    }

    static GitHubRepositoryRulePullRequestParameters pullRequestRuleParameters() {
        return new GitHubRepositoryRulePullRequestParameters(
                List.of(
                        GitHubRepositoryRulePullRequestAllowedMergeMethod.MERGE,
                        GitHubRepositoryRulePullRequestAllowedMergeMethod.SQUASH),
                false,
                new GitHubRepositoryRuleParamsDismissalRestriction(
                        List.of(new GitHubRepositoryRuleParamsActor(1L, GitHubRepositoryRuleParamsActorType.TEAM)),
                        false),
                false,
                false,
                1,
                false,
                List.of(new GitHubRepositoryRuleParamsRequiredReviewerConfiguration(
                        List.of("*.java"),
                        1,
                        new GitHubRepositoryRuleParamsRequiredReviewerConfigurationReviewer(
                                1L, GitHubRepositoryRuleParamsRequiredReviewerConfigurationReviewerType.TEAM))));
    }

    static GitHubRepositoryRuleset repositoryRulesetForVersionState() {
        return new GitHubRepositoryRuleset(
                42L,
                "super cool ruleset",
                GitHubRepositoryRulesetTarget.BRANCH,
                GitHubRulesetSourceType.REPOSITORY,
                "monalisa/my-repo",
                GitHubRepositoryRuleEnforcement.ACTIVE,
                List.of(new GitHubRepositoryRulesetBypassActor(
                        234L, GitHubRepositoryRulesetBypassActorType.TEAM, GitHubRepositoryRulesetBypassMode.ALWAYS)),
                GitHubRepositoryRulesetCurrentUserCanBypass.ALWAYS,
                "MDRS_ruleset42",
                new GitHubRepositoryRulesetLinks(
                        new GitHubRepositoryRulesetLink("https://api.github.com/repos/o/r/rulesets/42"),
                        new GitHubRepositoryRulesetLink("https://github.com/o/r/rules/42")),
                rulesetAnyOfConditions(),
                List.of(new GitHubRepositoryRuleCreation(GitHubRepositoryRuleCreationType.CREATION)),
                Instant.parse("2024-07-26T19:06:43Z"),
                Instant.parse("2024-07-26T19:06:43Z"));
    }

    public static GitHubRepositoryRulesetAnyOfConditions rulesetAnyOfConditions() {
        return new GitHubRepositoryRulesetAnyOfConditions(
                rulesetConditionsRefName(),
                repositoryNameConditions(),
                new GitHubRepositoryRulesetConditionsRepositoryIdTargetRepositoryId(List.of(1L)),
                repositoryPropertyTargetProperty());
    }

    public static GitHubRulesetVersionWithState rulesetVersionWithState() {
        return new GitHubRulesetVersionWithState(
                1L, rulesetVersionActor(), Instant.parse("2024-07-26T19:06:43Z"), repositoryRulesetForVersionState());
    }

    public static GitHubRuleSuiteListItem ruleSuiteListItem() {
        return new GitHubRuleSuiteListItem(
                1L,
                1L,
                "octocat",
                "0000000000000000000000000000000000000000",
                "1111111111111111111111111111111111111111",
                "refs/heads/main",
                1_296_269L,
                "Hello-World",
                Instant.parse("2011-01-26T19:06:43Z"),
                GitHubRuleSuiteResult.PASS,
                GitHubRuleSuiteResult.PASS);
    }

    public static GitHubRulesetVersion rulesetVersion() {
        return new GitHubRulesetVersion(1L, rulesetVersionActor(), Instant.parse("2024-07-26T19:06:43Z"));
    }

    public static GitHubRepositoryRuleWorkflowsDetailed workflowsRuleDetailed() {
        return new GitHubRepositoryRuleWorkflowsDetailed(
                GitHubRepositoryRuleWorkflowsType.WORKFLOWS,
                workflowsRuleParameters(),
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                RULESET_SOURCE_NAME,
                RULESET_ID);
    }

    public static GitHubRepositoryRuleRequiredStatusChecksDetailed requiredStatusChecksDetailed() {
        return new GitHubRepositoryRuleRequiredStatusChecksDetailed(
                GitHubRepositoryRuleRequiredStatusChecksType.REQUIRED_STATUS_CHECKS,
                requiredStatusChecksParameters(),
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                RULESET_SOURCE_NAME,
                RULESET_ID);
    }

    public static GitHubRepositoryRulePullRequestDetailed pullRequestRuleDetailed() {
        return new GitHubRepositoryRulePullRequestDetailed(
                GitHubRepositoryRulePullRequestType.PULL_REQUEST,
                pullRequestRuleParameters(),
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                RULESET_SOURCE_NAME,
                RULESET_ID);
    }

    public static GitHubRepositoryRuleset repositoryRuleset() {
        return new GitHubRepositoryRuleset(
                42L,
                "super cool ruleset",
                GitHubRepositoryRulesetTarget.BRANCH,
                GitHubRulesetSourceType.ORGANIZATION,
                "my-org",
                GitHubRepositoryRuleEnforcement.ACTIVE,
                List.of(new GitHubRepositoryRulesetBypassActor(
                        234L, GitHubRepositoryRulesetBypassActorType.TEAM, GitHubRepositoryRulesetBypassMode.ALWAYS)),
                GitHubRepositoryRulesetCurrentUserCanBypass.ALWAYS,
                "MDRS_ruleset42",
                new GitHubRepositoryRulesetLinks(
                        new GitHubRepositoryRulesetLink("https://api.github.com/repos/o/r/rulesets/42"),
                        new GitHubRepositoryRulesetLink("https://github.com/o/r/rules/42")),
                rulesetAnyOfConditions(),
                List.of(new GitHubRepositoryRuleCreation(GitHubRepositoryRuleCreationType.CREATION)),
                Instant.parse("2024-07-26T19:06:43Z"),
                Instant.parse("2024-07-26T19:06:43Z"));
    }

    static GitHubRepositoryRuleRequiredStatusChecksParameters requiredStatusChecksParameters() {
        return new GitHubRepositoryRuleRequiredStatusChecksParameters(
                false, List.of(new GitHubRepositoryRuleParamsStatusCheckConfiguration("ci/build", 1L)), false);
    }

    static GitHubRepositoryRuleWorkflowsParameters workflowsRuleParameters() {
        return new GitHubRepositoryRuleWorkflowsParameters(
                false,
                List.of(new GitHubRepositoryRuleParamsWorkflowFileReference(
                        ".github/workflows/ci.yml", "refs/heads/main", 1L, "abc123")));
    }

    static GitHubContributor contributorOctocat() {
        final GitHubSimpleUser octocat = UsersCatalogFixtures.octocat();
        return new GitHubContributor(
                octocat.login(),
                octocat.id(),
                octocat.nodeId(),
                octocat.avatarUrl(),
                octocat.gravatarId(),
                octocat.url(),
                octocat.htmlUrl(),
                octocat.followersUrl(),
                octocat.followingUrl(),
                octocat.gistsUrl(),
                octocat.starredUrl(),
                octocat.subscriptionsUrl(),
                octocat.organizationsUrl(),
                octocat.reposUrl(),
                octocat.eventsUrl(),
                octocat.receivedEventsUrl(),
                octocat.type(),
                octocat.siteAdmin(),
                32,
                octocat.email(),
                octocat.name(),
                octocat.userViewType() != null ? octocat.userViewType().wireValue() : null);
    }

    static GitHubGitUser gitUserOctocat() {
        return new GitHubGitUser("The Octocat", "octocat@github.com", Instant.parse("2011-01-26T19:01:12Z"));
    }

    static GitHubUpdateRepositorySecurityAndAnalysis updateRepositorySecurityAndAnalysis() {
        return new GitHubUpdateRepositorySecurityAndAnalysis(
                status(GitHubSecurityAndAnalysisEnablement.ENABLED),
                status(GitHubSecurityAndAnalysisEnablement.ENABLED),
                status(GitHubSecurityAndAnalysisEnablement.DISABLED),
                status(GitHubSecurityAndAnalysisEnablement.ENABLED),
                status(GitHubSecurityAndAnalysisEnablement.DISABLED),
                status(GitHubSecurityAndAnalysisEnablement.DISABLED),
                status(GitHubSecurityAndAnalysisEnablement.ENABLED),
                status(GitHubSecurityAndAnalysisEnablement.DISABLED),
                status(GitHubSecurityAndAnalysisEnablement.DISABLED),
                new GitHubSecretScanningDelegatedBypassOptions(List.of(new GitHubSecretScanningDelegatedBypassReviewer(
                        1L,
                        GitHubSecretScanningBypassReviewerType.TEAM,
                        GitHubSecretScanningDelegatedBypassMode.ALWAYS))));
    }

    static GitHubUpdatePayload updateRepositoryPayload() {
        return new GitHubUpdatePayload(
                "example",
                "Example repository",
                "https://github.com",
                false,
                GitHubRepositoryVisibility.PUBLIC,
                updateRepositorySecurityAndAnalysis(),
                true,
                true,
                true,
                true,
                GitHubPullRequestCreationPolicy.ALL,
                false,
                "main",
                true,
                true,
                true,
                false,
                false,
                true,
                false,
                GitHubSquashMergeCommitTitle.PR_TITLE,
                GitHubSquashMergeCommitMessage.PR_BODY,
                GitHubMergeCommitTitle.PR_TITLE,
                GitHubMergeCommitMessage.PR_BODY,
                false,
                true,
                false);
    }

    private static <T> T patternParameters(final PatternParametersFactory<T> factory) {
        return factory.create("main-branch", false, GitHubRepositoryRulePatternOperator.STARTS_WITH, "example");
    }

    @FunctionalInterface
    private interface PatternParametersFactory<T> {
        T create(String name, Boolean negate, GitHubRepositoryRulePatternOperator operator, String pattern);
    }

    public static GitHubRepositorySubscription repositorySubscription() {
        return GitHubRepositorySubscriptionBuilder.builder()
                .subscribed(true)
                .ignored(false)
                .reason("subscribed")
                .createdAt(Instant.parse("2012-10-06T21:34:12Z"))
                .url(REPO_API + "/subscription")
                .repositoryUrl(REPO_API)
                .build();
    }

    public static GitHubRepository teamEnvironmentRepository() {
        return teamEnvironmentRepository(GitHubPullRequestCreationPolicy.ALL);
    }

    static GitHubRepository teamEnvironmentRepository(final GitHubPullRequestCreationPolicy pullRequestCreationPolicy) {
        return new GitHubRepository(
                42L,
                "MDEwOlJlcG9zaXRvcnkxMjk2MjY5",
                "Team Environment",
                "octocat/Hello-World",
                OptionalNull.of(mitLicenseWithHtmlUrl()),
                9L,
                repositoryPermissions(),
                UsersCatalogFixtures.octocat(),
                false,
                "https://github.com/octocat/Hello-World",
                "This your first repo!",
                false,
                REPO_API,
                REPO_HTTP + "/{archive_format}{/ref}",
                REPO_HTTP + "/assignees{/user}",
                REPO_HTTP + "/git/blobs{/sha}",
                REPO_HTTP + "/branches{/branch}",
                REPO_HTTP + "/collaborators{/collaborator}",
                REPO_HTTP + "/comments{/number}",
                REPO_HTTP + "/commits{/sha}",
                REPO_HTTP + "/compare/{base}...{head}",
                REPO_HTTP + "/contents/{+path}",
                REPO_HTTP + "/contributors",
                REPO_HTTP + "/deployments",
                REPO_HTTP + "/downloads",
                REPO_HTTP + "/events",
                REPO_HTTP + "/forks",
                REPO_HTTP + "/git/commits{/sha}",
                REPO_HTTP + "/git/refs{/sha}",
                REPO_HTTP + "/git/tags{/sha}",
                "git:github.com/octocat/Hello-World.git",
                REPO_HTTP + "/issues/comments{/number}",
                REPO_HTTP + "/issues/events{/number}",
                REPO_HTTP + "/issues{/number}",
                REPO_HTTP + "/keys{/key_id}",
                REPO_HTTP + "/labels{/name}",
                REPO_HTTP + "/languages",
                REPO_HTTP + "/merges",
                REPO_HTTP + "/milestones{/number}",
                REPO_HTTP + "/notifications{?since,all,participating}",
                REPO_HTTP + "/pulls{/number}",
                REPO_HTTP + "/releases{/id}",
                "git@github.com:octocat/Hello-World.git",
                REPO_HTTP + "/stargazers",
                REPO_HTTP + "/statuses/{sha}",
                REPO_HTTP + "/subscribers",
                REPO_HTTP + "/subscription",
                REPO_HTTP + "/tags",
                REPO_HTTP + "/teams",
                REPO_HTTP + "/git/trees{/sha}",
                "https://github.com/octocat/Hello-World.git",
                OptionalNull.of("https://github.com/octocat/Hello-World-mirror.git"),
                REPO_HTTP + "/hooks",
                "https://svn.github.com/octocat/Hello-World",
                "https://github.com",
                OptionalNull.of("C"),
                9L,
                80L,
                80L,
                108L,
                "master",
                0L,
                false,
                List.of("octocat", "atom", "electron", "api"),
                true,
                true,
                true,
                false,
                false,
                true,
                pullRequestCreationPolicy,
                false,
                false,
                "public",
                Instant.parse("2011-01-26T19:06:43Z"),
                Instant.parse("2011-01-26T19:01:12Z"),
                Instant.parse("2011-01-26T19:14:43Z"),
                true,
                "abc123token",
                true,
                false,
                false,
                true,
                GitHubSquashMergeCommitTitle.PR_TITLE,
                GitHubSquashMergeCommitMessage.PR_BODY,
                GitHubMergeCommitTitle.PR_TITLE,
                GitHubMergeCommitMessage.PR_BODY,
                true,
                true,
                false,
                0L,
                80L,
                "2020-11-02T16:12:33Z",
                false,
                new GitHubCodeSearchIndexStatus(true, "6dcb09b5b57875f334f61aebed695e2e4193db5e"));
    }

    static GitHubRepository pullRequestListStubRepository() {
        return new GitHubRepository(
                42L,
                "MDEwOlJlcG9zaXRvcnkxMjk2MjY5",
                "Team Environment",
                "octocat/Hello-World",
                OptionalNull.ofNull(),
                9L,
                repositoryPermissions(),
                UsersCatalogFixtures.octocatWithoutOptionalFields(),
                false,
                "https://github.com/octocat/Hello-World",
                "This your first repo!",
                false,
                REPO_API,
                REPO_HTTP + "/{archive_format}{/ref}",
                REPO_HTTP + "/assignees{/user}",
                REPO_HTTP + "/git/blobs{/sha}",
                REPO_HTTP + "/branches{/branch}",
                REPO_HTTP + "/collaborators{/collaborator}",
                REPO_HTTP + "/comments{/number}",
                REPO_HTTP + "/commits{/sha}",
                REPO_HTTP + "/compare/{base}...{head}",
                REPO_HTTP + "/contents/{+path}",
                REPO_HTTP + "/contributors",
                REPO_HTTP + "/deployments",
                REPO_HTTP + "/downloads",
                REPO_HTTP + "/events",
                REPO_HTTP + "/forks",
                REPO_HTTP + "/git/commits{/sha}",
                REPO_HTTP + "/git/refs{/sha}",
                REPO_HTTP + "/git/tags{/sha}",
                "git:github.com/octocat/Hello-World.git",
                REPO_HTTP + "/issues/comments{/number}",
                REPO_HTTP + "/issues/events{/number}",
                REPO_HTTP + "/issues{/number}",
                REPO_HTTP + "/keys{/key_id}",
                REPO_HTTP + "/labels{/name}",
                REPO_HTTP + "/languages",
                REPO_HTTP + "/merges",
                REPO_HTTP + "/milestones{/number}",
                REPO_HTTP + "/notifications{?since,all,participating}",
                REPO_HTTP + "/pulls{/number}",
                REPO_HTTP + "/releases{/id}",
                "git@github.com:octocat/Hello-World.git",
                REPO_HTTP + "/stargazers",
                REPO_HTTP + "/statuses/{sha}",
                REPO_HTTP + "/subscribers",
                REPO_HTTP + "/subscription",
                REPO_HTTP + "/tags",
                REPO_HTTP + "/teams",
                REPO_HTTP + "/git/trees{/sha}",
                "https://github.com/octocat/Hello-World.git",
                OptionalNull.ofNull(),
                REPO_HTTP + "/hooks",
                "https://svn.github.com/octocat/Hello-World",
                "https://github.com",
                OptionalNull.ofNull(),
                9L,
                80L,
                80L,
                108L,
                "master",
                0L,
                null,
                List.of("octocat", "atom", "electron", "api"),
                true,
                true,
                true,
                false,
                null,
                null,
                null,
                false,
                false,
                null,
                Instant.parse("2011-01-26T19:06:43Z"),
                Instant.parse("2011-01-26T19:01:12Z"),
                Instant.parse("2011-01-26T19:14:43Z"),
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
                null,
                null,
                0L,
                80L,
                null,
                null,
                null);
    }

    public static GitHubCodeOfConduct contributorCovenantWithBody() {
        return new GitHubCodeOfConduct(
                "contributor_covenant",
                "Contributor Covenant",
                "https://api.github.com/codes_of_conduct/contributor_covenant",
                "# Contributor Covenant Code of Conduct",
                "https://github.com/contact/code-of-conduct");
    }

    static GitHubCodeOfConductSimple contributorCovenantCodeOfConduct() {
        return new GitHubCodeOfConductSimple(
                "https://api.github.com/codes_of_conduct/contributor_covenant",
                "contributor_covenant",
                "Contributor Covenant",
                "https://github.com/contact/code-of-conduct");
    }

    static GitHubSimpleUser octocatOrganization() {
        return GitHubSimpleUserBuilder.builder(UsersCatalogFixtures.octocat())
                .type("Organization")
                .build();
    }

    public static GitHubFullRepository fullRepository() {
        final GitHubRepository nestedRepository = teamEnvironmentRepository();
        final GitHubRepository forkNestedRepository = new GitHubRepository(
                nestedRepository.id(),
                nestedRepository.nodeId(),
                nestedRepository.name(),
                nestedRepository.fullName(),
                nestedRepository.license(),
                nestedRepository.forks(),
                nestedRepository.permissions(),
                nestedRepository.owner(),
                nestedRepository.isPrivate(),
                nestedRepository.htmlUrl(),
                nestedRepository.description(),
                true,
                nestedRepository.url(),
                nestedRepository.archiveUrl(),
                nestedRepository.assigneesUrl(),
                nestedRepository.blobsUrl(),
                nestedRepository.branchesUrl(),
                nestedRepository.collaboratorsUrl(),
                nestedRepository.commentsUrl(),
                nestedRepository.commitsUrl(),
                nestedRepository.compareUrl(),
                nestedRepository.contentsUrl(),
                nestedRepository.contributorsUrl(),
                nestedRepository.deploymentsUrl(),
                nestedRepository.downloadsUrl(),
                nestedRepository.eventsUrl(),
                nestedRepository.forksUrl(),
                nestedRepository.gitCommitsUrl(),
                nestedRepository.gitRefsUrl(),
                nestedRepository.gitTagsUrl(),
                nestedRepository.gitUrl(),
                nestedRepository.issueCommentUrl(),
                nestedRepository.issueEventsUrl(),
                nestedRepository.issuesUrl(),
                nestedRepository.keysUrl(),
                nestedRepository.labelsUrl(),
                nestedRepository.languagesUrl(),
                nestedRepository.mergesUrl(),
                nestedRepository.milestonesUrl(),
                nestedRepository.notificationsUrl(),
                nestedRepository.pullsUrl(),
                nestedRepository.releasesUrl(),
                nestedRepository.sshUrl(),
                nestedRepository.stargazersUrl(),
                nestedRepository.statusesUrl(),
                nestedRepository.subscribersUrl(),
                nestedRepository.subscriptionUrl(),
                nestedRepository.tagsUrl(),
                nestedRepository.teamsUrl(),
                nestedRepository.treesUrl(),
                nestedRepository.cloneUrl(),
                nestedRepository.mirrorUrl(),
                nestedRepository.hooksUrl(),
                nestedRepository.svnUrl(),
                nestedRepository.homepage(),
                nestedRepository.language(),
                nestedRepository.forksCount(),
                nestedRepository.stargazersCount(),
                nestedRepository.watchersCount(),
                nestedRepository.size(),
                nestedRepository.defaultBranch(),
                nestedRepository.openIssuesCount(),
                nestedRepository.isTemplate(),
                nestedRepository.topics(),
                nestedRepository.hasIssues(),
                nestedRepository.hasProjects(),
                nestedRepository.hasWiki(),
                nestedRepository.hasPages(),
                nestedRepository.hasDiscussions(),
                nestedRepository.hasPullRequests(),
                nestedRepository.pullRequestCreationPolicy(),
                nestedRepository.archived(),
                nestedRepository.disabled(),
                nestedRepository.visibility(),
                nestedRepository.pushedAt(),
                nestedRepository.createdAt(),
                nestedRepository.updatedAt(),
                nestedRepository.allowRebaseMerge(),
                nestedRepository.tempCloneToken(),
                nestedRepository.allowSquashMerge(),
                nestedRepository.allowAutoMerge(),
                nestedRepository.deleteBranchOnMerge(),
                nestedRepository.allowUpdateBranch(),
                nestedRepository.squashMergeCommitTitle(),
                nestedRepository.squashMergeCommitMessage(),
                nestedRepository.mergeCommitTitle(),
                nestedRepository.mergeCommitMessage(),
                nestedRepository.allowMergeCommit(),
                nestedRepository.allowForking(),
                nestedRepository.webCommitSignoffRequired(),
                nestedRepository.openIssues(),
                nestedRepository.watchers(),
                nestedRepository.starredAt(),
                nestedRepository.anonymousAccessEnabled(),
                nestedRepository.codeSearchIndexStatus());
        return new GitHubFullRepository(
                1_296_269L,
                "MDEwOlJlcG9zaXRvcnkxMjk2MjY5",
                "Hello-World",
                "octocat/Hello-World",
                UsersCatalogFixtures.octocat(),
                false,
                "https://github.com/octocat/Hello-World",
                "This your first repo!",
                false,
                REPO_API,
                REPO_HTTP + "/{archive_format}{/ref}",
                REPO_HTTP + "/assignees{/user}",
                REPO_HTTP + "/git/blobs{/sha}",
                REPO_HTTP + "/branches{/branch}",
                REPO_HTTP + "/collaborators{/collaborator}",
                REPO_HTTP + "/comments{/number}",
                REPO_HTTP + "/commits{/sha}",
                REPO_HTTP + "/compare/{base}...{head}",
                REPO_HTTP + "/contents/{+path}",
                REPO_HTTP + "/contributors",
                REPO_HTTP + "/deployments",
                REPO_HTTP + "/downloads",
                REPO_HTTP + "/events",
                REPO_HTTP + "/forks",
                REPO_HTTP + "/git/commits{/sha}",
                REPO_HTTP + "/git/refs{/sha}",
                REPO_HTTP + "/git/tags{/sha}",
                "git:github.com/octocat/Hello-World.git",
                REPO_HTTP + "/issues/comments{/number}",
                REPO_HTTP + "/issues/events{/number}",
                REPO_HTTP + "/issues{/number}",
                REPO_HTTP + "/keys{/key_id}",
                REPO_HTTP + "/labels{/name}",
                REPO_HTTP + "/languages",
                REPO_HTTP + "/merges",
                REPO_HTTP + "/milestones{/number}",
                REPO_HTTP + "/notifications{?since,all,participating}",
                REPO_HTTP + "/pulls{/number}",
                REPO_HTTP + "/releases{/id}",
                "git@github.com:octocat/Hello-World.git",
                REPO_HTTP + "/stargazers",
                REPO_HTTP + "/statuses/{sha}",
                REPO_HTTP + "/subscribers",
                REPO_HTTP + "/subscription",
                REPO_HTTP + "/tags",
                REPO_HTTP + "/teams",
                REPO_HTTP + "/git/trees{/sha}",
                "https://github.com/octocat/Hello-World.git",
                OptionalNull.of("https://github.com/octocat/Hello-World-mirror.git"),
                REPO_HTTP + "/hooks",
                "https://svn.github.com/octocat/Hello-World",
                "https://github.com",
                OptionalNull.of("C"),
                9L,
                80L,
                80L,
                108L,
                "master",
                0L,
                false,
                List.of("octocat", "atom", "electron", "api"),
                true,
                true,
                true,
                false,
                true,
                true,
                GitHubPullRequestCreationPolicy.COLLABORATORS_ONLY,
                false,
                false,
                "public",
                Instant.parse("2011-01-26T19:06:43Z"),
                Instant.parse("2011-01-26T19:01:12Z"),
                Instant.parse("2011-01-26T19:14:43Z"),
                repositoryPermissions(),
                true,
                nestedRepository,
                "abc123token",
                true,
                false,
                false,
                true,
                true,
                GitHubSquashMergeCommitTitle.PR_TITLE,
                GitHubSquashMergeCommitMessage.PR_BODY,
                GitHubMergeCommitTitle.MERGE_MESSAGE,
                GitHubMergeCommitMessage.PR_TITLE,
                true,
                false,
                42L,
                0L,
                OptionalNull.of(mitLicenseWithHtmlUrl()),
                octocatOrganization(),
                forkNestedRepository,
                forkNestedRepository,
                9L,
                "master",
                0L,
                80L,
                false,
                contributorCovenantCodeOfConduct(),
                securityAndAnalysis(),
                Map.of("team", "core"));
    }

    static GitHubPullRequestBranchRef pullRequestBranchRef(
            final String label, final String ref, final String sha, final GitHubPullRequestCreationPolicy policy) {
        return new GitHubPullRequestBranchRef(
                label, ref, teamEnvironmentRepository(policy), sha, OptionalNull.of(UsersCatalogFixtures.octocat()));
    }

    public static GitHubMinimalRepository helloWorldMinimalRepository() {
        return GitHubMinimalRepositoryBuilder.builder()
                .id(1_296_269L)
                .nodeId("MDEwOlJlcG9zaXRvcnkxMjk2MjY5")
                .name("Hello-World")
                .fullName("octocat/Hello-World")
                .owner(UsersCatalogFixtures.octocat())
                .isPrivate(false)
                .htmlUrl("https://github.com/octocat/Hello-World")
                .description("This your first repo!")
                .fork(false)
                .url(REPO_API)
                .archiveUrl(REPO_HTTP + "/{archive_format}{/ref}")
                .assigneesUrl(REPO_HTTP + "/assignees{/user}")
                .blobsUrl(REPO_HTTP + "/git/blobs{/sha}")
                .branchesUrl(REPO_HTTP + "/branches{/branch}")
                .collaboratorsUrl(REPO_HTTP + "/collaborators{/collaborator}")
                .commentsUrl(REPO_HTTP + "/comments{/number}")
                .commitsUrl(REPO_HTTP + "/commits{/sha}")
                .compareUrl(REPO_HTTP + "/compare/{base}...{head}")
                .contentsUrl(REPO_HTTP + "/contents/{+path}")
                .contributorsUrl(REPO_HTTP + "/contributors")
                .deploymentsUrl(REPO_HTTP + "/deployments")
                .downloadsUrl(REPO_HTTP + "/downloads")
                .eventsUrl(REPO_HTTP + "/events")
                .forksUrl(REPO_HTTP + "/forks")
                .gitCommitsUrl(REPO_HTTP + "/git/commits{/sha}")
                .gitRefsUrl(REPO_HTTP + "/git/refs{/sha}")
                .gitTagsUrl(REPO_HTTP + "/git/tags{/sha}")
                .gitUrl("git:github.com/octocat/Hello-World.git")
                .issueCommentUrl(REPO_HTTP + "/issues/comments{/number}")
                .issueEventsUrl(REPO_HTTP + "/issues/events{/number}")
                .issuesUrl(REPO_HTTP + "/issues{/number}")
                .keysUrl(REPO_HTTP + "/keys{/key_id}")
                .labelsUrl(REPO_HTTP + "/labels{/name}")
                .languagesUrl(REPO_HTTP + "/languages")
                .mergesUrl(REPO_HTTP + "/merges")
                .milestonesUrl(REPO_HTTP + "/milestones{/number}")
                .notificationsUrl(REPO_HTTP + "/notifications{?since,all,participating}")
                .pullsUrl(REPO_HTTP + "/pulls{/number}")
                .releasesUrl(REPO_HTTP + "/releases{/id}")
                .sshUrl("git@github.com:octocat/Hello-World.git")
                .stargazersUrl(REPO_HTTP + "/stargazers")
                .statusesUrl(REPO_HTTP + "/statuses/{sha}")
                .subscribersUrl(REPO_HTTP + "/subscribers")
                .subscriptionUrl(REPO_HTTP + "/subscription")
                .tagsUrl(REPO_HTTP + "/tags")
                .teamsUrl(REPO_HTTP + "/teams")
                .treesUrl(REPO_HTTP + "/git/trees{/sha}")
                .cloneUrl("https://github.com/octocat/Hello-World.git")
                .mirrorUrl("https://github.com/octocat/Hello-World-mirror.git")
                .hooksUrl(REPO_HTTP + "/hooks")
                .svnUrl("https://svn.github.com/octocat/Hello-World")
                .homepage("https://github.com")
                .language("C")
                .forksCount(9L)
                .stargazersCount(80L)
                .watchersCount(80L)
                .size(108L)
                .defaultBranch("master")
                .openIssuesCount(0L)
                .isTemplate(false)
                .topics(List.of("octocat", "atom", "electron", "api"))
                .hasIssues(true)
                .hasProjects(true)
                .hasWiki(true)
                .hasPages(false)
                .hasDiscussions(false)
                .hasPullRequests(true)
                .pullRequestCreationPolicy(GitHubPullRequestCreationPolicy.COLLABORATORS_ONLY)
                .archived(false)
                .disabled(false)
                .visibility("public")
                .pushedAt(Instant.parse("2011-01-26T19:06:43Z"))
                .createdAt(Instant.parse("2011-01-26T19:01:12Z"))
                .updatedAt(Instant.parse("2011-01-26T19:14:43Z"))
                .permissions(repositoryPermissions())
                .tempCloneToken("abc123token")
                .deleteBranchOnMerge(false)
                .subscribersCount(80L)
                .networkCount(0L)
                .roleName("admin")
                .codeOfConduct(contributorCovenantWithBody())
                .license(minimalRepositoryLicense())
                .forks(9L)
                .openIssues(0L)
                .watchers(80L)
                .allowForking(true)
                .webCommitSignoffRequired(false)
                .securityAndAnalysis(securityAndAnalysis())
                .customProperties(Map.of("team", "core"))
                .build();
    }

    public static GitHubPullRequestSimple pullRequestSimple() {
        final GitHubSimpleUser octocat = UsersCatalogFixtures.octocat();
        final String sha = "6dcb09b5b57875f334f61aebed695e2e4193db5e";
        return new GitHubPullRequestSimple(
                "https://api.github.com/repos/octocat/Hello-World/pulls/1347",
                1L,
                "MDExOlB1bGxSZXF1ZXN0MQ==",
                "https://github.com/octocat/Hello-World/pull/1347",
                "https://github.com/octocat/Hello-World/pull/1347.diff",
                "https://github.com/octocat/Hello-World/pull/1347.patch",
                "https://api.github.com/repos/octocat/Hello-World/issues/1347",
                "https://api.github.com/repos/octocat/Hello-World/pulls/1347/commits",
                "https://api.github.com/repos/octocat/Hello-World/pulls/1347/comments",
                "https://api.github.com/repos/octocat/Hello-World/pulls/comments{/number}",
                "https://api.github.com/repos/octocat/Hello-World/issues/1347/comments",
                "https://api.github.com/repos/octocat/Hello-World/statuses/" + sha,
                1347,
                "open",
                false,
                "new-feature",
                OptionalNull.of(octocat),
                "Please pull these awesome changes",
                List.of(pullRequestLabel()),
                OptionalNull.of(pullRequestMilestone()),
                "resolved",
                Instant.parse("2011-01-26T19:01:12Z"),
                Instant.parse("2011-01-26T19:01:12Z"),
                OptionalNull.of(Instant.parse("2011-01-26T19:14:43Z")),
                OptionalNull.of(Instant.parse("2011-01-26T19:14:43Z")),
                List.of(octocat),
                List.of(octocat),
                List.of(requestedTeam()),
                pullRequestBranchRef("octocat:new-topic", "new-topic", sha, GitHubPullRequestCreationPolicy.ALL),
                pullRequestBranchRef("octocat:master", "master", sha, GitHubPullRequestCreationPolicy.ALL),
                pullRequestSimpleLinks(sha),
                GitHubAuthorAssociation.OWNER,
                OptionalNull.of(pullRequestAutoMerge()),
                pullRequestStack(sha),
                false);
    }

    public static GitHubPullRequestSimple pullRequestSimpleSparse() {
        final String sha = "6dcb09b5b57875f334f61aebed695e2e4193db5e";
        return new GitHubPullRequestSimple(
                "https://api.github.com/repos/octocat/Hello-World/pulls/1347",
                1L,
                "MDExOlB1bGxSZXF1ZXN0MQ==",
                "https://github.com/octocat/Hello-World/pull/1347",
                "https://github.com/octocat/Hello-World/pull/1347.diff",
                "https://github.com/octocat/Hello-World/pull/1347.patch",
                "https://api.github.com/repos/octocat/Hello-World/issues/1347",
                "https://api.github.com/repos/octocat/Hello-World/pulls/1347/commits",
                "https://api.github.com/repos/octocat/Hello-World/pulls/1347/comments",
                "https://api.github.com/repos/octocat/Hello-World/pulls/comments{/number}",
                "https://api.github.com/repos/octocat/Hello-World/issues/1347/comments",
                "https://api.github.com/repos/octocat/Hello-World/statuses/" + sha,
                1347,
                "open",
                false,
                "new-feature",
                OptionalNull.ofNull(),
                "Please pull these awesome changes",
                List.of(),
                OptionalNull.ofNull(),
                null,
                Instant.parse("2011-01-26T19:01:12Z"),
                Instant.parse("2011-01-26T19:01:12Z"),
                OptionalNull.ofNull(),
                OptionalNull.ofNull(),
                null,
                null,
                null,
                new GitHubPullRequestBranchRef(
                        "octocat:new-topic", "new-topic", pullRequestListStubRepository(), sha, OptionalNull.ofNull()),
                new GitHubPullRequestBranchRef(
                        "octocat:master", "master", pullRequestListStubRepository(), sha, OptionalNull.ofNull()),
                pullRequestSimpleLinks(sha),
                GitHubAuthorAssociation.OWNER,
                OptionalNull.ofNull(),
                null,
                null);
    }

    static GitHubPullRequestLabel pullRequestLabel() {
        return new GitHubPullRequestLabel(
                208_045_946L,
                "MDU6TGFiZWwyMDgwNDU5NDY=",
                "https://api.github.com/repos/octocat/Hello-World/labels/bug",
                "bug",
                "Something isn't working",
                "f29513",
                true,
                Instant.parse("2023-01-01T12:00:00Z"),
                UsersCatalogFixtures.octocat());
    }

    static GitHubMilestone pullRequestMilestone() {
        return new GitHubMilestone(
                "https://api.github.com/repos/octocat/Hello-World/milestones/1",
                "https://github.com/octocat/Hello-World/milestone/1",
                "https://api.github.com/repos/octocat/Hello-World/milestones/1/labels",
                1_002_604L,
                "MDQ6TWlsZXN0b25lMTAwMjYwNA==",
                1,
                GitHubMilestoneState.OPEN,
                "v1.0",
                "Tracking milestone for version 1.0",
                UsersCatalogFixtures.octocat(),
                4,
                8,
                Instant.parse("2011-01-26T19:01:12Z"),
                Instant.parse("2011-01-26T19:14:43Z"),
                Instant.parse("2011-01-26T19:14:43Z"),
                Instant.parse("2012-07-09T00:00:00Z"));
    }

    static GitHubAutoMerge pullRequestAutoMerge() {
        return new GitHubAutoMerge(
                UsersCatalogFixtures.octocat(),
                GitHubAutoMergeMethod.SQUASH,
                "Amazing pull request",
                "Merge pull request");
    }

    static GitHubPullRequestStack pullRequestStack(final String sha) {
        return new GitHubPullRequestStack(new GitHubPullRequestStackBase("master", sha), 2, 1, 10L, 100);
    }

    public static GitHubTeam requestedTeam() {
        return new GitHubTeam(
                1L,
                "MDQ6VGVhbTE=",
                "Justice League",
                "justice-league",
                "A great team.",
                "closed",
                "notifications_enabled",
                "admin",
                new GitHubRepositoryPermissions(true, false, true, false, true),
                "https://api.github.com/organizations/1/team/1",
                "https://github.com/orgs/rails/teams/core",
                "https://api.github.com/organizations/1/team/1/members{/member}",
                "https://api.github.com/organizations/1/team/1/repos",
                GitHubTeamOwnershipType.ORGANIZATION,
                GitHubTeamAccessSource.DIRECT,
                1L,
                2L,
                new GitHubTeamSimple(
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
                        2L));
    }

    private static GitHubPullRequestSimpleLinks pullRequestSimpleLinks(final String sha) {
        return new GitHubPullRequestSimpleLinks(
                new GitHubLink("https://api.github.com/repos/octocat/Hello-World/issues/1347/comments"),
                new GitHubLink("https://api.github.com/repos/octocat/Hello-World/pulls/1347/commits"),
                new GitHubLink("https://api.github.com/repos/octocat/Hello-World/statuses/" + sha),
                new GitHubLink("https://github.com/octocat/Hello-World/pull/1347"),
                new GitHubLink("https://api.github.com/repos/octocat/Hello-World/issues/1347"),
                new GitHubLink("https://api.github.com/repos/octocat/Hello-World/pulls/1347/comments"),
                new GitHubLink("https://api.github.com/repos/octocat/Hello-World/pulls/comments{/number}"),
                new GitHubLink("https://api.github.com/repos/octocat/Hello-World/pulls/1347"));
    }

    static GitHubCreateInOrgPayload createOrgRepositoryPayload() {
        return new GitHubCreateInOrgPayload(
                "example",
                "Example repository",
                "https://github.com",
                false,
                GitHubRepositoryVisibility.PUBLIC,
                true,
                true,
                true,
                true,
                false,
                1L,
                true,
                "Java",
                "mit",
                true,
                true,
                true,
                true,
                false,
                false,
                GitHubSquashMergeCommitTitle.PR_TITLE,
                GitHubSquashMergeCommitMessage.PR_BODY,
                GitHubMergeCommitTitle.PR_TITLE,
                GitHubMergeCommitMessage.PR_BODY,
                Map.of("team", "core"));
    }

    static GitHubCommitTree commitTree() {
        return new GitHubCommitTree(
                "827efc6d56897b048c772eb4087f854f46256132",
                "https://api.github.com/repos/octocat/Hello-World/tree/827efc6d56897b048c772eb4087f854f46256132");
    }

    static GitHubCommitDetail commitDetail() {
        return new GitHubCommitDetail(
                "https://api.github.com/repos/octocat/Hello-World/commits/6dcb09b5b57875f334f61aebed695e2e4193db5e",
                OptionalNull.of(gitUserOctocat()),
                OptionalNull.of(gitUserOctocat()),
                "Fix all the bugs",
                0,
                commitTree(),
                sampleVerification());
    }

    static GitHubCommitParent commitParent() {
        return new GitHubCommitParent(
                "7638417db6d59f3c431d3e1f261cc637155684cd",
                "https://api.github.com/repos/octocat/Hello-World/commits/7638417db6d59f3c431d3e1f261cc637155684cd",
                "https://github.com/octocat/Hello-World/commit/7638417db6d59f3c431d3e1f261cc637155684cd");
    }

    static GitHubCommitStats commitStats() {
        return new GitHubCommitStats(10, 2, 12);
    }

    public static GitHubDiffEntry sampleDiffEntry() {
        return new GitHubDiffEntry(
                "bbcd538c8e72b8c175046e27cc8f907076331401",
                "file1.txt",
                GitHubDiffEntryStatus.ADDED,
                103,
                21,
                124,
                "https://github.com/octocat/Hello-World/blob/6dcb09b5b57875f334f61aebed695e2e4193db5e/file1.txt",
                "https://github.com/octocat/Hello-World/raw/6dcb09b5b57875f334f61aebed695e2e4193db5e/file1.txt",
                "https://api.github.com/repos/octocat/Hello-World/contents/file1.txt?ref=6dcb09b5b57875f334f61aebed695e2e4193db5e",
                "@@ -0,0 +1 @@\n+example\n",
                "file0.txt");
    }

    public static GitHubWebhookConfig webhookConfig() {
        return new GitHubWebhookConfig(
                "https://example.com/webhook", "json", "webhook-secret", new GitHubWebhookConfigInsecureSsl("0", null));
    }

    static GitHubHook sampleHook() {
        return new GitHubHook(
                "Repository",
                42L,
                "web",
                true,
                List.of("push", "pull_request"),
                webhookConfig(),
                Instant.parse("2011-09-06T20:39:23Z"),
                Instant.parse("2011-09-06T17:26:27Z"),
                "https://api.github.com/repos/octocat/Hello-World/hooks/1",
                "https://api.github.com/repos/octocat/Hello-World/hooks/1/test",
                "https://api.github.com/repos/octocat/Hello-World/hooks/1/pings",
                "https://api.github.com/repos/octocat/Hello-World/hooks/1/deliveries",
                new GitHubHookResponse(200, "OK", "OK"));
    }

    static GitHubContentSubmodule contentSubmodule() {
        final String git =
                "https://api.github.com/repos/octocat/Hello-World/git/blobs/95b966ae1c166bd92f8ae7d1c313e738c731dfc3";
        final String html = "https://github.com/octocat/Hello-World/blob/master/Hello-World";
        final String download = "https://raw.githubusercontent.com/octocat/Hello-World/master/Hello-World";
        final String self = "https://api.github.com/repos/octocat/Hello-World/contents/Hello-World";
        return new GitHubContentSubmodule(
                GitHubContentSubmoduleType.SUBMODULE,
                "git://github.com/octocat/Hello-World.git",
                0,
                "Hello-World",
                "Hello-World",
                "95b966ae1c166bd92f8ae7d1c313e738c731dfc3",
                self,
                OptionalNull.of(git),
                OptionalNull.of(html),
                OptionalNull.of(download),
                new GitHubContentLinks(OptionalNull.of(git), OptionalNull.of(html), self));
    }

    public static GitHubCommit commit() {
        return new GitHubCommit(
                "https://api.github.com/repos/octocat/Hello-World/commits/6dcb09b5b57875f334f61aebed695e2e4193db5e",
                "6dcb09b5b57875f334f61aebed695e2e4193db5e",
                "MDY6Q29tbWl0NmRjYjA5YjViNTc4NzVmMzM0ZjYxYWViZWQ2OTVlMmU0MTkzZGI1ZQ==",
                "https://github.com/octocat/Hello-World/commit/6dcb09b5b57875f334f61aebed695e2e4193db5e",
                "https://api.github.com/repos/octocat/Hello-World/commits/6dcb09b5b57875f334f61aebed695e2e4193db5e/comments",
                commitDetail(),
                UsersCatalogFixtures.octocat(),
                UsersCatalogFixtures.octocat(),
                List.of(commitParent()),
                commitStats(),
                List.of(sampleDiffEntry()));
    }

    static GitHubListForAuthenticatedUserFilter listAuthenticatedUserReposFilter() {
        return new GitHubListForAuthenticatedUserFilter(
                GitHubAuthenticatedUserReposVisibility.ALL,
                "owner,collaborator",
                GitHubAuthenticatedUserReposType.ALL,
                GitHubReposSort.UPDATED,
                GitHubReposSortDirection.DESC,
                Instant.parse("2011-01-26T19:01:12Z"),
                Instant.parse("2011-01-26T19:14:43Z"));
    }

    static GitHubListPublicFilter listPublicFilter() {
        return new GitHubListPublicFilter(GitHubPublicReposVisibility.ALL);
    }

    static GitHubListForOrgFilter listOrgReposFilter() {
        return new GitHubListForOrgFilter(
                GitHubOrgReposListType.ALL, GitHubReposSort.UPDATED, GitHubReposSortDirection.DESC);
    }

    static GitHubListForUserFilter listUserReposFilter() {
        return new GitHubListForUserFilter(
                GitHubUserReposListType.ALL, GitHubReposSort.UPDATED, GitHubReposSortDirection.DESC);
    }

    static GitHubListActivitiesFilter listActivitiesFilter() {
        return new GitHubListActivitiesFilter(
                GitHubReposSortDirection.DESC,
                "main",
                "octocat",
                GitHubRepositoryActivityTimePeriod.WEEK,
                GitHubRepositoryActivityType.PUSH);
    }

    static GitHubGetRepoRuleSuitesFilter listRuleSuitesFilter() {
        return new GitHubGetRepoRuleSuitesFilter(
                "main",
                GitHubRuleSuiteTimePeriod.WEEK,
                "octocat",
                GitHubRuleSuiteListResult.ALL,
                GitHubRuleSuiteEvaluateStatus.ALL);
    }

    static GitHubGetRepoRuleSuiteFilter getRepoRuleSuiteFilter() {
        return new GitHubGetRepoRuleSuiteFilter(true);
    }

    static GitHubGetRepoRulesetsFilter listRepoRulesetsFilter() {
        return new GitHubGetRepoRulesetsFilter(true, "branch,tag,push");
    }

    static GitHubCreateForAuthenticatedUserPayload createAuthenticatedUserRepositoryPayload() {
        return new GitHubCreateForAuthenticatedUserPayload(
                "example",
                "Example repository",
                "https://github.com",
                false,
                true,
                true,
                true,
                false,
                1L,
                true,
                "Java",
                "mit",
                true,
                true,
                true,
                false,
                false,
                GitHubSquashMergeCommitTitle.PR_TITLE,
                GitHubSquashMergeCommitMessage.PR_BODY,
                GitHubMergeCommitTitle.PR_TITLE,
                GitHubMergeCommitMessage.PR_BODY,
                true,
                false);
    }

    public static GitHubRepositoryRulesetConditionsRefName rulesetConditionsRefName() {
        return new GitHubRepositoryRulesetConditionsRefName(List.of("refs/heads/main"), List.of("refs/heads/dev*"));
    }

    public static GitHubRepositoryRulesetConditions rulesetConditions() {
        return new GitHubRepositoryRulesetConditions(rulesetConditionsRefName());
    }

    public static GitHubRepositoryRulesetLinks rulesetLinks() {
        return new GitHubRepositoryRulesetLinks(
                new GitHubRepositoryRulesetLink("https://api.github.com/repos/o/r/rulesets/1"),
                new GitHubRepositoryRulesetLink("https://github.com/o/r/rules/1"));
    }

    public static GitHubRepositoryRulesetBypassActor rulesetBypassActor() {
        return new GitHubRepositoryRulesetBypassActor(
                1L, GitHubRepositoryRulesetBypassActorType.INTEGRATION, GitHubRepositoryRulesetBypassMode.ALWAYS);
    }

    public static GitHubRepositoryRulesetConditionsRepositoryNameTargetRepositoryName repositoryNameConditions() {
        return new GitHubRepositoryRulesetConditionsRepositoryNameTargetRepositoryName(
                List.of("Hello-World"), List.of("secret-*"), false);
    }

    static GitHubRepositoryRulesetConditionsRepositoryPropertySpec repositoryPropertySpec() {
        return new GitHubRepositoryRulesetConditionsRepositoryPropertySpec(
                "team", List.of("core"), GitHubRepositoryRulesetConditionsRepositoryPropertySource.CUSTOM);
    }

    public static GitHubRepositoryRuleParamsWorkflowFileReference workflowFileReference() {
        return new GitHubRepositoryRuleParamsWorkflowFileReference(
                ".github/workflows/ci.yml", "refs/heads/main", 1L, "abc123");
    }

    public static GitHubRepositoryRuleParamsStatusCheckConfiguration statusCheckConfiguration() {
        return new GitHubRepositoryRuleParamsStatusCheckConfiguration("ci/build", 1L);
    }

    static GitHubRulesetVersionActor rulesetVersionActor() {
        return new GitHubRulesetVersionActor(1L, "User");
    }

    static GitHubRuleSuiteEvaluation ruleSuiteEvaluation() {
        return new GitHubRuleSuiteEvaluation(
                new GitHubRuleSuiteRuleSource("Repository", 42L, "super cool ruleset"),
                GitHubRuleSuiteRuleEvaluationEnforcement.ACTIVE,
                GitHubRuleSuiteRuleEvaluationResult.PASS,
                "creation",
                null,
                OptionalNull.of("Rule passed successfully"));
    }

    static GitHubRuleSuiteMetadata ruleSuiteMetadataPullRequest() {
        return new GitHubRuleSuiteMetadata(ruleSuitePullRequestMetadata(), null);
    }

    static GitHubRuleSuiteMetadata ruleSuiteMetadataChecks() {
        return new GitHubRuleSuiteMetadata(null, List.of(ruleSuiteCheck()));
    }

    static GitHubRuleSuitePullRequestMetadata ruleSuitePullRequestMetadata() {
        return new GitHubRuleSuitePullRequestMetadata(
                1296L, 7, ruleSuitePullRequestUser(), List.of(ruleSuiteReview()), ruleSuiteProofOfPresence());
    }

    static GitHubRuleSuitePullRequestUser ruleSuitePullRequestUser() {
        return new GitHubRuleSuitePullRequestUser(1L, "octocat", "User");
    }

    static GitHubRuleSuiteReview ruleSuiteReview() {
        return new GitHubRuleSuiteReview(80L, ruleSuiteReviewUser(), "APPROVED");
    }

    static GitHubRuleSuiteReviewUser ruleSuiteReviewUser() {
        return new GitHubRuleSuiteReviewUser(2L, "hubot", "Bot");
    }

    static GitHubRuleSuiteProofOfPresence ruleSuiteProofOfPresence() {
        return new GitHubRuleSuiteProofOfPresence(
                "verified",
                "verified",
                "0000000000000000000000000000000000000000",
                "1111111111111111111111111111111111111111",
                "approve",
                "approve",
                "2011-01-26T19:06:43Z",
                ruleSuiteProofOfPresenceVerifier());
    }

    static GitHubRuleSuiteProofOfPresenceVerifier ruleSuiteProofOfPresenceVerifier() {
        return new GitHubRuleSuiteProofOfPresenceVerifier(1L, "octocat", "User");
    }

    static GitHubRuleSuiteCheck ruleSuiteCheck() {
        return new GitHubRuleSuiteCheck(2L, "ci/build", "success", "check_run", ruleSuiteCheckApp());
    }

    static GitHubRuleSuiteCheckApp ruleSuiteCheckApp() {
        return new GitHubRuleSuiteCheckApp(15_368L, "github-actions", "GitHub Actions");
    }

    public static GitHubRuleSuite ruleSuite() {
        return ruleSuite(ruleSuiteEvaluation());
    }

    public static GitHubRuleSuite ruleSuiteWithNullDetails() {
        return ruleSuite(new GitHubRuleSuiteEvaluation(
                new GitHubRuleSuiteRuleSource("Repository", 42L, "super cool ruleset"),
                GitHubRuleSuiteRuleEvaluationEnforcement.ACTIVE,
                GitHubRuleSuiteRuleEvaluationResult.PASS,
                "creation",
                null,
                OptionalNull.ofNull()));
    }

    private static GitHubRuleSuite ruleSuite(final GitHubRuleSuiteEvaluation evaluation) {
        return new GitHubRuleSuite(
                1L,
                1L,
                "octocat",
                "0000000000000000000000000000000000000000",
                "1111111111111111111111111111111111111111",
                "refs/heads/main",
                1_296_269L,
                "Hello-World",
                Instant.parse("2011-01-26T19:06:43Z"),
                GitHubRuleSuiteResult.PASS,
                GitHubRuleSuiteResult.PASS,
                List.of(evaluation));
    }

    public static GitHubHookDelivery sampleHookDelivery() {
        return new GitHubHookDelivery(
                42L,
                "58474f00-b361-11eb-836d-0e4f3503ccbe",
                Instant.parse("2021-05-12T20:33:44Z"),
                false,
                0.03,
                "failed to connect",
                502,
                "issues",
                "opened",
                123L,
                456L,
                Instant.parse("2021-05-12T20:33:44Z"),
                "https://www.example.com",
                new GitHubHookDeliveryMessage(Map.of(CONTENT_TYPE, "application/json"), Map.of("action", "opened")),
                new GitHubHookDeliveryResponseMessage(
                        OptionalNull.of(Map.of(CONTENT_TYPE, "text/plain")), OptionalNull.of("OK")));
    }

    static GitHubCodeownersError codeownersError() {
        return new GitHubCodeownersError(
                7,
                3,
                "* user",
                "Invalid owner",
                OptionalNull.of("* @octocat"),
                "Invalid owner on line 7:\n\n  * user\n    ^",
                ".github/CODEOWNERS");
    }

    static GitHubRepositoryActivity repositoryActivity() {
        return new GitHubRepositoryActivity(
                1_296_269L,
                "MDEwOlJlcG9zaXRvcnkxMjk2MjY5",
                "6dcb09b5b57875f334f61aebed695e2e4193db5e",
                "827efc6d56897b048c772eb4087f854f46256132",
                "refs/heads/main",
                Instant.parse("2011-01-26T19:06:43Z"),
                GitHubRepositoryActivityType.FORCE_PUSH,
                OptionalNull.of(UsersCatalogFixtures.octocat()));
    }

    static GitHubAutolink sampleAutolink() {
        return new GitHubAutolink(
                3, "TICKET-", "https://example.com/TICKET?query=<num>", true, Instant.parse("2011-01-26T19:01:12Z"));
    }

    static GitHubFileContentsAuthor fileContentsAuthor() {
        return new GitHubFileContentsAuthor("Monalisa Octocat", "octocat@github.com", "2011-01-26T19:01:12Z");
    }

    static GitHubDeleteFileAuthor deleteFileAuthor() {
        return new GitHubDeleteFileAuthor("Monalisa Octocat", "octocat@github.com");
    }

    static GitHubCreateAttestationPayload.CreateAttestationBundle attestationBundlePayload() {
        return new GitHubCreateAttestationPayload.CreateAttestationBundle(
                "application/vnd.dev.sigstore.bundle.v0.3+json",
                Map.of("certificate", Map.of("chain", List.of("cert-data"))),
                Map.of("payload", "eyJhbGci", "payloadType", "application/vnd.in-toto+json"));
    }

    static GitHubCreateAttestationPayload createAttestationPayload() {
        return new GitHubCreateAttestationPayload(attestationBundlePayload());
    }

    static GitHubCreateRepoRulesetPayload createRepoRulesetPayload() {
        return new GitHubCreateRepoRulesetPayload(
                "example",
                GitHubRepositoryRulesetPayloadTarget.BRANCH,
                GitHubRepositoryRuleEnforcement.ACTIVE,
                List.of(rulesetBypassActor()),
                rulesetConditions(),
                List.of(new GitHubRepositoryRuleCreation(GitHubRepositoryRuleCreationType.CREATION)));
    }

    static GitHubUpdateRepoRulesetPayload updateRepoRulesetPayload() {
        return new GitHubUpdateRepoRulesetPayload(
                "example",
                GitHubRepositoryRulesetPayloadTarget.BRANCH,
                GitHubRepositoryRuleEnforcement.ACTIVE,
                List.of(rulesetBypassActor()),
                rulesetConditions(),
                List.of(new GitHubRepositoryRuleCreation(GitHubRepositoryRuleCreationType.CREATION)));
    }

    static GitHubCreateWebhookPayload createWebhookPayload() {
        return new GitHubCreateWebhookPayload("web", webhookConfig(), List.of("push", "pull_request"), true);
    }

    public static GitHubUpdateWebhookConfigForRepoPayload updateWebhookConfigForRepoPayload() {
        return GitHubUpdateWebhookConfigForRepoPayloadBuilder.builder()
                .url(URI.create("https://example.com/webhook"))
                .build();
    }

    static GitHubCreateOrUpdateFileContentsPayload createOrUpdateFileContentsPayload() {
        return new GitHubCreateOrUpdateFileContentsPayload(
                "Update README",
                "Zm9v",
                "95b966ae1c166bd92f8ae7d1c313e738c731dfc3",
                "main",
                fileContentsAuthor(),
                fileContentsAuthor());
    }

    static GitHubCreateForkPayload createForkPayload() {
        return new GitHubCreateForkPayload("octocat", "Hello-World-Fork", true);
    }

    static GitHubTransferPayload transferRepositoryPayload() {
        return new GitHubTransferPayload("octocat", "Hello-World-Transfer", List.of(1L));
    }

    static GitHubDeleteFilePayload deleteFilePayload() {
        return new GitHubDeleteFilePayload(
                "Remove file",
                "95b966ae1c166bd92f8ae7d1c313e738c731dfc3",
                "main",
                deleteFileAuthor(),
                deleteFileAuthor());
    }

    static GitHubCreateDispatchEventPayload createDispatchEventPayload() {
        return new GitHubCreateDispatchEventPayload("deploy", Map.of("environment", "production"));
    }

    static GitHubCreateAutolinkPayload createAutolinkPayload() {
        return new GitHubCreateAutolinkPayload("TICKET-", "https://example.com/TICKET?query=<num>", true);
    }

    static GitHubCreateUsingTemplatePayload createRepositoryFromTemplatePayload() {
        return new GitHubCreateUsingTemplatePayload("octocat", "Hello-World-Template", "From template", true, false);
    }

    public static GitHubRepositoryRuleCopilotCodeReviewDetailed copilotCodeReviewDetailed() {
        return new GitHubRepositoryRuleCopilotCodeReviewDetailed(
                GitHubRepositoryRuleCopilotCodeReviewType.COPILOT_CODE_REVIEW,
                copilotCodeReviewParameters(),
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                RULESET_SOURCE_NAME,
                RULESET_ID);
    }

    static GitHubRepositoryRuleCodeQualityParameters codeQualityParameters() {
        return new GitHubRepositoryRuleCodeQualityParameters(GitHubRepositoryRuleCodeQualitySeverity.ERRORS);
    }

    public static GitHubRepositoryRuleCodeQualityDetailed codeQualityDetailed() {
        return new GitHubRepositoryRuleCodeQualityDetailed(
                GitHubRepositoryRuleCodeQualityType.CODE_QUALITY,
                codeQualityParameters(),
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                RULESET_SOURCE_NAME,
                RULESET_ID);
    }

    static GitHubRepositoryRuleCodeCoverageParameters codeCoverageParameters() {
        return new GitHubRepositoryRuleCodeCoverageParameters(5.0, 80.0);
    }

    public static GitHubRepositoryRuleCodeCoverageDetailed codeCoverageDetailed() {
        return new GitHubRepositoryRuleCodeCoverageDetailed(
                GitHubRepositoryRuleCodeCoverageType.CODE_COVERAGE,
                codeCoverageParameters(),
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                RULESET_SOURCE_NAME,
                RULESET_ID);
    }

    public static GitHubRepositoryRuleCommitMessagePatternDetailed commitMessagePatternDetailed() {
        return new GitHubRepositoryRuleCommitMessagePatternDetailed(
                GitHubRepositoryRuleCommitMessagePatternType.COMMIT_MESSAGE_PATTERN,
                commitMessagePatternParameters(),
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                RULESET_SOURCE_NAME,
                RULESET_ID);
    }

    public static GitHubRepositoryRuleCommitAuthorEmailPatternDetailed commitAuthorEmailPatternDetailed() {
        return new GitHubRepositoryRuleCommitAuthorEmailPatternDetailed(
                GitHubRepositoryRuleCommitAuthorEmailPatternType.COMMIT_AUTHOR_EMAIL_PATTERN,
                commitAuthorEmailPatternParameters(),
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                RULESET_SOURCE_NAME,
                RULESET_ID);
    }

    public static GitHubRepositoryRuleCommitterEmailPatternDetailed committerEmailPatternDetailed() {
        return new GitHubRepositoryRuleCommitterEmailPatternDetailed(
                GitHubRepositoryRuleCommitterEmailPatternType.COMMITTER_EMAIL_PATTERN,
                committerEmailPatternParameters(),
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                RULESET_SOURCE_NAME,
                RULESET_ID);
    }

    public static GitHubRepositoryRuleTagNamePatternDetailed tagNamePatternDetailed() {
        return new GitHubRepositoryRuleTagNamePatternDetailed(
                GitHubRepositoryRuleTagNamePatternType.TAG_NAME_PATTERN,
                tagNamePatternParameters(),
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                RULESET_SOURCE_NAME,
                RULESET_ID);
    }

    static GitHubRepositoryRulesetConditionsRepositoryNameTarget repositoryNameTarget() {
        return new GitHubRepositoryRulesetConditionsRepositoryNameTarget(repositoryNameConditions());
    }

    static GitHubRepositoryRulesetConditionsRepositoryPropertyTarget repositoryPropertyTarget() {
        return new GitHubRepositoryRulesetConditionsRepositoryPropertyTarget(repositoryPropertyTargetProperty());
    }

    public static GitHubRepositoryRulesetConditionsRepositoryPropertyTargetRepositoryProperty
            repositoryPropertyTargetProperty() {
        return new GitHubRepositoryRulesetConditionsRepositoryPropertyTargetRepositoryProperty(
                List.of(repositoryPropertySpec()),
                List.of(new GitHubRepositoryRulesetConditionsRepositoryPropertySpec(
                        "lifecycle",
                        List.of("deprecated"),
                        GitHubRepositoryRulesetConditionsRepositoryPropertySource.CUSTOM)));
    }

    static GitHubCodeownersErrors codeownersErrors() {
        return new GitHubCodeownersErrors(List.of(codeownersError()));
    }

    static GitHubAutoMerge autoMergeCatalog() {
        return new GitHubAutoMerge(UsersCatalogFixtures.octocat(), GitHubAutoMergeMethod.MERGE, "example", "example");
    }

    static GitHubDiffEntry catalogDiffEntry() {
        return new GitHubDiffEntry(
                "example",
                "example",
                GitHubDiffEntryStatus.ADDED,
                1,
                1,
                1,
                "https://example.com",
                "https://example.com",
                "https://example.com",
                "@@ -0,0 +1 @@\n+example\n",
                "example-old.txt");
    }

    static GitHubCustomPropertiesForReposCreateOrUpdateRepositoryValuesPayload
            customPropertiesForReposCreateOrUpdateRepositoryValuesPayload() {
        return GitHubCustomPropertiesForReposCreateOrUpdateRepositoryValuesPayloadBuilder.builder()
                .properties(List.of(GitHubCustomPropertyValue.of("environment", "production")))
                .build();
    }

    static GitHubContentLinks contentLinks() {
        return new GitHubContentLinks(null, null, "self");
    }

    static GitHubReplaceAllTopicsPayload replaceAllTopicsPayload() {
        return GitHubReplaceAllTopicsPayloadBuilder.builder()
                .names(List.of("api"))
                .build();
    }

    static GitHubUpdateWebhookPayload updateWebhookPayload() {
        return GitHubUpdateWebhookPayloadBuilder.builder().active(true).build();
    }

    public static GitHubHookDeliveryItem hookDeliveryItem() {
        return new GitHubHookDeliveryItem(
                42L,
                "58474f00-b361-11eb-836d-0e4f3503ccbe",
                Instant.parse("2021-05-12T20:33:44Z"),
                false,
                0.03,
                "failed to connect",
                502,
                "issues",
                "opened",
                123L,
                456L,
                Instant.parse("2021-05-12T20:33:44Z"));
    }

    private static GitHubSecurityAndAnalysisStatus status(final GitHubSecurityAndAnalysisEnablement enablement) {
        return new GitHubSecurityAndAnalysisStatus(enablement);
    }

    public static GitHubSimpleRepository helloWorldSimpleRepository() {
        return new GitHubSimpleRepository(
                1_296_269L,
                "MDEwOlJlcG9zaXRvcnkxMjk2MjY5",
                "Hello-World",
                "octocat/Hello-World",
                UsersCatalogFixtures.octocat(),
                false,
                "https://github.com/octocat/Hello-World",
                "This your first repo!",
                false,
                "https://api.github.com/repos/octocat/Hello-World",
                "http://api.github.com/repos/octocat/Hello-World/{archive_format}{/ref}",
                "http://api.github.com/repos/octocat/Hello-World/assignees{/user}",
                "http://api.github.com/repos/octocat/Hello-World/git/blobs{/sha}",
                "http://api.github.com/repos/octocat/Hello-World/branches{/branch}",
                "http://api.github.com/repos/octocat/Hello-World/collaborators{/collaborator}",
                "http://api.github.com/repos/octocat/Hello-World/comments{/number}",
                "http://api.github.com/repos/octocat/Hello-World/commits{/sha}",
                "http://api.github.com/repos/octocat/Hello-World/compare/{base}...{head}",
                "http://api.github.com/repos/octocat/Hello-World/contents/{+path}",
                "http://api.github.com/repos/octocat/Hello-World/contributors",
                "http://api.github.com/repos/octocat/Hello-World/deployments",
                "http://api.github.com/repos/octocat/Hello-World/downloads",
                "http://api.github.com/repos/octocat/Hello-World/events",
                "http://api.github.com/repos/octocat/Hello-World/forks",
                "http://api.github.com/repos/octocat/Hello-World/git/commits{/sha}",
                "http://api.github.com/repos/octocat/Hello-World/git/refs{/sha}",
                "http://api.github.com/repos/octocat/Hello-World/git/tags{/sha}",
                "http://api.github.com/repos/octocat/Hello-World/issues/comments{/number}",
                "http://api.github.com/repos/octocat/Hello-World/issues/events{/number}",
                "http://api.github.com/repos/octocat/Hello-World/issues{/number}",
                "http://api.github.com/repos/octocat/Hello-World/keys{/key_id}",
                "http://api.github.com/repos/octocat/Hello-World/labels{/name}",
                "http://api.github.com/repos/octocat/Hello-World/languages",
                "http://api.github.com/repos/octocat/Hello-World/merges",
                "http://api.github.com/repos/octocat/Hello-World/milestones{/number}",
                "http://api.github.com/repos/octocat/Hello-World/notifications{?since,all,participating}",
                "http://api.github.com/repos/octocat/Hello-World/pulls{/number}",
                "http://api.github.com/repos/octocat/Hello-World/releases{/id}",
                "http://api.github.com/repos/octocat/Hello-World/stargazers",
                "http://api.github.com/repos/octocat/Hello-World/statuses/{sha}",
                "http://api.github.com/repos/octocat/Hello-World/subscribers",
                "http://api.github.com/repos/octocat/Hello-World/subscription",
                "http://api.github.com/repos/octocat/Hello-World/tags",
                "http://api.github.com/repos/octocat/Hello-World/teams",
                "http://api.github.com/repos/octocat/Hello-World/git/trees{/sha}",
                "http://api.github.com/repos/octocat/Hello-World/hooks");
    }

    static GitHubCreatedAttestation createdAttestation() {
        return new GitHubCreatedAttestation(2L);
    }

    static GitHubAutomatedSecurityFixesStatus automatedSecurityFixesStatus() {
        return new GitHubAutomatedSecurityFixesStatus(true, false);
    }

    static GitHubContentDirectory contentDirectory() {
        return new GitHubContentDirectory(List.of(new GitHubContentDirectoryEntry(
                GitHubContentDirectoryEntryType.FILE,
                5362,
                "README.md",
                "README.md",
                "IyBIZWxsby1Xb3JsZAo=",
                "95b966ae1c166bd92f8ae7d1c313e738c731dfc3",
                "https://api.github.com/repos/octocat/Hello-World/contents/README.md",
                "https://api.github.com/repos/octocat/Hello-World/git/blobs/95b966ae1c166bd92f8ae7d1c313e738c731dfc3",
                "https://github.com/octocat/Hello-World/blob/master/README.md",
                "https://raw.githubusercontent.com/octocat/Hello-World/master/README.md",
                new GitHubContentLinks(
                        OptionalNull.of(
                                "https://api.github.com/repos/octocat/Hello-World/git/blobs/95b966ae1c166bd92f8ae7d1c313e738c731dfc3"),
                        OptionalNull.of("https://github.com/octocat/Hello-World/blob/master/README.md"),
                        "https://api.github.com/repos/octocat/Hello-World/contents/README.md"))));
    }

    static GitHubContentFile contentFile() {
        return new GitHubContentFile(
                GitHubContentFileType.FILE,
                "base64",
                5362,
                "README.md",
                "README.md",
                "SGVsbG8gV29ybGQ=",
                "95b966ae1c166bd92f8ae7d1c313e738c731dfc3",
                "https://api.github.com/repos/octocat/Hello-World/contents/README.md",
                "https://api.github.com/repos/octocat/Hello-World/git/blobs/95b966ae1c166bd92f8ae7d1c313e738c731dfc3",
                "https://github.com/octocat/Hello-World/blob/master/README.md",
                "https://raw.githubusercontent.com/octocat/Hello-World/master/README.md",
                new GitHubContentLinks(
                        OptionalNull.of(
                                "https://api.github.com/repos/octocat/Hello-World/git/blobs/95b966ae1c166bd92f8ae7d1c313e738c731dfc3"),
                        OptionalNull.of("https://github.com/octocat/Hello-World/blob/master/README.md"),
                        "https://api.github.com/repos/octocat/Hello-World/contents/README.md"),
                "actual/actual.md",
                "git://example.com/defunkt/dotjs.git");
    }

    static GitHubFileCommit fileCommit() {
        return new GitHubFileCommit(
                new GitHubFileCommitContent(
                        "README.md",
                        "README.md",
                        "95b966ae1c166bd92f8ae7d1c313e738c731dfc3",
                        5362,
                        "https://api.github.com/repos/octocat/Hello-World/contents/README.md",
                        "https://github.com/octocat/Hello-World/blob/master/README.md",
                        "https://api.github.com/repos/octocat/Hello-World/git/blobs/95b966ae1c166bd92f8ae7d1c313e738c731dfc3",
                        "https://raw.githubusercontent.com/octocat/Hello-World/master/README.md",
                        "file",
                        new GitHubFileCommitContentLinks(
                                "https://api.github.com/repos/octocat/Hello-World/contents/README.md",
                                "https://api.github.com/repos/octocat/Hello-World/git/blobs/95b966ae1c166bd92f8ae7d1c313e738c731dfc3",
                                "https://github.com/octocat/Hello-World/blob/master/README.md")),
                new GitHubFileCommitResult(
                        "6dcb09b5b57875f334f61aebed695e2e4193db5e",
                        "MDY6Q29tbWl0NmRjYjA5YjViNTc4NzVmMzM0ZjYxYWViZWQ2OTVlMmU0MTkzZGI1ZQ==",
                        "https://api.github.com/repos/octocat/Hello-World/commits/6dcb09b5b57875f334f61aebed695e2e4193db5e",
                        "https://github.com/octocat/Hello-World/commit/6dcb09b5b57875f334f61aebed695e2e4193db5e",
                        new GitHubFileCommitPerson("2011-04-14T16:00:49Z", "Monalisa Octocat", "octocat@github.com"),
                        new GitHubFileCommitPerson("2011-04-14T16:00:49Z", "Monalisa Octocat", "octocat@github.com"),
                        "my commit message",
                        new GitHubFileCommitTree(
                                "https://api.github.com/repos/octocat/Hello-World/git/trees/827efc6d56897b048c772eb4087f854f46256132",
                                "827efc6d56897b048c772eb4087f854f46256132"),
                        List.of(new GitHubFileCommitParent(
                                "https://api.github.com/repos/octocat/Hello-World/commits/7638417db6d59f3c431d3e1f261cc637155684cd",
                                "https://github.com/octocat/Hello-World/commit/7638417db6d59f3c431d3e1f261cc637155684cd",
                                "7638417db6d59f3c431d3e1f261cc637155684cd")),
                        sampleVerification()));
    }

    static GitHubImmutableReleasesStatus immutableReleasesStatus() {
        return new GitHubImmutableReleasesStatus(true, false);
    }

    static GitHubRepositoryLanguages repositoryLanguages() {
        return new GitHubRepositoryLanguages(Map.ofEntries(Map.entry("Ruby", 2_849), Map.entry("Java", 120_937)));
    }

    static GitHubRepositoryTopics repositoryTopics() {
        return new GitHubRepositoryTopics(List.of("octocat", "atom", "electron", "api"));
    }

    static GitHubRepositoryTag repositoryTag() {
        return new GitHubRepositoryTag(
                "v0.1",
                new GitHubRepositoryTagCommit(
                        "6dcb09b5b57875f334f61aebed695e2e4193db5e",
                        "https://api.github.com/repos/octocat/Hello-World/commits/6dcb09b5b57875f334f61aebed695e2e4193db5e"),
                "https://github.com/octocat/Hello-World/zipball/v0.1",
                "https://github.com/octocat/Hello-World/tarball/v0.1",
                "MDQ6UmVmMTA=");
    }

    static GitHubRepositoryHashAlgorithmResponse repositoryHashAlgorithmResponse() {
        return new GitHubRepositoryHashAlgorithmResponse(GitHubRepositoryHashAlgorithm.SHA1);
    }

    static GitHubPrivateVulnerabilityReportingStatus privateVulnerabilityReportingEnabled() {
        return new GitHubPrivateVulnerabilityReportingStatus(true);
    }

    public static GitHubRepositoryRuleCreationDetailed creationDetailed() {
        return new GitHubRepositoryRuleCreationDetailed(
                GitHubRepositoryRuleCreationType.CREATION,
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                "my-org/my-repo",
                42L);
    }

    static List<GitHubCustomPropertyValue> repositoryCustomPropertyValues() {
        return List.of(
                new GitHubCustomPropertyValue("environment", List.of("production")),
                new GitHubCustomPropertyValue("team", List.of("core", "platform")));
    }

    public static GitHubMergedUpstream mergedUpstream() {
        return new GitHubMergedUpstream(
                "Successfully merged default branch with upstream", GitHubMergedUpstreamMergeType.MERGE, "main");
    }

    static GitHubIssueType issueTypeFromStub() {
        return new GitHubIssueType(1, "IT_kwDOA", "Bug", "An unexpected problem or behavior", null, null, null, null);
    }

    static GitHubAttestation repositoryAttestation() {
        return new GitHubAttestation(
                1_296_269L,
                "https://api.github.com/repos/octocat/Hello-World/attestations/sha256:abcd",
                "github-actions");
    }

    public static GitHubCommit commitFromStub() {
        return new GitHubCommit(
                "https://api.github.com/repos/octocat/Hello-World/commits/6dcb09b5b57875f334f61aebed695e2e4193db5e",
                "6dcb09b5b57875f334f61aebed695e2e4193db5e",
                "MDY6Q29tbWl0NmRjYjA5YjViNTc4NzVmMzM0ZjYxYWViZWQ2OTVlMmU0MTkzZGI1ZQ==",
                "https://github.com/octocat/Hello-World/commit/6dcb09b5b57875f334f61aebed695e2e4193db5e",
                "https://api.github.com/repos/octocat/Hello-World/commits/6dcb09b5b57875f334f61aebed695e2e4193db5e/comments",
                new GitHubCommitDetail(
                        "https://api.github.com/repos/octocat/Hello-World/commits/6dcb09b5b57875f334f61aebed695e2e4193db5e",
                        OptionalNull.ofNull(),
                        OptionalNull.ofNull(),
                        "Fix all the bugs",
                        0,
                        commitTree(),
                        null),
                null,
                null,
                List.of(new GitHubCommitParent(
                        "7638417db6d59f3c431d3e1f261cc637155684cd",
                        "https://api.github.com/repos/octocat/Hello-World/commits/7638417db6d59f3c431d3e1f261cc637155684cd",
                        null)),
                null,
                null);
    }

    public static GitHubRepositoryRuleCreation repositoryRuleCreation() {
        return new GitHubRepositoryRuleCreation(GitHubRepositoryRuleCreationType.CREATION);
    }

    public static GitHubRepositoryRuleUpdate repositoryRuleUpdate() {
        return new GitHubRepositoryRuleUpdate(GitHubRepositoryRuleUpdateType.UPDATE, updateRuleParameters());
    }

    public static GitHubRepositoryRuleDeletion repositoryRuleDeletion() {
        return new GitHubRepositoryRuleDeletion(GitHubRepositoryRuleDeletionType.DELETION);
    }

    public static GitHubRepositoryRuleRequiredLinearHistory repositoryRuleRequiredLinearHistory() {
        return new GitHubRepositoryRuleRequiredLinearHistory(
                GitHubRepositoryRuleRequiredLinearHistoryType.REQUIRED_LINEAR_HISTORY);
    }

    public static GitHubRepositoryRuleMergeQueue repositoryRuleMergeQueue() {
        return new GitHubRepositoryRuleMergeQueue(
                GitHubRepositoryRuleMergeQueueType.MERGE_QUEUE, mergeQueueParameters());
    }

    public static GitHubRepositoryRuleRequiredDeployments repositoryRuleRequiredDeployments() {
        return new GitHubRepositoryRuleRequiredDeployments(
                GitHubRepositoryRuleRequiredDeploymentsType.REQUIRED_DEPLOYMENTS, requiredDeploymentsParameters());
    }

    public static GitHubRepositoryRuleRequiredSignatures repositoryRuleRequiredSignatures() {
        return new GitHubRepositoryRuleRequiredSignatures(
                GitHubRepositoryRuleRequiredSignaturesType.REQUIRED_SIGNATURES);
    }

    public static GitHubRepositoryRulePullRequest repositoryRulePullRequest() {
        return new GitHubRepositoryRulePullRequest(
                GitHubRepositoryRulePullRequestType.PULL_REQUEST, pullRequestRuleParameters());
    }

    public static GitHubRepositoryRuleRequiredStatusChecks repositoryRuleRequiredStatusChecks() {
        return new GitHubRepositoryRuleRequiredStatusChecks(
                GitHubRepositoryRuleRequiredStatusChecksType.REQUIRED_STATUS_CHECKS, requiredStatusChecksParameters());
    }

    public static GitHubRepositoryRuleNonFastForward repositoryRuleNonFastForward() {
        return new GitHubRepositoryRuleNonFastForward(GitHubRepositoryRuleNonFastForwardType.NON_FAST_FORWARD);
    }

    public static GitHubRepositoryRuleCommitMessagePattern repositoryRuleCommitMessagePattern() {
        return new GitHubRepositoryRuleCommitMessagePattern(
                GitHubRepositoryRuleCommitMessagePatternType.COMMIT_MESSAGE_PATTERN, commitMessagePatternParameters());
    }

    public static GitHubRepositoryRuleCommitAuthorEmailPattern repositoryRuleCommitAuthorEmailPattern() {
        return new GitHubRepositoryRuleCommitAuthorEmailPattern(
                GitHubRepositoryRuleCommitAuthorEmailPatternType.COMMIT_AUTHOR_EMAIL_PATTERN,
                commitAuthorEmailPatternParameters());
    }

    public static GitHubRepositoryRuleCommitterEmailPattern repositoryRuleCommitterEmailPattern() {
        return new GitHubRepositoryRuleCommitterEmailPattern(
                GitHubRepositoryRuleCommitterEmailPatternType.COMMITTER_EMAIL_PATTERN,
                committerEmailPatternParameters());
    }

    public static GitHubRepositoryRuleBranchNamePattern repositoryRuleBranchNamePattern() {
        return new GitHubRepositoryRuleBranchNamePattern(
                GitHubRepositoryRuleBranchNamePatternType.BRANCH_NAME_PATTERN, branchNamePatternParameters());
    }

    public static GitHubRepositoryRuleTagNamePattern repositoryRuleTagNamePattern() {
        return new GitHubRepositoryRuleTagNamePattern(
                GitHubRepositoryRuleTagNamePatternType.TAG_NAME_PATTERN, tagNamePatternParameters());
    }

    public static GitHubRepositoryRuleWorkflows repositoryRuleWorkflows() {
        return new GitHubRepositoryRuleWorkflows(
                GitHubRepositoryRuleWorkflowsType.WORKFLOWS, workflowsRuleParameters());
    }

    public static GitHubRepositoryRuleCodeScanning repositoryRuleCodeScanning() {
        return new GitHubRepositoryRuleCodeScanning(
                GitHubRepositoryRuleCodeScanningType.CODE_SCANNING, codeScanningParameters());
    }

    public static GitHubRepositoryRuleCodeQuality repositoryRuleCodeQuality() {
        return new GitHubRepositoryRuleCodeQuality(
                GitHubRepositoryRuleCodeQualityType.CODE_QUALITY, codeQualityParameters());
    }

    public static GitHubRepositoryRuleCodeCoverage repositoryRuleCodeCoverage() {
        return new GitHubRepositoryRuleCodeCoverage(
                GitHubRepositoryRuleCodeCoverageType.CODE_COVERAGE, codeCoverageParameters());
    }

    public static GitHubRepositoryRuleCopilotCodeReview repositoryRuleCopilotCodeReview() {
        return new GitHubRepositoryRuleCopilotCodeReview(
                GitHubRepositoryRuleCopilotCodeReviewType.COPILOT_CODE_REVIEW, copilotCodeReviewParameters());
    }

    public static GitHubRepositoryRuleLicenseComplianceScanning repositoryRuleLicenseComplianceScanning() {
        return new GitHubRepositoryRuleLicenseComplianceScanning(
                GitHubRepositoryRuleLicenseComplianceScanningType.LICENSE_COMPLIANCE_SCANNING);
    }

    public static GitHubRepositoryRuleFilePathRestriction repositoryRuleFilePathRestriction() {
        return new GitHubRepositoryRuleFilePathRestriction(
                GitHubRepositoryRuleFilePathRestrictionType.FILE_PATH_RESTRICTION, filePathRestrictionParameters());
    }

    public static GitHubRepositoryRuleMaxFilePathLength repositoryRuleMaxFilePathLength() {
        return new GitHubRepositoryRuleMaxFilePathLength(
                GitHubRepositoryRuleMaxFilePathLengthType.MAX_FILE_PATH_LENGTH, maxFilePathLengthParameters());
    }

    public static GitHubRepositoryRuleFileExtensionRestriction repositoryRuleFileExtensionRestriction() {
        return new GitHubRepositoryRuleFileExtensionRestriction(
                GitHubRepositoryRuleFileExtensionRestrictionType.FILE_EXTENSION_RESTRICTION,
                fileExtensionRestrictionParameters());
    }

    public static GitHubRepositoryRuleMaxFileSize repositoryRuleMaxFileSize() {
        return new GitHubRepositoryRuleMaxFileSize(
                GitHubRepositoryRuleMaxFileSizeType.MAX_FILE_SIZE, maxFileSizeParameters());
    }

    public static GitHubRepositoryRuleParamsCodeScanningTool repositoryRuleParamsCodeScanningTool() {
        return new GitHubRepositoryRuleParamsCodeScanningTool(
                GitHubRepositoryRuleCodeScanningAlertsThreshold.NONE,
                GitHubRepositoryRuleCodeScanningSecurityAlertsThreshold.NONE,
                "example");
    }

    public static GitHubRepositoryRuleParamsDismissalRestriction repositoryRuleParamsDismissalRestriction() {
        return new GitHubRepositoryRuleParamsDismissalRestriction(
                List.of(new GitHubRepositoryRuleParamsActor(1L, GitHubRepositoryRuleParamsActorType.TEAM)), true);
    }

    public static GitHubRepositoryRuleParamsRequiredReviewerConfiguration
            repositoryRuleParamsRequiredReviewerConfiguration() {
        return new GitHubRepositoryRuleParamsRequiredReviewerConfiguration(
                List.of("example"),
                1,
                new GitHubRepositoryRuleParamsRequiredReviewerConfigurationReviewer(
                        1L, GitHubRepositoryRuleParamsRequiredReviewerConfigurationReviewerType.TEAM));
    }

    public static GitHubRepositoryRuleParamsReviewer repositoryRuleParamsReviewer() {
        return new GitHubRepositoryRuleParamsReviewer(1L, GitHubRepositoryRuleParamsReviewerType.TEAM);
    }

    public static GitHubRepositoryRuleParamsActor repositoryRuleParamsActor() {
        return new GitHubRepositoryRuleParamsActor(1L, GitHubRepositoryRuleParamsActorType.USER);
    }

    public static GitHubRepositoryRuleRulesetInfo repositoryRuleRulesetInfo() {
        return new GitHubRepositoryRuleRulesetInfo(
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY, "my-org/my-repo", 42L);
    }

    public static GitHubRepositoryRuleUpdateDetailed repositoryRuleUpdateDetailed() {
        return new GitHubRepositoryRuleUpdateDetailed(
                GitHubRepositoryRuleUpdateType.UPDATE,
                new GitHubRepositoryRuleUpdateParameters(false),
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                "my-org/my-repo",
                42L);
    }

    public static GitHubRepositoryRuleDeletionDetailed repositoryRuleDeletionDetailed() {
        return new GitHubRepositoryRuleDeletionDetailed(
                GitHubRepositoryRuleDeletionType.DELETION,
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                "my-org/my-repo",
                42L);
    }

    public static GitHubRepositoryRuleRequiredLinearHistoryDetailed repositoryRuleRequiredLinearHistoryDetailed() {
        return new GitHubRepositoryRuleRequiredLinearHistoryDetailed(
                GitHubRepositoryRuleRequiredLinearHistoryType.REQUIRED_LINEAR_HISTORY,
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                "my-org/my-repo",
                42L);
    }

    public static GitHubRepositoryRuleMergeQueueDetailed repositoryRuleMergeQueueDetailed() {
        return new GitHubRepositoryRuleMergeQueueDetailed(
                GitHubRepositoryRuleMergeQueueType.MERGE_QUEUE,
                new GitHubRepositoryRuleMergeQueueParameters(
                        1,
                        GitHubRepositoryRuleMergeQueueGroupingStrategy.ALLGREEN,
                        1,
                        1,
                        GitHubRepositoryRuleMergeQueueMergeMethod.MERGE,
                        1,
                        1),
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                "my-org/my-repo",
                42L);
    }

    public static GitHubRepositoryRuleRequiredDeploymentsDetailed repositoryRuleRequiredDeploymentsDetailed() {
        return new GitHubRepositoryRuleRequiredDeploymentsDetailed(
                GitHubRepositoryRuleRequiredDeploymentsType.REQUIRED_DEPLOYMENTS,
                new GitHubRepositoryRuleRequiredDeploymentsParameters(List.of("example")),
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                "my-org/my-repo",
                42L);
    }

    public static GitHubRepositoryRuleRequiredSignaturesDetailed repositoryRuleRequiredSignaturesDetailed() {
        return new GitHubRepositoryRuleRequiredSignaturesDetailed(
                GitHubRepositoryRuleRequiredSignaturesType.REQUIRED_SIGNATURES,
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                "my-org/my-repo",
                42L);
    }

    public static GitHubRepositoryRuleNonFastForwardDetailed repositoryRuleNonFastForwardDetailed() {
        return new GitHubRepositoryRuleNonFastForwardDetailed(
                GitHubRepositoryRuleNonFastForwardType.NON_FAST_FORWARD,
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                "my-org/my-repo",
                42L);
    }

    public static GitHubRepositoryRuleBranchNamePatternDetailed repositoryRuleBranchNamePatternDetailed() {
        return new GitHubRepositoryRuleBranchNamePatternDetailed(
                GitHubRepositoryRuleBranchNamePatternType.BRANCH_NAME_PATTERN,
                branchNamePatternParameters(),
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                "my-org/my-repo",
                42L);
    }

    public static GitHubRepositoryRuleCodeScanningDetailed repositoryRuleCodeScanningDetailed() {
        return new GitHubRepositoryRuleCodeScanningDetailed(
                GitHubRepositoryRuleCodeScanningType.CODE_SCANNING,
                codeScanningParameters(),
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                "my-org/my-repo",
                42L);
    }

    public static GitHubRepositoryRuleLicenseComplianceScanningDetailed
            repositoryRuleLicenseComplianceScanningDetailed() {
        return new GitHubRepositoryRuleLicenseComplianceScanningDetailed(
                GitHubRepositoryRuleLicenseComplianceScanningType.LICENSE_COMPLIANCE_SCANNING,
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                "my-org/my-repo",
                42L);
    }

    public static GitHubRepositoryRuleFilePathRestrictionDetailed repositoryRuleFilePathRestrictionDetailed() {
        return new GitHubRepositoryRuleFilePathRestrictionDetailed(
                GitHubRepositoryRuleFilePathRestrictionType.FILE_PATH_RESTRICTION,
                new GitHubRepositoryRuleFilePathRestrictionParameters(List.of("example")),
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                "my-org/my-repo",
                42L);
    }

    public static GitHubRepositoryRuleMaxFilePathLengthDetailed repositoryRuleMaxFilePathLengthDetailed() {
        return new GitHubRepositoryRuleMaxFilePathLengthDetailed(
                GitHubRepositoryRuleMaxFilePathLengthType.MAX_FILE_PATH_LENGTH,
                new GitHubRepositoryRuleMaxFilePathLengthParameters(1),
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                "my-org/my-repo",
                42L);
    }

    public static GitHubRepositoryRuleFileExtensionRestrictionDetailed
            repositoryRuleFileExtensionRestrictionDetailed() {
        return new GitHubRepositoryRuleFileExtensionRestrictionDetailed(
                GitHubRepositoryRuleFileExtensionRestrictionType.FILE_EXTENSION_RESTRICTION,
                new GitHubRepositoryRuleFileExtensionRestrictionParameters(List.of("example")),
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                "my-org/my-repo",
                42L);
    }

    public static GitHubRepositoryRuleMaxFileSizeDetailed repositoryRuleMaxFileSizeDetailed() {
        return new GitHubRepositoryRuleMaxFileSizeDetailed(
                GitHubRepositoryRuleMaxFileSizeType.MAX_FILE_SIZE,
                new GitHubRepositoryRuleMaxFileSizeParameters(1),
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                "my-org/my-repo",
                42L);
    }

    public static GitHubListRepoPushBypassRequestsFilter listRepoPushBypassRequestsFilter() {
        return GitHubListRepoPushBypassRequestsFilterBuilder.builder()
                .reviewer("octocat")
                .requester("hubot")
                .timePeriod(GitHubRuleSuiteTimePeriod.WEEK)
                .requestStatus(GitHubBypassRequestStatus.OPEN)
                .build();
    }

    static GitHubListCacheInfoItem listCacheInfoItem() {
        return GitHubListCacheInfoItemBuilder.builder()
                .host("cache-replica-1.example.com")
                .location("us-east")
                .git(listCacheInfoItemGit())
                .build();
    }

    static GitHubListCacheInfoItemGit listCacheInfoItemGit() {
        return GitHubListCacheInfoItemGitBuilder.builder()
                .syncStatus(GitHubListCacheInfoItemGitSyncStatus.IN_SYNC)
                .lastSync(Instant.parse("2026-01-01T00:00:00Z"))
                .build();
    }
}
