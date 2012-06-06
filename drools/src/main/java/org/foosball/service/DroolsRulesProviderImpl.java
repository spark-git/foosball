package org.foosball.service;

import java.util.Arrays;
import java.util.List;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.command.Command;
import org.drools.command.CommandFactory;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatelessKnowledgeSession;
import org.foosball.domain.Player;
import org.foosball.domain.Team;

import com.google.common.collect.Lists;

class DroolsRulesProviderImpl implements RulesProvider {
	final List<String> allRules;
	private KnowledgeBase kbase;

	DroolsRulesProviderImpl(List<String> theRules) {
		allRules = theRules;
	}

	public void init() {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory
				.newKnowledgeBuilder();
		for (String r : allRules) {
			kbuilder.add(ResourceFactory.newClassPathResource(r, getClass()),
					ResourceType.DRL);
			if (kbuilder.hasErrors()) {
				System.out.println(kbuilder.getErrors().toString());
			}
		}
		kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
	}

	public List<Team> executeRules(List<Player> allPlayers) {
		StatelessKnowledgeSession ksession = kbase
				.newStatelessKnowledgeSession();
		List<Team> teams = Lists.newArrayList();
		while (!allPlayers.isEmpty()) {
			List<Team> newTeams = runEvaluation(allPlayers, ksession);
			teams.addAll(newTeams);
			for (Team team : newTeams) {
				allPlayers.removeAll(team.players());
			}
		}
		return teams;
	}

	private List<Team> runEvaluation(List<Player> allPlayer,
			StatelessKnowledgeSession ksession) {
		List<Team> teams = Lists.newArrayList();
		List<Command> cmds = Lists.newArrayList(
				CommandFactory.newInsertElements(allPlayer),
				CommandFactory.newSetGlobal("teams", teams),
				CommandFactory.newFireAllRules());
		ksession.execute(CommandFactory.newBatchExecution(cmds));
		return teams;
	}
}
