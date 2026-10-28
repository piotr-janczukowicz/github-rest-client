package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * A member of a provisioned SCIM group.
 *
 * @param value The local unique identifier for the member.
 * @param ref The SCIM reference URL of the member.
 * @param display The display name associated with the member.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "components/schemas/group-response/properties/members/items",
    "components/schemas/scim-enterprise-group-response/allOf/1/properties/members/items"
})
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubGroupResponseMember(
        String value,
        @JsonProperty("$ref") String ref,
        @Nullable String display) {

    public GitHubGroupResponseMember {
        requireNonNull(value, "value");
        requireNonNull(ref, "ref");
    }
}
