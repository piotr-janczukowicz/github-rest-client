package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLength;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLengthIfPresent;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubSimpleRepository;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeam;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * A repository security advisory.
 *
 * @param ghsaId The GitHub Security Advisory ID.
 * @param cveId The Common Vulnerabilities and Exposures (CVE) ID.
 * @param url The API URL for the advisory.
 * @param htmlUrl The URL for the advisory.
 * @param summary A short summary of the advisory.
 * @param description A detailed description of what the advisory entails.
 * @param severity The severity of the advisory.
 * @param author The author of the advisory.
 * @param publisher The publisher of the advisory.
 * @param identifiers Identifiers
 * @param state The state of the advisory.
 * @param createdAt The date and time of when the advisory was created, in ISO 8601 format.
 * @param updatedAt The date and time of when the advisory was last updated, in ISO 8601 format.
 * @param publishedAt The date and time of when the advisory was published, in ISO 8601 format.
 * @param closedAt The date and time of when the advisory was closed, in ISO 8601 format.
 * @param withdrawnAt The date and time of when the advisory was withdrawn, in ISO 8601 format.
 * @param submission Submission
 * @param vulnerabilities Vulnerabilities
 * @param cvssSeverities Cvss severities
 * @param cwes Cwes
 * @param cweIds A list of only the CWE IDs.
 * @param credits Credits
 * @param creditsDetailed Credits detailed
 * @param collaboratingUsers A list of users that collaborate on the advisory.
 * @param collaboratingTeams A list of teams that collaborate on the advisory.
 * @param privateFork A temporary private fork of the advisory's repository for collaborating on a fix.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/repository-advisory")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubRepositoryAdvisory(
        String ghsaId,
        @Nullable String cveId,
        String url,
        String htmlUrl,
        String summary,
        @Nullable String description,
        @Nullable GitHubRepositoryAdvisorySeverity severity,
        @Nullable GitHubSimpleUser author,
        @Nullable GitHubSimpleUser publisher,
        List<GitHubAdvisoryIdentifier> identifiers,
        GitHubRepositoryAdvisoryState state,
        @Nullable Instant createdAt,
        @Nullable Instant updatedAt,
        @Nullable Instant publishedAt,
        @Nullable Instant closedAt,
        @Nullable Instant withdrawnAt,
        @Nullable GitHubRepositoryAdvisorySubmission submission,
        @Nullable List<GitHubRepositoryAdvisoryVulnerability> vulnerabilities,
        @Nullable GitHubCvssSeverities cvssSeverities,
        @Nullable List<GitHubAdvisoryCwe> cwes,
        @Nullable List<String> cweIds,
        @Nullable List<GitHubRepositoryAdvisoryCreditSummary> credits,
        @Nullable List<GitHubRepositoryAdvisoryCredit> creditsDetailed,
        @Nullable List<GitHubSimpleUser> collaboratingUsers,
        @Nullable List<GitHubTeam> collaboratingTeams,
        @Nullable GitHubSimpleRepository privateFork) {

    public GitHubRepositoryAdvisory {
        requireNonNull(ghsaId, "ghsaId");
        requireNonNull(url, "url");
        requireNonNull(htmlUrl, "htmlUrl");
        summary = requireMaxLength(summary, 1024, "summary");
        description = requireMaxLengthIfPresent(description, 65_535, "description");
        requireNonNull(identifiers, "identifiers");
        requireNonNull(state, "state");
    }
}
