package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.StringUtils.isBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /orgs/{org}/rulesets}, excluding pagination.
 *
 * @param targets Comma-separated ruleset targets ({@code branch}, {@code tag}, {@code push}, {@code repository})
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/repos/get-org-rulesets/parameters")
@RecordBuilder
public record GitHubListOrgRulesetsFilter(@Nullable String targets) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListOrgRulesetsFilter empty() {
        return GitHubListOrgRulesetsFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (isBlank(targets)) {
            return Map.of();
        }

        return Map.of("targets", targets);
    }
}
