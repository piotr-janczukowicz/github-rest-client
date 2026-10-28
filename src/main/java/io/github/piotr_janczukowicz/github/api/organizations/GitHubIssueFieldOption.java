package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Option for a single select or multi select organization issue field.
 *
 * @param id The unique identifier of the option
 * @param name The name of the option
 * @param description The description of the option
 * @param color The color of the option
 * @param priority The priority of the option for ordering
 * @param createdAt The time the option was created
 * @param updatedAt The time the option was last updated
 */
@RecordBuilder
@CatalogSchema("components/schemas/issue-field/properties/options/items")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubIssueFieldOption(
        long id,
        String name,
        @Nullable String description,
        @Nullable GitHubIssueFieldOptionColor color,
        @Nullable Integer priority,
        @Nullable Instant createdAt,
        @Nullable Instant updatedAt) {

    private static final String PARAM_NAME = "name";

    public GitHubIssueFieldOption {
        requireNonNull(name, PARAM_NAME);
    }
}
