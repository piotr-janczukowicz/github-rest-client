package io.github.piotr_janczukowicz.github.api.scim;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * One operation of an update-attribute request.
 *
 * @param op The operation to perform
 * @param path Path of the attribute to change
 * @param value Value of the attribute
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/scim/update-attribute-for-user/requestBody/properties/Operations/items")
@Flavors(GitHubFlavor.GHEC)
public record GitHubUpdateAttributeForUserOperation(
        GitHubUpdateAttributeForUserOperationOp op,
        @Nullable String path,
        @Nullable GitHubUpdateAttributeForUserValue value) {

    public GitHubUpdateAttributeForUserOperation {
        requireNonNull(op, "op");
    }
}
