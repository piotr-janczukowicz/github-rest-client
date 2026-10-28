package io.github.piotr_janczukowicz.github.api.codespaces;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Runtime constraints of a codespace.
 *
 * @param allowedPortPrivacySettings The privacy settings a user can select from when forwarding a port.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "components/schemas/codespace/properties/runtime_constraints",
    "components/schemas/codespace-with-full-repository/properties/runtime_constraints"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCodespaceRuntimeConstraints(@Nullable List<String> allowedPortPrivacySettings) {}
