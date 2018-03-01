package com.itfan.analy.service;

import com.itfan.analy.domain.Episode;
import com.itfan.analy.search.Letv;
import com.itfan.crawler.domain.Video;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ralap
 * @date: created at 2018/1/13 14:09
 */
@RestController
public class AnalyServiceImpl implements AnalyService {

    @Autowired
    private Letv letv;

    @Override
    public Video analysisLetv(String url) {
        url = url.replaceAll("\\?(spm|from).*", "");
        return letv.parse(url);
    }

    @Override
    public List<Episode> episodeLetv(String url) {
        url = url.replaceAll("\\?(spm|from).*", "");
        return letv.parseEpisodes(url);
    }
}
