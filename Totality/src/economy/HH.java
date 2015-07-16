package economy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import extras.Extras;

public class HH {
	public List<Pers> members = new ArrayList<Pers>();
	double dispInc = 0;
	Pers hoh;

	/**
	 * Create a random household
	 */
	public HH(Pers person) {
		addMember(person);
		hoh = members.get(0);
	}

	public HH() {
		Random rand = new Random();
		int hhSize = 1 + rand.nextInt(15);
		for (int i = hhSize; i > 0; i--) {
			members.add(new Pers());
		}
		hoh = getHoh();
	}

	public HH(Pers... persons) {
		for (Pers pers : persons) {
			addMember(pers);
		}
		hoh = getHoh();
	}

	/**
	 * Step household into the future - reproduce - age
	 * 
	 */
	public void step() {
		for (int i = 0; i < members.size(); i++) {
			Random rand = new Random();

			if (members.get(i).pLife >= rand.nextDouble()) {
				members.get(i).step();
				// Add income to HH income
				dispInc += members.get(i).income;
			} else {
				Pers rm = members.get(i);
				if (rm.partner != null)
					divorce(rm);
				members.remove(i);
				i--;
				Pers.nPers--;
				if (rm == hoh && members.size() > 0) {
					hoh = getHoh();
				}

			}

			// Make income and consume
		}
		// Reproduce, age etc
		reproduce();
		// Family Allowance
		dispInc += Population.getGov().familyAllowance(this,
				Population.getParams().faAmount);

		for (int i = 0; i < members.size(); i++) {
			dispInc -= members.get(i).consumption;
		}
	}

	/**
	 * Return ages of people
	 * 
	 * @return
	 */
	public int[] getAges() {
		int[] ages = new int[members.size()];
		for (int i = 0; i < members.size(); i++) {
			ages[i] = members.get(i).age;
		}
		return ages;
	}

	/**
	 * Return gender of members
	 * 
	 * @return
	 */
	public boolean[] getGender() {
		boolean[] sex = new boolean[members.size()];
		for (int i = 0; i < members.size(); i++) {
			sex[i] = members.get(i).sex;
		}
		return sex;
	}

	/**
	 * Add new members
	 * 
	 * @param age
	 * @param sex
	 */
	public void addMember(int age, boolean sex) {
		members.add(new Pers(age, sex));
	}

	public void addMember(Pers pers) {
		members.add(pers);
	}

	public void delMember(Pers pers) {
		members.remove(pers);
		if (pers == hoh) {
			hoh.partner = null;
			if (members.size() > 0)
				hoh = getHoh();
		}
	}

	public void delMember(Pers... persons) {
		for (Pers pers : persons) {
			delMember(pers);
		}
	}

	public void haveChild() {
		members.add(new Pers(0, new Random().nextBoolean()));
	}

	void reproduce() {
		if (hoh != null && hoh.partner != null) {
			int inReproductionAge = 0;
			double crit = Population.getParams().birthRate / Pers.nPers;
			for (Pers h : new Pers[] { hoh, hoh.partner }) {
				if (h.sex && h.age >= 17 && h.age < 50)
					inReproductionAge++;
				if (!h.sex && h.age >= 20 && h.age < 40)
					inReproductionAge++;

			}
			if (inReproductionAge == 2
					&& (Extras.invLogit(new Random().nextDouble()) <= crit)
					&& dispInc > 500 && members.size() < 10)
				haveChild();
		}

	}

	/**
	 * Determine whether is a single household and ready to marry
	 * 
	 * @return
	 */
	public boolean isSingle() {
		if (members.size() == 1) {
			Pers sing = members.get(0);
			if (sing.age > 21) {
				if ((sing.sex) && sing.age < 50)
					return true;
				else if (!(sing.sex) && sing.age < 40)
					return true;
				else
					return false;
			} else
				return false;
		} else
			return false;
	}

	public Pers[] toSplit() {
		Random rand = new Random();
		List<Integer> out = new ArrayList<Integer>();

		for (int i = 0; i < members.size(); i++) {
			int age = members.get(i).age;
			if (i != members.indexOf(hoh) && !members.get(i).hasPartner()
					&& age > 15
					&& (Extras.invLogit(0.5 * (age - 23))) >= rand.nextDouble()) {
				out.add(i);
			}
		}

		Pers[] res = new Pers[out.size()];
		for (int i = 0; i < out.size(); i++) {
			res[i] = members.get(out.get(i));
		}
		return res;

	}

	private Pers getHoh() {
		int age = 0;
		int res = 0;
		for (int i = 0; i < members.size(); i++) {
			if (members.get(i).age > age) {
				age = members.get(i).age;
				res = i;
			}
		}
		return members.get(res);
	}

	public void marry(Pers h1, Pers h2) {
		if (h1.sex != h2.sex) {
			h1.partner = h2;
			h2.partner = h1;
		}
	}

	public void divorce(Pers h1) {
		h1.partner.partner = null;
		h1.partner = null;
	}

	public double getIncome() {
		return dispInc;
	}
	/*
	 * private getPartners(){
	 * 
	 * }
	 */
}
