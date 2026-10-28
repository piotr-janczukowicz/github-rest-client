package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Image of a hosted runner to create.
 *
 * @param id The unique identifier of the runner image
 * @param source The source of the runner image
 * @param version The version of the runner image to deploy, relevant only for custom images
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/create-hosted-runner-for-org/requestBody/properties/image")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCreateHostedRunnerImage(
        @Nullable String id,
        @Nullable GitHubHostedRunnerImageSource source,
        @Nullable String version) {}
