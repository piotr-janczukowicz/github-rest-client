package io.github.piotr_janczukowicz.github.api.releases;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code POST /repos/{owner}/{repo}/releases/{release_id}/assets}, excluding pagination.
 *
 * @param name name
 * @param label label
 */
@CatalogSchema("operations/repos/upload-release-asset/parameters")
@RecordBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record GitHubUploadReleaseAssetFilter(
        String name, @Nullable String label) {

    public GitHubUploadReleaseAssetFilter {
        requireNonNull(name, "name");
    }

    /**
     * Filter with only the required name set.
     *
     * @param name name
     * @return filter
     */
    public static GitHubUploadReleaseAssetFilter of(final String name) {
        return GitHubUploadReleaseAssetFilterBuilder.builder().name(name).build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();

        query.put("name", name);
        if (label != null) {
            query.put("label", label);
        }

        return Collections.unmodifiableMap(query);
    }
}
