package economy;

import java.util.Random;

import consumption.Basket;
import consumption.Good;
import production.Company;

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
    public double savings = 0;
    public double totalUtility = 0;
    public double maUtility = 0;
    public boolean employed = false;
    private static final double[] initPrice = new double[] { 0.1, 0.5 };
    // Consumption
    private Basket basket;
    // Pens found
    private double pensFound = 0;
    private Company comp;
    public double wageIncome = 0;

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
	if (age > 0) {
	    pLife = 1.000;
	} else {
	    pLife = 0.99;
	}
	charLife = Population.getParams().getParamByName("charLife");
	basket = new Basket();
	for (int i = 0; i < 2; i++) {
	    basket.addGood(new Good());
	    basket.getGood(i).setPrice(initPrice[i]);
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
	for (int i = 0; i < 8; i++) {
	    basket.addGood(new Good());
	}
	basket.normalizeBetas();
    }

    /**
     * Step person into the future. - age
     */
    public void step() {
	// Check employment
	employed = isEmployed();
	// Produce income
	income = getIncome(employed);

	// Add to consumption
	// TODO Model consumption
	// TODO Model saving, so people do not consume everything
	double cons = 0;
	if (income>basket.getMinExp()){
	    cons = basket.getMinExp() + 0.7 * (income - basket.getMinExp());
	}
	else{
	    cons = basket.getMinExp();
	}
	
	consumption = consume(cons);

	// Age by 1 and get survival probability
	pLife = survivalProb(age);
	age++;
    }

    private boolean isEmployed() {
	if (age >= 17 && age < Population.getParams().pensRetAge) {
	    if (comp != null)
		return true;
	    else {
		Random rand = new Random();
		int cid = rand.nextInt(Population.firms.size);
		Population.firms.firms.get(cid).addEmployee(this);
		return true;
	    }
	} else
	    return false;
    }

    /**
     * Calculate the survival probability at time t. From: Analysis of trends in
     * human longevity by Byung Mook Weon exp(-((x+1)/a)^(0.2*x-0.001*x^2+0.8)))
     * 
     * @param t
     *            Time at which the probability is calculated.
     * @return Probability of survival.
     */
    private double survivalProb(int t) {
	return Math.exp(-Math.pow((((double) t + 1) / charLife), ageShape(t, b2i(sex))));
    }

    /**
     * Age dependent shape function.
     * 
     * @param t
     *            Age in years
     * @return Age shape
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
     * @param isEmployed
     * 
     * @return Value produced
     */
    private double getIncome(boolean isEmployed) {

	if (isEmployed && age >= 17 && age < Population.getParams().pensRetAge) {
	    double gross = wageIncome;
	    double net = Population.getGov().taxWork(this, gross);
	    pensFound += gross - net;
	    return net;
	} else if (age >= Population.getParams().pensRetAge) {
	    double retPens = pensFound / Population.getParams().pensLiveAge;
	    return Population.getGov().payRetPens(retPens);
	} else
	    return 0;
    }

    private double consume(double cons) {
	// double cons = 50;
	// this.income -= cons;
	double utlility = basket.getUtilityBudget(cons);
	savings = income - cons;
	totalUtility += utlility;
	maUtility = (maUtility * 4 + utlility) / 5.0;
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

    @Override
    public String toString() {
	return "Pers [age=" + age + ", sex=" + sex + ", charLife=" + charLife + ", pLife=" + pLife + ", partner="
		+ partner + ", income=" + income + ", consumption=" + consumption + ", totalUtility=" + totalUtility
		+ ", maUtility=" + maUtility + ", employed=" + employed + ", basket=" + basket + ", pensFound="
		+ pensFound + ", comp=" + comp + "]";
    }

}
