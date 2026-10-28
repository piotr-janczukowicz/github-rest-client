package io.github.piotr_janczukowicz.github.api.repositories;

import io.github.piotr_janczukowicz.github.api.organizations.GitHubOrgRulesetConditions;
import java.util.List;

final class RulesCoverageFixtures {

    private static final String SOURCE_NAME = "org/repo";
    private static final long RULESET_ID = 1L;

    private RulesCoverageFixtures() {}

    static GitHubRepositoryRulesetBypassActor coverageRulesetBypassActor() {
        return new GitHubRepositoryRulesetBypassActor(
                1L, GitHubRepositoryRulesetBypassActorType.TEAM, GitHubRepositoryRulesetBypassMode.ALWAYS);
    }

    static GitHubRepositoryRulesetConditionsRefName coverageRefName() {
        return new GitHubRepositoryRulesetConditionsRefName(List.of("refs/heads/main"), List.of());
    }

    static GitHubRepositoryRulesetConditions coverageRulesetConditions() {
        return new GitHubRepositoryRulesetConditions(coverageRefName());
    }

    static GitHubRepositoryRulesetAnyOfConditions coverageRulesetAnyOfConditions() {
        return new GitHubRepositoryRulesetAnyOfConditions(coverageRefName(), null, null, null);
    }

    static GitHubOrgRulesetConditions coverageOrgRulesetConditions() {
        return GitHubOrgRulesetConditions.byRepositoryName(
                new GitHubRepositoryRulesetConditionsRefName(List.of("refs/heads/main"), null),
                new GitHubRepositoryRulesetConditionsRepositoryNameTargetRepositoryName(List.of("repo"), null, true));
    }

    static GitHubRepositoryRuleMergeQueueParameters coverageMergeQueueParameters() {
        return new GitHubRepositoryRuleMergeQueueParameters(
                1,
                GitHubRepositoryRuleMergeQueueGroupingStrategy.ALLGREEN,
                1,
                1,
                GitHubRepositoryRuleMergeQueueMergeMethod.MERGE,
                1,
                1);
    }

    static GitHubRepositoryRuleMergeQueue coverageMergeQueue() {
        return new GitHubRepositoryRuleMergeQueue(
                GitHubRepositoryRuleMergeQueueType.MERGE_QUEUE, coverageMergeQueueParameters());
    }

    static GitHubRepositoryRulePullRequestParameters coveragePullRequestParameters() {
        return new GitHubRepositoryRulePullRequestParameters(null, false, null, false, false, 1, false, null);
    }

    static GitHubRepositoryRulePullRequest coveragePullRequest() {
        return new GitHubRepositoryRulePullRequest(
                GitHubRepositoryRulePullRequestType.PULL_REQUEST,
                new GitHubRepositoryRulePullRequestParameters(
                        null,
                        false,
                        new GitHubRepositoryRuleParamsDismissalRestriction(null, false),
                        false,
                        false,
                        1,
                        false,
                        null));
    }

    static GitHubRepositoryRuleRequiredStatusChecksParameters coverageRequiredStatusChecksParameters() {
        return new GitHubRepositoryRuleRequiredStatusChecksParameters(null, List.of(), false);
    }

    static GitHubRepositoryRuleRequiredStatusChecks coverageRequiredStatusChecks() {
        return new GitHubRepositoryRuleRequiredStatusChecks(
                GitHubRepositoryRuleRequiredStatusChecksType.REQUIRED_STATUS_CHECKS,
                coverageRequiredStatusChecksParameters());
    }

    static GitHubRepositoryRuleCommitMessagePatternParameters coverageCommitMessageParameters() {
        return new GitHubRepositoryRuleCommitMessagePatternParameters(
                null, null, GitHubRepositoryRulePatternOperator.CONTAINS, "fix");
    }

    static GitHubRepositoryRuleCommitMessagePattern coverageCommitMessagePattern() {
        return new GitHubRepositoryRuleCommitMessagePattern(
                GitHubRepositoryRuleCommitMessagePatternType.COMMIT_MESSAGE_PATTERN, coverageCommitMessageParameters());
    }

