package io.github.piotr_janczukowicz.github.api.billing;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Resource reassigned to a cost center.
 *
 * @param resourceType The type of resource that was reassigned.
 * @param name The name of the resource that was reassigned.
 * @param previousCostCenter The previous cost center of the resource.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/billing/add-resource-to-cost-center/200/properties/reassigned_resources/items")
@Flavors(GitHubFlavor.GHEC)
public record GitHubAddResourceToCostCenterResponseReassignedResource(
        @Nullable String resourceType,
        @Nullable String name,
        @Nullable String previousCostCenter) {}
