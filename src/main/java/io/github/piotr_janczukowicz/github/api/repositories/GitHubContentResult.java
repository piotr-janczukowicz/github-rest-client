package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/** Repository content result: directory listing or single content item. */
@CatalogSchema("operations/repos/get-content/200")
@JsonInclude(JsonInclude.Include.NON_NULL)
public sealed interface GitHubContentResult
        permits GitHubContentDirectory, GitHubContentFile, GitHubContentSymlink, GitHubContentSubmodule {}
