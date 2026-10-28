package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Enterprise roles available to an enterprise.
 *
 * @param totalCount The total number of enterprise roles available to the enterprise.
 * @param roles The list of enterprise roles available to the enterprise.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/list-enterprise-roles/200")
@Flavors(GitHubFlavor.GHEC)
public record GitHubEnterpriseRoleList(
        @Nullable Integer totalCount, @Nullable List<GitHubEnterpriseRole> roles) {}
