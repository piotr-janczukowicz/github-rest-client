package io.github.piotr_janczukowicz.github.api.apps;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * GitHub App created from a manifest, with its credentials.
 *
 * @param id Unique identifier of the GitHub app
 * @param slug The slug name of the GitHub app
 * @param nodeId Node id
 * @param clientId Client id
 * @param clientSecret Client secret
 * @param webhookSecret Webhook secret
 * @param pem Pem
 * @param owner Owner
 * @param name The name of the GitHub app
 * @param description Description
 * @param externalUrl External url
 * @param htmlUrl Html url
 * @param createdAt Created at
 * @param updatedAt Updated at
 * @param permissions The set of permissions for the GitHub app
 * @param events The list of events for the GitHub app
 * @param installationsCount The number of installations associated with the GitHub app
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/apps/create-from-manifest/201")
public record GitHubCreateFromManifestResponse(
        long id,
        @Nullable String slug,
        String nodeId,
        String clientId,
        String clientSecret,
        @Nullable String webhookSecret,
        String pem,
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

    public GitHubCreateFromManifestResponse {
        requireNonNull(nodeId, "nodeId");
        requireNonNull(clientId, "clientId");
        requireNonNull(clientSecret, "clientSecret");
        requireNonNull(pem, "pem");
        requireNonNull(owner, "owner");
        requireNonNull(name, "name");
        requireNonNull(externalUrl, "externalUrl");
        requireNonNull(htmlUrl, "htmlUrl");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
        requireNonNull(permissions, "permissions");
        requireNonNull(events, "events");
    }

    /**
     * Describes this record without its secrets.
     *
     * @return text with {@code clientSecret, webhookSecret, pem} redacted
     */
    @Override
    public String toString() {
        return "GitHubCreateFromManifestResponse[id=" + id + ", slug=" + slug + ", nodeId=" + nodeId + ", clientId="
                + clientId + ", clientSecret=<redacted>, webhookSecret=<redacted>, pem=<redacted>, owner=" + owner
                + ", name=" + name + ", description=" + description + ", externalUrl=" + externalUrl + ", htmlUrl="
                + htmlUrl + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", permissions=" + permissions
                + ", events=" + events + ", installationsCount=" + installationsCount + "]";
    }
}
