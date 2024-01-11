package no.runsafe.clans;

import no.runsafe.clans.commands.*;
import no.runsafe.clans.database.*;
import no.runsafe.clans.handlers.CharterHandler;
import no.runsafe.clans.handlers.ClanHandler;
import no.runsafe.clans.handlers.RankingHandler;
import no.runsafe.clans.monitors.CombatMonitor;
import no.runsafe.clans.monitors.DergonKillMonitor;
import no.runsafe.clans.monitors.PlayerMonitor;
import no.runsafe.framework.RunsafeConfigurablePlugin;
import no.runsafe.framework.api.IServer;
import no.runsafe.framework.api.command.Command;
import no.runsafe.framework.features.*;

public class RunsafeClans extends RunsafeConfigurablePlugin
{
	public static IServer server;

	@Override
	protected void pluginSetup()
	{
		server = getComponent(IServer.class);

		// Framework features
		addComponent(Commands.class);
		addComponent(Database.class);
		addComponent(Events.class);
		addComponent(FrameworkHooks.class);

		// Plugin components
		addComponent(Config.class);
		addComponent(ClanArgument.class);
		addComponent(TimeFormatter.class);

		// Database
		addComponent(ClanRepository.class);
		addComponent(ClanMemberRepository.class);
		addComponent(ClanInviteRepository.class);
		addComponent(ClanKillRepository.class);
		addComponent(ClanDergonKillRepository.class);

		// Handlers/Monitors
		addComponent(CharterHandler.class);
		addComponent(ClanHandler.class);
		addComponent(PlayerMonitor.class);
		addComponent(CombatMonitor.class);
		addComponent(RankingHandler.class);
		addComponent(DergonKillMonitor.class);

		// Commands
		Command clans = new Command("clan", "Clan related commands", null);
		addComponent(clans);

		clans.addSubCommand(getInstance(CreateClan.class));
		clans.addSubCommand(getInstance(InviteMember.class));
		clans.addSubCommand(getInstance(ClanInfo.class));
		clans.addSubCommand(getInstance(JoinClan.class));
		clans.addSubCommand(getInstance(DeclineClan.class));
		clans.addSubCommand(getInstance(DisbandClan.class));
		clans.addSubCommand(getInstance(LeaveClan.class));
		clans.addSubCommand(getInstance(PassLeadership.class));
		clans.addSubCommand(getInstance(KickClanMember.class));
		clans.addSubCommand(getInstance(LookupClan.class));
		clans.addSubCommand(getInstance(SetMotd.class));
		clans.addSubCommand(getInstance(ClanRankings.class));
		clans.addSubCommand(getInstance(ClanAllTimeRankings.class));
		clans.addSubCommand(getInstance(ListClans.class));
		clans.addSubCommand(getInstance(ClanFlare.class));
	}
}
