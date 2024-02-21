package no.runsafe.clans.handlers;

import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.api.server.IPlayerProvider;
import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.item.meta.RunsafeBook;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class CharterHandler
{
	/**
	 * Constructor for handling clan charters.
	 * @param playerProvider The provider of player objects.
	 */
	public CharterHandler(IPlayerProvider playerProvider)
	{
		this.playerProvider = playerProvider;
	}

	public void givePlayerCharter(IPlayer player, String clanName)
	{
		RunsafeMeta charter = Item.Special.Crafted.WrittenBook.getItem(); // Create a book item.
		charter.setDisplayName("Leather-bound Charter"); // Give the item a name.
		charter.addLore("§7Clan: " + clanName); // Append the clan name.
		charter.addLore("§7Leader: " + player.getName()); // Append the clan leader.
		charter.addLore("§fRight-click to sign the clan charter!"); // Add some info.
		addCharterSign(charter, player); // Sign the charter.

		player.give(charter); // Give the player the charter.
	}

	public boolean itemIsCharter(RunsafeMeta item)
	{
		List<String> lore = item.getLore();
		return lore != null && lore.size() == 3 && lore.get(2).equals("§fRight-click to sign the clan charter!");
	}

	public String getClanName(RunsafeMeta charter)
	{
		List<String> lore = charter.getLore();
		if (lore == null)
			return "INVALID";
		return lore.get(0).split("\\s")[1];
	}

	public IPlayer getLeader(RunsafeMeta charter)
	{
		return getCharterSigns(charter).get(0);
	}

	public List<IPlayer> getCharterSigns(RunsafeMeta item)
	{
		RunsafeBook charter = (RunsafeBook) item; // Convert item to a book.
		if (!charter.hasPages()) // Check we have some pages.
			return Collections.emptyList(); // Return an empty thing.

		List<String> charterPages = charter.getPages();
		List<IPlayer> charterSigns = new ArrayList<>(0);
		for(String page : charterPages)
			charterSigns.add(playerProvider.getPlayer(UUID.fromString(page)));
		return charterSigns;
	}

	public void addCharterSign(RunsafeMeta item, IPlayer player)
	{
		RunsafeBook charter = (RunsafeBook) item; // Convert item to a book.
		charter.addPages(player.getUniqueId().toString()); // Add the sign to the charter.
	}

	private final IPlayerProvider playerProvider;
}
