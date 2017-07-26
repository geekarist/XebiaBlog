package fr.xebia.cpele.xebiablog.model.api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BlogApi {
    @GET("/feed")
    Call<Feed> fetchFeed();
}
