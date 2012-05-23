package org.foosball.service;

import java.util.List;

import org.foosball.domain.Player;
import org.foosball.domain.Team;

public interface RulesProvider {

	void init();

	List<Team> executeRules(List<Player> allPlayer);

}