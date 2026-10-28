package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireBetweenIfPresent;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonEmpty;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireUniqueItems;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Configuration of the interval trigger.
 *
 * @param cadence One or more schedules on which the Automation runs.
 * @param hourUtc UTC hour anchor (0 through 23) for daily and weekly schedules. Omitted daily and weekly values default
 *     to {@code 0} (midnight UTC).
 * @param dayOfWeek UTC weekday anchor for weekly schedules, where {@code 0} is Sunday and {@code 6} is Saturday.
 *     Omitted weekly values default to Monday ({@code 1}).
 * @param minuteUtc UTC minute anchor for daily and weekly schedules. Omitted daily and weekly values default to
 *     {@code 0}. Must be omitted whenever {@code cadence} includes {@code hourly}.
 * @param writeScope Override the Automation {@code write_scope} for runs started by this trigger. If omitted, the
 *     automation's {@code write_scope} is used. Organization or repository policy may restrict the effective scope to
 *     {@code trigger}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/interval")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubEditRepoAutomationTriggersInterval(
        List<GitHubEditRepoAutomationTriggersIntervalCadence> cadence,
        @Nullable Integer hourUtc,
        @Nullable Integer dayOfWeek,
        @Nullable GitHubAutomationMinuteUtc minuteUtc,
        @Nullable GitHubEditRepoAutomationTriggersIntervalWriteScope writeScope) {
    public GitHubEditRepoAutomationTriggersInterval {
        cadence = List.copyOf(requireUniqueItems(requireNonEmpty(cadence, "cadence"), "cadence"));
        requireBetweenIfPresent(hourUtc, 0, 23, "hourUtc");
        requireBetweenIfPresent(dayOfWeek, 0, 6, "dayOfWeek");
    }
}
