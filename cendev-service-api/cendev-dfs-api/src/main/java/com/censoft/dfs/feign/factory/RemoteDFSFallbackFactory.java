package com.censoft.dfs.feign.factory;
import com.censoft.common.core.domain.R;
import com.censoft.dfs.feign.RemoteDFSService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName RemoteDFSFallbackFactory
 * @Description TODO
 * @Author hpf
 * @Date 2020/12/31  14:34
 * @Version 1.0
 **/
@Slf4j
@Component
public class RemoteDFSFallbackFactory implements FallbackFactory<RemoteDFSService> {

    @Override
    public RemoteDFSService create(Throwable throwable) {
        return new RemoteDFSService() {
            @Override
            public R upload(MultipartFile file) {
                System.out.println("文件服务出错，请检查文件服务");
                return R.error("文件服务出错");
            }
        };
    }
}
