package io.github.piotr_janczukowicz.github.api.copilotspaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLengthIfPresent;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * A GitHub Copilot Space represents an interactive AI workspace where users can ask questions and get assistance.
 *
 * @param id The unique identifier of the space.
 * @param number The number that identifies the space within its owner.
 * @param name The display name of the space.
 * @param description A description of the space.
 * @param generalInstructions General instructions for the Copilot Space.
 * @param baseRole The base role that determines default permissions.
 * @param owner The user or organization that owns this space.
 * @param creator Creator
 * @param createdAt The date and time the space was created.
 * @param updatedAt The date and time the space was last updated.
 * @param htmlUrl The HTML URL of the space.
 * @param apiUrl The API URL of the space.
 * @param resourcesAttributes Resources attached to the space.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/copilot-space")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCopilotSpace(
        long id,
        long number,
        String name,
        @Nullable String description,
        @Nullable String generalInstructions,
        GitHubCopilotSpaceBaseRole baseRole,
        GitHubCopilotSpaceOwner owner,
        GitHubSimpleUser creator,
        Instant createdAt,
        Instant updatedAt,
        String htmlUrl,
        String apiUrl,
        @Nullable List<GitHubCopilotSpaceResourceAttribute> resourcesAttributes) {

    private static final int MAX_GENERAL_INSTRUCTIONS_LENGTH = 4000;

    public GitHubCopilotSpace {
        requireNonNull(name, "name");
        generalInstructions =
                requireMaxLengthIfPresent(generalInstructions, MAX_GENERAL_INSTRUCTIONS_LENGTH, "generalInstructions");
        requireNonNull(baseRole, "baseRole");
        requireNonNull(owner, "owner");
        requireNonNull(creator, "creator");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
        requireNonNull(htmlUrl, "htmlUrl");
        requireNonNull(apiUrl, "apiUrl");
    }
}
