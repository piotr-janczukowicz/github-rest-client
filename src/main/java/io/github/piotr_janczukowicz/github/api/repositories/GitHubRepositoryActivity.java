package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Activity.
 *
 * @param id Id
 * @param nodeId Node id
 * @param before The SHA of the commit before the activity.
 * @param after The SHA of the commit after the activity.
 * @param ref The full Git reference.
 * @param timestamp The time when the activity occurred.
 * @param activityType The type of the activity that was performed.
 * @param actor Actor
 */
@CatalogSchema("components/schemas/activity")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryActivity(
        long id,
        String nodeId,
        String before,
        String after,
        String ref,
        Instant timestamp,
        GitHubRepositoryActivityType activityType,
        @Nullable OptionalNull<GitHubSimpleUser> actor) {

    public GitHubRepositoryActivity {
        requireNonNull(nodeId, "nodeId");
        requireNonNull(before, "before");
        requireNonNull(after, "after");
        requireNonNull(ref, "ref");
        requireNonNull(timestamp, "timestamp");
        requireNonNull(activityType, "activityType");
    }
}
