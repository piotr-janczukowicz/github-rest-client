package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * License of a minimal repository.
 *
 * @param key Key
 * @param name Name
 * @param spdxId Spdx id
 * @param url Url
 * @param nodeId Node id
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/minimal-repository/properties/license")
@RecordBuilder
public record GitHubMinimalRepositoryLicense(
        @Nullable String key,
        @Nullable String name,
        @Nullable String spdxId,
        @Nullable String url,
        @Nullable String nodeId) {}
