package com.lh.blog.util;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.lh.blog.enums.PathEnum;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.awt.image.DirectColorModel;
import java.awt.image.PixelGrabber;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageUtil {

    private static String path;

    static {
        Environment env = SpringContextUtils.getBean(Environment.class);
        path = env.getProperty("upload-path");
    }

    public static BufferedImage change2jpg(File f) {
        try {
            Image i = Toolkit.getDefaultToolkit().createImage(f.getAbsolutePath());
            PixelGrabber pg = new PixelGrabber(i, 0, 0, -1, -1, true);
            pg.grabPixels();
            int width = pg.getWidth(), height = pg.getHeight();
            final int[] RGB_MASKS = { 0xFF0000, 0xFF00, 0xFF };
            final ColorModel RGB_OPAQUE = new DirectColorModel(32, RGB_MASKS[0], RGB_MASKS[1], RGB_MASKS[2]);
            DataBuffer buffer = new DataBufferInt((int[]) pg.getPixels(), pg.getWidth() * pg.getHeight());
            WritableRaster raster = Raster.createPackedRaster(buffer, width, height, width, RGB_MASKS, null);
            BufferedImage img = new BufferedImage(RGB_OPAQUE, raster, false, null);
            return img;
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static void resizeImage(File srcFile, int width,int height, File destFile) {
        try {
            if(!destFile.getParentFile().exists())
                destFile.getParentFile().mkdirs();
            Image i = ImageIO.read(srcFile);
            i = resizeImage(i, width, height);
            ImageIO.write((RenderedImage) i, "jpg", destFile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static Image resizeImage(Image srcImage, int width, int height) {
        try {

            BufferedImage buffImg = null;
            buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            buffImg.getGraphics().drawImage(srcImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);

            return buffImg;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static String getPath(){
        return path;
    }

    public static String getPath(PathEnum type){
        File imageFolder= FileUtil.file(path, type.path);
        return imageFolder.getPath();
    }

    public static void uploadImg(String id, int uid, MultipartFile image, PathEnum type) throws IOException {
        String fileName = id+".jpg";
        // 文件夹
        File imageFolder= FileUtil.file(path, type.path);
        if (uid >= 0){
            imageFolder = FileUtil.file(imageFolder,  uid + "/");
        }
        if(!FileUtil.exist(imageFolder)) {
            FileUtil.mkdir(imageFolder);
        }
        // 文件
        File file = FileUtil.file(imageFolder,fileName);
        image.transferTo(file);
        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img, "jpg", file);
    }

    public static void deleteImg(String id, int uid, PathEnum type){
        String fileName = id+".jpg";
        // 文件夹
        File imageFolder= FileUtil.file(path, type.path);
        if (uid >= 0){
            imageFolder = FileUtil.file(imageFolder, uid + "/");
        }
        // 文件
        File file = FileUtil.file(imageFolder,fileName);
        FileUtil.del(file);
    }

    public static int getUserImg(){
        File imageFolder= FileUtil.file(path, PathEnum.USER_PROFILE.path);
        File[] files = imageFolder.listFiles();
        return (files != null ? files.length : 1) - 1;
    }

    public static String setUserImg(int num){
        // 初始化头像
        if (num == 0) {
            num = (int) (Math.random() * getUserImg()) + 1;
        }
        File imageFolder= FileUtil.file(path, PathEnum.USER_PROFILE.path);
        String fileName = num + ".jpg";
        File file = FileUtil.file(imageFolder, fileName);
        String subPath = FileUtil.subPath(path, file.getAbsolutePath());
        return "/image/" + subPath;
    }

    public static String uploadArticle(MultipartFile image, PathEnum type) throws IOException {

        // 使用随机数生成文件名
        String fileName = CalendarRandomUtil.getRandom() + ".jpg";
        // 当前日期作为文件夹名
        String subPath = DateUtil.format(new Date(), "yyyy/MM/dd/");

        File imageFolder= FileUtil.file(path, type.path, subPath);
        //如果不存在,创建文件夹
        if(!FileUtil.exist(imageFolder)) {
            FileUtil.mkdir(imageFolder);
        }
        File file = new File(imageFolder,fileName);
        image.transferTo(file);
        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img, "jpg", file);
        return FileUtil.subPath(path, file);
    }
}
