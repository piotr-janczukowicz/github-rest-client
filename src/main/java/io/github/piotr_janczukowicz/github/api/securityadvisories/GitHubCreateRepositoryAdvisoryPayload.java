package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLength;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Create repository advisory.
 *
 * @param summary A short summary of the advisory.
 * @param description A detailed description of what the advisory impacts.
 * @param vulnerabilities A product affected by the vulnerability detailed in a repository security advisory.
 * @param cveId The Common Vulnerabilities and Exposures (CVE) ID.
 * @param cweIds A list of Common Weakness Enumeration (CWE) IDs.
 * @param credits A list of users receiving credit for their participation in the security advisory.
 * @param severity The severity of the advisory.
 * @param cvssVectorString The CVSS vector that calculates the severity of the advisory.
 * @param startPrivateFork Whether to create a temporary private fork of the repository to collaborate on a fix.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/security-advisories/create-repository-advisory/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCreateRepositoryAdvisoryPayload(
        String summary,
        String description,
        List<GitHubRepositoryAdvisoryVulnerabilityPayload> vulnerabilities,
        @Nullable String cveId,
        @Nullable List<String> cweIds,
        @Nullable List<GitHubRepositoryAdvisoryCreditPayload> credits,
        @Nullable GitHubRepositoryAdvisorySeverity severity,
        @Nullable String cvssVectorString,
        @Nullable Boolean startPrivateFork) {

    public GitHubCreateRepositoryAdvisoryPayload {
        summary = requireMaxLength(summary, 1024, "summary");
        description = requireMaxLength(description, 65_535, "description");
        vulnerabilities = List.copyOf(requireNonNull(vulnerabilities, "vulnerabilities"));
        if (cweIds != null) {
            cweIds = List.copyOf(cweIds);
        }
        if (credits != null) {
            credits = List.copyOf(credits);
        }
    }
}
