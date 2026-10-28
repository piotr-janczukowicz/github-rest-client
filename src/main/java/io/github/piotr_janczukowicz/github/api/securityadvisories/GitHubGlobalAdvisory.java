package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLength;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLengthIfPresent;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * A GitHub Security Advisory.
 *
 * @param ghsaId The GitHub Security Advisory ID.
 * @param cveId The Common Vulnerabilities and Exposures (CVE) ID.
 * @param url The API URL for the advisory.
 * @param htmlUrl The URL for the advisory.
 * @param repositoryAdvisoryUrl The API URL for the repository advisory.
 * @param summary A short summary of the advisory.
 * @param description A detailed description of what the advisory entails.
 * @param type The type of advisory.
 * @param severity The severity of the advisory.
 * @param sourceCodeLocation The URL of the advisory's source code.
 * @param identifiers Identifiers
 * @param references References
 * @param publishedAt The date and time of when the advisory was published, in ISO 8601 format.
 * @param updatedAt The date and time of when the advisory was last updated, in ISO 8601 format.
 * @param githubReviewedAt The date and time of when the advisory was reviewed by GitHub, in ISO 8601 format.
 * @param nvdPublishedAt The date and time when the advisory was published in the National Vulnerability Database, in
 *     ISO 8601 format. This field is only populated when the advisory is imported from the National Vulnerability
 *     Database.
 * @param withdrawnAt The date and time of when the advisory was withdrawn, in ISO 8601 format.
 * @param vulnerabilities The products and respective version ranges affected by the advisory.
 * @param cvssSeverities Cvss severities
 * @param epss The EPSS scores as calculated by the [Exploit Prediction Scoring System](https://www.first.org/epss).
 * @param cwes Cwes
 * @param credits The users who contributed to the advisory.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/global-advisory")
public record GitHubGlobalAdvisory(
        String ghsaId,
        @Nullable String cveId,
        String url,
        String htmlUrl,
        @Nullable String repositoryAdvisoryUrl,
        String summary,
        @Nullable String description,
        GitHubGlobalAdvisoryType type,
        GitHubSecurityAdvisorySeverity severity,
        @Nullable String sourceCodeLocation,
        @Nullable List<GitHubAdvisoryIdentifier> identifiers,
        @Nullable List<String> references,
        Instant publishedAt,
        Instant updatedAt,
        @Nullable Instant githubReviewedAt,
        @Nullable Instant nvdPublishedAt,
        @Nullable Instant withdrawnAt,
        @Nullable List<GitHubVulnerability> vulnerabilities,
        @Nullable GitHubCvssSeverities cvssSeverities,
        @Nullable GitHubSecurityAdvisoryEpss epss,
        @Nullable List<GitHubAdvisoryCwe> cwes,
        @Nullable List<GitHubGlobalAdvisoryCredit> credits) {

    public GitHubGlobalAdvisory {
        requireNonNull(ghsaId, "ghsaId");
        requireNonNull(url, "url");
        requireNonNull(htmlUrl, "htmlUrl");
        summary = requireMaxLength(summary, 1024, "summary");
        description = requireMaxLengthIfPresent(description, 65_535, "description");
        requireNonNull(type, "type");
        requireNonNull(severity, "severity");
        requireNonNull(publishedAt, "publishedAt");
        requireNonNull(updatedAt, "updatedAt");
    }
}
