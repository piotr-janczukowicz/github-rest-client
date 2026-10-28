package io.github.piotr_janczukowicz.github.api.interactions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * The type of GitHub user that can comment, open issues, or create pull requests while the interaction limit is in
 * effect.
 */
@CatalogSchema("components/schemas/interaction-group")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubInteractionGroup {
    EXISTING_USERS("existing_users"),
    CONTRIBUTORS_ONLY("contributors_only"),
    COLLABORATORS_ONLY("collaborators_only");

    private static final EnumUtils.WireIndex<GitHubInteractionGroup> WIRE_INDEX =
            EnumUtils.index(GitHubInteractionGroup.class, GitHubInteractionGroup::wireValue);

    private final String wireValue;

    GitHubInteractionGroup(final String wireValue) {
        this.wireValue = wireValue;
    }

    /**
     * Value GitHub sends and expects in JSON and query parameters.
     *
     * @return wire string
     */
    @JsonValue
    public String wireValue() {
        return wireValue;
    }

    /**
     * Interaction group for GitHub's {@code limit} string.
     *
     * @param value JSON or query value
     * @return matching group
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known group
     */
    @JsonCreator
    public static GitHubInteractionGroup fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub interaction group: " + value));
    }
}
