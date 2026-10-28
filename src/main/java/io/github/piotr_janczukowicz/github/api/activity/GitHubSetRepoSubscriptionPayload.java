package io.github.piotr_janczukowicz.github.api.activity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Set repository subscription request body.
 *
 * @param subscribed Determines if notifications should be received from this repository.
 * @param ignored Determines if all notifications should be blocked from this repository.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/activity/set-repo-subscription/requestBody")
@RecordBuilder
public record GitHubSetRepoSubscriptionPayload(
        @Nullable Boolean subscribed, @Nullable Boolean ignored) {}
