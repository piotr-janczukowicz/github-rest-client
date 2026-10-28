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
 * A page of provisioned SCIM groups of an enterprise.
 *
 * @param schemas The URIs that are used to indicate the namespaces of the list SCIM schemas.
 * @param totalResults Number of results found.
 * @param resources Information about each provisioned group.
 * @param startIndex A starting index for the returned page.
 * @param itemsPerPage Number of objects per page.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/scim-enterprise-group-list")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubScimEnterpriseGroupList(
        List<GitHubScimEnterpriseGroupListSchemaUrn> schemas,

        @JsonProperty(value = "totalResults", required = true)
        int totalResults,

        @JsonProperty("Resources") List<GitHubScimEnterpriseGroupResponse> resources,
        @JsonProperty("startIndex") int startIndex,
        @JsonProperty("itemsPerPage") int itemsPerPage) {

    public GitHubScimEnterpriseGroupList {
        requireNonNull(schemas, "schemas");
        requireNonNull(resources, "resources");
    }
}
