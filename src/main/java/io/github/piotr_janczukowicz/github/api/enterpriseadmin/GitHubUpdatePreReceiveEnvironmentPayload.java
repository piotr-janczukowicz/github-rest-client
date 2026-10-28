package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHubUpdatePreReceiveEnvironmentPayload
 *
 * @param name This pre-receive environment's new name.
 * @param imageUrl URL from which to download a tarball of this environment.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/update-pre-receive-environment/requestBody")
@Flavors(GitHubFlavor.GHES)
public record GitHubUpdatePreReceiveEnvironmentPayload(
        @Nullable String name, @Nullable String imageUrl) {}
