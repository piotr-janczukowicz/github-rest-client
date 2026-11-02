package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * Override the Automation {@code write_scope} for runs started by this trigger. If omitted, the automation's
 * {@code write_scope} is used. Organization or repository policy may restrict the effective scope to {@code trigger}.
 * Explicitly setting {@code repository} on create or edit returns 422 when policy validation is enabled and that policy
 * applies.
 */
@CatalogSchema({
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/issue_opened/properties/write_scope",
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/interval/properties/write_scope",
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/issue_labeled/properties/write_scope",
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/workflow_run/properties/write_scope",
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/pull_request_opened/properties/write_scope",
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/pull_request_synchronize/properties/write_scope",
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/pull_request_merged/properties/write_scope",
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/issue_comment/properties/write_scope",
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/pull_request_comment/properties/write_scope",
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/discussion_opened/properties/write_scope",
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/discussion_updated/properties/write_scope",
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/discussion_comment/properties/write_scope",
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/issue_assigned/properties/write_scope",
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/pull_request_assigned/properties/write_scope",
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/sub_issue_added/properties/write_scope",
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/pull_request_ready_for_review/properties/write_scope",
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/pull_request_labeled/properties/write_scope",
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/pull_request_merge_conflict/properties/write_scope",
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/pull_request_review_submitted/properties/write_scope",
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/pull_request_review_requested/properties/write_scope",
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/issue_closed/properties/write_scope",
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/issue_reopened/properties/write_scope",
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/pull_request_closed/properties/write_scope",
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/pull_request_reopened/properties/write_scope",
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/pull_request_converted_to_draft/properties/write_scope",
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/pull_request_review_dismissed/properties/write_scope",
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/pull_request_review_thread_resolved/properties/write_scope",
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/pull_request_review_thread_unresolved/properties/write_scope",
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/automation_completed/properties/write_scope",
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/automation_failed/properties/write_scope",
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/code_scanning_alert/properties/write_scope",
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/secret_scanning_alert/properties/write_scope",
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/dependabot_alert/properties/write_scope",
    "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/interval/properties/write_scope",
    "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/issue_opened/properties/write_scope",
    "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/issue_labeled/properties/write_scope",
    "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/workflow_run/properties/write_scope",
    "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/pull_request_opened/properties/write_scope",
    "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/pull_request_synchronize/properties/write_scope",
    "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/pull_request_merged/properties/write_scope",
    "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/issue_comment/properties/write_scope",
    "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/pull_request_comment/properties/write_scope",
    "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/discussion_opened/properties/write_scope",
    "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/discussion_updated/properties/write_scope",
    "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/discussion_comment/properties/write_scope",
    "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/issue_assigned/properties/write_scope",
    "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/pull_request_assigned/properties/write_scope",
    "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/sub_issue_added/properties/write_scope",
    "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/pull_request_ready_for_review/properties/write_scope",
    "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/pull_request_labeled/properties/write_scope",
    "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/pull_request_merge_conflict/properties/write_scope",
    "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/pull_request_review_submitted/properties/write_scope",
    "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/pull_request_review_requested/properties/write_scope",
    "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/issue_closed/properties/write_scope",
    "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/issue_reopened/properties/write_scope",
    "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/pull_request_closed/properties/write_scope",
    "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/pull_request_reopened/properties/write_scope",
    "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/pull_request_converted_to_draft/properties/write_scope",
    "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/pull_request_review_dismissed/properties/write_scope",
    "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/pull_request_review_thread_resolved/properties/write_scope",
    "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/pull_request_review_thread_unresolved/properties/write_scope",
    "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/automation_completed/properties/write_scope",
    "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/automation_failed/properties/write_scope",
    "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/code_scanning_alert/properties/write_scope",
    "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/secret_scanning_alert/properties/write_scope",
    "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/dependabot_alert/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/interval/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/issue_opened/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/issue_labeled/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/workflow_run/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/pull_request_opened/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/pull_request_synchronize/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/pull_request_merged/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/issue_comment/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/pull_request_comment/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/discussion_opened/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/discussion_updated/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/discussion_comment/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/issue_assigned/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/pull_request_assigned/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/sub_issue_added/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/pull_request_ready_for_review/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/pull_request_labeled/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/pull_request_merge_conflict/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/pull_request_review_submitted/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/pull_request_review_requested/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/issue_closed/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/issue_reopened/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/pull_request_closed/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/pull_request_reopened/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/pull_request_converted_to_draft/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/pull_request_review_dismissed/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/pull_request_review_thread_resolved/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/pull_request_review_thread_unresolved/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/automation_completed/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/automation_failed/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/code_scanning_alert/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/secret_scanning_alert/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/dependabot_alert/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/interval/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/issue_opened/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/issue_labeled/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/workflow_run/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/pull_request_opened/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/pull_request_synchronize/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/pull_request_merged/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/issue_comment/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/pull_request_comment/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/discussion_opened/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/discussion_updated/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/discussion_comment/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/issue_assigned/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/pull_request_assigned/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/sub_issue_added/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/pull_request_ready_for_review/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/pull_request_labeled/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/pull_request_merge_conflict/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/pull_request_review_submitted/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/pull_request_review_requested/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/issue_closed/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/issue_reopened/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/pull_request_closed/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/pull_request_reopened/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/pull_request_converted_to_draft/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/pull_request_review_dismissed/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/pull_request_review_thread_resolved/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/pull_request_review_thread_unresolved/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/automation_completed/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/automation_failed/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/code_scanning_alert/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/secret_scanning_alert/properties/write_scope",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/dependabot_alert/properties/write_scope",
    "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/interval/properties/write_scope",
    "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/issue_opened/properties/write_scope",
    "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/issue_labeled/properties/write_scope",
    "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/workflow_run/properties/write_scope",
    "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/pull_request_opened/properties/write_scope",
    "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/pull_request_synchronize/properties/write_scope",
    "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/pull_request_merged/properties/write_scope",
    "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/issue_comment/properties/write_scope",
    "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/pull_request_comment/properties/write_scope",
    "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/discussion_opened/properties/write_scope",
    "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/discussion_updated/properties/write_scope",
    "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/discussion_comment/properties/write_scope",
    "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/issue_assigned/properties/write_scope",
    "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/pull_request_assigned/properties/write_scope",
    "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/sub_issue_added/properties/write_scope",
    "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/pull_request_ready_for_review/properties/write_scope",
    "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/pull_request_labeled/properties/write_scope",
    "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/pull_request_merge_conflict/properties/write_scope",
    "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/pull_request_review_submitted/properties/write_scope",
    "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/pull_request_review_requested/properties/write_scope",
    "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/issue_closed/properties/write_scope",
    "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/issue_reopened/properties/write_scope",
    "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/pull_request_closed/properties/write_scope",
    "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/pull_request_reopened/properties/write_scope",
    "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/pull_request_converted_to_draft/properties/write_scope",
    "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/pull_request_review_dismissed/properties/write_scope",
    "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/pull_request_review_thread_resolved/properties/write_scope",
    "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/pull_request_review_thread_unresolved/properties/write_scope",
    "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/automation_completed/properties/write_scope",
    "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/automation_failed/properties/write_scope",
    "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/code_scanning_alert/properties/write_scope",
    "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/secret_scanning_alert/properties/write_scope",
    "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/dependabot_alert/properties/write_scope",
    "operations/agent-automations/list-automations/200/items/properties/triggers/properties/interval/properties/write_scope",
    "operations/agent-automations/list-automations/200/items/properties/triggers/properties/issue_opened/properties/write_scope",
    "operations/agent-automations/list-automations/200/items/properties/triggers/properties/issue_labeled/properties/write_scope",
    "operations/agent-automations/list-automations/200/items/properties/triggers/properties/workflow_run/properties/write_scope",
    "operations/agent-automations/list-automations/200/items/properties/triggers/properties/pull_request_opened/properties/write_scope",
    "operations/agent-automations/list-automations/200/items/properties/triggers/properties/pull_request_synchronize/properties/write_scope",
    "operations/agent-automations/list-automations/200/items/properties/triggers/properties/pull_request_merged/properties/write_scope",
    "operations/agent-automations/list-automations/200/items/properties/triggers/properties/issue_comment/properties/write_scope",
    "operations/agent-automations/list-automations/200/items/properties/triggers/properties/pull_request_comment/properties/write_scope",
    "operations/agent-automations/list-automations/200/items/properties/triggers/properties/discussion_opened/properties/write_scope",
    "operations/agent-automations/list-automations/200/items/properties/triggers/properties/discussion_updated/properties/write_scope",
    "operations/agent-automations/list-automations/200/items/properties/triggers/properties/discussion_comment/properties/write_scope",
    "operations/agent-automations/list-automations/200/items/properties/triggers/properties/issue_assigned/properties/write_scope",
    "operations/agent-automations/list-automations/200/items/properties/triggers/properties/pull_request_assigned/properties/write_scope",
    "operations/agent-automations/list-automations/200/items/properties/triggers/properties/sub_issue_added/properties/write_scope",
    "operations/agent-automations/list-automations/200/items/properties/triggers/properties/pull_request_ready_for_review/properties/write_scope",
    "operations/agent-automations/list-automations/200/items/properties/triggers/properties/pull_request_labeled/properties/write_scope",
    "operations/agent-automations/list-automations/200/items/properties/triggers/properties/pull_request_merge_conflict/properties/write_scope",
    "operations/agent-automations/list-automations/200/items/properties/triggers/properties/pull_request_review_submitted/properties/write_scope",
    "operations/agent-automations/list-automations/200/items/properties/triggers/properties/pull_request_review_requested/properties/write_scope",
    "operations/agent-automations/list-automations/200/items/properties/triggers/properties/issue_closed/properties/write_scope",
    "operations/agent-automations/list-automations/200/items/properties/triggers/properties/issue_reopened/properties/write_scope",
    "operations/agent-automations/list-automations/200/items/properties/triggers/properties/pull_request_closed/properties/write_scope",
    "operations/agent-automations/list-automations/200/items/properties/triggers/properties/pull_request_reopened/properties/write_scope",
    "operations/agent-automations/list-automations/200/items/properties/triggers/properties/pull_request_converted_to_draft/properties/write_scope",
    "operations/agent-automations/list-automations/200/items/properties/triggers/properties/pull_request_review_dismissed/properties/write_scope",
    "operations/agent-automations/list-automations/200/items/properties/triggers/properties/pull_request_review_thread_resolved/properties/write_scope",
    "operations/agent-automations/list-automations/200/items/properties/triggers/properties/pull_request_review_thread_unresolved/properties/write_scope",
    "operations/agent-automations/list-automations/200/items/properties/triggers/properties/automation_completed/properties/write_scope",
    "operations/agent-automations/list-automations/200/items/properties/triggers/properties/automation_failed/properties/write_scope",
    "operations/agent-automations/list-automations/200/items/properties/triggers/properties/code_scanning_alert/properties/write_scope",
    "operations/agent-automations/list-automations/200/items/properties/triggers/properties/secret_scanning_alert/properties/write_scope",
    "operations/agent-automations/list-automations/200/items/properties/triggers/properties/dependabot_alert/properties/write_scope",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/interval/properties/write_scope",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/issue_opened/properties/write_scope",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/issue_labeled/properties/write_scope",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/workflow_run/properties/write_scope",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/pull_request_opened/properties/write_scope",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/pull_request_synchronize/properties/write_scope",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/pull_request_merged/properties/write_scope",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/issue_comment/properties/write_scope",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/pull_request_comment/properties/write_scope",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/discussion_opened/properties/write_scope",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/discussion_updated/properties/write_scope",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/discussion_comment/properties/write_scope",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/issue_assigned/properties/write_scope",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/pull_request_assigned/properties/write_scope",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/sub_issue_added/properties/write_scope",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/pull_request_ready_for_review/properties/write_scope",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/pull_request_labeled/properties/write_scope",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/pull_request_merge_conflict/properties/write_scope",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/pull_request_review_submitted/properties/write_scope",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/pull_request_review_requested/properties/write_scope",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/issue_closed/properties/write_scope",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/issue_reopened/properties/write_scope",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/pull_request_closed/properties/write_scope",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/pull_request_reopened/properties/write_scope",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/pull_request_converted_to_draft/properties/write_scope",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/pull_request_review_dismissed/properties/write_scope",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/pull_request_review_thread_resolved/properties/write_scope",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/pull_request_review_thread_unresolved/properties/write_scope",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/automation_completed/properties/write_scope",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/automation_failed/properties/write_scope",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/code_scanning_alert/properties/write_scope",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/secret_scanning_alert/properties/write_scope",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/dependabot_alert/properties/write_scope"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubAutomationWriteScope {
    TRIGGER("trigger"),
    REPOSITORY("repository");

    private static final EnumUtils.WireIndex<GitHubAutomationWriteScope> WIRE_INDEX =
            EnumUtils.index(GitHubAutomationWriteScope.class, GitHubAutomationWriteScope::wireValue);

    private final String wireValue;

    GitHubAutomationWriteScope(final String wireValue) {
        this.wireValue = wireValue;
    }

    /**
     * Value GitHub sends and expects in JSON.
     *
     * @return wire string
     */
    @JsonValue
    public String wireValue() {
        return wireValue;
    }

    /**
     * Resolves the constant from its wire value.
     *
     * @param value JSON value
     * @return matching constant
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubAutomationWriteScope fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub automation write scope: " + value));
    }
}
