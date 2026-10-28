package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHubManageSettingsLicense
 *
 * @param seats seats
 * @param evaluation evaluation
 * @param perpetual perpetual
 * @param unlimitedSeating unlimited seating
 * @param supportKey support key
 * @param sshAllowed ssh allowed
 * @param clusterSupport cluster support
 * @param expireAt expire at
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ghes-get-settings/properties/license")
@Flavors(GitHubFlavor.GHES)
public record GitHubManageSettingsLicense(
        @Nullable Integer seats,
        @Nullable Boolean evaluation,
        @Nullable Boolean perpetual,
        @Nullable Boolean unlimitedSeating,
        @Nullable String supportKey,
        @Nullable Boolean sshAllowed,
        @Nullable Boolean clusterSupport,
        @Nullable String expireAt) {}
