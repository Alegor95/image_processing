/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gorovojimage.filters;

import javafx.scene.paint.Color;

/**
 *
 * @author aleksandr-gorovoi
 */
public class GreyFilter extends ImageFilter {

    @Override
    protected Color[][] filterImageImpl(Color[][] buffer) {
        if (buffer.length == 0){
            return new Color[0][0];
        }
        int width = buffer.length,
                height = buffer[0].length;
        Color[][] result = new Color[width][height];
        //Processing
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                Color c = buffer[i][j];
                double brightness = (c.getRed()+c.getBlue()+c.getGreen())/3;
                result[i][j] = Color.color(brightness, brightness, brightness);
            }
        }
        //
        return result;
    }
}
