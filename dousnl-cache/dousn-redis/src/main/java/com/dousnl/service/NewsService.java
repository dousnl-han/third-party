package com.dousnl.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * TODO
 *
 * @version 1.0
 * @author: hanliangliang
 * @date: 2020/7/14 13:39
 */
@Service
public class NewsService {
    private HashMap<Integer, Integer> news = new HashMap<Integer, Integer>();

    public void updateLikeCount(int newsId, int likeCount) {
        news.put(newsId, likeCount);
    }
}
