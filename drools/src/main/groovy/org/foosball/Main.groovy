package org.foosball

import org.foosball.domain.Player;
import org.foosball.domain.Team;
import org.foosball.service.DroolsRulesProviderImpl;
import org.foosball.service.ImperativeRulesProvider;
import org.foosball.service.RulesProvider;

List allPlayers = [
	new Player(name:"Augusto Evangelisti"	, handicap:0),
	new Player(name:"Bill Blake"	, handicap:4),
	new Player(name:"Guillaume Argaud"	, handicap:2),
	new Player(name:"Maurizio Colleluori"	, handicap:0),
	new Player(name:"Ronan McMahon"	, handicap:4),
	new Player(name:"David O'Reilly Healy"	, handicap:4),
	new Player(name:"Wouter Desmit"	, handicap:4),
	new Player(name:"Dorothée Coquet"	, handicap:6),
	new Player(name:"Joao Vicente"	, handicap:4),
	new Player(name:"Paul ONeill"	, handicap:2),
	new Player(name:"Paul Hitz"	, handicap:2),
	new Player(name:"Diego Sessona"	, handicap:2),
	new Player(name:"Telmo Felix"	, handicap:2),
	new Player(name:"Luis Oscar Trigueiros"	, handicap:5)
]

RulesProvider rp = new ImperativeRulesProvider()
rp.init()
List<Team> allTeams = rp.executeRules(allPlayers);
allTeams.each {
	println it
}

RulesProvider rp2 = new DroolsRulesProviderImpl(["foosball.drl"])
rp2.init()