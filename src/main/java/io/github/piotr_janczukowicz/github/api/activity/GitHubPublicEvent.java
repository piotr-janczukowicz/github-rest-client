package io.github.piotr_janczukowicz.github.api.activity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/** Public event payload. The payload is an empty object. */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/public-event")
public record GitHubPublicEvent() implements GitHubEventPayload {}
