package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/** Rule Suite. */
@CatalogSchema("components/schemas/rule-suite")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRuleSuite(
        @Nullable Long id,
        @Nullable Long actorId,
        @Nullable String actorName,
        @Nullable String beforeSha,
        @Nullable String afterSha,
        @Nullable String ref,
        @Nullable Long repositoryId,
        @Nullable String repositoryName,
        @Nullable Instant pushedAt,
        @Nullable GitHubRuleSuiteResult result,
        @Nullable GitHubRuleSuiteResult evaluationResult,
        @Nullable List<GitHubRuleSuiteEvaluation> ruleEvaluations) {}
