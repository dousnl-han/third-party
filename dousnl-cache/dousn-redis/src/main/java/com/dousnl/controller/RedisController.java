package com.dousnl.controller;

import com.dousnl.constant.EntityType;
import com.dousnl.service.LikeService;
import com.dousnl.service.NewsService;
import com.dousnl.utils.HostHolder;
import com.dousnl.utils.ToutiaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * TODO
 *
 * @version 1.0
 * @author: hanliangliang
 * @date: 2020/7/14 11:16
 */
@RestController
public class RedisController {

    @Autowired
    private LikeService likeService;
    @Autowired
    private NewsService newsService;
    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(path = {"/like"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String like(@RequestParam("newsId") int newsId){
        //在likeKey对应的集合中加入当前用户的id
        long likeCount = likeService.like(HostHolder.getUser().getId(), EntityType.ENTITY_NEWS, newsId);

        //资讯上更新点赞数
        newsService.updateLikeCount(newsId, (int)likeCount);
        return ToutiaoUtil.getJSONString(0, String.valueOf(likeCount));
    }

    @RequestMapping(path = {"/dislike"}, method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String disLike(@RequestParam("newsId") int newsId){

        //在disLikeKey对应的集合中加入当前用户
        long likeCount = likeService.disLike(HostHolder.getUser().getId(), EntityType.ENTITY_NEWS, newsId);
        if(likeCount <= 0){
            likeCount = 0;
        }

        //资讯上更新喜欢数
        newsService.updateLikeCount(newsId, (int)likeCount);
        return ToutiaoUtil.getJSONString(0, String.valueOf(likeCount));
    }

}
