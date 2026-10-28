package io.github.piotr_janczukowicz.github.api.codescanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Code scanning alert instance.
 *
 * @param ref The Git reference, formatted as `refs/pull/&lt;number&gt;/merge`, `refs/pull/&lt;number&gt;/head`,
 *     `refs/heads/&lt;branch name&gt;` or simply `&lt;branch name&gt;`.
 * @param analysisKey Identifies the configuration under which the analysis was executed.
 * @param environment Identifies the variable values associated with the environment in which the analysis that
 *     generated this alert instance was performed, such as the language that was analyzed.
 * @param category Identifies the configuration under which the analysis was executed.
 * @param state State of a code scanning alert.
 * @param commitSha Commit sha.
 * @param message Message.
 * @param location Describe a region within a file for the alert.
 * @param htmlUrl Html url.
 * @param classifications Classifications that have been applied to the file that triggered the alert.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/code-scanning-alert-instance")
public record GitHubCodeScanningAlertInstance(
        @Nullable String ref,
        @Nullable String analysisKey,
        @Nullable String environment,
        @Nullable String category,
        @Nullable GitHubCodeScanningAlertState state,
        @Nullable String commitSha,
        @Nullable GitHubCodeScanningAlertInstanceMessage message,
        @Nullable GitHubCodeScanningAlertLocation location,
        @Nullable String htmlUrl,
        @Nullable List<GitHubCodeScanningAlertClassification> classifications) {}
