package io.github.piotr_janczukowicz.github.api.codequality;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Code quality rule
 *
 * @param id A unique identifier for the rule used to detect the finding.
 * @param title The name of the rule used to detect the finding.
 * @param description A short description of the rule used to detect the finding.
 * @param help A detailed description of the rule used to detect the finding.
 * @param severity The severity of the rule used to detect the finding.
 * @param category The category of the rule used to detect the finding.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/code-quality-finding-rule")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCodeQualityFindingRule(
        String id,
        String title,
        String description,
        @Nullable String help,
        GitHubCodeQualityFindingRuleSeverity severity,
        GitHubCodeQualityFindingRuleCategory category) {

    public GitHubCodeQualityFindingRule {
        requireNonNull(id, "id");
        requireNonNull(title, "title");
        requireNonNull(description, "description");
        requireNonNull(severity, "severity");
        requireNonNull(category, "category");
    }
}
