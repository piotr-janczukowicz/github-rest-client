package io.github.piotr_janczukowicz.github.api.scim;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Metadata of a SCIM resource.
 *
 * @param resourceType The resource type, {@code User}
 * @param created When the resource was created
 * @param lastModified When the resource was last modified
 * @param location The API URL of the resource
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/scim-user/properties/meta")
@Flavors(GitHubFlavor.GHEC)
public record GitHubScimUserMeta(
        @JsonProperty("resourceType") @Nullable String resourceType,
        @Nullable Instant created,
        @JsonProperty("lastModified") @Nullable Instant lastModified,
        @Nullable String location) {}
