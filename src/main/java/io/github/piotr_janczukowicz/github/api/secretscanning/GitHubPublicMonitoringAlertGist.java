package io.github.piotr_janczukowicz.github.api.secretscanning;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.gists.GitHubBaseGist;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * The gist the secret was found in: a full base gist, or the delivered gist reference.
 *
 * <p>The reference is a subset of the base gist, so deduction cannot tell them apart from a small payload; the
 * reference is the default, and a payload with a field only the base gist has decodes as {@link GitHubBaseGist}.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION, defaultImpl = GitHubPublicMonitoringAlertGistReference.class)
@JsonSubTypes({
    @JsonSubTypes.Type(GitHubBaseGist.class),
    @JsonSubTypes.Type(GitHubPublicMonitoringAlertGistReference.class)
})
@CatalogSchema("components/schemas/public-monitoring-alert/properties/gist")
@Flavors(GitHubFlavor.GHEC)
public sealed interface GitHubPublicMonitoringAlertGist
        permits GitHubBaseGist, GitHubPublicMonitoringAlertGistReference {}
