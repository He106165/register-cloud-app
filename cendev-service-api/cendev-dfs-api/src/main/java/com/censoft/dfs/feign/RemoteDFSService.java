package com.censoft.dfs.feign;

import com.censoft.common.constant.ServiceNameConstants;
import com.censoft.common.core.domain.R;
import com.censoft.dfs.feign.factory.RemoteDFSFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: hepengfei
 * @Date: 2020/12/31 14:26
 */
@FeignClient(name = ServiceNameConstants.CEN_DFS,fallbackFactory = RemoteDFSFallbackFactory.class)
@Component
public interface RemoteDFSService {
    // 文件必须设置 multipart/form-data
//    @RequestMapping(value = "upload",method = RequestMethod.POST,consumes =  MediaType.MULTIPART_FORM_DATA_VALUE)
//    @CrossOrigin
//    public R upload(@RequestParam("file") MultipartFile file) throws Exception;

    @PostMapping(value = "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R upload(@RequestPart(value = "file") MultipartFile file);
}
