package consumption;

import java.util.Random;

public class Good {
	/*
	 * Stone-Geary linear consumption
	 * http://en.wikipedia.org/wiki/Stone%E2%80%93Geary_utility_function
	 */
	double price;
	double depreciation;
	double q0;
	double beta;
	
	Basket b0;
	
	Good(double price, double depreciation, double q0, double beta){
		this.price = price;
		this.depreciation = depreciation;
		this.q0 = q0;
		this.beta = beta;
	}
	
	Good(){
		Random r = new Random();
		this.price = r.nextDouble();
		this.depreciation = r.nextDouble();
		this.q0 = r.nextDouble();
		this.beta = r.nextDouble();
	}
	
	public void setPrice(double price){
		this.price = price;
		b0.updateMinExp();
	}
	public void changeGamma(double q0){
		this.q0 = q0;
		b0.updateMinExp();
	}
}
