package io.github.piotr_janczukowicz.github.api.branches;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Branch protection enabled setting.
 *
 * @param enabled Enabled
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "components/schemas/branch-protection/properties/required_linear_history",
    "components/schemas/branch-protection/properties/allow_force_pushes",
    "components/schemas/branch-protection/properties/allow_deletions",
    "components/schemas/branch-protection/properties/block_creations",
    "components/schemas/branch-protection/properties/required_conversation_resolution",
    "components/schemas/branch-protection/properties/lock_branch",
    "components/schemas/branch-protection/properties/allow_fork_syncing",
    "components/schemas/protected-branch/properties/required_linear_history",
    "components/schemas/protected-branch/properties/allow_force_pushes",
    "components/schemas/protected-branch/properties/allow_deletions",
    "components/schemas/protected-branch/properties/required_conversation_resolution",
    "components/schemas/protected-branch/properties/block_creations",
    "components/schemas/protected-branch/properties/lock_branch",
    "components/schemas/protected-branch/properties/allow_fork_syncing"
})
public record GitHubBranchProtectionSetting(@Nullable Boolean enabled) {}
