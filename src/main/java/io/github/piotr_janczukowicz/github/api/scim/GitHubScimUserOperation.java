package io.github.piotr_janczukowicz.github.api.scim;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * An operation recorded on a SCIM user.
 *
 * @param op The operation performed
 * @param path Path of the attribute that was changed
 * @param value Value of the attribute
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/scim-user/properties/operations/items")
@Flavors(GitHubFlavor.GHEC)
public record GitHubScimUserOperation(
        GitHubScimUserOperationOp op,
        @Nullable String path,
        @Nullable GitHubScimOperationValue value) {

    public GitHubScimUserOperation {
        requireNonNull(op, "op");
    }
}
