package io.github.piotr_janczukowicz.github.api.codespaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * A description of the machine powering a codespace.
 *
 * @param name The name of the machine.
 * @param displayName The display name of the machine includes cores, memory, and storage.
 * @param operatingSystem The operating system of the machine.
 * @param storageInBytes How much storage is available to the codespace.
 * @param memoryInBytes How much memory is available to the codespace.
 * @param cpus How many cores are available to the codespace.
 * @param prebuildAvailability Whether a prebuild is currently available when creating a codespace for this machine and
 *     repository.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/codespace-machine")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCodespaceMachine(
        String name,
        String displayName,
        String operatingSystem,
        long storageInBytes,
        long memoryInBytes,
        int cpus,
        @Nullable GitHubCodespaceMachinePrebuildAvailability prebuildAvailability) {

    public GitHubCodespaceMachine {
        requireNonNull(name, "name");
        requireNonNull(displayName, "displayName");
        requireNonNull(operatingSystem, "operatingSystem");
    }
}
