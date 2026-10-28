package io.github.piotr_janczukowicz.github.api.users;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Information about the current page.
 *
 * @param hasNext Indicates whether there is a next page.
 * @param hasPrevious Indicates whether there is a previous page.
 * @param next The cursor to the next page.
 * @param previous The cursor to the previous page.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema({
    "operations/users/list-attestations-bulk/200/properties/page_info",
    "operations/orgs/list-attestations-bulk/200/properties/page_info"
})
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubAttestationsPageInfo(
        @Nullable Boolean hasNext,
        @Nullable Boolean hasPrevious,
        @Nullable String next,
        @Nullable String previous) {}
