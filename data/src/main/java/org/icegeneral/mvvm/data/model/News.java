package org.icegeneral.mvvm.data.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jianjun.lin on 2017/1/6.
 */

public class News implements Serializable {

    private String date;

    private List<StoriesBean> stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

    public static class StoriesBean implements Serializable {
        private String title;
        private String ga_prefix;
        private int type;
        private long id;
        private List<String> images;
        private boolean multipic;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public boolean isMultipic() {
            return multipic;
        }

        public void setMultipic(boolean multipic) {
            this.multipic = multipic;
        }

        private boolean isHeader;
        private String date;

        public boolean isHeader() {
            return isHeader;
        }

        public void setHeader(boolean header) {
            isHeader = header;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }

}