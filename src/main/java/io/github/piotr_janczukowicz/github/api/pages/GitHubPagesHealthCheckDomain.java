package io.github.piotr_janczukowicz.github.api.pages;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** Pages health check domain details. */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "components/schemas/pages-health-check/properties/domain",
    "components/schemas/pages-health-check/properties/alt_domain"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubPagesHealthCheckDomain(
        @Nullable String host,
        @Nullable String uri,
        @Nullable String nameservers,
        @Nullable Boolean dnsResolves,
        @Nullable Boolean isProxied,
        @Nullable Boolean isCloudflareIp,
        @Nullable Boolean isFastlyIp,
        @Nullable Boolean isOldIpAddress,
        @JsonProperty("is_a_record") @Nullable Boolean isARecord,
        @Nullable Boolean hasCnameRecord,
        @Nullable Boolean hasMxRecordsPresent,
        @Nullable Boolean isValidDomain,
        @Nullable Boolean isApexDomain,
        @JsonProperty("should_be_a_record") @Nullable Boolean shouldBeARecord,
        @Nullable Boolean isCnameToGithubUserDomain,
        @Nullable Boolean isCnameToPagesDotGithubDotCom,
        @Nullable Boolean isCnameToFastly,
        @Nullable Boolean isPointedToGithubPagesIp,
        @Nullable Boolean isNonGithubPagesIpPresent,
        @Nullable Boolean isPagesDomain,
        @Nullable Boolean isServedByPages,
        @Nullable Boolean isValid,
        @Nullable String reason,
        @Nullable Boolean respondsToHttps,
        @Nullable Boolean enforcesHttps,
        @Nullable String httpsError,
        @Nullable Boolean isHttpsEligible,
        @Nullable String caaError) {}
