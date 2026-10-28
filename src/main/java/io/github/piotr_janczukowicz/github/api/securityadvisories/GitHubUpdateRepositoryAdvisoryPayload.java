package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLengthIfPresent;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Update repository advisory.
 *
 * @param summary A short summary of the advisory.
 * @param description A detailed description of what the advisory impacts.
 * @param cveId The Common Vulnerabilities and Exposures (CVE) ID.
 * @param vulnerabilities A product affected by the vulnerability detailed in a repository security advisory.
 * @param cweIds A list of Common Weakness Enumeration (CWE) IDs.
 * @param credits A list of users receiving credit for their participation in the security advisory.
 * @param severity The severity of the advisory.
 * @param cvssVectorString The CVSS vector that calculates the severity of the advisory.
 * @param state The state of the advisory.
 * @param collaboratingUsers A list of usernames who have been granted write access to the advisory.
 * @param collaboratingTeams A list of team slugs which have been granted write access to the advisory.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/security-advisories/update-repository-advisory/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubUpdateRepositoryAdvisoryPayload(
        @Nullable String summary,
        @Nullable String description,
        @Nullable String cveId,
        @Nullable List<GitHubRepositoryAdvisoryVulnerabilityPayload> vulnerabilities,
        @Nullable List<String> cweIds,
        @Nullable List<GitHubRepositoryAdvisoryCreditPayload> credits,
        @Nullable GitHubRepositoryAdvisorySeverity severity,
        @Nullable String cvssVectorString,
        @Nullable GitHubRepositoryAdvisoryUpdateState state,
        @Nullable List<String> collaboratingUsers,
        @Nullable List<String> collaboratingTeams) {

    public GitHubUpdateRepositoryAdvisoryPayload {
        summary = requireMaxLengthIfPresent(summary, 1024, "summary");
        description = requireMaxLengthIfPresent(description, 65_535, "description");
        if (vulnerabilities != null) {
            vulnerabilities = List.copyOf(vulnerabilities);
        }
        if (cweIds != null) {
            cweIds = List.copyOf(cweIds);
        }
        if (credits != null) {
            credits = List.copyOf(credits);
        }
        if (collaboratingUsers != null) {
            collaboratingUsers = List.copyOf(collaboratingUsers);
        }
        if (collaboratingTeams != null) {
            collaboratingTeams = List.copyOf(collaboratingTeams);
        }
    }
}
