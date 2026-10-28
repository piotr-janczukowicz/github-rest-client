package io.github.piotr_janczukowicz.github.api.billing;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Cost centers of an enterprise.
 *
 * @param costCenters Cost centers of the enterprise.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/get-all-cost-centers")
@Flavors(GitHubFlavor.GHEC)
public record GitHubGetAllCostCenters(
        @JsonProperty("costCenters") @Nullable List<GitHubGetAllCostCentersCostCenter> costCenters) {}
