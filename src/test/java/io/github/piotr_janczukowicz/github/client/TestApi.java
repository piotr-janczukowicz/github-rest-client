package io.github.piotr_janczukowicz.github.client;

import io.github.piotr_janczukowicz.http.client.HttpCall;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.net.URI;

final class TestApi extends GitHubResourceApi {

    TestApi(final GitHubCaller caller) {
        super(caller);
    }

    @Override
    public HttpCall get(final PathTemplate path, final Object... values) {
        return super.get(path, values);
    }

    @Override
    public HttpCall post(final PathTemplate path, final Object... values) {
        return super.post(path, values);
    }

    @Override
    public HttpCall post(final URI uri) {
        return super.post(uri);
    }

    @Override
    public HttpCall put(final PathTemplate path, final Object... values) {
        return super.put(path, values);
    }

    @Override
    public HttpCall patch(final PathTemplate path, final Object... values) {
        return super.patch(path, values);
    }

    @Override
    public HttpCall delete(final PathTemplate path, final Object... values) {
        return super.delete(path, values);
    }

    public void checkGhesVersion(final String since, final String until) {
        requireGhesVersion(since, until);
    }
}
