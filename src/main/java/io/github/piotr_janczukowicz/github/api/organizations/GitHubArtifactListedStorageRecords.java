package io.github.piotr_janczukowicz.github.api.organizations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Listed artifact storage records.
 *
 * @param totalCount The number of storage records for this digest and organization
 * @param storageRecords Storage records
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/orgs/list-artifact-storage-records/200")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubArtifactListedStorageRecords(
        @Nullable Integer totalCount, @Nullable List<GitHubArtifactStorageRecord> storageRecords) {}
