package io.github.piotr_janczukowicz.github.api.agenttasks;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Map;

/**
 * Artifact reference data whose shape this library version does not know, kept as GitHub sent it so that one new shape
 * does not fail the whole task or task list it is in.
 *
 * <p>It is the fallback for a {@code data} object that matches none of the known shapes. It is not part of the OpenAPI
 * schema: a shape that has its own record is never decoded into this one.
 *
 * @param properties the properties of the data object, by name
 */
public record GitHubCreateTaskInRepoResponseArtifactDataUnknown(
        @JsonValue Map<String, Object> properties) implements GitHubCreateTaskInRepoResponseArtifactData {

    public GitHubCreateTaskInRepoResponseArtifactDataUnknown {
        requireNonNull(properties, "properties");
    }
}
