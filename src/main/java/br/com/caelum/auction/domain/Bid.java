package br.com.caelum.auction.domain;

/**
 * Represents an offer of a price, esp. at an auction.
 */
public class Bid {

	private User user;
	private double amount;
	private int id;
	
	public Bid(User user, double amount) {
		this.user = user;
		this.amount = amount;
	}

	public User getUser() {  return user; }

	public double getAmount() { return amount; }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bid other = (Bid) obj;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (Double.doubleToLongBits(amount) != Double
				.doubleToLongBits(other.amount))
			return false;
		return true;
	}

}
