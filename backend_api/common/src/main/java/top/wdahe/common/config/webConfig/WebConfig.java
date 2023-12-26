package top.wdahe.common.config.webConfig;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class WebConfig {
    public static String getHtml(String urlStr) {
        try {
            URL url = new URL(urlStr);
            //通过url的openStream()方法获取网络资源,打开输入流后读取url内容
            InputStream inputStream = url.openStream();
            //读取可用的字节大小
            byte[] arr = new byte[inputStream.available()];
            inputStream.read(arr);
            //关闭,释放系统资源
            inputStream.close();
            return new String(arr, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}
