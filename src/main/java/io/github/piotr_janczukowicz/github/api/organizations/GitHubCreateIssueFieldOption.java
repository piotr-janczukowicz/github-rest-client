package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Option for a select field in a create issue field request body.
 *
 * @param name Name of the option
 * @param description Description of the option
 * @param color Color for the option
 * @param priority Priority of the option for ordering
 */
@RecordBuilder
@CatalogSchema("components/schemas/organization-create-issue-field/properties/options/items")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCreateIssueFieldOption(
        String name, @Nullable OptionalNull<String> description, GitHubIssueFieldOptionColor color, int priority) {

    private static final String PARAM_NAME = "name";
    private static final String PARAM_COLOR = "color";

    public GitHubCreateIssueFieldOption {
        requireNonNull(name, PARAM_NAME);
        requireNonNull(color, PARAM_COLOR);
    }
}
