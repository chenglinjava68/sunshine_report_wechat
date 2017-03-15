package com.wechat.controller;

import com.wechat.response.BaseResponse;
import com.wechat.response.ResponseCode;
import com.wechat.sys.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhusen on 2017/1/9.
 */
@Controller
@RequestMapping(value = "/upload")
public class UploadController extends ABaseController {

    private final Logger logger = LoggerFactory.getLogger(UploadController.class);

    /**
     * 图片上传
     * @param multipartFile
     * @return
     */
    @RequestMapping(value = "/image", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse upload(@RequestParam(value = "file") MultipartFile multipartFile) {
        BaseResponse baseResponse = new BaseResponse();
        if (multipartFile == null) {
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.code, "multipartFile");
        }
        try {
            String baseUrl = CommonConstant.FILE_UPLOAD_BASE_URL;
            File file = new File(baseUrl);
            if (!file.exists()) {
                file.mkdirs();
            }
            String name = multipartFile.getOriginalFilename();
            name = System.currentTimeMillis() + name.substring(name.lastIndexOf("."));
            String path = baseUrl + name;
            file = new File(path);
            multipartFile.transferTo(file);
            Map<String, Object> result = new HashMap();
            result.put("path", CommonConstant.IMG_BASE_URL + name);
            baseResponse.setResult(result);
            baseResponse.setCode(ResponseCode.SUCCESS.code);
            logger.info("fileUpload  response :" + super.gson.toJson(baseResponse));
            return baseResponse;
        } catch (IOException e) {
            logger.error("fileUpload error", e);
            baseResponse.setCode(ResponseCode.SERVICE_ERROR.code);
            return baseResponse;
        }
    }
}
