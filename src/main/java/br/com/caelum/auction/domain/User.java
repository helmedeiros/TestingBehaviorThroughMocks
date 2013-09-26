package br.com.caelum.auction.domain;

/**
 * Represents an auction bidders.
 */
public class User {

	private int id;
	private String name;
	
	public User(String name) {  this(0, name); }

	public User(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() { return id; }

	public String getName() { return name; }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		User other = (User) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	
}
