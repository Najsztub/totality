package production;

import java.util.ArrayList;

import economy.Pers;
import economy.Population;

public class Company {
	private double stock = 0;
	private double capital = 0;

	public double getCapital() {
		return capital;
	}

	public void setCapital(double capital) {
		this.capital = capital;
	}

	private double savingRate = 0.05;
	private double amort = 0.03;
	private double KLratio = 0.3;
	private double tech = 1;

	private ArrayList<Pers> ees;

	public Company(double KL, double tech) {
		ees = new ArrayList<Pers>();
		this.KLratio = KL;
		this.tech = tech;
	}

	public void addEmployee(Pers ee) {
		ee.employed = true;
		ees.add(ee);
	}

	public void sackEmployee(int id) {
		ees.get(id).employed = false;
		ees.remove(id);
	}

	public double getAmort() {
		return amort;
	}

	public void setAmort(double amort) {
		if (amort < 0 || amort > 1.0) {
			System.out.println("Cannot set amortization to greater than 1 or less than 0!");
		} else {
			this.amort = amort;
		}
	}

	static double gdp = 0;

	public void step() {
		stock -= stock * amort;
		gdp -= stock * amort;
		double product = produce();
		double totalCosts = payCosts(0.7 * product);
		double profit = product - totalCosts;
		capital += profit;
		gdp += product;
		stock += product;
		capital -= capital * amort;
	}

	private double payCosts(double product) {
		double totalCosts = 0;
		totalCosts += payWages(product);
		return totalCosts;

	}

	private double produce() {
		double labour = ees.size();
		double product = 0;
		product = tech * Math.pow(capital, KLratio) * Math.pow(labour, 1 - KLratio);
		return product;
	}

	private double payWages(double labourShare) {
		double paidWages = 0;
		double wage = labourShare / ees.size();
		for (int i = 0; i < ees.size(); i++) {
			paidWages += wage;
			ees.get(i).wageIncome += wage;
			// stock -= wage;
		}
		return paidWages;

	}

	public double getStock() {
		return stock;
	}

	public void addStock(double stock) {
		this.stock += stock;
	}

	public double sellStock(double stock) {

		if (stock > this.stock) {
			System.out.println("Cannot sell more than there is in stock!");
			return 0;
		} else {
			this.stock -= stock;
			return stock;
		}
	}

	public double getSavingRate() {
		return savingRate;
	}

	public void setSavingRate(double savingRate) {
		this.savingRate = savingRate;
	}

	@Override
	public String toString() {
		return "Company [stock=" + stock + ", capital=" + capital + ", savingRate=" + savingRate + ", amort=" + amort
				+ ", KLratio=" + KLratio + ", tech=" + tech + "]";
	}

	public static void main(String[] args) {
		Company comp = new Company(0.3, 1);

		Population popul = new Population(1);

		Pers ee = popul.households.get(0).members.get(0);
		comp.addEmployee(ee);

		comp.setCapital(1);

		System.out.println(comp);
		System.out.println(ee);

		for (int i = 0; i < 5; i++) {
			comp.step();
			popul.step();
		}
		System.out.println(comp);
		System.out.println(ee);
	}
}
