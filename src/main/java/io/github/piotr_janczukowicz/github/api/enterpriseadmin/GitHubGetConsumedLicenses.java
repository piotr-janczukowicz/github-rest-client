package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * A breakdown of the licenses consumed by an enterprise.
 *
 * @param totalSeatsConsumed Seats consumed by the enterprise
 * @param totalSeatsPurchased Seats purchased by the enterprise
 * @param users Users consuming a license
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/get-consumed-licenses")
@Flavors(GitHubFlavor.GHEC)
public record GitHubGetConsumedLicenses(
        @Nullable Integer totalSeatsConsumed,
        @Nullable Integer totalSeatsPurchased,
        @Nullable List<GitHubGetConsumedLicensesUser> users) {}
