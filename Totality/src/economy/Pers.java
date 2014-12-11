package economy;

import java.util.Random;

import consumption.Basket;
import consumption.Good;

public class Pers {

	// Age & sex
	public int age;
	public boolean sex;
	public double charLife = 81.0;
	public double pLife;
	public Pers partner;
	public static long nPers = 0;

	// Income & consumption
	public double income = 0;
	public double consumption = 0;
	public double totalUtility = 0;
	//Consumption
	private Basket basket;
	// Pens found
	private double pensFound = 0;

	/**
	 * Create household member
	 * 
	 * @param age
	 *            Age of member
	 * @param sex
	 *            Sex of member 0 male, 1 female
	 */
	public Pers(int age, boolean sex) {
		nPers++;
		this.age = age;
		this.sex = sex;
		if (age > 0)
			pLife = 1.000;
		else
			pLife = 0.99;
		charLife = Population.getParams().getParamByName("charLife");
		basket = new Basket();
		for (int i = 0; i<8; i++){
			basket.addGood(new Good());
		}
		basket.normalizeBetas();
		

	}

	/**
	 * Create a random HH member
	 */
	public Pers() {
		nPers++;
		Random rand = new Random();
		age = rand.nextInt(100);
		sex = rand.nextBoolean();
		pLife = 1.000;
		charLife = Population.getParams().getParamByName("charLife");
		basket = new Basket();
		for (int i = 0; i<8; i++){
			basket.addGood(new Good());
		}
		basket.normalizeBetas();
	}

	/**
	 * Step person into the future. - age
	 */
	public void step() {
		// Produce income
		income = getIncome();

		// Add to consumption
		consumption = consume();

		// Age by 1 and get survival probability
		pLife = survivalProb(age);
		age++;
	}

	/**
	 * Calculate the survival probability at time t. From: Analysis of trends in
	 * human longevity by Byung Mook Weon exp(-((x+1)/a)^(0.2*x-0.001*x^2+0.8)))
	 * 
	 * @param t
	 * @return
	 */
	private double survivalProb(int t) {
		return Math.exp(-Math.pow((((double) t + 1) / charLife),
				ageShape(t, b2i(sex))));
	}

	/**
	 * Age dependent shape function.
	 * 
	 * @param t
	 * @return
	 */
	private double ageShape(double t, int g) {
		if (t > 40)
			return 0.2 * t - 0.001 * Math.pow(t, 2) + 0.8 - 0.05 * g;
		else
			return 0.2 * t - 0.001 * Math.pow(t, 2) + 0.8;
	}

	private int b2i(boolean b) {
		return b ? 1 : 0;
	}

	/**
	 * Production function.
	 * 
	 * @return Value produced
	 */
	private double getIncome() {
		double gross = 100;
		if (age >= 17 && age < Population.getParams().pensRetAge) {
			double net = Population.getGov().taxWork(this, gross);
			pensFound += gross - net;
			return net;
		} else if (age >= Population.getParams().pensRetAge) {
			double retPens = pensFound / Population.getParams().pensLiveAge;
			return Population.getGov().payRetPens(retPens);
		} else
			return 0;
	}

	private double consume() {
		double cons = 50;
		totalUtility += basket.getUtilityBudget(cons);
		return cons;
		

	}

	public boolean hasPartner() {
		return partner == null ? false : true;
	}
	
	public Basket getBasket() {
		return basket;
	}

	public void setBasket(Basket basket) {
		this.basket = basket;
	}

}