    static GitHubRepositoryRuleCommitAuthorEmailPatternParameters coverageCommitAuthorEmailParameters() {
        return new GitHubRepositoryRuleCommitAuthorEmailPatternParameters(
                null, null, GitHubRepositoryRulePatternOperator.CONTAINS, "github");
    }

    static GitHubRepositoryRuleCommitAuthorEmailPattern coverageCommitAuthorEmailPattern() {
        return new GitHubRepositoryRuleCommitAuthorEmailPattern(
                GitHubRepositoryRuleCommitAuthorEmailPatternType.COMMIT_AUTHOR_EMAIL_PATTERN,
                coverageCommitAuthorEmailParameters());
    }

    static GitHubRepositoryRuleCommitterEmailPatternParameters coverageCommitterEmailParameters() {
        return new GitHubRepositoryRuleCommitterEmailPatternParameters(
                null, null, GitHubRepositoryRulePatternOperator.CONTAINS, "github");
    }

    static GitHubRepositoryRuleCommitterEmailPattern coverageCommitterEmailPattern() {
        return new GitHubRepositoryRuleCommitterEmailPattern(
                GitHubRepositoryRuleCommitterEmailPatternType.COMMITTER_EMAIL_PATTERN,
                coverageCommitterEmailParameters());
    }

    static GitHubRepositoryRuleBranchNamePatternParameters coverageBranchNameParameters() {
        return new GitHubRepositoryRuleBranchNamePatternParameters(
                null, null, GitHubRepositoryRulePatternOperator.CONTAINS, "main");
    }

    static GitHubRepositoryRuleBranchNamePattern coverageBranchNamePattern() {
        return new GitHubRepositoryRuleBranchNamePattern(
                GitHubRepositoryRuleBranchNamePatternType.BRANCH_NAME_PATTERN, coverageBranchNameParameters());
    }

    static GitHubRepositoryRuleTagNamePatternParameters coverageTagNameParameters() {
        return new GitHubRepositoryRuleTagNamePatternParameters(
                null, null, GitHubRepositoryRulePatternOperator.CONTAINS, "v");
    }

    static GitHubRepositoryRuleTagNamePattern coverageTagNamePattern() {
        return new GitHubRepositoryRuleTagNamePattern(
                GitHubRepositoryRuleTagNamePatternType.TAG_NAME_PATTERN, coverageTagNameParameters());
    }

    static GitHubRepositoryRuleParamsWorkflowFileReference coverageWorkflowFileReference() {
        return new GitHubRepositoryRuleParamsWorkflowFileReference(
                ".github/workflows/ci.yml", "refs/heads/main", 1L, null);
    }

    static GitHubRepositoryRuleWorkflowsParameters coverageWorkflowsParameters() {
        return new GitHubRepositoryRuleWorkflowsParameters(null, List.of(coverageWorkflowFileReference()));
    }

    static GitHubRepositoryRuleWorkflows coverageWorkflows() {
        return new GitHubRepositoryRuleWorkflows(
                GitHubRepositoryRuleWorkflowsType.WORKFLOWS, coverageWorkflowsParameters());
    }

    static GitHubRepositoryRuleParamsCodeScanningTool coverageCodeScanningTool() {
        return new GitHubRepositoryRuleParamsCodeScanningTool(
                GitHubRepositoryRuleCodeScanningAlertsThreshold.ERRORS,
                GitHubRepositoryRuleCodeScanningSecurityAlertsThreshold.CRITICAL,
                "CodeQL");
    }

    static GitHubRepositoryRuleCodeScanningParameters coverageCodeScanningParameters() {
        return new GitHubRepositoryRuleCodeScanningParameters(List.of(coverageCodeScanningTool()));
    }

    static GitHubRepositoryRuleCodeScanning coverageCodeScanning() {
        return new GitHubRepositoryRuleCodeScanning(
                GitHubRepositoryRuleCodeScanningType.CODE_SCANNING, coverageCodeScanningParameters());
    }

    static GitHubRepositoryRuleCopilotCodeReviewParameters coverageCopilotCodeReviewParameters() {
        return new GitHubRepositoryRuleCopilotCodeReviewParameters(null, null);
    }

    static GitHubRepositoryRuleCopilotCodeReview coverageCopilotCodeReview() {
        return new GitHubRepositoryRuleCopilotCodeReview(
                GitHubRepositoryRuleCopilotCodeReviewType.COPILOT_CODE_REVIEW, coverageCopilotCodeReviewParameters());
    }

