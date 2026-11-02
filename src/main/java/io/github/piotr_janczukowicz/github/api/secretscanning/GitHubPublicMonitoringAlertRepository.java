package io.github.piotr_janczukowicz.github.api.secretscanning;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubSimpleRepository;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * The repository the secret was found in: a full simple repository, or the delivered public repository details.
 *
 * <p>The delivered details are a subset of the simple repository, so deduction cannot tell them apart from a small
 * payload; they are the default, and a payload with a field only the simple repository has decodes as
 * {@link GitHubSimpleRepository}.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION, defaultImpl = GitHubPublicMonitoringRepository.class)
@JsonSubTypes({
    @JsonSubTypes.Type(GitHubSimpleRepository.class),
    @JsonSubTypes.Type(GitHubPublicMonitoringRepository.class)
})
@CatalogSchema("components/schemas/public-monitoring-alert/properties/repository")
@Flavors(GitHubFlavor.GHEC)
public sealed interface GitHubPublicMonitoringAlertRepository
        permits GitHubSimpleRepository, GitHubPublicMonitoringRepository {}
