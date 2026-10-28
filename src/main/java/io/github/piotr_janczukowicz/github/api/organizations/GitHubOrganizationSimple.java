package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * A GitHub organization.
 *
 * @param login Login
 * @param id Id
 * @param nodeId Node id
 * @param url Url
 * @param reposUrl Repos url
 * @param eventsUrl Events url
 * @param hooksUrl Hooks url
 * @param issuesUrl Issues url
 * @param membersUrl Members url
 * @param publicMembersUrl Public members url
 * @param avatarUrl Avatar url
 * @param description Description
 */
@RecordBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/organization-simple")
public record GitHubOrganizationSimple(
        String login,
        long id,
        String nodeId,
        String url,
        String reposUrl,
        String eventsUrl,
        String hooksUrl,
        String issuesUrl,
        String membersUrl,
        String publicMembersUrl,
        String avatarUrl,
        @Nullable String description) {

    public GitHubOrganizationSimple {
        requireNonNull(login, "login");
        requireNonNull(nodeId, "nodeId");
        requireNonNull(url, "url");
        requireNonNull(reposUrl, "reposUrl");
        requireNonNull(eventsUrl, "eventsUrl");
        requireNonNull(hooksUrl, "hooksUrl");
        requireNonNull(issuesUrl, "issuesUrl");
        requireNonNull(membersUrl, "membersUrl");
        requireNonNull(publicMembersUrl, "publicMembersUrl");
        requireNonNull(avatarUrl, "avatarUrl");
    }
}
