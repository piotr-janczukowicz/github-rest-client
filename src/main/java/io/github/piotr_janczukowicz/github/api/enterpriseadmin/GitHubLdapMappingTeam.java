package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * A team with its LDAP mapping on GitHub Enterprise Server.
 *
 * @param ldapDn ldap dn
 * @param id id
 * @param nodeId node id
 * @param url url
 * @param htmlUrl html url
 * @param name name
 * @param slug slug
 * @param description description
 * @param privacy privacy
 * @param notificationSetting notification setting
 * @param permission permission
 * @param membersUrl members url
 * @param repositoriesUrl repositories url
 * @param type type
 * @param organizationId organization id
 * @param enterpriseId enterprise id
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ldap-mapping-team")
@Flavors(GitHubFlavor.GHES)
public record GitHubLdapMappingTeam(
        @Nullable String ldapDn,
        @Nullable Integer id,
        @Nullable String nodeId,
        @Nullable String url,
        @Nullable String htmlUrl,
        @Nullable String name,
        @Nullable String slug,
        @Nullable String description,
        @Nullable String privacy,
        @Nullable String notificationSetting,
        @Nullable String permission,
        @Nullable String membersUrl,
        @Nullable String repositoriesUrl,
        @Nullable String type,
        @Nullable Integer organizationId,
        @Nullable Integer enterpriseId) {}
