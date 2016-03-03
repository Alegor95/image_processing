/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gorovojimage.math;

/**
 *
 * @author aleksandr-gorovoi
 */
public class GaussianCounter {
    private double k;
    private double stand;
    
    /**
     * Пересчитать коэффиценты с заданной дисперсией
     * @param dispersion 
     */
    public final void countCoefficents(double dispersion){
        k = 1/(dispersion*Math.sqrt(2*Math.PI));
        double power = -1/(2*dispersion*dispersion);
        stand = Math.pow(Math.E, power);
    }
    
    /**
     * Получить значение гауссиана в точке x
     * @param x
     * @return 
     */
    public double getGaussianValue(double x){
        return k*getNormalGaussianValue(x);
    }
    
    /**
     * Получить нормализованное (0; 1) значение гауссиана в точке x
     * @param x
     * @return 
     */
    public double getNormalGaussianValue(double x){
        return Math.pow(stand, x*x);
    }
    
    public GaussianCounter(double dispersion){
        countCoefficents(dispersion);
    }
}
