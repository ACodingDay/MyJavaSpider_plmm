package test;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @author yyt
 * @date 2021年09月19日 19:17
 * 多线程下载测试
 */
public class DownloadTest extends Thread{
    // 网络图片地址
    private String url;
    // 保存的文件名
    private String fileName;

    public DownloadTest(String url, String fileName) {
        this.url = url;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        WebDownloader downloader = new WebDownloader();
        downloader.download(url, fileName);
        System.out.println(fileName + "文件下载完成");
    }

    public static void main(String[] args) {
        DownloadTest t1 = new DownloadTest("https://imeizi.me/static/images/20201029/2728/1584846420iFpF.jpg", "resource/img/t1.jpg");
        DownloadTest t2 = new DownloadTest("https://imeizi.me/static/images/20201029/2528/1583328393GrWA.jpg", "resource/img/t2.jpg");
        DownloadTest t3 = new DownloadTest("https://imeizi.me/static/images/20201228/4136/1609090513mhnz.jpg", "resource/img/t3.jpg");
        DownloadTest t4 = new DownloadTest("https://imeizi.me/static/images/20201029/2528/15833283834Rme.jpg", "resource/img/t4.jpg");
        DownloadTest t5 = new DownloadTest("https://imeizi.me/static/images/20201029/2834/1585649671cwfI.jpg", "resource/img/t5.jpg");
        // 启动线程下载
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
    }
}

// 下载器
class WebDownloader {
    public void download(String url, String fileName) {
        try {
            // 若出现错误：java.net.ConnectException: Connection timed out: connect，说明网络条件不好
            FileUtils.copyURLToFile(new URL(url), new File(fileName));
        }catch (IOException e){
            System.out.println("【WebDownloader】类的【download】方法出现了【IO】异常");
            e.printStackTrace();
        }
    }
}