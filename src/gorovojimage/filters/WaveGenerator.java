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
public class WaveGenerator extends ImageFilter {
    private static int IMAGE_WIDTH = 500,
            IMAGE_HEIGHT = 500;

    private final double expected = 0.5;
    
    private final double dispersion = 0.2;
    
    private final double u;
    private final double v;
    
    @Override
    protected Color[][] filterImageImpl(Color[][] buffer) {
        Color[][] result = new Color[IMAGE_WIDTH][IMAGE_HEIGHT];
        for (int i = 0; i < IMAGE_WIDTH; i++){
            for (int j = 0; j < IMAGE_HEIGHT; j++){
                double counted_u = i * u;
                double counted_v = j * v;
                double br = expected + dispersion*Math.cos(counted_u+counted_v);
                result[i][j] = Color.color(br, br, br);
            }
        }
        return result;
    }
    
    public WaveGenerator(double u, double v){
        this.u = u;
        this.v = v;
    }
    
}
