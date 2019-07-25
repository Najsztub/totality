package economy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import production.Companies;

public class Population {
	public List<HH> households;

	// Government
	static Gov gov;

	// Setup
	public static Setup params;

	// Companies
	public static Companies firms;

	/**
	 * Create empty population with Government and Setup.
	 */
	public Population() {
		households = new ArrayList<HH>();
		Population.setParams(new Setup());
		Population.setGov(new Gov(params));
		firms = new Companies(10);
	}

	/**
	 * Create z population. Constructor creates given number of Households. Params
	 * from Setup() and Government from Gov() are also created.
	 * 
	 * @param nHH Number of Households to generate.
	 */
	public Population(int nHH) {
		households = new ArrayList<HH>();
		Population.setParams(new Setup());
		Population.setGov(new Gov(params));
		for (int i = 0; i < nHH; i++) {
			addhh(new HH());
		}
		firms = new Companies(10);
	}

	/**
	 * Merge households
	 * 
	 * @param households Population in which we make the merge
	 * @param target     Target HH in the population
	 * @param source     Source HH in the population
	 */
	public void mergeHhs(int target, int source) {
		if (target <= households.size() && source <= households.size() && target >= 0 && source >= 0
				&& source != target) {
			for (int i = households.get(source).members.size() - 1; i >= 0; i--) {
				households.get(target).members.add(households.get(source).members.get(i));
			}
			households.remove(source);

		} else {
			System.err.println("Cannot merge hhs");
		}
	}

	/**
	 * Add a Household to population.
	 * 
	 * @param hh HH class object to add
	 */
	public void addhh(HH hh) {
		households.add(hh);
	}

	/**
	 * Get mean size of HHs
	 * 
	 * @param hh List of HHs
	 * @return Mean HH size
	 */
	public float meanSize() {
		return (float) nPeople() / households.size();
	}

	/**
	 * Get number of households in the population.
	 * 
	 * @return Number of households
	 */
	public int size() {
		return households.size();
	}

	/**
	 * Get number of people in the population.
	 * 
	 * @return Number of people in households.
	 */
	public int nPeople() {
		return (int) Pers.nPers;
	}

	public void step() {
		// Step companies in time
		firms.step();
		// Possible matches
		List<HH> possibleMatch = new ArrayList<HH>();
		// Step all HHs in time
		for (int i = 0; i < households.size(); i++) {
			households.get(i).step();
			if (households.get(i).members.isEmpty()) {
				households.remove(i);
				i--;
				continue;
			}

		}
		// Split if HH members are ready to move on their own
		for (int i = 0; i < households.size(); i++) {
			// Split members
			Pers[] toSplit = households.get(i).toSplit();
			if (toSplit != null && toSplit.length != 0) {
				for (int id = 0; id < toSplit.length; id++) {
					HH tmphh = new HH(toSplit[id]);
					addhh(tmphh);
					households.get(i).delMember(toSplit[id]);
				}
			}

			// Add possible matches
			if (households.get(i).isSingle()) {
				possibleMatch.add(households.get(i));
			}
		}

		// Gen matches
		matchPeople(possibleMatch);
	}

	public void step(int rep) {
		for (; rep > 0; rep--) {
			step();
		}
	}

	public double[] getAges() {
		double[] ages;
		ages = new double[nPeople()];
		int id = 0;
		for (int i = 0; i < households.size(); i++) {
			int[] hages = households.get(i).getAges();
			for (int j = 0; j < hages.length; j++) {
				ages[id] = hages[j];
				id++;
			}
		}
		return ages;
	}

	private void matchPeople(List<HH> possibleMatch) {
		if (possibleMatch.size() > 0) {
			Random rand = new Random();
			for (int i = 0; i < possibleMatch.size(); i++) {
				int j = i + 1;
				while (j < possibleMatch.size()) {
					boolean notSameSex = possibleMatch.get(i).members.get(0).sex != possibleMatch.get(j).members
							.get(0).sex;
					double ageDiff = Math
							.abs(possibleMatch.get(i).members.get(0).age - possibleMatch.get(j).members.get(0).age);
					double uRelDiff = (possibleMatch.get(i).members.get(0).maUtility
							- possibleMatch.get(j).members.get(0).maUtility)
							/ possibleMatch.get(i).members.get(0).maUtility;
					if (notSameSex && (1.5 / (2 + ageDiff - uRelDiff) > rand.nextDouble())) {
						// Add person to HH
						possibleMatch.get(i).addMember(possibleMatch.get(j).members.get(0));
						// Marry people
						possibleMatch.get(i).marry(possibleMatch.get(i).hoh, possibleMatch.get(i).members.get(1));
						// Merge incomes
						possibleMatch.get(i).dispInc += possibleMatch.get(j).dispInc;

						// Remove persons from HHs
						households.remove(possibleMatch.get(i));
						households.remove(possibleMatch.get(j));
						households.add(possibleMatch.get(i));
						possibleMatch.remove(j);
						break;
					}
					j++;

				}

			}

		}
	}

	// Setup and government getters and setters
	//

	protected static Setup getParams() {
		return params;
	}

	public Setup getParamsOut() {
		return params;
	}

	protected static void setParams(Setup params) {
		Population.params = params;
	}

	public void setParamsOut(Setup params) {
		Population.params = params;
	}

	public void killAge(int age) {
		for (int i = 0; i < households.size(); i++) {
			for (int j = 0; j < households.get(i).members.size(); j++) {
				if (households.get(i).members.get(j).age == age) {
					households.get(i).delMember(households.get(i).members.get(j));
					j--;
					Pers.nPers--;
				}
			}
		}
	}

	public static Gov getGov() {
		return gov;
	}

	public static void setGov(Gov gov) {
		Population.gov = gov;
	}

}
