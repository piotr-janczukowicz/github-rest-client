package io.github.piotr_janczukowicz.github.api.gitdatabase;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Entry of a tree to create.
 *
 * @param path The file referenced in the tree.
 * @param mode The file mode; one of {@code 100644} for file (blob), {@code 100755} for executable (blob),
 *     {@code 040000} for subdirectory (tree), {@code 160000} for submodule (commit), or {@code 120000} for a blob that
 *     specifies the path of a symlink.
 * @param type Either {@code blob}, {@code tree}, or {@code commit}.
 * @param sha The SHA1 checksum ID of the object in the tree. Also called {@code tree.sha}. If the value is {@code null}
 *     then the file will be deleted. Use either {@code tree.sha} or {@code content} to specify the contents of the
 *     entry. Using both will return an error.
 * @param content The content you want this file to have. GitHub will write this blob out and use that SHA for this
 *     entry. Use either this, or {@code tree.sha}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/git/create-tree/requestBody/properties/tree/items")
public record GitHubCreateTreeEntry(
        @Nullable String path,
        @Nullable GitHubCreateTreeEntryMode mode,
        @Nullable GitHubCreateTreeEntryType type,
        @Nullable OptionalNull<String> sha,
        @Nullable String content) {}