    static GitHubRepositoryRuleFilePathRestrictionParameters coverageFilePathRestrictionParameters() {
        return new GitHubRepositoryRuleFilePathRestrictionParameters(List.of("secrets/"));
    }

    static GitHubRepositoryRuleFilePathRestriction coverageFilePathRestriction() {
        return new GitHubRepositoryRuleFilePathRestriction(
                GitHubRepositoryRuleFilePathRestrictionType.FILE_PATH_RESTRICTION,
                coverageFilePathRestrictionParameters());
    }

    static GitHubRepositoryRuleMaxFileSizeParameters coverageMaxFileSizeParameters() {
        return new GitHubRepositoryRuleMaxFileSizeParameters(10);
    }

    static GitHubRepositoryRuleMaxFileSize coverageMaxFileSize() {
        return new GitHubRepositoryRuleMaxFileSize(
                GitHubRepositoryRuleMaxFileSizeType.MAX_FILE_SIZE, coverageMaxFileSizeParameters());
    }

    static GitHubRepositoryRuleParamsRequiredReviewerConfigurationReviewer coverageReviewer() {
        return new GitHubRepositoryRuleParamsRequiredReviewerConfigurationReviewer(
                1L, GitHubRepositoryRuleParamsRequiredReviewerConfigurationReviewerType.TEAM);
    }

    static GitHubRepositoryRuleParamsRequiredReviewerConfiguration coverageRequiredReviewerConfiguration() {
        return new GitHubRepositoryRuleParamsRequiredReviewerConfiguration(List.of("*.java"), 0, coverageReviewer());
    }

    static GitHubRepositoryRuleParamsStatusCheckConfiguration coverageStatusCheckConfiguration() {
        return new GitHubRepositoryRuleParamsStatusCheckConfiguration("ci", 1L);
    }

    static GitHubRepositoryRuleParamsActor coverageParamsActor() {
        return new GitHubRepositoryRuleParamsActor(1L, GitHubRepositoryRuleParamsActorType.TEAM);
    }

    static GitHubRepositoryRuleRulesetInfo coverageRulesetInfo() {
        return new GitHubRepositoryRuleRulesetInfo(
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY, SOURCE_NAME, RULESET_ID);
    }

    static GitHubRepositoryRuleCreationDetailed coverageCreationDetailed() {
        return new GitHubRepositoryRuleCreationDetailed(
                GitHubRepositoryRuleCreationType.CREATION,
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                SOURCE_NAME,
                RULESET_ID);
    }

    static GitHubRepositoryRuleUpdateDetailed coverageUpdateDetailed() {
        return new GitHubRepositoryRuleUpdateDetailed(
                GitHubRepositoryRuleUpdateType.UPDATE,
                new GitHubRepositoryRuleUpdateParameters(true),
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                SOURCE_NAME,
                RULESET_ID);
    }

    static GitHubRepositoryRuleDeletionDetailed coverageDeletionDetailed() {
        return new GitHubRepositoryRuleDeletionDetailed(
                GitHubRepositoryRuleDeletionType.DELETION,
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                SOURCE_NAME,
                RULESET_ID);
    }

    static GitHubRepositoryRuleRequiredLinearHistoryDetailed coverageRequiredLinearHistoryDetailed() {
        return new GitHubRepositoryRuleRequiredLinearHistoryDetailed(
                GitHubRepositoryRuleRequiredLinearHistoryType.REQUIRED_LINEAR_HISTORY,
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                SOURCE_NAME,
                RULESET_ID);
    }

    static GitHubRepositoryRuleMergeQueueDetailed coverageMergeQueueDetailed() {
        return new GitHubRepositoryRuleMergeQueueDetailed(
                GitHubRepositoryRuleMergeQueueType.MERGE_QUEUE,
                coverageMergeQueueParameters(),
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                SOURCE_NAME,
                RULESET_ID);
    }

    static GitHubRepositoryRuleRequiredDeploymentsDetailed coverageRequiredDeploymentsDetailed() {
        return new GitHubRepositoryRuleRequiredDeploymentsDetailed(
                GitHubRepositoryRuleRequiredDeploymentsType.REQUIRED_DEPLOYMENTS,
                new GitHubRepositoryRuleRequiredDeploymentsParameters(List.of("production")),
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                SOURCE_NAME,
                RULESET_ID);
    }

