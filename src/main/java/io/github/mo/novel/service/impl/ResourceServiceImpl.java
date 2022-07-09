package io.github.mo.novel.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import io.github.mo.novel.core.common.resp.RestResp;
import io.github.mo.novel.dto.resp.ImgVerifyCodeRespDto;
import io.github.mo.novel.manager.VerifyCodeManager;
import io.github.mo.novel.service.ResourceService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * 资源（图片/视频/文档）相关服务实现类
 *
 * @author mo
 * @date 2022/7/9 16:26
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ResourceServiceImpl implements ResourceService {

    private final VerifyCodeManager verifyCodeManager;

    @Value("${novel.file.upload.path}")
    private String fileUploadPath;

    @Override
    public RestResp<ImgVerifyCodeRespDto> getImgVerifyCode() throws IOException {
        String sessionId = IdWorker.get32UUID();
        return RestResp.ok(ImgVerifyCodeRespDto.builder()
                .sessionId(sessionId)
                .img(verifyCodeManager.genImgVerifyCode(sessionId))
                .build());
    }


    @SneakyThrows
    @Override
    /**
     * 。。。
     */
    public RestResp<String> uploadImage(MultipartFile file) {
        LocalDateTime now = LocalDateTime.now();
        String savePath = "";
        String oriName = file.getOriginalFilename();
        String saveFileName = "";
        return RestResp.ok(savePath + File.separator + saveFileName);
    }
}
