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
 * The metadata associated with the creation and updates of a SCIM resource.
 *
 * @param resourceType A type of a resource.
 * @param created A date and time when the resource was created.
 * @param lastModified A date and time when the resource was last modified.
 * @param location A URL location of the resource.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/meta")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubMeta(
        @JsonProperty("resourceType") GitHubMetaResourceType resourceType,
        @Nullable String created,
        @JsonProperty("lastModified") @Nullable String lastModified,
        @Nullable String location) {

    public GitHubMeta {
        requireNonNull(resourceType, "resourceType");
    }
}
