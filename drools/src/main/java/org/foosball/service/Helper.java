package org.foosball.service;

import java.util.List;

import org.drools.runtime.KnowledgeRuntime;
import org.drools.runtime.rule.RuleContext;
import org.foosball.domain.Player;
import org.foosball.domain.Team;

public class Helper {

	@SuppressWarnings("unchecked")
	public static void teamUp(RuleContext context, Player a, Player b) {
		KnowledgeRuntime kr = context.getKnowledgeRuntime();
		List<Team> teams = (List<Team>) kr.getGlobal("teams");
		Team t = new Team(a, b);
		teams.add(t);
		System.out.println(t);
	}
}
