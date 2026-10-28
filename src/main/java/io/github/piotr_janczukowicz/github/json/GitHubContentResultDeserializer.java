package io.github.piotr_janczukowicz.github.json;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubContentDirectory;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubContentDirectoryEntry;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubContentFile;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubContentResult;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubContentSubmodule;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubContentSymlink;
import io.github.piotr_janczukowicz.json.jackson.BufferedJson;
import java.util.List;
import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.deser.std.StdDeserializer;

/**
 * Decodes get-content responses: JSON array (directory) or object (file, symlink, submodule).
 *
 * <p>A content {@code type} that this library does not know fails the decode with a
 * {@link io.github.piotr_janczukowicz.json.JsonDecodeException}; there is no value to return in its place.
 */
final class GitHubContentResultDeserializer extends StdDeserializer<GitHubContentResult> {

    private static final TypeReference<List<GitHubContentDirectoryEntry>> DIRECTORY_TYPE = new TypeReference<>() {};
    private static final String TYPE = "type";

    GitHubContentResultDeserializer() {
        super(GitHubContentResult.class);
    }

    @Override
    public GitHubContentResult deserialize(final JsonParser parser, final DeserializationContext context) {
        if (parser.currentToken() == JsonToken.START_ARRAY) {
            final List<GitHubContentDirectoryEntry> entries = context.readValue(parser, DIRECTORY_TYPE);
            return new GitHubContentDirectory(entries);
        }

        try (BufferedJson json = BufferedJson.capture(parser, context)) {
            final String type = json.requiredText(TYPE);
            return switch (type) {
                case "file" -> json.read(GitHubContentFile.class);
                case "symlink" -> json.read(GitHubContentSymlink.class);
                case "submodule" -> json.read(GitHubContentSubmodule.class);
                default -> throw json.mismatch("Unknown GitHub content type: " + type);
            };
        }
    }
}
