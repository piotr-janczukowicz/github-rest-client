package io.github.piotr_janczukowicz.github.api.agentautomations;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * A restricted JSON Schema fragment describing the field value accepted by Automation create and update requests. A
 * schema whose {@code type} is none of the known ones is kept raw as
 * {@link GitHubListAvailableAutomationTriggersItemFieldSchemaUnknown}.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        visible = true,
        defaultImpl = GitHubListAvailableAutomationTriggersItemFieldSchemaUnknown.class)
@JsonSubTypes({
    @JsonSubTypes.Type(value = GitHubListAvailableAutomationTriggersItemFieldSchemaString.class, name = "string"),
    @JsonSubTypes.Type(value = GitHubListAvailableAutomationTriggersItemFieldSchemaArray.class, name = "array"),
    @JsonSubTypes.Type(value = GitHubListAvailableAutomationTriggersItemFieldSchemaInteger.class, name = "integer")
})
@CatalogSchema(
        "operations/agent-automations/list-available-automation-triggers/200/items/properties/fields/items/properties/schema")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public sealed interface GitHubListAvailableAutomationTriggersItemFieldSchema
        permits GitHubListAvailableAutomationTriggersItemFieldSchemaString,
                GitHubListAvailableAutomationTriggersItemFieldSchemaArray,
                GitHubListAvailableAutomationTriggersItemFieldSchemaInteger,
                GitHubListAvailableAutomationTriggersItemFieldSchemaUnknown {}
