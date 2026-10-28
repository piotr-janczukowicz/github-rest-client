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
 * Update issue type request body.
 *
 * @param name Name of the issue type
 * @param isEnabled Whether or not the issue type is enabled at the organization level
 * @param description Description of the issue type
 * @param color Color for the issue type
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({"components/schemas/organization-update-issue-type", "operations/orgs/update-issue-type/requestBody"})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubUpdateIssueTypePayload(
        String name,
        boolean isEnabled,
        @Nullable OptionalNull<String> description,
        @Nullable OptionalNull<GitHubIssueTypeColor> color) {

    private static final String PARAM_NAME = "name";

    public GitHubUpdateIssueTypePayload {
        requireNonNull(name, PARAM_NAME);
    }
}
