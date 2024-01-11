package no.runsafe.clans.handlers;

import no.runsafe.clans.Clan;
import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;

import java.util.*;

public class RankingHandler implements IConfigurationChanged
{
	public RankingHandler(ClanHandler clanHandler)
	{
		this.clanHandler = clanHandler;
	}

	public List<String> getRankingRoster(boolean recentStatistics)
	{
		Map<String, Clan> clanMap = clanHandler.getClans();
		Map<String, Integer> roster = new HashMap<>(clanMap.size());

		for (Map.Entry<String, Clan> clanNode : clanMap.entrySet())
		{
			Clan clan = clanNode.getValue();
			int score;
			if (recentStatistics) // Get score from recent statistics
				score = (clan.getMemberCount() * clanMemberScore)
					+ (clan.getRecentClanKills() * clanKillScore)
					- (clan.getRecentClanDeaths() * (clanKillScore / 2))
					+ (clan.getRecentDergonKills() * clanDergonKillScore);
			else // Get all-time score
				score = (clan.getMemberCount() * clanMemberScore)
					+ (clan.getClanKills() * clanKillScore)
					- (clan.getClanDeaths() * (clanKillScore / 2))
					+ (clan.getDergonKills() * clanDergonKillScore);

			roster.put(clan.getId(), score);
		}

		LinkedHashMap<String, Integer> sorted = sortByValues(roster); // Sort the stuff!
		List<String> ordered = new ArrayList<>(sorted.size());

		List<Map.Entry<String, Integer>> holder = new ArrayList<>(sorted.entrySet());
		for (int i = holder.size() - 1; i >= 0; i--)
			ordered.add(holder.get(i).getKey());

		return ordered;
	}

	public static <K extends Comparable,V extends Comparable> LinkedHashMap<K,V> sortByValues(Map<K,V> map)
	{
		List<Map.Entry<K,V>> entries = new LinkedList<>(map.entrySet());

		entries.sort(Map.Entry.comparingByValue());

		LinkedHashMap<K,V> sortedMap = new LinkedHashMap<>();

		for(Map.Entry<K,V> entry: entries)
			sortedMap.put(entry.getKey(), entry.getValue());

		return sortedMap;
	}


	@Override
	public void OnConfigurationChanged(IConfiguration config)
	{
		clanMemberScore = config.getConfigValueAsInt("ranking.clanMember");
		clanKillScore = config.getConfigValueAsInt("ranking.clanKill");
		clanDergonKillScore = config.getConfigValueAsInt("ranking.dergonKill");
	}

	private int clanMemberScore;
	private int clanKillScore;
	private int clanDergonKillScore;
	private final ClanHandler clanHandler;
}
