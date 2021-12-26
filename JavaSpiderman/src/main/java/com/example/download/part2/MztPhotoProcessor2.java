package com.example.download.part2;

import com.example.common.MztImage;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yyt
 * @date 2021年12月25日 1:46
 */
public class MztPhotoProcessor2 implements PageProcessor {

    /**
     * 抓取网站的相关配置，包括编码、抓取间隔、重试次数等
     */
    private Site site = Site.me()
            .setDomain("mmzztt.com")
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36 Edg/96.0.1054.62")
            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
            .addHeader("Accept-Encoding", "gzip, deflate, br")
            .addHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6,zh-TW;q=0.5")
            .addHeader("Cache-Control", "no-cache")
            .addHeader("Connection", "keep-alive")
            .addHeader("DNT", "1")
            .addHeader("Host", "mmzztt.com")
            .addHeader("Pragma", "no-cache")
            .addHeader("Referer", "https://mmzztt.com/photo/")
            .addHeader("sec-ch-ua", "' Not A;Brand';v='99', 'Chromium';v='96', 'Microsoft Edge';v='96'")
            .addHeader("sec-ch-ua-mobile", "?0")
            .addHeader("sec-ch-ua-platform", "'Windows'")
            .addHeader("Sec-Fetch-Site", "same-origin")
            .setCharset("utf-8")
            .setRetryTimes(3)
            .setSleepTime(5000);

    /**
     * 专辑列表：1,2,3,4,5...
     * https://mmzztt.com/photo/page/1/
     */
    public static final String URL_LIST = "https:\\/\\/mmzztt\\.com\\/photo\\/page\\/\\d+\\/";

    /**
     * 具体专辑内容：52043,abc,ijk,xyz...
     * https://mmzztt.com/photo/52043
     */
    public static final String URL_ARTICLE = "https:\\/\\/mmzztt\\.com\\/photo\\/\\d+";

    /**
     * 记录下载图片的次数
     */
    public static AtomicInteger counter = new AtomicInteger(1);

    public void atomicAdd() {
        counter.incrementAndGet();
    }

    /**
     * process 是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
     *
     * @param page
     * @author yyt
     * @date 2021/12/21 18:14
     */
    @Override
    public void process(Page page) {
        // 列表页
        if (page.getUrl().regex(URL_LIST).match()) {
            /**
             * 运行时抛出异常：Links can not apply to plain text.
             * Please check whether you use a previous xpath with attribute select (/@href etc).
             * 原因：使用过正则匹配后就不能使用 XPath 抽取了，因为正则取出来的没有办法保证是 Html，当做是是纯文本处理了
             */
            page.addTargetRequests(page.getHtml().xpath("//a[@class='uk-inline']/@href").regex(URL_ARTICLE).all());
            page.addTargetRequests(page.getHtml().links().regex(URL_LIST).all());
        } else {
            // 标题名
            String imgTitle = page.getHtml().xpath("//h1[@class='uk-article-title uk-text-truncate']/text()").toString();
            // 套图第一张的链接
            String imgUrl = page.getHtml().xpath("//figure[@class='uk-inline']/img/@src").toString();
            MztImage image = new MztImage();
            image.setId(counter.get());
            atomicAdd();
            image.setName(imgTitle);
            image.setAvatar(imgUrl);
            page.putField("result", image);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        long startTime, endTime;
        System.out.println("开始爬取数据...");
        startTime = System.currentTimeMillis();

        // new MztPhotoProcessor()
        Spider.create(new MztPhotoProcessor2())
                // 从这个网址开始爬取
                .addUrl("https://mmzztt.com/photo/page/1/")
                // .addPipeline(new JsonFilePipeline("D:\\Desktop\\MZT\\"))
                .addPipeline(new DownloadImgPipeline2())
                // 开启 3 个线程抓取
                .thread(3)
                .run();

        endTime = System.currentTimeMillis();
        System.out.println("爬取结束，耗时约 " + ((endTime - startTime) / 1000) + " 秒，共爬取 " + counter.get() + " 张图片");
    }
}
