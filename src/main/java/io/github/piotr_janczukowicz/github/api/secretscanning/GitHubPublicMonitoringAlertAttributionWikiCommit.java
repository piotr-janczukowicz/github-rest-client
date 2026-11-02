package io.github.piotr_janczukowicz.github.api.secretscanning;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * The wiki commit location of a public monitoring alert: the full secret scanning location, or the delivered location.
 *
 * <p>The delivered location has only optional properties, so deduction cannot pick it from a small payload; it is the
 * default, and a payload with a property only the full location has decodes as
 * {@link GitHubSecretScanningLocationWikiCommit}.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION, defaultImpl = GitHubPublicMonitoringAlertLocation.class)
@JsonSubTypes({
    @JsonSubTypes.Type(GitHubSecretScanningLocationWikiCommit.class),
    @JsonSubTypes.Type(GitHubPublicMonitoringAlertLocation.class)
})
@CatalogSchema("components/schemas/public-monitoring-alert/properties/attribution/properties/wiki_commit")
@Flavors(GitHubFlavor.GHEC)
public sealed interface GitHubPublicMonitoringAlertAttributionWikiCommit
        permits GitHubSecretScanningLocationWikiCommit, GitHubPublicMonitoringAlertLocation {}
