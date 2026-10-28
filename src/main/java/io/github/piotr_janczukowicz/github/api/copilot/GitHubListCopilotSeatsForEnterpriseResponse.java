package io.github.piotr_janczukowicz.github.api.copilot;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Copilot seat assignments for an enterprise.
 *
 * @param totalSeats Total number of Copilot seats for the enterprise currently being billed.
 * @param seats Seats.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/copilot/list-copilot-seats-for-enterprise/200")
@Flavors(GitHubFlavor.GHEC)
public record GitHubListCopilotSeatsForEnterpriseResponse(
        @Nullable Integer totalSeats, @Nullable List<GitHubCopilotSeatDetails> seats) {}
