package org.foosball.domain;

import java.util.List;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

public class Team implements Comparable<Team> {
	private Player playerA;
	private Player playerB;

	public Team() {
	}

	public Team(Player playerA, Player playerB) {
		super();
		this.playerA = playerA;
		this.playerB = playerB;
	}

	public Integer totalHandicap() {
		return playerA.getHandicap() + playerB.getHandicap();
	}

	public List<Player> players() {
		return Lists.newArrayList(playerA, playerB);
	}

	public Player getPlayerA() {
		return playerA;
	}

	public void setPlayerA(Player playerA) {
		this.playerA = playerA;
	}

	public Player getPlayerB() {
		return playerB;
	}

	public void setPlayerB(Player playerB) {
		this.playerB = playerB;
	}

	public int compareTo(Team other) {
		return totalHandicap() - other.totalHandicap();
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("playerA", playerA)
				.add("playerB", playerB).toString();
	}

}
