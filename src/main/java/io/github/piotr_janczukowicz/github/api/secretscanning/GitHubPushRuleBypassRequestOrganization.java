package io.github.piotr_janczukowicz.github.api.secretscanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * The organization associated with the repository a push rule bypass request is for.
 *
 * @param id The ID of the organization
 * @param name The name of the organization
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/push-rule-bypass-request/properties/organization")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubPushRuleBypassRequestOrganization(
        @Nullable Long id, @Nullable String name) {}
