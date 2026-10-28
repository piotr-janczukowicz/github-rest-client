package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLength;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.securityadvisories.GitHubCvssSeverities;
import io.github.piotr_janczukowicz.github.api.securityadvisories.GitHubSecurityAdvisoryEpss;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Details for the GitHub Security Advisory.
 *
 * @param ghsaId The unique GitHub Security Advisory ID assigned to the advisory.
 * @param cveId The unique CVE ID assigned to the advisory.
 * @param summary A short, plain text summary of the advisory.
 * @param description A long-form Markdown-supported description of the advisory.
 * @param vulnerabilities Vulnerable version range information for the advisory.
 * @param severity The severity of the advisory.
 * @param classification The classification of the advisory.
 * @param cvssSeverities Cvss severities
 * @param epss Epss
 * @param cwes Details for the advisory pertaining to Common Weakness Enumeration.
 * @param identifiers Values that identify this advisory among security information sources.
 * @param references Links to additional advisory information.
 * @param publishedAt The time that the advisory was published in ISO 8601 format: {@code YYYY-MM-DDTHH:MM:SSZ}.
 * @param updatedAt The time that the advisory was last modified in ISO 8601 format: {@code YYYY-MM-DDTHH:MM:SSZ}.
 * @param withdrawnAt The time that the advisory was withdrawn in ISO 8601 format: {@code YYYY-MM-DDTHH:MM:SSZ}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/dependabot-alert-security-advisory")
public record GitHubDependabotAlertSecurityAdvisory(
        String ghsaId,
        @Nullable String cveId,
        String summary,
        String description,
        List<GitHubDependabotAlertSecurityVulnerability> vulnerabilities,
        GitHubDependabotAlertSeverity severity,
        @Nullable GitHubDependabotAlertClassification classification,
        @Nullable GitHubCvssSeverities cvssSeverities,
        @Nullable GitHubSecurityAdvisoryEpss epss,
        List<GitHubDependabotAlertSecurityAdvisoryCwe> cwes,
        List<GitHubDependabotAlertSecurityAdvisoryIdentifier> identifiers,
        List<GitHubDependabotAlertSecurityAdvisoryReference> references,
        Instant publishedAt,
        Instant updatedAt,
        @Nullable Instant withdrawnAt) {

    private static final int MAX_SUMMARY_LENGTH = 1024;

    public GitHubDependabotAlertSecurityAdvisory {
        requireNonNull(ghsaId, "ghsaId");
        summary = requireMaxLength(summary, MAX_SUMMARY_LENGTH, "summary");
        requireNonNull(description, "description");
        requireNonNull(vulnerabilities, "vulnerabilities");
        requireNonNull(severity, "severity");
        requireNonNull(cwes, "cwes");
        requireNonNull(identifiers, "identifiers");
        requireNonNull(references, "references");
        requireNonNull(publishedAt, "publishedAt");
        requireNonNull(updatedAt, "updatedAt");
    }
}
