package com.itfan.analy.utils;

import org.apache.logging.log4j.Logger;


/**
 * Created by ralap on 2017/8/15.
 */
public class LogUtils {

    public static void longInfo(Logger logger, String info) {
        logger.info("=============================================================\n"
                + info + "\n"
                + "=============================================================");
    }

}
