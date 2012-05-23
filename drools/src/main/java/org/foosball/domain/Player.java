package org.foosball.domain;

import com.google.common.base.Objects;

public class Player implements Comparable<Player> {
	private String name;
	private Integer handicap;

	public Player() {
	}

	public Player(String name, Integer handicap) {
		super();
		this.name = name;
		this.handicap = handicap;
	}

	public int compareTo(Player other) {
		return handicap - other.handicap;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(name, handicap);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Player) {
			Player other = (Player) obj;
			return Objects.equal(name, other.name)
					&& Objects.equal(handicap, other.handicap);
		}
		return false;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("name", name)
				.add("handicap", handicap).toString();
	}

	public String getName() {
		return name;
	}

	public Integer getHandicap() {
		return handicap;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setHandicap(Integer handicap) {
		this.handicap = handicap;
	}

}
