package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Option.
 *
 * @param id The unique identifier of the option.
 * @param name The name of the option.
 * @param description The description of the option.
 * @param color The color of the option.
 * @param priority The priority of the option for ordering.
 * @param createdAt The time the option was created.
 * @param updatedAt The time the option was last updated.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/user-issue-field/properties/options/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubUserIssueFieldOption(
        long id,
        String name,
        @Nullable String description,
        @Nullable GitHubUserIssueFieldOptionColor color,
        @Nullable Integer priority,
        @Nullable Instant createdAt,
        @Nullable Instant updatedAt) {
    public GitHubUserIssueFieldOption {
        requireNonNull(name, "name");
    }
}
