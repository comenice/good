package com.ku.bobo.util;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ku.bobo.api.CommonResult;
import com.ku.bobo.api.ICodeInfo;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Date 2020/11/3 17:01
 * @Created by xb
 */
@Slf4j
public class ResponseUtils {

    /**
     * 往 response 写出 json
     *
     * @param response 响应
     * @param codeInfo   状态
     * @param data     返回数据
     */
    public static void renderJson(HttpServletResponse response, ICodeInfo codeInfo, Object data) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "*");
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(200);

            // FIXME: hutool 的 BUG：JSONUtil.toJsonStr()
            //  将JSON转为String的时候，忽略null值的时候转成的String存在错误
            response.getWriter()
                    .write(JSONUtil.toJsonStr(new JSONObject(CommonResult.failed( codeInfo ), false)));
            response.getWriter().flush();
        } catch (IOException e) {
            log.error("Response写出JSON异常，", e);
        }
    }

    public static void renderJson(HttpServletResponse response, ICodeInfo codeInfo ) {
        renderJson( response , codeInfo ,null );
    }
}