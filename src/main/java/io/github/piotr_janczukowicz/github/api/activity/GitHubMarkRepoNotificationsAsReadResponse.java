package io.github.piotr_janczukowicz.github.api.activity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Response of marking repository notifications as read.
 *
 * @param message Description of the accepted request.
 * @param url The URL to check the status of the request.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/activity/mark-repo-notifications-as-read/202")
@RecordBuilder
public record GitHubMarkRepoNotificationsAsReadResponse(
        @Nullable String message, @Nullable String url) {}
