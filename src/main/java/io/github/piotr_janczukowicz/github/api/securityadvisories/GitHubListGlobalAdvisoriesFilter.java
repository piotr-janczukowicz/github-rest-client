package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeAtMost;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.CatalogSimpleXor;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /advisories}, excluding pagination.
 *
 * @param ghsaId If specified, only advisories with this GHSA ID will be returned.
 * @param type If specified, only advisories of this type will be returned.
 * @param cveId If specified, only advisories with this CVE ID will be returned.
 * @param ecosystem If specified, only advisories for these ecosystems will be returned.
 * @param severity If specified, only advisories with this severity will be returned.
 * @param cwes If specified, only advisories with these CWE IDs will be returned.
 * @param isWithdrawn Whether to only return advisories that have been withdrawn.
 * @param affects If specified, only return advisories that affect any of {@code package} or {@code package@version}.
 * @param published If specified, only return advisories that were published on a date or date range.
 * @param updated If specified, only return advisories that were updated on a date or date range.
 * @param modified If specified, only show advisories that were updated or published on a date or date range.
 * @param epssPercentage If specified, only return advisories that have an EPSS percentage that matches the provided
 *     value.
 * @param epssPercentile If specified, only return advisories that have an EPSS percentile that matches the provided
 *     value.
 * @param direction The direction to sort the results by.
 * @param sort The property to sort the results by.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/security-advisories/list-global-advisories/parameters")
@RecordBuilder
public record GitHubListGlobalAdvisoriesFilter(
        @Nullable String ghsaId,
        @Nullable GitHubGlobalAdvisoryType type,
        @Nullable String cveId,
        @Nullable GitHubSecurityAdvisoryEcosystem ecosystem,
        @Nullable GitHubSecurityAdvisorySeverity severity,
        @Nullable @CatalogSimpleXor("string|array") List<String> cwes,
        @Nullable Boolean isWithdrawn,
        @Nullable @CatalogSimpleXor("string|array") List<String> affects,
        @Nullable String published,
        @Nullable String updated,
        @Nullable String modified,
        @Nullable String epssPercentage,
        @Nullable String epssPercentile,
        @Nullable GitHubDirection direction,
        @Nullable GitHubGlobalAdvisoriesSort sort) {

    public GitHubListGlobalAdvisoriesFilter {
        if (cwes != null) {
            cwes = List.copyOf(cwes);
        }
        if (affects != null) {
            affects = List.copyOf(requireSizeAtMost(affects, 1000, "affects"));
        }
    }

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListGlobalAdvisoriesFilter empty() {
        return GitHubListGlobalAdvisoriesFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    @SuppressWarnings({"java:S3776", "PMD.CognitiveComplexity"})
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();

        if (ghsaId != null) {
            query.put("ghsa_id", ghsaId);
        }
        if (type != null) {
            query.put("type", type.wireValue());
        }
        if (cveId != null) {
            query.put("cve_id", cveId);
        }
        if (ecosystem != null) {
            query.put("ecosystem", ecosystem.wireValue());
        }
        if (severity != null) {
            query.put("severity", severity.wireValue());
        }
        if (cwes != null) {
            query.put("cwes", String.join(",", cwes));
        }
        if (isWithdrawn != null) {
            query.put("is_withdrawn", isWithdrawn.toString());
        }
        if (affects != null) {
            query.put("affects", String.join(",", affects));
        }
        if (published != null) {
            query.put("published", published);
        }
        if (updated != null) {
            query.put("updated", updated);
        }
        if (modified != null) {
            query.put("modified", modified);
        }
        if (epssPercentage != null) {
            query.put("epss_percentage", epssPercentage);
        }
        if (epssPercentile != null) {
            query.put("epss_percentile", epssPercentile);
        }
        if (direction != null) {
            query.put("direction", direction.wireValue());
        }
        if (sort != null) {
            query.put("sort", sort.wireValue());
        }

        return Collections.unmodifiableMap(query);
    }
}
