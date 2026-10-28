package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHubLiveMigrationCutoverPayload
 *
 * @param force Bypass the cutover readiness checks and initiate cutover even when the migration is not reported as
 *     ready. Defaults to `false`.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/live-migration-cutover/requestBody")
@Flavors(GitHubFlavor.GHES)
public record GitHubLiveMigrationCutoverPayload(@Nullable Boolean force) {}
