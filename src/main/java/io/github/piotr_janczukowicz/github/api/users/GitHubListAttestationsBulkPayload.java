package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeBetween;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * List Attestations Bulk.
 *
 * @param subjectDigests List of subject digests to fetch attestations for.
 * @param predicateType Optional filter for fetching attestations with a given predicate type. This option accepts
 *     `provenance`, `sbom`, `release`, or freeform text for custom predicate types.
 */
@CatalogSchema({
    "operations/users/list-attestations-bulk/requestBody",
    "operations/orgs/list-attestations-bulk/requestBody"
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListAttestationsBulkPayload(
        List<String> subjectDigests, @Nullable String predicateType) {

    public GitHubListAttestationsBulkPayload {
        subjectDigests = List.copyOf(requireSizeBetween(subjectDigests, 1, 1024, "subjectDigests"));
    }
}
