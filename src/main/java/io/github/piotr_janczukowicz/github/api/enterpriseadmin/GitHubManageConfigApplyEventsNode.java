package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * GitHubManageConfigApplyEventsNode
 *
 * @param node Hostname of the node
 * @param lastRequestId Unique ID of the last response from a host used for pagination
 * @param events events
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/get-manage-config-apply-events/200/properties/nodes/items")
@Flavors(GitHubFlavor.GHES)
public record GitHubManageConfigApplyEventsNode(
        @Nullable String node,
        @Nullable String lastRequestId,
        @Nullable List<GitHubManageConfigApplyEventsNodeEvent> events) {}
