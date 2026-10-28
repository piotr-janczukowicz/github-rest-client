package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Dormant user statistics.
 *
 * @param totalDormantUsers Total dormant users
 * @param dormancyThreshold The inactivity period that defines a dormant user
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/server-statistics/items/properties/dormant_users")
@Flavors(GitHubFlavor.GHEC)
public record GitHubServerStatisticsDormantUsers(
        @Nullable Integer totalDormantUsers, @Nullable String dormancyThreshold) {}
