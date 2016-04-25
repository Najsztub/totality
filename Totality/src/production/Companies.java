package production;

import java.util.ArrayList;

public class Companies {

    public ArrayList<Company> firms;
    public int size = 0;

    public Companies(int n) {
	firms = new ArrayList<Company>();
	for (int i = 0; i < n; i++) {

	    Company addComp = new Company(0.3, 1);
	    addComp.setCapital(1);
	    firms.add(addComp);
	    size++;

	}
    }

    public void step() {
	for (int i = 0; i < firms.size(); i++) {
	    firms.get(i).step();
	}
    }

}
