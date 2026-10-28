package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Create tag object.
 *
 * @param tag The tag's name. This is typically a version (e.g., "v0.0.1").
 * @param message The tag message.
 * @param object The SHA of the git object this is tagging.
 * @param type The type of the object we're tagging. Normally this is a {@code commit} but it can also be a {@code tree}
 *     or a {@code blob}.
 * @param tagger An object with information about the individual creating the tag.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/git/create-tag/requestBody")
public record GitHubCreateTagPayload(
        String tag,
        String message,
        String object,
        GitHubCreateTagType type,
        @Nullable GitHubCreateTagTagger tagger) {

    public GitHubCreateTagPayload {
        requireNonNull(tag, "tag");
        requireNonNull(message, "message");
        requireNonNull(object, "object");
        requireNonNull(type, "type");
    }
}
