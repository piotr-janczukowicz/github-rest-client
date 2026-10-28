package io.github.piotr_janczukowicz.github.api.emojis;

import java.util.Map;

final class EmojisCatalogFixtures {

    private EmojisCatalogFixtures() {}

    static Map<String, String> emojis() {
        return Map.of(
                "+1", "https://example.test/1f44d.png",
                "100", "https://example.test/1f4af.png");
    }
}
