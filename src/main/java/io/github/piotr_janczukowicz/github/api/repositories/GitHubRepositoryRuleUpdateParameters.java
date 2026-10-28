package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/** Parameters of the update rule: whether updating a ref is allowed through fetch-and-merge. */
@CatalogSchema("components/schemas/repository-rule-update/properties/parameters")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRuleUpdateParameters(boolean updateAllowsFetchAndMerge) {}
