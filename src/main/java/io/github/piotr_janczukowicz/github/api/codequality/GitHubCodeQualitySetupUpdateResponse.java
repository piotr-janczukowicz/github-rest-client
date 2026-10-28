package io.github.piotr_janczukowicz.github.api.codequality;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * You can use `run_url` to track the status of the run. This includes a property status and conclusion. You should not
 * rely on this always being an actions workflow run object.
 *
 * @param runId ID of the corresponding run.
 * @param runUrl URL of the corresponding run.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/code-quality-setup-update-response")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCodeQualitySetupUpdateResponse(
        @Nullable Long runId, @Nullable String runUrl) {}
