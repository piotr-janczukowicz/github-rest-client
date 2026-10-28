package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * One SCIM patch operation.
 *
 * @param op The patch operation to perform.
 * @param path The attribute path the operation applies to.
 * @param value The value of the attribute specified by the path.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/patch-schema/properties/Operations/items")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubPatchSchemaOperation(
        GitHubPatchSchemaOperationOp op,
        @Nullable String path,
        @Nullable String value) {

    public GitHubPatchSchemaOperation {
        requireNonNull(op, "op");
    }
}
