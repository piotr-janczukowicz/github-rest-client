package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireBetweenIfPresent;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Interval.
 *
 * @param cadence One or more schedules on which the Automation runs.
 * @param hourUtc UTC hour anchor (0 through 23) for daily and weekly schedules. Omitted daily and weekly values default
 *     to {@code 0} (midnight UTC).
 * @param dayOfWeek UTC weekday anchor for weekly schedules, where {@code 0} is Sunday and {@code 6} is Saturday.
 *     Omitted weekly values default to Monday ({@code 1}).
 * @param minuteUtc UTC minute anchor for daily and weekly schedules. Omitted daily and weekly values default to
 *     {@code 0}. Must be omitted whenever {@code cadence} includes {@code hourly}.
 * @param writeScope Has no effect: scheduled runs can change any resource in the repository, and an organization or
 *     repository policy requiring trigger-scoped writes does not apply to them.
 * @param allowCopilotTriggers When true, events Copilot creates, including from other automations and Copilot sessions,
 *     can start this automation through this trigger. Defaults to false. The Copilot coding agent app cannot turn it
 *     on: creating a trigger with true, or changing it from false to true, returns 422.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agent-automations/list-automations/200/items/properties/triggers/properties/interval")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListAutomationsItemTriggersInterval(
        List<GitHubListAutomationsItemTriggersIntervalCadence> cadence,
        @Nullable Integer hourUtc,
        @Nullable Integer dayOfWeek,
        @Nullable GitHubAutomationMinuteUtc minuteUtc,
        @Nullable GitHubAutomationWriteScope writeScope,
        @Nullable Boolean allowCopilotTriggers) {
    public GitHubListAutomationsItemTriggersInterval {
        requireNonEmpty(cadence, "cadence");
        requireBetweenIfPresent(hourUtc, 0, 23, "hourUtc");
        requireBetweenIfPresent(dayOfWeek, 0, 6, "dayOfWeek");
    }
}
