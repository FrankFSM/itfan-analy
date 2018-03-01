package com.itfan.analy.utils;

import com.itfan.crawler.global.Constant.THIS_SERVER_URL;
import com.itfan.crawler.global.Constant.TimeOutInfo;
import com.itfan.crawler.global.Constant.UA;
import java.io.IOException;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * JsoupUtils
 * Jsoup工具类
 *
 * @Author: ralap
 * @Date: created at 2017/8/15 22:29
 */
@Log4j2
public class JsoupUtils {

    public static Document getDocWithPC(String url) {
        Document document = new Document(THIS_SERVER_URL.WEB_URL);
        try {
            document = Jsoup.connect(url).userAgent(UA.PC).timeout(TimeOutInfo.JSOUP)
                    .ignoreContentType(true)
                    .get();
        } catch (IOException e) {
            log.error("网址请求失败：" + url);
        }
        return document;
    }

    public static Document getDocWithPhone(String url) {
        Document document = new Document(THIS_SERVER_URL.WEB_URL);
        try {
            document = Jsoup.connect(url).userAgent(UA.PHONE).timeout(TimeOutInfo.JSOUP)
                    .ignoreContentType(true).validateTLSCertificates(false).get();
        } catch (IOException e) {
            log.error("网址请求失败：" + url);
        }
        return document;
    }

    public static Document getDocWithPad(String url) {
        Document document = new Document(THIS_SERVER_URL.WEB_URL);
        try {
            document = Jsoup.connect(url).userAgent(UA.PAD).timeout(TimeOutInfo.JSOUP)
                    .ignoreContentType(true).validateTLSCertificates(false).get();
        } catch (IOException e) {
            log.error("网址请求失败：" + url);
        }
        return document;
    }

    public static Document getDocWithPhone(String url, String cookie) {
        Document document = new Document(THIS_SERVER_URL.WEB_URL);
        try {
            document = Jsoup.connect(url).userAgent(UA.PHONE).timeout(TimeOutInfo.JSOUP)
                    .header("Cookie", cookie).ignoreContentType(true).get();
        } catch (IOException e) {
            log.error("网址请求失败：" + url);
        }
        return document;
    }

    public static Document getDocWithPC(String url, String cookie) {
        Document document = new Document(THIS_SERVER_URL.WEB_URL);
        try {
            document = Jsoup.connect(url).userAgent(UA.PHONE).timeout(TimeOutInfo.JSOUP)
                    .header("Cookie", cookie).ignoreContentType(true).get();
        } catch (IOException e) {
            log.error("网址请求失败：" + url);
        }
        return document;
    }

}
