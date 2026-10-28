package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * GitHubManageLicenseInfo
 *
 * @param advancedSecurityEnabled Whether the GitHub Advanced Security feature is enabled.
 * @param advancedSecuritySeats If the GitHub Advanced Security feature is enabled, the seat count.
 * @param clusterSupport Whether the cluster support feature is enabled.
 * @param company The company under which the license is issued.
 * @param croquetSupport Whether the GitHub Connect feature is enabled.
 * @param customTerms Whether this license is issued under custom terms.
 * @param evaluation Wheter this license is issued as an evaluation license.
 * @param expireAt The expiration date of the license.
 * @param insightsEnabled Whether the insights feature is enabled.
 * @param insightsExpireAt If the insights feature is enabled, the expiration date.
 * @param learningLabEvaluationExpires If the learning lab feature is enabled, the expiration date.
 * @param learningLabSeats If the learning lab feature is enabled, the seat count.
 * @param perpetual Whether the perpetual feature is enabled.
 * @param referenceNumber The unique reference number of the license.
 * @param seats If the license is issued with limited seating, the seat count.
 * @param sshAllowed Whether the SSH feature is enabled.
 * @param supportKey The support key of the license.
 * @param unlimitedSeating Whether the license is issued with unlimited seat count.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ghes-license-info")
@Flavors(GitHubFlavor.GHES)
public record GitHubManageLicenseInfo(
        @Nullable @JsonProperty("advancedSecurityEnabled") Boolean advancedSecurityEnabled,
        @Nullable @JsonProperty("advancedSecuritySeats") Integer advancedSecuritySeats,
        @Nullable @JsonProperty("clusterSupport") Boolean clusterSupport,
        @Nullable String company,
        @Nullable @JsonProperty("croquetSupport") Boolean croquetSupport,
        @Nullable @JsonProperty("customTerms") Boolean customTerms,
        @Nullable Boolean evaluation,
        @Nullable @JsonProperty("expireAt") Instant expireAt,
        @Nullable @JsonProperty("insightsEnabled") Boolean insightsEnabled,
        @Nullable @JsonProperty("insightsExpireAt") Instant insightsExpireAt,

        @Nullable @JsonProperty("learningLabEvaluationExpires")
        Instant learningLabEvaluationExpires,

        @Nullable @JsonProperty("learningLabSeats") Integer learningLabSeats,
        @Nullable Boolean perpetual,
        @Nullable @JsonProperty("referenceNumber") String referenceNumber,
        @Nullable Integer seats,
        @Nullable @JsonProperty("sshAllowed") Boolean sshAllowed,
        @Nullable @JsonProperty("supportKey") String supportKey,
        @Nullable @JsonProperty("unlimitedSeating") Boolean unlimitedSeating) {}
