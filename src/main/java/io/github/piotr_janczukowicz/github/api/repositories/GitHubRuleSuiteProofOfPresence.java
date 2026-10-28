package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * The proof of presence evaluation for the pull request of a pull request rule evaluation.
 *
 * @param state state of the proof of presence evaluation
 * @param code code describing the reason for the proof of presence state
 * @param baseSha base commit SHA the evaluation applies to, empty if unavailable
 * @param headSha head commit SHA the evaluation applies to, empty if unavailable
 * @param requiredLevel authentication level required by the rule, empty if unavailable
 * @param verifiedLevel authentication level that was verified, empty if proof of presence has not been completed
 * @param verifiedAt time proof of presence was verified, in ISO 8601 format, empty if not completed
 * @param verifier user who provided proof of presence, null if no proof of presence was recorded
 */
@CatalogSchema("components/schemas/rule-suite-pull-request/properties/pull_request/properties/proof_of_presence")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubRuleSuiteProofOfPresence(
        @Nullable String state,
        @Nullable String code,
        @Nullable String baseSha,
        @Nullable String headSha,
        @Nullable String requiredLevel,
        @Nullable String verifiedLevel,
        @Nullable String verifiedAt,
        @Nullable GitHubRuleSuiteProofOfPresenceVerifier verifier) {}
