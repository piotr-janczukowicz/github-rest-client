package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubCustomPropertyValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Custom property values for an organization repository.
 *
 * @param repositoryId Repository id
 * @param repositoryName Repository name
 * @param repositoryFullName Repository full name
 * @param properties Custom property values
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/org-repo-custom-property-values")
public record GitHubOrgRepoCustomPropertyValues(
        long repositoryId,
        String repositoryName,
        String repositoryFullName,
        List<GitHubCustomPropertyValue> properties) {

    private static final String PARAM_PROPERTIES = "properties";
    private static final String PARAM_REPOSITORY_FULL_NAME = "repositoryFullName";
    private static final String PARAM_REPOSITORY_NAME = "repositoryName";

    public GitHubOrgRepoCustomPropertyValues {
        requireNonNull(repositoryName, PARAM_REPOSITORY_NAME);
        requireNonNull(repositoryFullName, PARAM_REPOSITORY_FULL_NAME);
        requireNonNull(properties, PARAM_PROPERTIES);
    }
}
