package io.github.piotr_janczukowicz.github.api.activity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Response of marking all notifications as read.
 *
 * @param message Description of the accepted request.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/activity/mark-notifications-as-read/202")
@RecordBuilder
public record GitHubMarkNotificationsAsReadResponse(
        @Nullable String message) {}
