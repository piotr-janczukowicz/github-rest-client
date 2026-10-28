package io.github.piotr_janczukowicz.github.api.meta;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Domains that GitHub features use, grouped by feature; a {@code null} group was not sent.
 *
 * @param website domains of the web site
 * @param codespaces domains of Codespaces
 * @param copilot domains of Copilot
 * @param packages domains of GitHub Packages
 * @param storage domains of GitHub storage
 * @param actions domains of GitHub Actions
 * @param actionsInbound domains that Actions runners must reach
 * @param artifactAttestations trust domain and services of artifact attestations
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/api-overview/properties/domains")
public record GitHubApiOverviewDomains(
        @Nullable List<String> website,
        @Nullable List<String> codespaces,
        @Nullable List<String> copilot,
        @Nullable List<String> packages,
        @Nullable List<String> storage,
        @Nullable List<String> actions,
        @Nullable GitHubApiOverviewActionsInbound actionsInbound,
        @Nullable GitHubApiOverviewArtifactAttestations artifactAttestations) {}
