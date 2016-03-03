/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gorovojimage.filters;

import gorovojimage.math.FurierCounter;
import javafx.scene.paint.Color;

/**
 *
 * @author aleksandr-gorovoi
 */
public class FurierFilter extends ImageFilter {
    
    private final FurierCounter counter = new FurierCounter();

    @Override
    protected Color[][] filterImageImpl(Color[][] buffer) {
        if (buffer.length == 0) return new Color[0][0];
        int width = buffer.length,
                height = buffer[0].length;
        double[][] r = new double[width][height],
                g = new double[width][height],
                b = new double[width][height];
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                Color c = buffer[i][j];
                r[i][j] = c.getRed();
                g[i][j] = c.getGreen();
                b[i][j] = c.getBlue();
            }
        }
        //Count furier
        r = counter.countNormalFurier(r);
        //g = counter.countNormalFurier(g);
        //b = counter.countNormalFurier(b);
        //Count back
        r = counter.countReverseFurier(r);
        //g = counter.countReverseFurier(g);
        //b = counter.countReverseFurier(b);
        //Get it back
        Color[][] result = new Color[width][height];
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                Color c = Color.color(r[i][j], /*g[i][j]*/0, /*b[i][j]*/0);
                result[i][j] = c;
            }
        }
        return result;
    }
    
}
