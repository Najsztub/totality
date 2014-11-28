package consumption;

import java.util.Locale;

public class Basket {

	private Good[] basket;
	private double minExp;
	
	Basket(){
		basket = new Good[0];
		minExp = 0;
	}
	
	public void addGood(Good g){
		int s0 = basket.length;
		Good[] tmpBasket = new Good[s0+1];
		for(int i = 0; i<s0; i++ ){
			tmpBasket[i]=basket[i];
		}
		tmpBasket[s0] = g;
		basket = tmpBasket;
		
		g.b0 = this;
		
		minExp += g.price*g.q0;
	}
	
	public double[] getQuant(double budget){
		double[] q = new double[basket.length];
		//Gamma * p
		double[] gp = new double[basket.length];
		for (int i = 0; i<gp.length; i++){
			gp[i]=basket[i].q0*basket[i].price;
		}
		for (int i = 0; i<gp.length; i++){
			q[i] = basket[i].q0 + basket[i].beta/basket[i].price*(budget - minExp);
		}
		return q;
	}
	
	public double getUtility(double[] quants){
		double U=1;
		for (int i =0; i<quants.length; i++){
			U *= Math.pow(quants[i] - basket[i].q0, basket[i].beta);
		}
		return U;
	}
	
	public void normalizeBetas(){
		double sBeta = 0;
		for (int i =0; i<basket.length; i++){
			sBeta += basket[i].beta;
		}
		for (int i =0; i<basket.length; i++){
			basket[i].beta /= sBeta ;
		}		
	}
	
	public void printBasket(){
		int len = basket.length;
		String title = "\t";
		String price = "Price:\t";
		String q0 = "Gamma:\t";
		String beta = "Beta:\t";
		Locale l = new Locale("en_US");		
		for(int i = 0; i<len; i++){
			title+="Item "+Integer.toString(i+1)+"\t";
			price+=String.format(l, "%4.3f" , basket[i].price) + "\t";
			q0+=String.format(l, "%4.3f" , basket[i].q0) + "\t";
			beta+=String.format(l, "%4.3f" , basket[i].beta) + "\t";
		}
		
		System.out.println(title);
		System.out.println(price);
		System.out.println(q0);
		System.out.println(beta);
		System.out.println("Min Exp.: "+String.format(l, "%4.3f" , minExp));
	}
	
	protected void updateMinExp(){
		minExp = 0;
		for (int i = 0; i<basket.length; i++)
			minExp += basket[i].q0*basket[i].price;		
	}
	
	public Good[] getBasket() {
		return basket;
	}
	public Good getGood(int id){
		return basket[id];
	}
	
}
