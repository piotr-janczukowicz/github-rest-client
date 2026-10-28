package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/** Ref name condition of a ruleset: patterns selecting the refs to include and exclude. */
@CatalogSchema("components/schemas/repository-ruleset-conditions/properties/ref_name")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRulesetConditionsRefName(
        @Nullable List<String> include, @Nullable List<String> exclude) {

    public GitHubRepositoryRulesetConditionsRefName {
        if (include != null) {
            include = List.copyOf(include);
        }
        if (exclude != null) {
            exclude = List.copyOf(exclude);
        }
    }
}
