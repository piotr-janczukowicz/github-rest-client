package io.github.piotr_janczukowicz.github.api.apps;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * GitHub app. GitHub apps are a new way to extend GitHub. They can be installed directly on organizations and user
 * accounts and granted access to specific repositories. They come with granular permissions and built-in webhooks.
 * GitHub apps are first class actors within GitHub.
 *
 * @param id Unique identifier of the GitHub app
 * @param slug The slug name of the GitHub app
 * @param nodeId Node id
 * @param clientId Client id
 * @param owner Owner
 * @param name The name of the GitHub app
 * @param description Description
 * @param externalUrl External url
 * @param htmlUrl Html url
 * @param createdAt Created at
 * @param updatedAt Updated at
 * @param permissions The set of permissions for the GitHub app
 * @param events The list of events for the GitHub app. Note that the {@code installation_target},
 *     {@code security_advisory}, and {@code meta} events are not included because they are global events and not
 *     specific to an installation.
 * @param installationsCount The number of installations associated with the GitHub app. Only returned when the
 *     integration is requesting details about itself.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/integration")
public record GitHubIntegration(
        long id,
        @Nullable String slug,
        String nodeId,
        @Nullable String clientId,
        GitHubIntegrationOwner owner,
        String name,
        @Nullable String description,
        String externalUrl,
        String htmlUrl,
        Instant createdAt,
        Instant updatedAt,
        GitHubIntegrationPermissions permissions,
        List<String> events,
        @Nullable Integer installationsCount) {

    public GitHubIntegration {
        requireNonNull(nodeId, "nodeId");
        requireNonNull(owner, "owner");
        requireNonNull(name, "name");
        requireNonNull(externalUrl, "externalUrl");
        requireNonNull(htmlUrl, "htmlUrl");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
        requireNonNull(permissions, "permissions");
        requireNonNull(events, "events");
    }
}
