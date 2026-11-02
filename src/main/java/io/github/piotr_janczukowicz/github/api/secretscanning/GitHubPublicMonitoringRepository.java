package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Delivered details for the public repository where the secret was found.
 *
 * @param name The name of the repository.
 * @param fullName The full name of the repository, including the owner.
 * @param isPrivate Whether the repository is private.
 * @param htmlUrl The browser URL of the repository.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/public-monitoring-repository")
@Flavors(GitHubFlavor.GHEC)
public record GitHubPublicMonitoringRepository(
        String name,
        String fullName,
        @JsonProperty("private") boolean isPrivate,
        @Nullable String htmlUrl) implements GitHubPublicMonitoringAlertRepository {
    public GitHubPublicMonitoringRepository {
        requireNonNull(name, "name");
        requireNonNull(fullName, "fullName");
    }
}
