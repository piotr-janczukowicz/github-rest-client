package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Manual match of a Visual Studio subscription to a user.
 *
 * @param userIdentifier Identifier of the user to match the subscription to
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/update-vss-manual-match/requestBody")
@Flavors(GitHubFlavor.GHEC)
public record GitHubUpdateVssManualMatchPayload(@Nullable String userIdentifier) {}
