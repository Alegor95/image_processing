/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gorovojimage.filters;

import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

/**
 *
 * @author aleksandr-gorovoi
 */
public abstract class ImageFilter {
    
    /**
     * Get int color from RGB components(0-1)
     * @param r
     * @param g
     * @param b
     * @return 
     */    
    protected int countRGB(double r, double g, double b){
        return countRGB((byte)Math.round(r*255),
                (byte)Math.round(g*255),
                (byte)Math.round(b*255));
    }
    
    /**
     * Get int color from RGB components(0-255)
     * @param r
     * @param g
     * @param b
     * @return 
     */
    protected int countRGB(byte r, byte g, byte b){
        return r*256*256+g*256+b;
    }
    
    protected abstract Color[][] filterImageImpl(Color[][] buffer);
    
    protected Color[][] getPixelsFromImage(Image img){
        int width = (int) Math.round(img.getWidth()),
                height = (int) Math.round(img.getHeight());
        Color[][] pixels = new Color[width][height];
        PixelReader pr = img.getPixelReader();
        for (int i = 0; i < width; i++){
            for (int j =0; j < height; j++){
                pixels[i][j] = pr.getColor(i, j);
            }
        }
        return pixels;
    }
    
    protected Image getImageFromPixels(Color[][] pixels){
        if (pixels.length == 0) return null;
        int width = pixels.length,
                height = pixels[0].length;
        BufferedImage buffered = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                Color c = pixels[i][j];
                int color = countRGB(c.getRed(), c.getGreen(), c.getBlue());
                buffered.setRGB(i, j, color);
            }
        }
        return SwingFXUtils.toFXImage(buffered, null);
    }
    
    public Image filterImage(Image img){
        Color[][] pixels = getPixelsFromImage(img);
        Color[][] filtered = this.filterImageImpl(pixels);
        return getImageFromPixels(filtered);
    }
}
