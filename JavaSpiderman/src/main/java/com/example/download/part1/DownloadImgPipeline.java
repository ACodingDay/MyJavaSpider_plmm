package com.example.download.part1;

import com.example.common.MztImage;
import com.example.tool.HttpFileTool;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

/**
 * @author yyt
 * @date 2021年12月21日 19:30
 */
public class DownloadImgPipeline implements Pipeline {
    /**
     * 图片保存的路径
     */
    private static final String BASE_IMG_FILE_PATH = "D:" + File.separator + "Desktop" + File.separator + "MZT" + File.separator;

    @Override
    public void process(ResultItems resultItems, Task task) {
        MztImage newImage = resultItems.get("result");
        if(newImage != null){
            // 根据标题创建对应的文件
            String dirStr = BASE_IMG_FILE_PATH + newImage.getName() + ".txt";
            File file = new File(dirStr);
            if (!file.getParentFile().exists()){
                // 包含父文件夹
                file.getParentFile().mkdirs();
            }
            // 根据链接下载图片
            String imageUrl = newImage.getAvatar();
            // 获取图片的文件名
            // String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
            try {
                // 下载图片
                // HttpFileTool.download(imageUrl, dirStr + imageUrl);
                Writer writer = new FileWriter(file, true);
                writer.write(imageUrl + "\r\n");
                writer.flush();
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("DownloadImgPipeline 运行出现异常...");
            }
        }
    }
}
