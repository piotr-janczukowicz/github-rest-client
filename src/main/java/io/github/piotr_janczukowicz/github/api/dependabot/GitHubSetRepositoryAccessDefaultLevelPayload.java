package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Set the default repository access level for Dependabot.
 *
 * @param defaultLevel The default repository access level for Dependabot updates.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/dependabot/set-repository-access-default-level/requestBody",
    "operations/dependabot/set-repository-access-default-level-for-enterprise/requestBody"
})
public record GitHubSetRepositoryAccessDefaultLevelPayload(GitHubDependabotRepositoryAccessDefaultLevel defaultLevel) {

    public GitHubSetRepositoryAccessDefaultLevelPayload {
        requireNonNull(defaultLevel, "defaultLevel");
    }
}
