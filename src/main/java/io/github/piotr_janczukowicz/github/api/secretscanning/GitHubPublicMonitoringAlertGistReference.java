package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Delivered reference to the gist the secret was found in.
 *
 * @param id The identifier of the gist.
 * @param htmlUrl The browser URL of the gist.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/public-monitoring-alert/properties/gist/anyOf/1")
@Flavors(GitHubFlavor.GHEC)
public record GitHubPublicMonitoringAlertGistReference(
        String id, @Nullable String htmlUrl) implements GitHubPublicMonitoringAlertGist {
    public GitHubPublicMonitoringAlertGistReference {
        requireNonNull(id, "id");
    }
}
