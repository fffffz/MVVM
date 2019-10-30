package com.fffz.mvvm.data.webservice;

import com.fffz.mvvm.data.model.News;
import com.fffz.mvvm.data.model.TopNews;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface WebService {

    String BASE_URL = "http://news-at.zhihu.com/";

    String getNewsList = "/api/4/news/before/{date}";
    String getTopNewsList = "/api/4/news/latest";

    @GET(getNewsList)
    Observable<News> getNewsList(@Path("date") String date);

    @GET(getTopNewsList)
    Observable<TopNews> getTopNewsList();

}