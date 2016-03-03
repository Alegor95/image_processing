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
public class LinearFilter extends ImageFilter{
    private final int N = 2;
    private final double[][] matrix = new double[][]{
        {0.005, 0.005, 0.005, 0.005, 0.005},
        {0.005, 0.080, 0.080, 0.080, 0.005},
        {0.005, 0.080, 0.270, 0.080, 0.005},
        {0.005, 0.080, 0.080, 0.080, 0.005},
        {0.005, 0.005, 0.005, 0.005, 0.005}
    };

    @Override
    protected Color[][] filterImageImpl(Color[][] buffer) {        
        if (buffer.length == 0) return new Color[0][0];
        int width = buffer.length,
                height = buffer[0].length;
        Color[][] result = buffer;
        for (int i = N; i < width - N; i++){
            for (int j = N; j< height - N; j++){
                double r = 0, g = 0, b = 0;
                //Make linear transform
                for (int w_i = -N; w_i <= N; w_i++){
                    for (int w_j = -N; w_j <= N; w_j++){
                        int d_x = i + w_i, d_y = j + w_j;
                        Color current = buffer[d_x][d_y];
                        r+=matrix[w_i+N][w_j+N]*current.getRed();
                        g+=matrix[w_i+N][w_j+N]*current.getGreen();
                        b+=matrix[w_i+N][w_j+N]*current.getBlue();
                    }
                }
                result[i][j] = Color.color(r, g, b);
            }
        }
        return result;
    }
    
    
        
}
