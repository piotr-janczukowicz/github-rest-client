package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * SCIM patch request that changes attributes of a provisioned group or user.
 *
 * @param operations Patch operations list.
 * @param schemas The URIs that are used to indicate the namespaces of the SCIM patch schemas.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/patch-schema")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubPatchSchema(
        @JsonProperty("Operations") List<GitHubPatchSchemaOperation> operations, List<GitHubPatchOpSchemaUrn> schemas) {

    public GitHubPatchSchema {
        requireNonNull(operations, "operations");
        operations = List.copyOf(operations);
        requireNonNull(schemas, "schemas");
        schemas = List.copyOf(schemas);
    }
}
