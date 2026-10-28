package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/** Repository rule with ruleset details. */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        visible = true)
@JsonSubTypes({
    @JsonSubTypes.Type(value = GitHubRepositoryRuleCreationDetailed.class, name = "creation"),
    @JsonSubTypes.Type(value = GitHubRepositoryRuleUpdateDetailed.class, name = "update"),
    @JsonSubTypes.Type(value = GitHubRepositoryRuleDeletionDetailed.class, name = "deletion"),
    @JsonSubTypes.Type(
            value = GitHubRepositoryRuleRequiredLinearHistoryDetailed.class,
            name = "required_linear_history"),
    @JsonSubTypes.Type(value = GitHubRepositoryRuleMergeQueueDetailed.class, name = "merge_queue"),
    @JsonSubTypes.Type(value = GitHubRepositoryRuleRequiredDeploymentsDetailed.class, name = "required_deployments"),
    @JsonSubTypes.Type(value = GitHubRepositoryRuleRequiredSignaturesDetailed.class, name = "required_signatures"),
    @JsonSubTypes.Type(value = GitHubRepositoryRulePullRequestDetailed.class, name = "pull_request"),
    @JsonSubTypes.Type(value = GitHubRepositoryRuleRequiredStatusChecksDetailed.class, name = "required_status_checks"),
    @JsonSubTypes.Type(value = GitHubRepositoryRuleNonFastForwardDetailed.class, name = "non_fast_forward"),
    @JsonSubTypes.Type(value = GitHubRepositoryRuleCommitMessagePatternDetailed.class, name = "commit_message_pattern"),
    @JsonSubTypes.Type(
            value = GitHubRepositoryRuleCommitAuthorEmailPatternDetailed.class,
            name = "commit_author_email_pattern"),
    @JsonSubTypes.Type(
            value = GitHubRepositoryRuleCommitterEmailPatternDetailed.class,
            name = "committer_email_pattern"),
    @JsonSubTypes.Type(value = GitHubRepositoryRuleBranchNamePatternDetailed.class, name = "branch_name_pattern"),
    @JsonSubTypes.Type(value = GitHubRepositoryRuleTagNamePatternDetailed.class, name = "tag_name_pattern"),
    @JsonSubTypes.Type(value = GitHubRepositoryRuleWorkflowsDetailed.class, name = "workflows"),
    @JsonSubTypes.Type(value = GitHubRepositoryRuleCodeScanningDetailed.class, name = "code_scanning"),
    @JsonSubTypes.Type(value = GitHubRepositoryRuleCodeQualityDetailed.class, name = "code_quality"),
    @JsonSubTypes.Type(value = GitHubRepositoryRuleCodeCoverageDetailed.class, name = "code_coverage"),
    @JsonSubTypes.Type(value = GitHubRepositoryRuleCopilotCodeReviewDetailed.class, name = "copilot_code_review"),
    @JsonSubTypes.Type(
            value = GitHubRepositoryRuleLicenseComplianceScanningDetailed.class,
            name = "license_compliance_scanning"),
    @JsonSubTypes.Type(value = GitHubRepositoryRuleFilePathRestrictionDetailed.class, name = "file_path_restriction"),
    @JsonSubTypes.Type(value = GitHubRepositoryRuleMaxFilePathLengthDetailed.class, name = "max_file_path_length"),
    @JsonSubTypes.Type(
            value = GitHubRepositoryRuleFileExtensionRestrictionDetailed.class,
            name = "file_extension_restriction"),
    @JsonSubTypes.Type(value = GitHubRepositoryRuleMaxFileSizeDetailed.class, name = "max_file_size")
})
@CatalogSchema("components/schemas/repository-rule-detailed")
public sealed interface GitHubRepositoryRuleDetailed
        permits GitHubRepositoryRuleCreationDetailed,
                GitHubRepositoryRuleUpdateDetailed,
                GitHubRepositoryRuleDeletionDetailed,
                GitHubRepositoryRuleRequiredLinearHistoryDetailed,
                GitHubRepositoryRuleMergeQueueDetailed,
                GitHubRepositoryRuleRequiredDeploymentsDetailed,
                GitHubRepositoryRuleRequiredSignaturesDetailed,
                GitHubRepositoryRulePullRequestDetailed,
                GitHubRepositoryRuleRequiredStatusChecksDetailed,
                GitHubRepositoryRuleNonFastForwardDetailed,
                GitHubRepositoryRuleCommitMessagePatternDetailed,
                GitHubRepositoryRuleCommitAuthorEmailPatternDetailed,
                GitHubRepositoryRuleCommitterEmailPatternDetailed,
                GitHubRepositoryRuleBranchNamePatternDetailed,
                GitHubRepositoryRuleTagNamePatternDetailed,
                GitHubRepositoryRuleWorkflowsDetailed,
                GitHubRepositoryRuleCodeScanningDetailed,
                GitHubRepositoryRuleCodeQualityDetailed,
                GitHubRepositoryRuleCodeCoverageDetailed,
                GitHubRepositoryRuleCopilotCodeReviewDetailed,
                GitHubRepositoryRuleLicenseComplianceScanningDetailed,
                GitHubRepositoryRuleFilePathRestrictionDetailed,
                GitHubRepositoryRuleMaxFilePathLengthDetailed,
                GitHubRepositoryRuleFileExtensionRestrictionDetailed,
                GitHubRepositoryRuleMaxFileSizeDetailed {}
