package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** Repository Ruleset Bypass Actor. */
@CatalogSchema("components/schemas/repository-ruleset-bypass-actor")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRulesetBypassActor(
        @Nullable Long actorId,
        GitHubRepositoryRulesetBypassActorType actorType,
        @Nullable GitHubRepositoryRulesetBypassMode bypassMode) {

    public GitHubRepositoryRulesetBypassActor {
        requireNonNull(actorType, "actorType");
    }
}
