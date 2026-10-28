package io.github.piotr_janczukowicz.github.api.codescanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Code scanning alert rule.
 *
 * @param id A unique identifier for the rule used to detect the alert.
 * @param name The name of the rule used to detect the alert.
 * @param severity The severity of the alert.
 * @param securitySeverityLevel The security severity of the alert.
 * @param description A short description of the rule used to detect the alert.
 * @param fullDescription A description of the rule used to detect the alert.
 * @param tags A set of tags applicable for the rule.
 * @param help Detailed documentation for the rule as GitHub Flavored Markdown.
 * @param helpUri A link to the documentation for the rule used to detect the alert.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/code-scanning-alert-rule")
public record GitHubCodeScanningAlertRule(
        @Nullable String id,
        @Nullable String name,
        @Nullable GitHubCodeScanningAlertRuleSeverity severity,
        @Nullable GitHubCodeScanningAlertRuleSecuritySeverityLevel securitySeverityLevel,
        @Nullable String description,
        @Nullable String fullDescription,
        @Nullable List<String> tags,
        @Nullable String help,
        @Nullable String helpUri) {}
