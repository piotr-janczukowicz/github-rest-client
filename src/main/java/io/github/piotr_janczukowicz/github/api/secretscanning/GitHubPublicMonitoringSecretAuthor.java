package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Delivered public details for the user attributed as the secret author.
 *
 * @param login The login of the user.
 * @param avatarUrl The avatar URL of the user.
 * @param htmlUrl The profile URL of the user.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/public-monitoring-secret-author")
@Flavors(GitHubFlavor.GHEC)
public record GitHubPublicMonitoringSecretAuthor(
        String login, @Nullable String avatarUrl, @Nullable String htmlUrl)
        implements GitHubPublicMonitoringAlertSecretAuthor {
    public GitHubPublicMonitoringSecretAuthor {
        requireNonNull(login, "login");
    }
}
