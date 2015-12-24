package consumption;

import java.util.Locale;

public class TestConsumption {
	
	private static String printArray(double[] ar){
		Locale l = new Locale("en_US");
		String ret="[";
		for(int i = 0; i<ar.length; i++){
			ret+=String.format(l, "%4.3f" , ar[i]);
			if (i<ar.length-1) ret +=", ";
		}
		ret+="]";
		return ret;
	}

	public static void main(String[] args) {
		Basket basket = new Basket();
		for (int i = 0; i<8; i++){
			basket.addGood(new Good());
		}
		basket.normalizeBetas();
		
		
		// Basket 0ne
		basket.printBasket();
		for(double budget: new double[]{0, 1, 2, 4, 8}){
			double[] quants = basket.getQuant(budget);		
			System.out.println("Quants for budget: "+budget+": "
					+printArray(quants));
			System.out.println("Utility: "+basket.getUtility(quants));
		}
		
		//Change Price
		basket.getGood(0).setPrice(1.5);
		basket.printBasket();
		for(double budget: new double[]{0, 1, 2, 4, 8}){
			double[] quants = basket.getQuant(budget);		
			System.out.println("Quants for budget: "+budget+": "
					+printArray(quants));
			System.out.println("Utility: "+basket.getUtility(quants));
		}
		
		// Time system utility
		System.out.println("Test Stone Geary Utility function for time");
		Basket timeBasket = new Basket();
		for (int i = 0; i<3; i++){
			timeBasket.addGood(new Good());
			timeBasket.getGood(i).setPrice(1);
		}
		
		// Set first item as sleep
		timeBasket.getGood(0).setName("Slep");
		timeBasket.getGood(0).changeGamma(5);
		
		timeBasket.normalizeBetas();
		
		timeBasket.printBasket();
		double[] quants = timeBasket.getQuant(24);		
		System.out.println("Quants for budget: "+24+": "
				+printArray(quants));
		System.out.println("Utility: "+timeBasket.getUtility(quants));
		
	}

}
