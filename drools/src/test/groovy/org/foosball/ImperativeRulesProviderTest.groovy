package org.foosball;

import static org.junit.Assert.*;
import org.foosball.domain.Team
import org.foosball.service.ImperativeRulesProvider
import org.foosball.service.RulesProvider
import org.junit.Before;
import org.junit.Test;

class ImperativeRulesProviderTest {

	RulesProvider rulesProvider

	@Before
	void init() {
		rulesProvider = new ImperativeRulesProvider()
		rulesProvider.init()
	}

	@Test
	void pairUp() {
		List<Team> allTeams = rulesProvider.executeRules(Const.getAllPlayers());
		allTeams.each { println it }
	}
}
