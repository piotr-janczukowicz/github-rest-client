package io.github.piotr_janczukowicz.github.api.users;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Response of the bulk attestations listing endpoints for users and organizations. Maps each requested subject digest
 * to the attestation bundles found for it, with paging information.
 *
 * @param attestationsSubjectDigests Mapping of subject digest to bundles.
 * @param pageInfo Information about the current page.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema({"operations/users/list-attestations-bulk/200", "operations/orgs/list-attestations-bulk/200"})
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubAttestationsBulk(
        @Nullable Map<String, @Nullable List<GitHubAttestationDigestItem>> attestationsSubjectDigests,
        @Nullable GitHubAttestationsPageInfo pageInfo) {}
