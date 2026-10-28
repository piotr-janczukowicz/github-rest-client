package io.github.piotr_janczukowicz.github.api.organizations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * The location of the actor who performed an audit log event.
 *
 * @param countryName The name of the country the actor was located in
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/audit-log-event/properties/actor_location")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubAuditLogEventActorLocation(@Nullable String countryName) {}
