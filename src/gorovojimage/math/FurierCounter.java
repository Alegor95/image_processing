/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gorovojimage.math;

import static java.lang.Double.NaN;

/**
 *
 * @author aleksandr-gorovoi
 */
public class FurierCounter {
 
    private class Complex{
        private double x = 0;
        private double y = 0;
        
        public double getX(){
            return x;
        }
        
        public void setX(double new_x){
            x = new_x;
        }
        
        public double getY(){
            return y;
        }
        
        public void setY(double new_y){
            y = new_y;
        }
        //Operations
        /**
         * Summary of two complex numbers
         * @param b
         * @return 
         */
        public Complex plus(Complex b){
            x = x + b.x;
            y = y + b.y;
            return this;
        }
        /**
         * Difference of two complex numbers
         * @param b
         * @return 
         */
        public Complex minus(Complex b){
            x = x - b.x;
            y = y - b.y;
            return this;
        }
        /**
         * Multiplication of two complex numbers
         * @param b
         * @return 
         */
        public Complex multiply(Complex b){
            x = x*b.x - y*b.y;
            y = x*b.y + y*b.x;
            return this;
        }
        /**
         * Divide complex number to real
         * @param k
         * @return 
         */
        public Complex divide(double k){
            x = x/k;
            y = y/k;
            return this;
        }
        /**
         * Copy of this number
         * @return 
         */        
        public Complex copy(){
            return new Complex(x,y);
        }
        /**
         * Set values of this complex number
         * @param c 
         */        
        public void setComplex(Complex c){
            this.x = c.x;
            this.y = c.y;
        }
        /**
         * Normalize this number
         */
        public void normalize(){
            double r = Math.sqrt(x*x+y*y);
            if (r == 0 || r == Double.NaN){
                x = 0;
                y = 0;
            }
            x = x/r;
            y = y/r;
        }
        /**
         * Convert to string
         * @return 
         */
        @Override
        public String toString(){
            return "("+x+","+y+")";
        }
        //Constructors
        public Complex(double real){
            x = real;
            y = 0;
        }
        
        public Complex(double real, double complex){
            x = real;
            y = complex;
        }
    }
    
    private void fft(Complex[] a, boolean invert){
        int n = a.length;
	if (n == 1)  return;
 
	Complex[] a0 = new Complex[n/2],  a1 = new Complex[n/2];
	for (int i=0, j=0; i<n; i+=2, ++j) {
                if (j >= n/2) break;
                if (i < a.length){
                    a0[j] = a[i];
                }
                if (i + 1 < a.length){
                    a1[j] = a[i+1];
                }
	}
	fft (a0, invert);
	fft (a1, invert);
 
	double ang = 2*Math.PI/n * (invert ? -1 : 1);
        Complex w = new Complex(1), 
                wn = new Complex(Math.cos(ang), Math.sin(ang));
	for (int i=0; i<n/2; ++i) {
		a[i].setComplex(a0[i].copy().plus(w.copy().multiply(a1[i])));
		a[i+n/2].setComplex(a0[i].copy().minus(w.copy().multiply(a1[i])));
		if (invert){
			a[i].divide(2);
                        a[i+n/2].divide(2);
                }
		w.multiply(wn);
	}
    }
    
    
    /**
     * Count furier-transform for two-dimensional array
     * @param coefficents
     * @return 
     */
    private double[][] countFFT(double[][] coefficents, boolean invert){
        if (coefficents.length == 0) return new double[0][0];
        int width = coefficents.length,
                height = coefficents[0].length;
        //Count transform
        //Count columns
        Complex[][] coeff = new Complex[width][height];
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                coeff[i][j] = new Complex(coefficents[i][j]);
            }
            fft(coeff[i], invert);
        }
        //Normalize
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                coeff[i][j].normalize();
            }
        }
        //Count raws
        for (int j = 0; j < height; j++){
            Complex[] column = new Complex[width];
            for (int i = 0; i < width; i++){
                column[i]= coeff[i][j];
            }
            fft(column, invert);
        }
        //Normalize
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                coeff[i][j].normalize();
            }
        }
        //Count energy
        double[][] result = new double[width][height];
        double maxEnergy = 0;
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                Complex c = coeff[i][j];
                result[i][j] = (c.getX()*c.getX() + c.getY()*c.getY())/2;
                if (result[i][j] > maxEnergy){
                    maxEnergy = result[i][j];
                }
            }
        }
        //Normalize again
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                result[i][j] = result[i][j]/maxEnergy;
            }
        }        
        //Normalize
        return result;
    }
    
    /**
     * Count normal furier normal
     * @param coefficents
     * @return 
     */
    public double[][] countNormalFurier(double[][] coefficents){
        return this.countFFT(coefficents, false);
    }
    
    /**
     * Count reverse furier-transform for two-dimensional array
     * @param coefficents
     * @return 
     */
    public double[][] countReverseFurier(double[][] coefficents){        
        return this.countFFT(coefficents, true);
    }            
}