    static GitHubRepositoryRuleRequiredSignaturesDetailed coverageRequiredSignaturesDetailed() {
        return new GitHubRepositoryRuleRequiredSignaturesDetailed(
                GitHubRepositoryRuleRequiredSignaturesType.REQUIRED_SIGNATURES,
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                SOURCE_NAME,
                RULESET_ID);
    }

    static GitHubRepositoryRulePullRequestDetailed coveragePullRequestDetailed() {
        return new GitHubRepositoryRulePullRequestDetailed(
                GitHubRepositoryRulePullRequestType.PULL_REQUEST,
                coveragePullRequestParameters(),
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                SOURCE_NAME,
                RULESET_ID);
    }

    static GitHubRepositoryRuleRequiredStatusChecksDetailed coverageRequiredStatusChecksDetailed() {
        return new GitHubRepositoryRuleRequiredStatusChecksDetailed(
                GitHubRepositoryRuleRequiredStatusChecksType.REQUIRED_STATUS_CHECKS,
                coverageRequiredStatusChecksParameters(),
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                SOURCE_NAME,
                RULESET_ID);
    }

    static GitHubRepositoryRuleNonFastForwardDetailed coverageNonFastForwardDetailed() {
        return new GitHubRepositoryRuleNonFastForwardDetailed(
                GitHubRepositoryRuleNonFastForwardType.NON_FAST_FORWARD,
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                SOURCE_NAME,
                RULESET_ID);
    }

    static GitHubRepositoryRuleCommitMessagePatternDetailed coverageCommitMessagePatternDetailed() {
        return new GitHubRepositoryRuleCommitMessagePatternDetailed(
                GitHubRepositoryRuleCommitMessagePatternType.COMMIT_MESSAGE_PATTERN,
                coverageCommitMessageParameters(),
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                SOURCE_NAME,
                RULESET_ID);
    }

    static GitHubRepositoryRuleCommitAuthorEmailPatternDetailed coverageCommitAuthorEmailPatternDetailed() {
        return new GitHubRepositoryRuleCommitAuthorEmailPatternDetailed(
                GitHubRepositoryRuleCommitAuthorEmailPatternType.COMMIT_AUTHOR_EMAIL_PATTERN,
                coverageCommitAuthorEmailParameters(),
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                SOURCE_NAME,
                RULESET_ID);
    }

    static GitHubRepositoryRuleCommitterEmailPatternDetailed coverageCommitterEmailPatternDetailed() {
        return new GitHubRepositoryRuleCommitterEmailPatternDetailed(
                GitHubRepositoryRuleCommitterEmailPatternType.COMMITTER_EMAIL_PATTERN,
                coverageCommitterEmailParameters(),
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                SOURCE_NAME,
                RULESET_ID);
    }

    static GitHubRepositoryRuleBranchNamePatternDetailed coverageBranchNamePatternDetailed() {
        return new GitHubRepositoryRuleBranchNamePatternDetailed(
                GitHubRepositoryRuleBranchNamePatternType.BRANCH_NAME_PATTERN,
                coverageBranchNameParameters(),
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                SOURCE_NAME,
                RULESET_ID);
    }

    static GitHubRepositoryRuleTagNamePatternDetailed coverageTagNamePatternDetailed() {
        return new GitHubRepositoryRuleTagNamePatternDetailed(
                GitHubRepositoryRuleTagNamePatternType.TAG_NAME_PATTERN,
                coverageTagNameParameters(),
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                SOURCE_NAME,
                RULESET_ID);
    }

    static GitHubRepositoryRuleWorkflowsDetailed coverageWorkflowsDetailed() {
        return new GitHubRepositoryRuleWorkflowsDetailed(
                GitHubRepositoryRuleWorkflowsType.WORKFLOWS,
                coverageWorkflowsParameters(),
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                SOURCE_NAME,
                RULESET_ID);
    }

