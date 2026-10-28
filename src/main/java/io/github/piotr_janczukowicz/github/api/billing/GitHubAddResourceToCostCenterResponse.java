package io.github.piotr_janczukowicz.github.api.billing;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Result of adding resources to a cost center.
 *
 * @param message Result message.
 * @param reassignedResources Resources that were reassigned from another cost center.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/billing/add-resource-to-cost-center/200")
@Flavors(GitHubFlavor.GHEC)
public record GitHubAddResourceToCostCenterResponse(
        @Nullable String message,
        @Nullable List<GitHubAddResourceToCostCenterResponseReassignedResource> reassignedResources) {}
