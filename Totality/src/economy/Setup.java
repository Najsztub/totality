package economy;

import java.util.ArrayList;

public class Setup {

    protected double taxUtc;
    protected double taxRate;
    protected double birthRate;
    protected double pensLiveAge;
    protected double pensRetAge;
    protected double faAmount;
    protected double jTaxCredit;

    // Arrays with parameters, descriptions and names
    private ArrayList<Double> paramGlobal;
    private ArrayList<String> description;
    private ArrayList<String> names;

    private int nPar = 0;

    private static final int NPAR = 8;

    Setup() {
	paramGlobal = new ArrayList<Double>(NPAR);
	description = new ArrayList<String>(NPAR);
	names = new ArrayList<String>(NPAR);

	// Add parameters
	addParam("taxRate", "Tax rate", 0.13);
	addParam("taxUtc", "UTC value", 10);
	addParam("jTaxCredit", "Joint tax credit", 5);
	addParam("pensRetAge", "Reirement age", 65);
	addParam("pensLiveAge", "Exp. life after ret", 10);
	addParam("charLife", "Expected life", 81);
	addParam("birthRate", "Birth rate", 4000.0);
	addParam("faAmount", "FA Amount", 40);

	trigger();
    }

    /**
     * Set local variables.
     */
    private void trigger() {

	taxUtc = getParamByName("taxUtc");
	taxRate = getParamByName("taxRate");
	birthRate = getParamByName("birthRate");
	pensLiveAge = getParamByName("pensLiveAge");
	pensRetAge = getParamByName("pensRetAge");
	faAmount = getParamByName("faAmount");
	jTaxCredit = getParamByName("jTaxCredit");
    }

    /**
     * Set parameter by id in the parameters array.
     *
     * @param val
     *            New value
     * @param id
     *            ID of the set parameter
     */
    public void setParamByID(double val, int id) {
	paramGlobal.set(id, val);
	trigger();
    }

    /**
     * Add parameter to parameter array.
     *
     * @param name
     *            Short name of the parameter.
     * @param desc
     *            Short description of added parameter.
     * @param val
     *            Value of the parameter (double)
     */
    private void addParam(String name, String desc, double val) {
	names.add(name);
	description.add(desc);
	paramGlobal.add(val);
	nPar++;

    }

    /**
     * Get parameter value by name.
     *
     * @param name
     *            Short name of the parameter.
     * @return Parameter value (double).
     */
    public double getParamByName(String name) {
	int nId = names.indexOf(name);
	return paramGlobal.get(nId);
    }

    public double getParamByID(int nId) {
	return paramGlobal.get(nId);
    }

    public String getNameByID(int nId) {
	return names.get(nId);
    }

    public String getDescByID(int nId) {
	return description.get(nId);
    }

    public int parLength() {
	return nPar;
    }

}
