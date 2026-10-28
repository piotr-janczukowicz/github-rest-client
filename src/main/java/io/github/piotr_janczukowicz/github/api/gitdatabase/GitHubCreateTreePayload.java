package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Create tree.
 *
 * @param tree Objects (of {@code path}, {@code mode}, {@code type}, and {@code sha}) specifying a tree structure.
 * @param baseTree The SHA1 of an existing Git tree object which will be used as the base for the new tree. If provided,
 *     a new Git tree object will be created from entries in the Git tree object pointed to by {@code base_tree} and
 *     entries defined in the {@code tree} parameter. Entries defined in the {@code tree} parameter will overwrite items
 *     from {@code base_tree} with the same {@code path}. If not provided, GitHub will create a new Git tree object from
 *     only the entries defined in the {@code tree} parameter.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/git/create-tree/requestBody")
public record GitHubCreateTreePayload(
        List<GitHubCreateTreeEntry> tree, @Nullable String baseTree) {

    public GitHubCreateTreePayload {
        tree = List.copyOf(requireNonNull(tree, "tree"));
    }
}
