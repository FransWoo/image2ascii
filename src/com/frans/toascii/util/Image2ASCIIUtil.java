package com.frans.toascii.util;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Image2ASCIIUtil {
    /**
     * 按照灰度值从小到大排序的字符串（0为最黑，255为白）
     */
    private static String grayString = "@#$%MEWHXD8A4wp03u?7i{+tc*!<\"~:,^.` ";
    
    /**
     * 计算一小块区域中的平均灰度值
     * @param image       计算所使用的图片
     * @param xOffset     按照checkWidth和checkHeight的大小将图片分为若干块后，x轴上的坐标
     * @param yOffset     按照checkWidth和checkHeight的大小将图片分为若干块后，y轴上的坐标
     * @param checkWidth  检查的宽度
     * @param checkHeight 检查的高度
     * @return
     */
    private static int getAverageGrayScaleValue(final BufferedImage image, int xOffset, int yOffset, int checkWidth, int checkHeight) {
        long sum = 0;
        for (int i = xOffset * checkWidth; i < xOffset * checkWidth + checkWidth; ++i) {
            for (int j = yOffset * checkHeight; j < yOffset * checkHeight + checkHeight; ++j) {
                sum += getGrayScaleValue(image.getRGB(i, j));
            }
        }
        return (int)(sum / (checkWidth * checkHeight));
    }
    
    
    /**
     * @param  argb
     * @return (int)(0.3 * r + 0.59 * g + 0.11 * b)
     */
    private static int getGrayScaleValue(int argb) {
        //分别计算r，g，b的值
        int r = (argb >> 16) & 0xff;
        int g = (argb >> 8) & 0xff;
        int b = argb & 0xff;
        //使用加权平均值计算灰度值
        return (int) (0.3 * r + 0.59 * g + 0.11 * b);
    }
    
    /**
     * @param grayScaleValue 灰度值
     * @return grayString中灰度值最接近的字符
     */
    private static char getClosestChar(int grayScaleValue) {
        return grayString.charAt((int)(grayScaleValue / 255.0 * (grayString.length() - 1)));
    }
    
    /**
     * @param imagePath   图片路径
     * @param checkWidth  每多大的宽度检查一次灰度值
     * @param checkHeight 每多大的高度检查一次灰度值
     * @return  转换后的字符串列表
     */
    public static List<String> image2ascii(String imagePath, int checkWidth, int checkHeight){
        
        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            List<String> l = new ArrayList<String>();
            final int wNum = image.getWidth() / checkWidth;
            final int hNum = image.getHeight() / checkHeight;
            for (int y = 0; y < hNum; ++y) {
                StringBuffer sb = new StringBuffer();
                for (int x = 0; x < wNum; ++x) {
                    sb.append(getClosestChar(getAverageGrayScaleValue(image, x, y, checkWidth, checkHeight)));
                }
                l.add(sb.toString());
            }
            return l;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
