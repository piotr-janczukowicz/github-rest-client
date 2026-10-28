package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * GitHubLiveMigrationDetailsMessage
 *
 * @param messageType message type
 * @param message message
 * @param createdAt created at
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/live-migration-get/200/properties/messages/items")
@Flavors(GitHubFlavor.GHES)
public record GitHubLiveMigrationDetailsMessage(
        String messageType, String message, @Nullable Instant createdAt) {
    public GitHubLiveMigrationDetailsMessage {
        requireNonNull(messageType, "messageType");
        requireNonNull(message, "message");
    }
}
