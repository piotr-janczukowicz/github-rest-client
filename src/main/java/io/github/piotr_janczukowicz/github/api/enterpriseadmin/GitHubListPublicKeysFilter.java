package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code list-public-keys}, excluding pagination.
 *
 * @param sort The property to sort the results by
 * @param direction The direction to sort the results by
 * @param since Only show public keys accessed after the given time
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/enterprise-admin/list-public-keys/parameters")
@RecordBuilder
@Flavors(GitHubFlavor.GHES)
public record GitHubListPublicKeysFilter(
        @Nullable GitHubListPublicKeysSort sort,
        @Nullable GitHubDirection direction,
        @Nullable Instant since) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListPublicKeysFilter empty() {
        return GitHubListPublicKeysFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();
        if (sort != null) {
            query.put("sort", sort.wireValue());
        }
        if (direction != null) {
            query.put("direction", direction.wireValue());
        }
        if (since != null) {
            query.put("since", since.toString());
        }
        return Collections.unmodifiableMap(query);
    }
}
