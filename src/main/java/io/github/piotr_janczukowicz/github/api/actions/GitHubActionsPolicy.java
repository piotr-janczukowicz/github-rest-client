package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRuleEnforcement;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * An Actions policy defines rules for workflow execution protection.
 *
 * @param id The ID of the policy
 * @param name The name of the policy
 * @param target The target of the policy
 * @param sourceType The type of the source of the policy
 * @param source The name of the source
 * @param enforcement The enforcement level of the policy
 * @param conditions Conditions the policy applies to
 * @param rules An array of rules within the policy
 * @param nodeId Node id
 * @param links Links of the policy
 * @param createdAt Creation time
 * @param updatedAt Last update time
 */
@CatalogSchema("components/schemas/actions-policy")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubActionsPolicy(
        long id,
        String name,
        GitHubActionsPolicyTarget target,
        GitHubActionsPolicySourceType sourceType,
        String source,
        GitHubRepositoryRuleEnforcement enforcement,
        @Nullable GitHubActionsPolicyConditions conditions,
        @Nullable List<GitHubActionsRule> rules,
        @Nullable String nodeId,
        @Nullable @JsonProperty("_links") GitHubActionsPolicyLinks links,
        @Nullable Instant createdAt,
        @Nullable Instant updatedAt) {

    public GitHubActionsPolicy {
        requireNonNull(name, "name");
        requireNonNull(target, "target");
        requireNonNull(sourceType, "sourceType");
        requireNonNull(source, "source");
        requireNonNull(enforcement, "enforcement");
    }
}
