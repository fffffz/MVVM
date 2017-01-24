package org.icegeneral.mvvm.data.internal.webservice;

import org.icegeneral.mvvm.data.model.News;
import org.icegeneral.mvvm.data.model.TopNews;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by jianjun.lin on 2017/1/6.
 */

public interface WebService {

    String BASE_URL = "http://news-at.zhihu.com/";

    String getNewsList = "/api/4/news/before/{date}";
    String getTopNewsList = "/api/4/news/latest";

    @GET(getNewsList)
    Observable<News> getNewsList(@Path("date") String date);

    @GET(getTopNewsList)
    Observable<TopNews> getTopNewsList();

}