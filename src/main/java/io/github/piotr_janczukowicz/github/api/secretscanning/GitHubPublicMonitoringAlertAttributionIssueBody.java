package io.github.piotr_janczukowicz.github.api.secretscanning;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * The issue body location of a public monitoring alert: the full secret scanning location, or the delivered location.
 *
 * <p>The delivered location has only optional properties, so deduction cannot pick it from a small payload; it is the
 * default, and a payload with a property only the full location has decodes as
 * {@link GitHubSecretScanningLocationIssueBody}.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION, defaultImpl = GitHubPublicMonitoringAlertLocation.class)
@JsonSubTypes({
    @JsonSubTypes.Type(GitHubSecretScanningLocationIssueBody.class),
    @JsonSubTypes.Type(GitHubPublicMonitoringAlertLocation.class)
})
@CatalogSchema("components/schemas/public-monitoring-alert/properties/attribution/properties/issue_body")
@Flavors(GitHubFlavor.GHEC)
public sealed interface GitHubPublicMonitoringAlertAttributionIssueBody
        permits GitHubSecretScanningLocationIssueBody, GitHubPublicMonitoringAlertLocation {}
