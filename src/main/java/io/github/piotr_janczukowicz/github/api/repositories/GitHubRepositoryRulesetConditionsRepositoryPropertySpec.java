package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/** Repository ruleset property targeting definition. */
@CatalogSchema("components/schemas/repository-ruleset-conditions-repository-property-spec")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRulesetConditionsRepositoryPropertySpec(
        String name,
        List<String> propertyValues,
        @Nullable GitHubRepositoryRulesetConditionsRepositoryPropertySource source) {
    public GitHubRepositoryRulesetConditionsRepositoryPropertySpec {
        requireNonNull(name, "name");
        propertyValues = List.copyOf(requireNonNull(propertyValues, "propertyValues"));
    }
}
