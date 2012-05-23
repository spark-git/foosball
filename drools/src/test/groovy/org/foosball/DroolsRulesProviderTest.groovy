package org.foosball;

import static org.junit.Assert.*;
import org.foosball.domain.Team
import org.foosball.service.DroolsRulesProviderImpl
import org.foosball.service.ImperativeRulesProvider
import org.foosball.service.RulesProvider
import org.junit.Before;
import org.junit.Test;

class DroolsRulesProviderTest {

	RulesProvider rulesProvider

	@Before
	void init() {
		rulesProvider = new DroolsRulesProviderImpl(["foosball.drl"])
		rulesProvider.init()
	}

	@Test
	void pairUp() {
		List<Team> allTeams = rulesProvider.executeRules(Const.allPlayers);
		allTeams.each { println it }
	}
}
