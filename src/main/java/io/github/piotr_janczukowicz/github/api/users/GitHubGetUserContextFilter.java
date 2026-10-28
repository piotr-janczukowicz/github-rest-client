package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.util.StringUtils.stripToNull;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /users/{username}/hovercard}, excluding pagination.
 *
 * @param subjectType Identifies which additional information you'd like to receive about the person's hovercard. Can be
 *     `organization`, `repository`, `issue`, `pull_request`. **Required** when using `subject_id`.
 * @param subjectId Uses the ID for the `subject_type` you specified. **Required** when using `subject_type`.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/users/get-context-for-user/parameters")
@RecordBuilder
public record GitHubGetUserContextFilter(
        @Nullable GitHubUserContextSubjectType subjectType,
        @Nullable String subjectId) {

    public GitHubGetUserContextFilter {
        subjectId = stripToNull(subjectId);
        if ((subjectType == null) != (subjectId == null)) {
            throw new IllegalArgumentException("subject_type and subject_id must both be set or both be omitted");
        }
    }

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubGetUserContextFilter empty() {
        return GitHubGetUserContextFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (subjectType == null) {
            return Map.of();
        }
        return Map.of("subject_type", subjectType.wireValue(), "subject_id", requireNonNull(subjectId, "subjectId"));
    }
}
