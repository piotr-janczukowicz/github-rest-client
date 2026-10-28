package io.github.piotr_janczukowicz.github.api.markdown;

/** Content type of the body of {@code POST /markdown/raw}; GitHub accepts only these two. */
public enum GitHubMarkdownRawContentType {
    TEXT_PLAIN("text/plain"),
    TEXT_X_MARKDOWN("text/x-markdown");

    private final String mediaType;

    GitHubMarkdownRawContentType(final String mediaType) {
        this.mediaType = mediaType;
    }

    /**
     * Media type sent as {@code Content-Type} on {@code POST /markdown/raw}.
     *
     * @return media type
     */
    public String mediaType() {
        return mediaType;
    }
}
