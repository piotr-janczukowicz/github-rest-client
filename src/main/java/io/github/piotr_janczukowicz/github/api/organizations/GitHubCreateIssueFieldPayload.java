package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Create issue field request body.
 *
 * @param name Name of the issue field
 * @param description Description of the issue field
 * @param dataType The data type of the issue field
 * @param visibility The visibility of the issue field
 * @param options Options for select fields
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({"components/schemas/organization-create-issue-field", "operations/orgs/create-issue-field/requestBody"})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCreateIssueFieldPayload(
        String name,
        @Nullable OptionalNull<String> description,
        GitHubIssueFieldDataType dataType,
        @Nullable GitHubIssueFieldVisibility visibility,
        @Nullable OptionalNull<List<GitHubCreateIssueFieldOption>> options) {

    private static final String PARAM_NAME = "name";
    private static final String PARAM_DATA_TYPE = "dataType";

    public GitHubCreateIssueFieldPayload {
        requireNonNull(name, PARAM_NAME);
        requireNonNull(dataType, PARAM_DATA_TYPE);
        if (options != null && options.value() != null) {
            options = OptionalNull.of(List.copyOf(options.value()));
        }
    }
}
