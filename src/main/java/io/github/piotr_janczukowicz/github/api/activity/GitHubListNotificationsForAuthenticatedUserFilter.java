package io.github.piotr_janczukowicz.github.api.activity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /notifications}, excluding pagination.
 *
 * @param all If {@code true}, show notifications marked as read.
 * @param participating If {@code true}, only shows notifications in which the user is directly participating or
 *     mentioned.
 * @param since Only show notifications updated after the given time.
 * @param before Only show notifications updated before the given time.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/activity/list-notifications-for-authenticated-user/parameters")
@RecordBuilder
public record GitHubListNotificationsForAuthenticatedUserFilter(
        @Nullable Boolean all,
        @Nullable Boolean participating,
        @Nullable Instant since,
        @Nullable Instant before) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListNotificationsForAuthenticatedUserFilter empty() {
        return GitHubListNotificationsForAuthenticatedUserFilterBuilder.builder()
                .build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();

        if (all != null) {
            query.put("all", all.toString());
        }
        if (participating != null) {
            query.put("participating", participating.toString());
        }
        if (since != null) {
            query.put("since", since.toString());
        }
        if (before != null) {
            query.put("before", before.toString());
        }

        return Collections.unmodifiableMap(query);
    }
}
