package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * A provisioned SCIM group.
 *
 * @param schemas The URIs that are used to indicate the namespaces of the SCIM schemas.
 * @param externalId A unique identifier for the resource as defined by the provisioning client.
 * @param displayName A human-readable name for a security group.
 * @param members The group members.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/group-response")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubGroupResponse(
        List<GitHubGroupResponseSchemaUrn> schemas,
        @JsonProperty("externalId") @Nullable String externalId,
        @JsonProperty("displayName") @Nullable String displayName,
        @Nullable List<GitHubGroupResponseMember> members) {

    public GitHubGroupResponse {
        requireNonNull(schemas, "schemas");
    }
}
