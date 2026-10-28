package io.github.piotr_janczukowicz.github.api.copilot;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Result of setting Copilot content exclusion rules for an enterprise.
 *
 * @param message Message.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/copilot/set-copilot-content-exclusion-for-enterprise/200")
@Flavors(GitHubFlavor.GHEC)
public record GitHubSetCopilotContentExclusionForEnterpriseResponse(
        @Nullable String message) {}
