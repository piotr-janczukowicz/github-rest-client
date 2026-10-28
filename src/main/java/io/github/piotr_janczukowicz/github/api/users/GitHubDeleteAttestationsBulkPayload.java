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
 * The request body must include either `subject_digests` or `attestation_ids`, but not both.
 *
 * @param subjectDigests List of subject digests associated with the artifact attestations to delete.
 * @param attestationIds List of unique IDs associated with the artifact attestations to delete.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/users/delete-attestations-bulk/requestBody",
    "operations/orgs/delete-attestations-bulk/requestBody"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubDeleteAttestationsBulkPayload(
        @Nullable List<String> subjectDigests, @Nullable List<Long> attestationIds) {

    private static final String PARAM_SUBJECT_DIGESTS = "subjectDigests";
    private static final String PARAM_ATTESTATION_IDS = "attestationIds";

    public GitHubDeleteAttestationsBulkPayload {
        final boolean hasDigests = subjectDigests != null;
        final boolean hasIds = attestationIds != null;

        if (hasDigests == hasIds) {
            throw new IllegalArgumentException("exactly one of subjectDigests or attestationIds is required");
        }

        if (subjectDigests != null) {
            subjectDigests = List.copyOf(requireSizeBetween(subjectDigests, 1, 1024, PARAM_SUBJECT_DIGESTS));
        }

        if (attestationIds != null) {
            attestationIds = List.copyOf(requireSizeBetween(attestationIds, 1, 1024, PARAM_ATTESTATION_IDS));
        }
    }

    /**
     * Delete request keyed by subject digests.
     *
     * @param subjectDigests digests associated with the attestations to delete
     * @return request body
     */
    public static GitHubDeleteAttestationsBulkPayload bySubjectDigests(final List<String> subjectDigests) {
        return new GitHubDeleteAttestationsBulkPayload(subjectDigests, null);
    }

    /**
     * Delete request keyed by attestation ids.
     *
     * @param attestationIds unique attestation ids to delete
     * @return request body
     */
    public static GitHubDeleteAttestationsBulkPayload byAttestationIds(final List<Long> attestationIds) {
        return new GitHubDeleteAttestationsBulkPayload(null, attestationIds);
    }
}
