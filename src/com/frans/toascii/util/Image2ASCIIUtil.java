package com.frans.toascii.util;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Image2ASCIIUtil {
    /**
     * ���ջҶ�ֵ��С����������ַ�����0Ϊ��ڣ�255Ϊ�ף�
     */
    private static String grayString = "@#$%MEWHXD8A4wp03u?7i{+tc*!<\"~:,^.` ";
    
    /**
     * ����һС�������е�ƽ���Ҷ�ֵ
     * @param image       ������ʹ�õ�ͼƬ
     * @param xOffset     ����checkWidth��checkHeight�Ĵ�С��ͼƬ��Ϊ���ɿ��x���ϵ�����
     * @param yOffset     ����checkWidth��checkHeight�Ĵ�С��ͼƬ��Ϊ���ɿ��y���ϵ�����
     * @param checkWidth  ���Ŀ��
     * @param checkHeight ���ĸ߶�
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
        //�ֱ����r��g��b��ֵ
        int r = (argb >> 16) & 0xff;
        int g = (argb >> 8) & 0xff;
        int b = argb & 0xff;
        //ʹ�ü�Ȩƽ��ֵ����Ҷ�ֵ
        return (int) (0.3 * r + 0.59 * g + 0.11 * b);
    }
    
    /**
     * @param grayScaleValue �Ҷ�ֵ
     * @return grayString�лҶ�ֵ��ӽ����ַ�
     */
    private static char getClosestChar(int grayScaleValue) {
        return grayString.charAt((int)(grayScaleValue / 255.0 * (grayString.length() - 1)));
    }
    
    /**
     * @param imagePath   ͼƬ·��
     * @param checkWidth  ÿ���Ŀ�ȼ��һ�λҶ�ֵ
     * @param checkHeight ÿ���ĸ߶ȼ��һ�λҶ�ֵ
     * @return  ת������ַ����б�
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
