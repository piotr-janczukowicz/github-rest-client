package io.github.piotr_janczukowicz.github.api.organizations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Update issue field request body.
 *
 * @param name Name of the issue field
 * @param description Description of the issue field
 * @param visibility The visibility of the issue field
 * @param options Options for select fields
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({"components/schemas/organization-update-issue-field", "operations/orgs/update-issue-field/requestBody"})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubUpdateIssueFieldPayload(
        @Nullable String name,
        @Nullable OptionalNull<String> description,
        @Nullable GitHubIssueFieldVisibility visibility,
        @Nullable List<GitHubUpdateIssueFieldOption> options) {

    public GitHubUpdateIssueFieldPayload {
        if (options != null) {
            options = List.copyOf(options);
        }
    }
}
