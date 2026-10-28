package io.github.piotr_janczukowicz.github.api.codequality;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Code quality finding
 *
 * @param number The finding number.
 * @param state State of the code quality finding.
 * @param url The REST API URL of the code quality finding resource.
 * @param rule Code quality rule
 * @param location Code quality file location
 * @param message Code quality finding message
 * @param createdAt The time the code quality finding was created.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/code-quality-finding")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCodeQualityFinding(
        int number,
        GitHubCodeQualityFindingState state,
        String url,
        GitHubCodeQualityFindingRule rule,
        GitHubCodeQualityFindingLocation location,
        GitHubCodeQualityFindingMessage message,
        @Nullable Instant createdAt) {

    public GitHubCodeQualityFinding {
        requireNonNull(state, "state");
        requireNonNull(url, "url");
        requireNonNull(rule, "rule");
        requireNonNull(location, "location");
        requireNonNull(message, "message");
    }
}
