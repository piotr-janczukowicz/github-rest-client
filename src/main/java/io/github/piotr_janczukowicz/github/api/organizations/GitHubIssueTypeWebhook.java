package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Issue Type.
 *
 * @param id The unique identifier of the issue type.
 * @param name The name of the issue type.
 * @param color The color of the issue type.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/issue-type-webhook")
@RecordBuilder
public record GitHubIssueTypeWebhook(
        long id, String name, @Nullable GitHubIssueTypeColor color) {

    public GitHubIssueTypeWebhook {
        requireNonNull(name, "name");
    }
}
