package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Wire values of GitHubDiscussionAnswerChosenByType. */
@CatalogSchema("components/schemas/discussion/properties/answer_chosen_by/properties/type")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubDiscussionAnswerChosenByType {
    /** Bot. */
    BOT("Bot"),
    /** User. */
    USER("User"),
    /** Organization. */
    ORGANIZATION("Organization"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubDiscussionAnswerChosenByType> WIRE_INDEX =
            EnumUtils.index(GitHubDiscussionAnswerChosenByType.class, GitHubDiscussionAnswerChosenByType::wireValue);

    private final String wireValue;

    GitHubDiscussionAnswerChosenByType(final String wireValue) {
        this.wireValue = wireValue;
    }

    /**
     * JSON {@code wireValue} of this constant.
     *
     * @return wire value
     */
    @JsonValue
    public String wireValue() {
        return wireValue;
    }

    /**
     * Parses the wire value.
     *
     * @param value wire value
     * @return constant
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubDiscussionAnswerChosenByType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
