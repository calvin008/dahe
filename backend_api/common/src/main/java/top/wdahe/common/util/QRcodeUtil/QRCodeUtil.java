package top.wdahe.common.util.QRcodeUtil;



import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import jakarta.servlet.ServletOutputStream;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;


@Slf4j
@UtilityClass
public class QRCodeUtil {

    /*
    * 默认宽度
    *
    * */
    private static final Integer WIDTH = 140;

    /**
     * 默认高度
     */
    private static final Integer HEIGHT = 140;

    /**
     * LOGO 默认宽度
     */
    private static final Integer LOGO_WIDTH = 22;

    /**
     * LOGO 默认高度
     */
    private static final Integer LOGO_HEIGHT = 22;
    /**
     * 图片格式
     */
    private static final String IMAGE_FORMAT = "png";

    /**
     * 编码
     *
     */
    private static final String CHARSET = "utf-8";

    /**
     * 返回前端的前缀
     */
    private static final String BASE64_IMAGE = "data:image/png;base64,%s";


    /**
     * 生成二维码,使用默认尺寸,插入尺寸logo
     */
    public String getBase64QRCode(String content, String logoUrl) {
        return getBase64Image(content,WIDTH,HEIGHT,logoUrl,LOGO_WIDTH,LOGO_HEIGHT);
    }

    public String getBase64Image(String content,Integer width,Integer height,String logoUrl,Integer logoWidth,Integer logoHeight){
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        BufferedImage bufferedImage = createQRCode(content,width,height,logoUrl,logoWidth,logoHeight);
        try {
            ImageIO.write(bufferedImage,IMAGE_FORMAT,os);

        }catch (IOException e) {
            log.error("[生成二维码失败]",e);
        }
        return String.format(BASE64_IMAGE, Base64.encode(os.toByteArray()));
    }


    private BufferedImage createQRCode(String content,Integer width,Integer height,String logoUrl,Integer logoWidth,Integer logoHeight) {
        if(StrUtil.isNotBlank(content)) {
            ServletOutputStream stream = null;
            //创建hash对象
            HashMap<EncodeHintType,Comparable> hints = new HashMap<>(4);
            //指定字节编码
            hints.put(EncodeHintType.CHARACTER_SET,CHARSET);
            //指定二维码的纠错级别
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            //设置图片边距
            hints.put(EncodeHintType.MARGIN,2);
            try {
                QRCodeWriter writer = new QRCodeWriter();
                BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE,width,height,hints);
                BufferedImage bufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

                for(int x=0; x<width; x++) {
                    for (int y=0; y<height; y++) {
                        bufferedImage.setRGB(x,y,bitMatrix.get(x,y) ? 0xFF000000 : 0xFFFFFFFF);
                    }
                }
                if(StrUtil.isNotBlank(logoUrl)) {
                    //设置logo
                    insertLogo(bufferedImage,width,height,logoUrl,logoWidth,logoHeight);
                }
                return bufferedImage;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(stream != null) {
                    try {
                        stream.flush();
                        stream.close();
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    private void insertLogo(BufferedImage source,Integer width,Integer height,String logoUrl,Integer logoWidth,Integer logoHeight) throws Exception {
        Image src = ImageIO.read(new URL(logoUrl));
        Graphics2D graph = source.createGraphics();
        int x= (width - logoWidth) /2;
        int y = (height - logoHeight) /2;
        graph.drawImage(src,x,y,logoWidth,logoHeight,null);
        Shape shape = new RoundRectangle2D.Float(x,y,logoWidth,logoHeight,6,6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
        }
    }



