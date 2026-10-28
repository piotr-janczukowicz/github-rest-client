package io.github.piotr_janczukowicz.github.api.announcementbanners;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Enterprise global announcement.
 *
 * @param announcement The announcement text in GitHub Flavored Markdown.
 * @param expiresAt The time at which the announcement expires. To set an announcement that never expires, omit this
 *     parameter, set it to {@code null}, or set it to an empty string.
 * @param userDismissible Whether an announcement can be dismissed by the user.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/announcement")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubAnnouncement(
        @Nullable String announcement,
        @Nullable Instant expiresAt,
        @Nullable Boolean userDismissible) {}
