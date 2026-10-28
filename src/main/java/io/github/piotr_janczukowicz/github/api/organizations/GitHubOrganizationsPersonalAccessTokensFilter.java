package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.DateTimeUtils.toIso;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeAtMost;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /orgs/{org}/personal-access-token-requests} and {@code GET
 * /orgs/{org}/personal-access-tokens}, excluding pagination.
 *
 * @param sort Property by which to sort results
 * @param direction Sort direction
 * @param owner Owner usernames filter
 * @param repository Repository name filter
 * @param permission Permission filter
 * @param lastUsedBefore Only show tokens used before the given time
 * @param lastUsedAfter Only show tokens used after the given time
 * @param tokenId Token identifiers filter
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema({"operations/orgs/list-pat-grants/parameters", "operations/orgs/list-pat-grant-requests/parameters"})
@RecordBuilder
public record GitHubOrganizationsPersonalAccessTokensFilter(
        @Nullable GitHubOrganizationPersonalAccessTokenSort sort,
        @Nullable GitHubDirection direction,
        @Nullable List<String> owner,
        @Nullable String repository,
        @Nullable String permission,
        @Nullable Instant lastUsedBefore,
        @Nullable Instant lastUsedAfter,
        @Nullable List<String> tokenId) {

    private static final String PARAM_OWNER = "owner";
    private static final String PARAM_TOKEN_ID = "token_id";
    private static final String QUERY_OWNER = "owner[]";
    private static final String QUERY_TOKEN_ID = "token_id[]";
    private static final int MAX_OWNER = 10;
    private static final int MAX_TOKEN_ID = 50;

    public GitHubOrganizationsPersonalAccessTokensFilter {
        owner = copyBoundedQueryList(owner, MAX_OWNER, PARAM_OWNER);
        tokenId = copyBoundedQueryList(tokenId, MAX_TOKEN_ID, PARAM_TOKEN_ID);
    }

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubOrganizationsPersonalAccessTokensFilter empty() {
        return GitHubOrganizationsPersonalAccessTokensFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters with repeated {@code owner[]} and {@code token_id[]} values.
     *
     * @return query map
     */
    public Map<String, List<String>> toQueryValues() {
        final Map<String, List<String>> query = new LinkedHashMap<>();

        if (sort != null) {
            query.put("sort", List.of(sort.wireValue()));
        }
        if (direction != null) {
            query.put("direction", List.of(direction.wireValue()));
        }
        if (owner != null) {
            query.put(QUERY_OWNER, List.copyOf(owner));
        }
        if (repository != null) {
            query.put("repository", List.of(repository));
        }
        if (permission != null) {
            query.put("permission", List.of(permission));
        }
        if (lastUsedBefore != null) {
            query.put("last_used_before", List.of(toIso(lastUsedBefore)));
        }
        if (lastUsedAfter != null) {
            query.put("last_used_after", List.of(toIso(lastUsedAfter)));
        }
        if (tokenId != null) {
            query.put(QUERY_TOKEN_ID, List.copyOf(tokenId));
        }

        return Collections.unmodifiableMap(query);
    }

    @SuppressWarnings("PMD.ReturnEmptyCollectionRatherThanNull")
    private static @Nullable List<String> copyBoundedQueryList(
            final @Nullable List<String> values, final int maxSize, final String name) {
        if (values == null) {
            return null;
        }
        final List<String> copied = List.copyOf(requireSizeAtMost(values, maxSize, name));
        if (copied.isEmpty()) {
            return null;
        }
        return copied;
    }
}
