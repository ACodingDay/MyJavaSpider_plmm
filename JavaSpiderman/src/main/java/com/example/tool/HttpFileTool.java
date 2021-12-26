package com.example.tool;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author yyt
 * @date 2021年12月21日 19:10
 * 文件下载
 */
public class HttpFileTool {

    private static final int DOWNLOAD_TIMEOUT = 6000;

    public static void download(String url, String fileName) {
        try {
            FileUtils.copyURLToFile(new URL(url), new File(fileName));
        }catch (IOException e){
            System.out.println("【HttpFileTool】类运行【download】方法出现【IOException】异常");
            e.printStackTrace();
        }
    }

    /**
     * 下载文件（网络图片下载）
     */
    public static void download2(String fromUrl, String filename, String savePath) throws Exception {
        // 构造URL
        URL from = new URL(fromUrl);
        // 打开连接
        URLConnection con = from.openConnection();
        // 设置请求超时为6s
        con.setConnectTimeout(DOWNLOAD_TIMEOUT);
        // 输入流
        InputStream is = con.getInputStream();
        // 1K 的数据缓冲
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;
        // 输出的文件流
        File sf = new File(savePath);
        if (!sf.exists()) {
            sf.mkdirs();
        }
        OutputStream os = new FileOutputStream(sf.getPath() + "\\" + filename);
        // 开始读写
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }
        // 完毕，关闭所有链接
        os.close();
        is.close();
    }
}
