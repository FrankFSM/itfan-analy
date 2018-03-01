package com.itfan.analy.search;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.itfan.analy.domain.Episode;
import com.itfan.crawler.domain.Video;
import com.itfan.crawler.global.Constant.LetvInfo;
import com.itfan.crawler.global.Constant.Provider;
import com.itfan.crawler.redis.JsoupUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;


/**
 * Letv
 * 乐视视频解析
 *
 * @Author: ralap
 * @Date: created at 2017/8/16 16:46
 */
@Component
public class Letv {


    public Video parse(String url) {
        final Video video = new Video();
        video.setRawUrl(url);
        this.initVideo(video);
        String vid = this.matchVid(url);
        String routeUrl = String.format(LetvInfo.ROUTE, vid, getTkey());
        Document document = JsoupUtils.getDocWithPhone(routeUrl, LetvInfo.COOKIE);
        JSONObject object = JSONObject.parseObject(document.text());
        JSONObject playurl = object.getJSONObject("msgs").getJSONObject("playurl");
        String title = playurl.getString("title");
        video.setTitle(title);
        String image = playurl.getString("pic").replace("120_90", "360_180");
        image = image.replace("http:", "");
        video.setImageUrl(image);
        String domain = playurl.getJSONArray("domain").getString(0);
        String dispatch = getDispatch(playurl.getJSONObject("dispatch"));
        JSONObject yuanxian = object.getJSONObject("msgs").getJSONObject("yuanxian");
        String locationUrl;
        if (yuanxian != null) {
            String token = yuanxian.getString("token");
            locationUrl = String.format(LetvInfo.VIP_LOCATION, domain, dispatch, token, vid);
        } else {
            locationUrl = String.format(LetvInfo.VIP_LOCATION, domain, dispatch, "", vid);
        }
        Document result = JsoupUtils.getDocWithPhone(locationUrl);
        String text = StringEscapeUtils.unescapeJava(result.text());
        text = text.replace("vjs_149067353337651(", "");
        text = text.replace(");", "");
        JSONObject videoJson = JSONObject.parseObject(text);
        video.setRealUrl(videoJson.getJSONArray("nodelist").getJSONObject(0).getString("location"));
        return video;
    }


    public List<Episode> parseEpisodes(String videoUrl) {
        List<Episode> episodes = new ArrayList<>();
        Document document = JsoupUtils.getDocWithPhone(videoUrl);
        Matcher matcher = Pattern.compile("([0-9]{5,})\\.html").matcher(document.html());
        if (matcher.find()) {
            String vid = matcher.group(1);
            String videosAPI = String.format(LetvInfo.VIDEOS, vid);
            String data = JsoupUtils.getDocWithPhone(videosAPI).body().text();
            JSONObject jsonObject = JSONObject.parseObject(data);
            JSONArray array = jsonObject.getJSONObject("data").getJSONObject("episode")
                    .getJSONArray("videolist");
            if (array.size() > 1) {
                for (int i = array.size() - 1; i >= 0; i--) {
                    JSONObject object = array.getJSONObject(i);
                    Episode episode = new Episode();
                    Integer index = object.getInteger("episode");
                    if (index < 10) {
                        episode.setIndex("0" + index);
                    } else {
                        episode.setIndex("" + index);
                    }
                    episode.setRealUrl(object.getString("url"));
                    episodes.add(episode);
                }
            }
        }
        return episodes;
    }

    /**
     * 初始化视频信息
     */
    private void initVideo(Video video) {
        video.setProvider(Provider.LETV);
        video.setParserName("Github");
        video.setParser("http://github.com");
        video.setType("H5");
    }

    /**
     * 从 URL 中匹配 VID
     */
    private String matchVid(String videoUrl) {
        Matcher matcher = Pattern.compile(LetvInfo.VID_REGEX).matcher(videoUrl);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            Document realDocument = JsoupUtils.getDocWithPC(videoUrl);
            matcher = Pattern.compile("vid:\"(.*?)\"").matcher(realDocument.html());
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        return "";
    }

    /**
     * 获取最清晰的视频线路
     */
    private String getDispatch(JSONObject dispatch) {
        for (String dis : LetvInfo.DIS_LIST) {
            if (dispatch.containsKey(dis)) {
                return dispatch.getJSONArray(dis).getString(0);
            }
        }
        return null;
    }

    /**
     * 乐视tkey算法
     */
    private static String getTkey() {
        int a = (int) (System.currentTimeMillis() / 1000);
        for (int i = 0; i < 8; i++) {
            int b = a >> 1;
            int c = (0x1 & a) << 31;
            a = b + c;
        }
        int result = 0xB074319 ^ a;
        return "" + result;
    }

}
