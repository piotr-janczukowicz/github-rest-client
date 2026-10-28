package io.github.piotr_janczukowicz.github.api.copilotspaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.SuppressSchemaTypeMismatch;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * A team collaborator of a Copilot Space.
 *
 * @param actorType The collaborator actor type.
 * @param role The role granted to the collaborator
 * @param id Id
 * @param nodeId Node id
 * @param name Name
 * @param slug Slug
 * @param type Type
 * @param description Description
 * @param privacy Privacy
 * @param notificationSetting Notification setting
 * @param url Url
 * @param htmlUrl Html url
 * @param membersUrl Members url
 * @param repositoriesUrl Repositories url
 * @param organizationId Organization id
 * @param parent Parent
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/copilot-space-collaborator/anyOf/1")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCopilotSpaceTeamCollaborator(
        GitHubCopilotSpaceTeamCollaboratorActorType actorType,
        GitHubCopilotSpaceCollaboratorRole role,
        long id,
        String nodeId,
        String name,
        String slug,
        GitHubCopilotSpaceTeamCollaboratorType type,
        @Nullable String description,
        @Nullable String privacy,
        @Nullable String notificationSetting,
        @Nullable String url,
        @Nullable String htmlUrl,
        @Nullable String membersUrl,
        @Nullable String repositoriesUrl,
        @Nullable Long organizationId,

        @SuppressSchemaTypeMismatch("dump type is only null") @Nullable Map<String, Object> parent)
        implements GitHubCopilotSpaceCollaborator {

    public GitHubCopilotSpaceTeamCollaborator {
        requireNonNull(actorType, "actorType");
        requireNonNull(role, "role");
        requireNonNull(nodeId, "nodeId");
        requireNonNull(name, "name");
        requireNonNull(slug, "slug");
        requireNonNull(type, "type");
    }
}
