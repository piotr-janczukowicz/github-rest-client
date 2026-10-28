package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** StatusCheckConfiguration. */
@CatalogSchema("components/schemas/repository-rule-params-status-check-configuration")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRuleParamsStatusCheckConfiguration(
        String context, @Nullable Long integrationId) {

    public GitHubRepositoryRuleParamsStatusCheckConfiguration {
        requireNonNull(context, "context");
    }
}
