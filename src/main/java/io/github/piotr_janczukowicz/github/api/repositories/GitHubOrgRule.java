package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/** Organization ruleset rule variants. */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        visible = true)
@JsonSubTypes({
    @JsonSubTypes.Type(value = GitHubRepositoryRuleCreation.class, name = "creation"),
    @JsonSubTypes.Type(value = GitHubRepositoryRuleUpdate.class, name = "update"),
    @JsonSubTypes.Type(value = GitHubRepositoryRuleDeletion.class, name = "deletion"),
    @JsonSubTypes.Type(value = GitHubRepositoryRuleRequiredLinearHistory.class, name = "required_linear_history"),
    @JsonSubTypes.Type(value = GitHubRepositoryRuleRequiredDeployments.class, name = "required_deployments"),
    @JsonSubTypes.Type(value = GitHubRepositoryRuleRequiredSignatures.class, name = "required_signatures"),
    @JsonSubTypes.Type(value = GitHubRepositoryRulePullRequest.class, name = "pull_request"),
    @JsonSubTypes.Type(value = GitHubRepositoryRuleRequiredStatusChecks.class, name = "required_status_checks"),
    @JsonSubTypes.Type(value = GitHubRepositoryRuleNonFastForward.class, name = "non_fast_forward"),
    @JsonSubTypes.Type(value = GitHubRepositoryRuleCommitMessagePattern.class, name = "commit_message_pattern"),
    @JsonSubTypes.Type(
            value = GitHubRepositoryRuleCommitAuthorEmailPattern.class,
            name = "commit_author_email_pattern"),
    @JsonSubTypes.Type(value = GitHubRepositoryRuleCommitterEmailPattern.class, name = "committer_email_pattern"),
    @JsonSubTypes.Type(value = GitHubRepositoryRuleBranchNamePattern.class, name = "branch_name_pattern"),
    @JsonSubTypes.Type(value = GitHubRepositoryRuleTagNamePattern.class, name = "tag_name_pattern"),
    @JsonSubTypes.Type(value = GitHubRepositoryRuleFilePathRestriction.class, name = "file_path_restriction"),
    @JsonSubTypes.Type(value = GitHubRepositoryRuleMaxFilePathLength.class, name = "max_file_path_length"),
    @JsonSubTypes.Type(value = GitHubRepositoryRuleFileExtensionRestriction.class, name = "file_extension_restriction"),
    @JsonSubTypes.Type(value = GitHubRepositoryRuleMaxFileSize.class, name = "max_file_size"),
    @JsonSubTypes.Type(value = GitHubRepositoryRuleWorkflows.class, name = "workflows"),
    @JsonSubTypes.Type(value = GitHubRepositoryRuleCodeScanning.class, name = "code_scanning"),
    @JsonSubTypes.Type(value = GitHubRepositoryRuleCopilotCodeReview.class, name = "copilot_code_review")
})
@CatalogSchema("components/schemas/org-rules")
public sealed interface GitHubOrgRule
        permits GitHubRepositoryRuleCreation,
                GitHubRepositoryRuleUpdate,
                GitHubRepositoryRuleDeletion,
                GitHubRepositoryRuleRequiredLinearHistory,
                GitHubRepositoryRuleRequiredDeployments,
                GitHubRepositoryRuleRequiredSignatures,
                GitHubRepositoryRulePullRequest,
                GitHubRepositoryRuleRequiredStatusChecks,
                GitHubRepositoryRuleNonFastForward,
                GitHubRepositoryRuleCommitMessagePattern,
                GitHubRepositoryRuleCommitAuthorEmailPattern,
                GitHubRepositoryRuleCommitterEmailPattern,
                GitHubRepositoryRuleBranchNamePattern,
                GitHubRepositoryRuleTagNamePattern,
                GitHubRepositoryRuleFilePathRestriction,
                GitHubRepositoryRuleMaxFilePathLength,
                GitHubRepositoryRuleFileExtensionRestriction,
                GitHubRepositoryRuleMaxFileSize,
                GitHubRepositoryRuleWorkflows,
                GitHubRepositoryRuleCodeScanning,
                GitHubRepositoryRuleCopilotCodeReview {}
