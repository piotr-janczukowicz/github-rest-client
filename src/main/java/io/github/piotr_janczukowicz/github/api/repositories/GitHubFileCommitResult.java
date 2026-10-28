package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.search.GitHubVerification;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * File commit result.
 *
 * @param sha Sha
 * @param nodeId Node id
 * @param url Url
 * @param htmlUrl Html url
 * @param author Author
 * @param committer Committer
 * @param message Message
 * @param tree Tree
 * @param parents Parents
 * @param verification Verification
 */
@CatalogSchema("components/schemas/file-commit/properties/commit")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubFileCommitResult(
        @Nullable String sha,
        @Nullable String nodeId,
        @Nullable String url,
        @Nullable String htmlUrl,
        @Nullable GitHubFileCommitPerson author,
        @Nullable GitHubFileCommitPerson committer,
        @Nullable String message,
        @Nullable GitHubFileCommitTree tree,
        @Nullable List<GitHubFileCommitParent> parents,
        @Nullable GitHubVerification verification) {}
