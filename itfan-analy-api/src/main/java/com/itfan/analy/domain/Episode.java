package com.itfan.analy.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Episode
 * 电视剧集信息
 *
 * @Author: ralap
 * @Date: created at 2017/8/16 16:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Episode {

    /**
     * 集数
     */
    private String index;

    /**
     * 播放地址
     */
    private String realUrl;
}
