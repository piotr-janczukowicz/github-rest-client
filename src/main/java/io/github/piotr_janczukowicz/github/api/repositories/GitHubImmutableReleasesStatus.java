package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Check immutable releases.
 *
 * @param enabled Whether immutable releases are enabled for the repository.
 * @param enforcedByOwner Whether immutable releases are enforced by the repository owner.
 */
@CatalogSchema("components/schemas/check-immutable-releases")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubImmutableReleasesStatus(boolean enabled, boolean enforcedByOwner) {}
