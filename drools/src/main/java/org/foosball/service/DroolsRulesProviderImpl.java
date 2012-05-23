package org.foosball.service;

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

	public List<Team> executeRules(List<Player> allPlayer) {
		StatelessKnowledgeSession ksession = kbase
				.newStatelessKnowledgeSession();
		List<Team> teams = Lists.newArrayList();
		List<Command> cmds = Lists.newArrayList(
				CommandFactory.newInsertElements(allPlayer),
				CommandFactory.newSetGlobal("teams", teams),
				CommandFactory.newFireAllRules());
		ksession.execute(CommandFactory.newBatchExecution(cmds));
		return teams;
	}
}
