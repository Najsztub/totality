package economy;

public class Gov {
    private double balance;

    Gov(Setup params) {
	setBalance(0);
    }

    public double payOut(double am) {
	setBalance(getBalance() - am);
	return am;
    }

    public double payIn(double am) {
	setBalance(getBalance() + am);
	return am;
    }

    public double taxWork(Pers pers, double gross) {
	double utc = Population.getParams().taxUtc;
	double tax = 0;
	if (pers.hasPartner()) {
	    utc += Population.getParams().jTaxCredit;
	}
	if (gross > 0) {
	    tax = (gross - utc) * Population.getParams().taxRate;
	}
	if (tax > 0) {
	    this.payIn(tax);
	    return gross - tax;

	} else
	    return gross;
    }

    public double familyAllowance(HH hh, double am) {

	int nChild = 0;
	for (Pers member : hh.members) {
	    if (member.age < 18) {
		nChild++;
	    }
	}
	if (nChild > 2)
	    return payOut(nChild * am);
	else
	    return 0;
    }

    public double payRetPens(double am) {
	return payOut(am);
    }

    public double getBalance() {
	return balance;
    }

    public void setBalance(double balance) {
	this.balance = balance;
    }

}
