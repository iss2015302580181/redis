package com.h3c.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Vote {

    private static final int SCORE = 432;

    @Autowired
    RedisUtils redisUtils;

    public boolean vote(Article article, User user) {
        addVote(article, user);
        incrScore(article);
        return false;
    }

    public void createrticle(Article article){
        addArticle(article);
        addTime(article);
        addScore(article);
    }

    public void addArticle(Article article) {
        redisUtils.hSet("article", "article:" + article.getId(), article.getName());
    }

    public void addTime(Article article) {
        redisUtils.zAdd("time", "article:" + article.getId(), System.currentTimeMillis() / 1000);
    }

    public void addScore(Article article) {
        redisUtils.zAdd("score", "article:" + article.getId(), System.currentTimeMillis() / 1000);
    }

    public boolean addVote(Article article, User user) {
        return redisUtils.sAdd("voted:" + article.getId(), "user:" + user.getId()) > 0 ? true : false;
    }

    public void incrScore(Article article) {
        redisUtils.zIncrBy("score", "article:" + article.getId(), SCORE);
    }
}
