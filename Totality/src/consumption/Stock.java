package consumption;

public class Stock extends Good {
	public double amount = 0;
	
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public void subAmount(double amount) {
		this.amount -= amount;
	}
	
	public void addAmount(double amount){
		this.amount = amount;
	}
	
	public void setGood(Good g0){
		this.price = g0.price;
		this.depreciation = g0.depreciation;
		this.q0 = g0.q0;
		this.beta = g0.beta;
	}

	public Stock(double amount){
		super();
		this.amount = amount;
	}
	
	public Stock(Good g0){
		this.price = g0.price;
		this.depreciation = g0.depreciation;
		this.q0 = g0.q0;
		this.beta = g0.beta;
	}
	
	public Stock(Good g0, double amount){
		this.price = g0.price;
		this.depreciation = g0.depreciation;
		this.q0 = g0.q0;
		this.beta = g0.beta;
		this.amount = amount;
	}
	
	public Stock(double price, double depreciation, double q0, double beta){
		super(price, depreciation, q0, beta);
	}
	
	public void step(){
		this.amount *= (1-this.depreciation);
	}

}
