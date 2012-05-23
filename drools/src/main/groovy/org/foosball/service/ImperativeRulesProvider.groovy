package org.foosball.service

import java.util.List;

import org.foosball.domain.Player;
import org.foosball.domain.Team;
import org.foosball.service.RulesProvider;

class ImperativeRulesProvider implements RulesProvider {

	@Override
	public void init() {
		println "Done init"
	}

	@Override
	public List<Team> executeRules(List<Player> allPlayers) {
		assert (allPlayers.size() % 2 == 0) : "Need even number of players[$allPlayers.size()]"
		allPlayers.sort()
		List allTeams =[]
		while(allPlayers) {
			Team team = new Team(playerA:allPlayers[0], playerB:allPlayers[-1])
			allTeams += team
			allPlayers.removeAll(team.players())
		}
		allTeams
	}
}
