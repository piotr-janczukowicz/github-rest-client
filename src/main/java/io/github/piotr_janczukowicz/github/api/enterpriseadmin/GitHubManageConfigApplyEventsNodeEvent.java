package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHubManageConfigApplyEventsNodeEvent
 *
 * @param timestamp timestamp
 * @param severityText severity text
 * @param body body
 * @param eventName event name
 * @param topology topology
 * @param hostname hostname
 * @param configRunId config run id
 * @param traceId trace id
 * @param spanId span id
 * @param spanParentId span parent id
 * @param spanDepth span depth
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema(
        "operations/enterprise-admin/get-manage-config-apply-events/200/properties/nodes/items/properties/events/items")
@Flavors(GitHubFlavor.GHES)
public record GitHubManageConfigApplyEventsNodeEvent(
        @Nullable String timestamp,
        @Nullable String severityText,
        @Nullable String body,
        @Nullable String eventName,
        @Nullable String topology,
        @Nullable String hostname,
        @Nullable String configRunId,
        @Nullable String traceId,
        @Nullable String spanId,
        @Nullable String spanParentId,
        @Nullable Integer spanDepth) {}
