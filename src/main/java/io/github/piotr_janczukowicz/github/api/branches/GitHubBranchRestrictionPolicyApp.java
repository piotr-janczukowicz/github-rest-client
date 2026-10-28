package io.github.piotr_janczukowicz.github.api.branches;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Restricted GitHub App on a branch restriction policy.
 *
 * @param id Id
 * @param slug Slug
 * @param nodeId Node id
 * @param owner Owner
 * @param name Name
 * @param clientId Client id
 * @param description Description
 * @param externalUrl External url
 * @param htmlUrl Html url
 * @param createdAt Created at
 * @param updatedAt Updated at
 * @param permissions Permissions
 * @param events Events
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/branch-restriction-policy/properties/apps/items")
public record GitHubBranchRestrictionPolicyApp(
        @Nullable Long id,
        @Nullable String slug,
        @Nullable String nodeId,
        @Nullable GitHubBranchRestrictionPolicyAppOwner owner,
        @Nullable String name,
        @Nullable String clientId,
        @Nullable String description,
        @Nullable String externalUrl,
        @Nullable String htmlUrl,
        @Nullable String createdAt,
        @Nullable String updatedAt,
        @Nullable GitHubBranchRestrictionPolicyAppPermissions permissions,
        @Nullable List<String> events) {}
