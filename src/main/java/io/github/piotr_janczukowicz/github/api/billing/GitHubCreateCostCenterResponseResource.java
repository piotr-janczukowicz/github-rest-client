package io.github.piotr_janczukowicz.github.api.billing;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Resource assigned to a created cost center.
 *
 * @param type Type of resource (User, Org, or Repo).
 * @param name Name/login of the resource.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/billing/create-cost-center/200/properties/resources/items")
@Flavors(GitHubFlavor.GHEC)
public record GitHubCreateCostCenterResponseResource(
        @Nullable String type, @Nullable String name) {}
