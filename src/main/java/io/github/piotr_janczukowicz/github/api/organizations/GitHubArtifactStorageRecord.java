package io.github.piotr_janczukowicz.github.api.organizations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Artifact metadata storage record.
 *
 * @param id Id
 * @param name Name
 * @param digest Digest
 * @param artifactUrl Artifact url
 * @param registryUrl Registry url
 * @param repository Repository
 * @param status Status
 * @param createdAt Created at
 * @param updatedAt Updated at
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/orgs/create-artifact-storage-record/200/properties/storage_records/items",
    "operations/orgs/list-artifact-storage-records/200/properties/storage_records/items"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubArtifactStorageRecord(
        @Nullable Long id,
        @Nullable String name,
        @Nullable String digest,
        @Nullable String artifactUrl,
        @Nullable String registryUrl,
        @Nullable String repository,
        @Nullable String status,
        @Nullable String createdAt,
        @Nullable String updatedAt) {}
