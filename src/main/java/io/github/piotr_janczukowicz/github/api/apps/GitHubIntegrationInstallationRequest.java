package io.github.piotr_janczukowicz.github.api.apps;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Request to install an integration on a target.
 *
 * @param id Id
 * @param nodeId Node id
 * @param account Account
 * @param requester Requester
 * @param createdAt Created at
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/integration-installation-request")
public record GitHubIntegrationInstallationRequest(
        long id,
        @Nullable String nodeId,
        GitHubIntegrationInstallationRequestAccount account,
        GitHubSimpleUser requester,
        Instant createdAt) {

    public GitHubIntegrationInstallationRequest {
        requireNonNull(account, "account");
        requireNonNull(requester, "requester");
        requireNonNull(createdAt, "createdAt");
    }
}
