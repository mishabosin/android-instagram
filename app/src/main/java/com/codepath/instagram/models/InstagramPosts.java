package com.codepath.instagram.models;

import java.io.Serializable;
import java.util.List;

public class InstagramPosts implements Serializable {
    public List<InstagramPost> posts;

    public InstagramPosts(List<InstagramPost> posts) {
        this.posts = posts;
    }

    public List<InstagramPost> getPosts() {
        return posts;
    }
}
