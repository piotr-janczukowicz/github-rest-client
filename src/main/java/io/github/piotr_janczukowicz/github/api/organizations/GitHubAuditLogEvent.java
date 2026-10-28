package io.github.piotr_janczukowicz.github.api.organizations;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.SuppressSchemaTypeMismatch;
import java.util.List;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * An audit log event of an organization or enterprise.
 *
 * @param timestamp The time the audit log event occurred, given as a Unix timestamp
 * @param action The name of the action that was performed, for example {@code user.login} or {@code repo.create}
 * @param active Whether the event is active
 * @param activeWas The previous value of the active flag
 * @param actor The actor who performed the action
 * @param actorId The ID of the actor who performed the action
 * @param actorLocation The location of the actor who performed the action
 * @param data Additional data of the event
 * @param orgId The ID of the organization
 * @param userId The ID of the user
 * @param businessId The ID of the business
 * @param blockedUser The username of the account being blocked
 * @param business The name of the business
 * @param config The configuration of the event
 * @param configWas The previous configuration of the event
 * @param contentType The content type of the event
 * @param operationType The operation type of the event
 * @param createdAt The time the audit log event was recorded, given as a Unix timestamp
 * @param deployKeyFingerprint The fingerprint of the deploy key
 * @param documentId A unique identifier for an audit event
 * @param emoji The emoji of the event
 * @param events The events of the audit log event
 * @param eventsWere The previous events of the audit log event
 * @param explanation The explanation of the event
 * @param fingerprint The fingerprint of the key
 * @param hookId The ID of the webhook
 * @param limitedAvailability Whether the feature has limited availability
 * @param message The message of the event
 * @param name The name of the event subject
 * @param oldUser The previous username
 * @param opensshPublicKey The OpenSSH public key
 * @param org The name of the organization
 * @param previousVisibility The previous visibility of the repository
 * @param readOnly Whether the access is read-only
 * @param repo The name of the repository
 * @param repository The name of the repository
 * @param repositoryPublic Whether the repository is public
 * @param targetLogin The login of the target account
 * @param team The name of the team
 * @param transportProtocol The type of protocol (for example, HTTP or SSH) used to transfer Git data
 * @param transportProtocolName A human readable name for the protocol used to transfer Git data
 * @param user The user that was affected by the action performed, if available
 * @param visibility The repository visibility, for example {@code public} or {@code private}
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/audit-log-event")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubAuditLogEvent(
        @JsonProperty("@timestamp") @Nullable Long timestamp,
        @Nullable String action,
        @Nullable Boolean active,
        @Nullable Boolean activeWas,
        @Nullable String actor,
        @Nullable Long actorId,
        @Nullable GitHubAuditLogEventActorLocation actorLocation,
        @Nullable Map<String, Object> data,
        @Nullable Long orgId,
        @Nullable Long userId,
        @Nullable Long businessId,
        @Nullable String blockedUser,
        @Nullable String business,

        @SuppressSchemaTypeMismatch(GitHubAuditLogEvent.EMPTY_OBJECT_ITEMS) @Nullable List<Map<String, Object>> config,

        @SuppressSchemaTypeMismatch(GitHubAuditLogEvent.EMPTY_OBJECT_ITEMS) @Nullable List<Map<String, Object>> configWas,

        @Nullable String contentType,
        @Nullable String operationType,
        @Nullable Long createdAt,
        @Nullable String deployKeyFingerprint,
        @JsonProperty("_document_id") @Nullable String documentId,
        @Nullable String emoji,

        @SuppressSchemaTypeMismatch(GitHubAuditLogEvent.EMPTY_OBJECT_ITEMS) @Nullable List<Map<String, Object>> events,

        @SuppressSchemaTypeMismatch(GitHubAuditLogEvent.EMPTY_OBJECT_ITEMS) @Nullable List<Map<String, Object>> eventsWere,

        @Nullable String explanation,
        @Nullable String fingerprint,
        @Nullable Long hookId,
        @Nullable Boolean limitedAvailability,
        @Nullable String message,
        @Nullable String name,
        @Nullable String oldUser,
        @Nullable String opensshPublicKey,
        @Nullable String org,
        @Nullable String previousVisibility,
        @Nullable Boolean readOnly,
        @Nullable String repo,
        @Nullable String repository,
        @Nullable Boolean repositoryPublic,
        @Nullable String targetLogin,
        @Nullable String team,
        @Nullable Integer transportProtocol,
        @Nullable String transportProtocolName,
        @Nullable String user,
        @Nullable String visibility) {

    static final String EMPTY_OBJECT_ITEMS = "dump items are empty object; java is Map";
}
