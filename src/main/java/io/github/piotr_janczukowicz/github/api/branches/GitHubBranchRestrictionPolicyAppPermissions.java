package io.github.piotr_janczukowicz.github.api.branches;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Permissions of a restricted GitHub App on a branch restriction policy.
 *
 * @param metadata Metadata
 * @param contents Contents
 * @param issues Issues
 * @param singleFile Single file
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/branch-restriction-policy/properties/apps/items/properties/permissions")
public record GitHubBranchRestrictionPolicyAppPermissions(
        @Nullable String metadata,
        @Nullable String contents,
        @Nullable String issues,
        @Nullable String singleFile) {}
