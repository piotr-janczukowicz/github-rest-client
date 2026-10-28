package io.github.piotr_janczukowicz.github.api.copilot;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Copilot seat assignments for an organization.
 *
 * @param totalSeats Total number of Copilot seats for the organization currently being billed.
 * @param seats Seats
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/copilot/list-copilot-seats/200")
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListCopilotSeatsResponse(
        @Nullable Integer totalSeats, @Nullable List<GitHubCopilotSeatDetails> seats) {}
