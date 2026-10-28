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
 * SCIM group to provision in an enterprise.
 *
 * @param schemas The URIs that are used to indicate the namespaces of the SCIM schemas.
 * @param externalId A unique identifier for the resource as defined by the provisioning client.
 * @param displayName A human-readable name for a security group.
 * @param members The group members.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/group")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubScimEnterpriseGroup(
        List<GitHubScimEnterpriseGroupSchemaUrn> schemas,
        @JsonProperty("externalId") String externalId,
        @JsonProperty("displayName") String displayName,
        @Nullable List<GitHubScimEnterpriseGroupMember> members) {

    public GitHubScimEnterpriseGroup {
        requireNonNull(schemas, "schemas");
        schemas = List.copyOf(schemas);
        requireNonNull(externalId, "externalId");
        requireNonNull(displayName, "displayName");
        if (members != null) {
            members = List.copyOf(members);
        }
    }
}
