package extras;

public class Extras {
	public static double invLogit(double r){
		return 1.0/(1+Math.exp(-1*r));
	}
	
	public static double logit(double r){
		return Math.log(r/(1-r));
	}

}
