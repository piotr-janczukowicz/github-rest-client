package io.github.piotr_janczukowicz.github.api.scim;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonEmpty;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * A page of SCIM provisioned users.
 *
 * @param schemas SCIM schema used
 * @param totalResults Total number of results
 * @param itemsPerPage Number of results returned in this page
 * @param startIndex 1-based index of the first result in this page
 * @param resources The provisioned users of this page
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/scim-user-list")
@Flavors(GitHubFlavor.GHEC)
public record GitHubScimUserList(
        List<String> schemas,

        @JsonProperty(value = "totalResults", required = true)
        int totalResults,

        @JsonProperty("itemsPerPage") int itemsPerPage,
        @JsonProperty("startIndex") int startIndex,
        @JsonProperty("Resources") List<GitHubScimUser> resources) {

    public GitHubScimUserList {
        requireNonEmpty(schemas, "schemas");
        requireNonNull(resources, "resources");
    }
}
