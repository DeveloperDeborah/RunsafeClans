package no.runsafe.clans.commands;

import no.runsafe.clans.Clan;
import no.runsafe.clans.Config;
import no.runsafe.clans.handlers.ClanHandler;
import no.runsafe.framework.api.IScheduler;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.TrailingArgument;
import no.runsafe.framework.api.command.player.PlayerAsyncCommand;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.text.ChatColour;

public class SetMotd extends PlayerAsyncCommand
{
	public SetMotd(IScheduler scheduler, ClanHandler clanHandler)
	{
		super("motd", "Change the clan message of the day", "runsafe.clans.motd", scheduler, new TrailingArgument("motd"));
		this.clanHandler = clanHandler;
	}

	@Override
	public String OnAsyncExecute(IPlayer executor, IArgumentList parameters)
	{
		if (clanHandler.isNotInAnyClan(executor))
			return Config.Message.userNotInClan;

		if (!clanHandler.playerIsClanLeader(executor))
			return Config.Message.userNotClanLeader;

		Clan clan = clanHandler.getPlayerClan(executor);
		if (clan == null)
			return Config.Message.error;

		String motd = ChatColour.Strip(parameters.getValue("motd"));
		if (motd == null || motd.contains("%"))
			motd = "Welcome back.";

		clanHandler.setClanMotd(clan.getId(), motd);
		return null;
	}

	private final ClanHandler clanHandler;
}
