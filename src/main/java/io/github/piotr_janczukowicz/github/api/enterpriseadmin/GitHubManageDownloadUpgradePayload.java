package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.GhesVersions;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * GitHubManageDownloadUpgradePayload
 *
 * @param version Semantic version of the upgrade package to download. If omitted, the agent resolves the latest
 *     eligible version.
 * @param force Whether to remove an existing package file before starting a new download.
 * @param uuids Node UUIDs to target after gateway query filters are applied. Blank values are ignored; if all supplied
 *     values are blank, the request is rejected.
 * @param maxParallel Selects gateway-side orchestration and limits the number of nodes downloading at the same time.
 *     Non-positive values use the gateway default, and values above the target node count are capped to that count.
 * @param nodeTimeout Selects gateway-side orchestration and marks an in-flight node download as stuck after this Go
 *     duration. Defaults to 30m and must be at most 2h.
 * @param maxAttempts Selects gateway-side orchestration and sets the maximum number of attempts per node. Non-positive
 *     values use the gateway default. The maximum accepted value is 5.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ghes-download-manage-upgrade-request")
@Flavors(GitHubFlavor.GHES)
@GhesVersions(since = "3.22")
public record GitHubManageDownloadUpgradePayload(
        @Nullable String version,
        @Nullable Boolean force,
        @Nullable List<String> uuids,
        @Nullable Integer maxParallel,
        @Nullable String nodeTimeout,
        @Nullable Integer maxAttempts) {
    public GitHubManageDownloadUpgradePayload {
        if (uuids != null) {
            uuids = List.copyOf(uuids);
        }
    }
}
