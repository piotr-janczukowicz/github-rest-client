package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Option.
 *
 * @param name Name of the option.
 * @param description Description of the option.
 * @param color Color for the option.
 * @param priority Priority of the option for ordering.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/user-create-issue-field/properties/options/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCreateUserIssueFieldOption(
        String name,
        @Nullable OptionalNull<String> description,
        GitHubCreateUserIssueFieldOptionColor color,
        int priority) {
    public GitHubCreateUserIssueFieldOption {
        requireNonNull(name, "name");
        requireNonNull(color, "color");
    }
}
