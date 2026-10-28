package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Map;

/**
 * Payload of an event whose type this library version does not know, kept as GitHub sent it so that one new event type
 * does not fail the whole page of events it is in.
 *
 * <p>The type name is still available as {@link GitHubEvent#type()}. The map is a shallow, unmodifiable copy in JSON
 * order; nested objects and arrays are the plain maps and lists that were decoded. It is not part of the OpenAPI
 * schema: a payload that has its own record is never decoded into this one.
 *
 * @param properties the properties of the payload object, by name
 */
public record GitHubUnknownEventPayload(@JsonValue Map<String, Object> properties) implements GitHubEventPayload {

    public GitHubUnknownEventPayload {
        requireNonNull(properties, "properties");
    }
}
