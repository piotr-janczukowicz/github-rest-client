package io.github.piotr_janczukowicz.github.api.projects;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.issues.GitHubIssue;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubPullRequestSimple;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;
import java.util.Map;

/** Type of content tracked in a project item. */
@CatalogSchema("components/schemas/projects-v2-item-content-type")
public enum GitHubProjectItemContentType {
    ISSUE("Issue"),
    PULL_REQUEST("PullRequest"),
    DRAFT_ISSUE("DraftIssue"),

    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubProjectItemContentType> WIRE_INDEX =
            EnumUtils.index(GitHubProjectItemContentType.class, GitHubProjectItemContentType::wireValue);

    private final String wireValue;

    GitHubProjectItemContentType(final String wireValue) {
        this.wireValue = wireValue;
    }

    /**
     * Value GitHub sends and expects in JSON.
     *
     * @return wire string
     */
    @JsonValue
    public String wireValue() {
        return wireValue;
    }

    /**
     * Java type used to decode {@code content} JSON for this content type.
     *
     * @return content value class
     */
    public Class<?> contentValueType() {
        return switch (this) {
            case ISSUE -> GitHubIssue.class;
            case PULL_REQUEST -> GitHubPullRequestSimple.class;
            case DRAFT_ISSUE -> GitHubProjectDraftIssue.class;
            case UNKNOWN -> Map.class;
        };
    }

    /**
     * Item content type from a wire value.
     *
     * @param value JSON value
     * @return matching content type, or {@link #UNKNOWN} for a value this library does not know
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubProjectItemContentType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
