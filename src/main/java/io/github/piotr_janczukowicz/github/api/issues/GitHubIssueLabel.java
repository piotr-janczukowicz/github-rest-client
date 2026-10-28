package io.github.piotr_janczukowicz.github.api.issues;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Label on an issue.
 *
 * @param id Id
 * @param nodeId Node id
 * @param url Url
 * @param name Name
 * @param description Description
 * @param color Color
 * @param isDefault Default
 * @param archivedBy The user who archived the label, or {@code null} if it has not been archived
 */
@RecordBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/issue/properties/labels/items")
public record GitHubIssueLabel(
        @Nullable Long id,
        @Nullable String nodeId,
        @Nullable String url,
        @Nullable String name,
        @Nullable String description,
        @Nullable String color,
        @JsonProperty("default") @Nullable Boolean isDefault,
        @Nullable GitHubSimpleUser archivedBy) {}
