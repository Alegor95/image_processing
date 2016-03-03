/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gorovojimage.filters;

import gorovojimage.math.GaussianCounter;
import javafx.scene.paint.Color;

/**
 *
 * @author aleksandr-gorovoi
 */
public class NoiseFilter extends ImageFilter {
    
    private final GaussianCounter gauss = new GaussianCounter(1);
    
    private double maxNoiseValue = 1;

    private double between(double value, double b, double u){
        if (value>u) return u;
        if (value<b) return b;
        return value;
    }
    
    @Override
    protected Color[][] filterImageImpl(Color[][] buffer) {
        if (buffer.length == 0) return new Color[0][0];
        int width = buffer.length,
                height = buffer[0].length;
        Color[][] result = new Color[width][height];
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                Color old = buffer[i][j];
                double brightnessChange = gauss
                        .getGaussianValue(Math.random())*maxNoiseValue - maxNoiseValue/2;
                //Check 
                double r = between(old.getRed() - brightnessChange, 0, 1),
                        g = between(old.getGreen() - brightnessChange, 0, 1),
                        b = between(old.getBlue() - brightnessChange, 0, 1);
                Color newColor = Color.color(r, g, b);
                result[i][j] = newColor;
            }
        }
        return result;
    }
    
}