    static GitHubRepositoryRuleCodeScanningDetailed coverageCodeScanningDetailed() {
        return new GitHubRepositoryRuleCodeScanningDetailed(
                GitHubRepositoryRuleCodeScanningType.CODE_SCANNING,
                coverageCodeScanningParameters(),
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                SOURCE_NAME,
                RULESET_ID);
    }

    static GitHubRepositoryRuleCopilotCodeReviewDetailed coverageCopilotCodeReviewDetailed() {
        return new GitHubRepositoryRuleCopilotCodeReviewDetailed(
                GitHubRepositoryRuleCopilotCodeReviewType.COPILOT_CODE_REVIEW,
                coverageCopilotCodeReviewParameters(),
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                SOURCE_NAME,
                RULESET_ID);
    }

    static GitHubRepositoryRuleLicenseComplianceScanningDetailed coverageLicenseComplianceScanningDetailed() {
        return new GitHubRepositoryRuleLicenseComplianceScanningDetailed(
                GitHubRepositoryRuleLicenseComplianceScanningType.LICENSE_COMPLIANCE_SCANNING,
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                SOURCE_NAME,
                RULESET_ID);
    }

    static GitHubRepositoryRuleFilePathRestrictionDetailed coverageFilePathRestrictionDetailed() {
        return new GitHubRepositoryRuleFilePathRestrictionDetailed(
                GitHubRepositoryRuleFilePathRestrictionType.FILE_PATH_RESTRICTION,
                coverageFilePathRestrictionParameters(),
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                SOURCE_NAME,
                RULESET_ID);
    }

    static GitHubRepositoryRuleMaxFilePathLengthDetailed coverageMaxFilePathLengthDetailed() {
        return new GitHubRepositoryRuleMaxFilePathLengthDetailed(
                GitHubRepositoryRuleMaxFilePathLengthType.MAX_FILE_PATH_LENGTH,
                new GitHubRepositoryRuleMaxFilePathLengthParameters(255),
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                SOURCE_NAME,
                RULESET_ID);
    }

    static GitHubRepositoryRuleFileExtensionRestrictionDetailed coverageFileExtensionRestrictionDetailed() {
        return new GitHubRepositoryRuleFileExtensionRestrictionDetailed(
                GitHubRepositoryRuleFileExtensionRestrictionType.FILE_EXTENSION_RESTRICTION,
                new GitHubRepositoryRuleFileExtensionRestrictionParameters(List.of(".exe")),
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                SOURCE_NAME,
                RULESET_ID);
    }

    static GitHubRepositoryRuleMaxFileSizeDetailed coverageMaxFileSizeDetailed() {
        return new GitHubRepositoryRuleMaxFileSizeDetailed(
                GitHubRepositoryRuleMaxFileSizeType.MAX_FILE_SIZE,
                coverageMaxFileSizeParameters(),
                GitHubRepositoryRuleRulesetInfoSourceType.REPOSITORY,
                SOURCE_NAME,
                RULESET_ID);
    }

    static GitHubRepositoryRulesetConditionsRepositoryIdTarget coverageRepositoryIdTarget() {
        return new GitHubRepositoryRulesetConditionsRepositoryIdTarget(
                new GitHubRepositoryRulesetConditionsRepositoryIdTargetRepositoryId(List.of(1L, 2L)));
    }

    static GitHubRepositoryRulesetConditionsRepositoryNameTarget coverageRepositoryNameTarget() {
        return new GitHubRepositoryRulesetConditionsRepositoryNameTarget(
                new GitHubRepositoryRulesetConditionsRepositoryNameTargetRepositoryName(
                        List.of("repo-*"), List.of("repo-test"), true));
    }

    static GitHubRepositoryRulesetConditionsRepositoryPropertySpec coverageRepositoryPropertySpec() {
        return new GitHubRepositoryRulesetConditionsRepositoryPropertySpec(
                "environment", List.of("production"), GitHubRepositoryRulesetConditionsRepositoryPropertySource.CUSTOM);
    }

    static GitHubRepositoryRulesetConditionsRepositoryPropertyTarget coverageRepositoryPropertyTarget() {
        return new GitHubRepositoryRulesetConditionsRepositoryPropertyTarget(
                new GitHubRepositoryRulesetConditionsRepositoryPropertyTargetRepositoryProperty(
                        List.of(coverageRepositoryPropertySpec()), List.of()));
    }
}
