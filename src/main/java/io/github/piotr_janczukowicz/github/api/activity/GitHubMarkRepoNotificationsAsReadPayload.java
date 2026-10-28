package io.github.piotr_janczukowicz.github.api.activity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Mark repository notifications as read request body.
 *
 * @param lastReadAt Describes the last point that notifications were checked. Anything updated since this time will not
 *     be updated.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/activity/mark-repo-notifications-as-read/requestBody")
@RecordBuilder
public record GitHubMarkRepoNotificationsAsReadPayload(
        @Nullable Instant lastReadAt) {}
