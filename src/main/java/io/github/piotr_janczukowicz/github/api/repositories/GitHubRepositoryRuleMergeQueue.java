package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** merge_queue rule. */
@CatalogSchema("components/schemas/repository-rule-merge-queue")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRuleMergeQueue(
        GitHubRepositoryRuleMergeQueueType type, @Nullable GitHubRepositoryRuleMergeQueueParameters parameters)
        implements GitHubRepositoryRule {

    public GitHubRepositoryRuleMergeQueue {
        requireNonNull(type, "type");
    }
}
