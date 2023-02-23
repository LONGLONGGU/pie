package com.framework.pie.admin.util;

import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class ImageUtil {

    /**
     * 将BufferedImage转换成Base64字符串
     * @param bufferedImage
     * @param imageFormatName
     * @return
     * @throws IOException
     */
    public static String getBufferedImageToBase64(BufferedImage bufferedImage, String imageFormatName) throws IOException {
        if(StringUtils.isBlank(imageFormatName)){
            imageFormatName = "png";
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, imageFormatName, stream);
        String s = Base64.getEncoder().encodeToString(stream.toByteArray());
        return s;
    }
}
