package io.github.mo.novel.dto.resp;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * 图像验证码 响应DTO
 * @author mo
 * @date 2022/7/9 16:02
 */
public class ImgVerifyCodeRespDto {
    /**
     * 当前会话ID，用于标识该图形验证码属于哪个会话
     */
    @Schema(description = "sessionId")
    private String sessionId;

    /**
     * Base64 编码的验证码图片
     * */
    @Schema(description = "Base64 编码的验证码图片")
    private String img;
}