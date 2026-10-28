package io.github.piotr_janczukowicz.github.api.scim;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Update one or more attributes of a SCIM provisioned user.
 *
 * @param schemas SCIM schema used
 * @param operations Set of operations to be performed
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/scim/update-attribute-for-user/requestBody")
@Flavors(GitHubFlavor.GHEC)
public record GitHubUpdateAttributeForUserPayload(
        @Nullable List<String> schemas,
        @JsonProperty("Operations") List<GitHubUpdateAttributeForUserOperation> operations) {

    public GitHubUpdateAttributeForUserPayload {
        operations = List.copyOf(requireNonEmpty(operations, "operations"));
        if (schemas != null) {
            schemas = List.copyOf(schemas);
        }
    }
}
