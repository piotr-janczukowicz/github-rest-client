package io.github.piotr_janczukowicz.github.api.users;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * One attestation entry for a subject digest in a bulk attestations listing.
 *
 * @param repositoryId id of the repository the attestation bundle belongs to
 * @param bundleUrl URL from which the attestation bundle can be downloaded
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema({
    "operations/users/list-attestations-bulk/200/properties/attestations_subject_digests/additionalProperties/items",
    "operations/orgs/list-attestations-bulk/200/properties/attestations_subject_digests/additionalProperties/items"
})
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubAttestationDigestItem(
        @Nullable Long repositoryId, @Nullable String bundleUrl) {}
