package io.github.piotr_janczukowicz.github.api.secretscanning;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * The user attributed as the author of the secret: a full simple user, or the delivered public details.
 *
 * <p>The delivered details are a subset of the simple user, so deduction cannot tell them apart from a small payload;
 * they are the default, and a payload with a field only the simple user has decodes as {@link GitHubSimpleUser}.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION, defaultImpl = GitHubPublicMonitoringSecretAuthor.class)
@JsonSubTypes({@JsonSubTypes.Type(GitHubSimpleUser.class), @JsonSubTypes.Type(GitHubPublicMonitoringSecretAuthor.class)
})
@CatalogSchema("components/schemas/public-monitoring-alert/properties/attribution/properties/secret_author")
@Flavors(GitHubFlavor.GHEC)
public sealed interface GitHubPublicMonitoringAlertSecretAuthor
        permits GitHubSimpleUser, GitHubPublicMonitoringSecretAuthor {}
