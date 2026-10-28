package io.github.piotr_janczukowicz.github.api.markdown;

final class MarkdownCatalogFixtures {

    private MarkdownCatalogFixtures() {}

    static GitHubRenderMarkdownPayload renderMarkdownPayload() {
        return GitHubRenderMarkdownPayloadBuilder.builder()
                .text("Hello **world**")
                .build();
    }
}
