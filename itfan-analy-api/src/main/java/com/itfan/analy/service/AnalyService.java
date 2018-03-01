package com.itfan.analy.service;

import com.itfan.analy.domain.Episode;
import com.itfan.crawler.domain.Video;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: ralap
 * @date: created at 2018/1/13 14:09
 */
public interface AnalyService {

    /**
     * 解析letv地址
     */
    @RequestMapping(value = "/analysisLetv", method = RequestMethod.GET)
    Video analysisLetv(@RequestParam("url") String url);

    /**
     * 获取letv电视剧集数
     */
    @RequestMapping(value = "/episodeLetv", method = RequestMethod.GET)
    List<Episode> episodeLetv(@RequestParam("url") String url);
}
