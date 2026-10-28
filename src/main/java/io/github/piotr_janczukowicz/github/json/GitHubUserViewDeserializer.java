package io.github.piotr_janczukowicz.github.json;

import static io.github.piotr_janczukowicz.util.StringUtils.isNotBlank;

import io.github.piotr_janczukowicz.github.api.users.GitHubAuthenticatedUser;
import io.github.piotr_janczukowicz.github.api.users.GitHubUser;
import io.github.piotr_janczukowicz.github.api.users.GitHubUserView;
import io.github.piotr_janczukowicz.json.jackson.BufferedJson;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.deser.std.StdDeserializer;

/**
 * Decodes {@link GitHubUserView}: uses {@code user_view_type} when present, otherwise infers {@code private} from
 * {@code two_factor_authentication}.
 *
 * <p>A {@code user_view_type} that this library does not know fails the decode with a
 * {@link io.github.piotr_janczukowicz.json.JsonDecodeException}; there is no value to return in its place.
 */
final class GitHubUserViewDeserializer extends StdDeserializer<GitHubUserView> {

    private static final String PROP_USER_VIEW_TYPE = "user_view_type";
    private static final String PROP_TWO_FACTOR = "two_factor_authentication";
    private static final String PUBLIC = "public";
    private static final String PRIVATE = "private";

    GitHubUserViewDeserializer() {
        super(GitHubUserView.class);
    }

    @Override
    public GitHubUserView deserialize(final JsonParser parser, final DeserializationContext context) {
        try (BufferedJson json = BufferedJson.capture(parser, context)) {
            final String viewType = resolveViewType(json);
            return switch (viewType) {
                case PUBLIC -> read(json, viewType, GitHubUser.class);
                case PRIVATE -> read(json, viewType, GitHubAuthenticatedUser.class);
                default -> throw json.mismatch("Unknown GitHub user view type: " + viewType);
            };
        }
    }

    private static String resolveViewType(final BufferedJson json) {
        final String property = json.text(PROP_USER_VIEW_TYPE);
        if (isNotBlank(property)) {
            return property;
        }
        return json.hasField(PROP_TWO_FACTOR) ? PRIVATE : PUBLIC;
    }

    private static <T extends GitHubUserView> T read(
            final BufferedJson json, final String viewType, final Class<T> type) {
        final String present = json.text(PROP_USER_VIEW_TYPE);
        if (viewType.equals(present)) {
            return json.read(type);
        }
        return json.readReplacingText(PROP_USER_VIEW_TYPE, viewType, type);
    }
}
