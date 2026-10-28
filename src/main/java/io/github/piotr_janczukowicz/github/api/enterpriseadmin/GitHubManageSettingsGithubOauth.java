package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHubManageSettingsGithubOauth
 *
 * @param clientId client id
 * @param clientSecret client secret
 * @param organizationName organization name
 * @param organizationTeam organization team
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ghes-get-settings/properties/github_oauth")
@Flavors(GitHubFlavor.GHES)
public record GitHubManageSettingsGithubOauth(
        @Nullable String clientId,
        @Nullable String clientSecret,
        @Nullable String organizationName,
        @Nullable String organizationTeam) {
    /**
     * Describes this record without its secret.
     *
     * @return text with {@code clientSecret} redacted
     */
    @Override
    public String toString() {
        return "GitHubManageSettingsGithubOauth[" + "clientId=" + clientId + ", clientSecret=<redacted>"
                + ", organizationName=" + organizationName + ", organizationTeam=" + organizationTeam + "]";
    }
}
