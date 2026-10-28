package io.github.piotr_janczukowicz.github.api.copilot;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * The enabled review tools for Copilot cloud agent.
 *
 * @param codeql Codeql
 * @param copilotCodeReview Copilot code review
 * @param secretScanning Secret scanning
 * @param dependencyVulnerabilityChecks Dependency vulnerability checks
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/copilot/get-copilot-cloud-agent-configuration/200/properties/enabled_tools")
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCopilotCloudAgentEnabledTools(
        boolean codeql, boolean copilotCodeReview, boolean secretScanning, boolean dependencyVulnerabilityChecks) {}
