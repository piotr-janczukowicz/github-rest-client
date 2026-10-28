package io.github.piotr_janczukowicz.github.api.codesecurity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Attach a configuration to repositories.
 *
 * @param scope The type of repositories to attach the configuration to. `selected` means the configuration will be
 *     attached to only the repositories specified by `selected_repository_ids`
 * @param selectedRepositoryIds An array of repository IDs to attach the configuration to. You can only provide a list
 *     of repository ids when the `scope` is set to `selected`.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/code-security/attach-configuration/requestBody")
@RecordBuilder
public record GitHubAttachConfigurationPayload(
        GitHubAttachConfigurationScope scope, @Nullable List<Long> selectedRepositoryIds) {

    public GitHubAttachConfigurationPayload {
        requireNonNull(scope, "scope");
        if (selectedRepositoryIds != null) {
            selectedRepositoryIds = List.copyOf(selectedRepositoryIds);
        }
    }
}
