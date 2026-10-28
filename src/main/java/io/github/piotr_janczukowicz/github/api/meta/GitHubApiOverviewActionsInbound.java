package io.github.piotr_janczukowicz.github.api.meta;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Domains that GitHub Actions runners must be able to reach.
 *
 * @param fullDomains complete host names, or {@code null} when GitHub sends none
 * @param wildcardDomains host patterns with a wildcard, or {@code null} when GitHub sends none
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/api-overview/properties/domains/properties/actions_inbound")
public record GitHubApiOverviewActionsInbound(
        @Nullable List<String> fullDomains, @Nullable List<String> wildcardDomains) {}
