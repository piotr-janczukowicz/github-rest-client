package io.github.piotr_janczukowicz.github.api.pages;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * The configuration for GitHub Pages for a repository.
 *
 * @param url The API address for accessing this Page resource.
 * @param status The status of the most recent build of the Page.
 * @param cname The Pages site's custom domain
 * @param protectedDomainState The state if the domain is verified
 * @param pendingDomainUnverifiedAt The timestamp when a pending domain becomes unverified.
 * @param custom404 Whether the Page has a custom 404 page.
 * @param htmlUrl The web address the Page can be accessed from.
 * @param buildType The process in which the Page will be built.
 * @param source Source
 * @param sitePublic Whether the GitHub Pages site is publicly visible. If set to {@code true}, the site is accessible
 *     to anyone on the internet. If set to {@code false}, the site will only be accessible to users who have at least
 *     {@code read} access to the repository that published the site.
 * @param httpsCertificate Https certificate
 * @param httpsEnforced Whether https is enabled on the domain
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/page")
public record GitHubPagesSite(
        String url,
        @Nullable GitHubPagesSiteStatus status,
        @Nullable String cname,
        @Nullable GitHubPagesProtectedDomainState protectedDomainState,
        @Nullable Instant pendingDomainUnverifiedAt,
        @JsonProperty("custom_404") boolean custom404,
        @Nullable String htmlUrl,
        @Nullable GitHubPagesBuildType buildType,
        @Nullable GitHubPagesSource source,
        @JsonProperty("public") boolean sitePublic,
        @Nullable GitHubPagesHttpsCertificate httpsCertificate,
        @Nullable Boolean httpsEnforced) {

    public GitHubPagesSite {
        requireNonNull(url, "url");
    }
}
