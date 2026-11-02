package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Map;

/**
 * A field schema whose {@code type} this library version does not know, kept as GitHub sent it (including its
 * {@code type}) so that one new schema type does not fail the whole list of available triggers.
 *
 * <p>It is the fallback for a schema object whose {@code type} is none of the known ones. It is not part of the OpenAPI
 * schema: a type that has its own record is never decoded into this one.
 *
 * @param properties the properties of the schema object, by name
 */
// The union tags its subtypes by name; this fallback keeps the object it was built from, untagged.
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
public record GitHubListAvailableRepoAutomationTriggersItemFieldSchemaUnknown(
        @JsonValue Map<String, Object> properties) implements GitHubListAvailableRepoAutomationTriggersItemFieldSchema {

    public GitHubListAvailableRepoAutomationTriggersItemFieldSchemaUnknown {
        requireNonNull(properties, "properties");
    }
}
