package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.GhesVersions;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * GitHubManageUpgradeStatusStep
 *
 * @param phase The upgrade phase: `pre-upgrade` or `upgrade`.
 * @param step The step name.
 * @param status The step status. One of: `not started`, `in progress`, `completed`, `failed`.
 * @param startedAt The time when the step started in ISO 8601 format.
 * @param endedAt The time when the step ended in ISO 8601 format.
 * @param error An error message if the step failed.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ghes-get-manage-upgrade-status-response/items/properties/steps/items")
@Flavors(GitHubFlavor.GHES)
@GhesVersions(since = "3.22")
public record GitHubManageUpgradeStatusStep(
        @Nullable String phase,
        @Nullable String step,
        @Nullable String status,
        @Nullable Instant startedAt,
        @Nullable Instant endedAt,
        @Nullable String error) {}
