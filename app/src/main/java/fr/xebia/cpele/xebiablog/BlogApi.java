package fr.xebia.cpele.xebiablog;

import retrofit2.Call;
import retrofit2.http.GET;

interface BlogApi {
    @GET("/feed")
    Call<Feed> fetchFeed();
}
