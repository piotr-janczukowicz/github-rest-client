package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubMinimalRepository;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Thread. A notification thread.
 *
 * @param id Thread id
 * @param repository Minimal repository the thread belongs to
 * @param subject Thread subject
 * @param reason Reason the thread was received
 * @param unread Whether the thread is unread
 * @param updatedAt Time the thread was last updated
 * @param lastReadAt Time the thread was last read
 * @param url Thread url
 * @param subscriptionUrl Thread subscription url
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/thread")
@RecordBuilder
public record GitHubThread(
        String id,
        GitHubMinimalRepository repository,
        GitHubThreadSubject subject,
        String reason,
        boolean unread,
        String updatedAt,
        @Nullable String lastReadAt,
        String url,
        String subscriptionUrl) {

    public GitHubThread {
        requireNonNull(id, "id");
        requireNonNull(repository, "repository");
        requireNonNull(subject, "subject");
        requireNonNull(reason, "reason");
        requireNonNull(updatedAt, "updatedAt");
        requireNonNull(url, "url");
        requireNonNull(subscriptionUrl, "subscriptionUrl");
    }
}
