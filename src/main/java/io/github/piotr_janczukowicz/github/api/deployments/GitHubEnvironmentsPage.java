package io.github.piotr_janczukowicz.github.api.deployments;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * List environments response.
 *
 * @param totalCount The number of environments in this repository
 * @param environments Environments
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/repos/get-all-environments/200")
@RecordBuilder
public record GitHubEnvironmentsPage(
        @Nullable Integer totalCount, @Nullable List<GitHubEnvironment> environments) {}
