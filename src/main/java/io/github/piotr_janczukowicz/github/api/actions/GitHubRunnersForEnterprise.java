package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.SuppressSchemaTypeMismatch;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Self-hosted runners of an enterprise.
 *
 * @param totalCount Total number of runners
 * @param runners The runners
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/list-self-hosted-runners-for-enterprise/200")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubRunnersForEnterprise(
        @SuppressSchemaTypeMismatch("dump declares number; total_count is an integer count") @Nullable Integer totalCount,

        @Nullable List<GitHubRunner> runners) {}
