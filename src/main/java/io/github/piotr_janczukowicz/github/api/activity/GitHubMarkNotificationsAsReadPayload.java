package io.github.piotr_janczukowicz.github.api.activity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Mark notifications as read request body.
 *
 * @param lastReadAt Describes the last point that notifications were checked. Anything updated since this time will not
 *     be updated.
 * @param read Whether the notification has been read.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/activity/mark-notifications-as-read/requestBody")
@RecordBuilder
public record GitHubMarkNotificationsAsReadPayload(
        @Nullable Instant lastReadAt, @Nullable Boolean read) {}
