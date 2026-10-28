package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Wire values of GitHubDiscussionAuthorAssociation. */
@CatalogSchema("components/schemas/discussion/properties/author_association")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubDiscussionAuthorAssociation {
    /** COLLABORATOR. */
    COLLABORATOR("COLLABORATOR"),
    /** CONTRIBUTOR. */
    CONTRIBUTOR("CONTRIBUTOR"),
    /** FIRST_TIMER. */
    FIRST_TIMER("FIRST_TIMER"),
    /** FIRST_TIME_CONTRIBUTOR. */
    FIRST_TIME_CONTRIBUTOR("FIRST_TIME_CONTRIBUTOR"),
    /** MANNEQUIN. */
    MANNEQUIN("MANNEQUIN"),
    /** MEMBER. */
    MEMBER("MEMBER"),
    /** NONE. */
    NONE("NONE"),
    /** OWNER. */
    OWNER("OWNER");

    private static final EnumUtils.WireIndex<GitHubDiscussionAuthorAssociation> WIRE_INDEX =
            EnumUtils.index(GitHubDiscussionAuthorAssociation.class, GitHubDiscussionAuthorAssociation::wireValue);

    private final String wireValue;

    GitHubDiscussionAuthorAssociation(final String wireValue) {
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
     * @throws IllegalArgumentException if {@code value} is not a known constant
     */
    @JsonCreator
    public static GitHubDiscussionAuthorAssociation fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHubDiscussionAuthorAssociation: " + value));
    }
}
