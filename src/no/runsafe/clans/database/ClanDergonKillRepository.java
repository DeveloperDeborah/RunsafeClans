package no.runsafe.clans.database;

import no.runsafe.framework.api.database.ISchemaUpdate;
import no.runsafe.framework.api.database.Repository;
import no.runsafe.framework.api.database.SchemaUpdate;
import no.runsafe.framework.api.player.IPlayer;

import javax.annotation.Nonnull;

public class ClanDergonKillRepository extends Repository
{
	@Nonnull
	@Override
	public String getTableName()
	{
		return "clan_dergon_kills";
	}

	@Nonnull
	@Override
	public ISchemaUpdate getSchemaUpdateQueries()
	{
		ISchemaUpdate update = new SchemaUpdate();
		update.addQueries(
			"CREATE TABLE `clan_dergon_kills` (" +
				"`killer` VARCHAR(36) NOT NULL," +
				"`clanID` VARCHAR(3) NULL," +
				"`date` DATETIME NOT NULL," +
				"PRIMARY KEY (`killer`,`date`)" +
			");"
		);
		return update;
	}

	public void recordDergonKill(IPlayer killer, String clanID)
	{
		database.execute(
			"INSERT INTO " + getTableName() + " (`killer`, `clanID`, `date`) VALUES(?, ?, NOW());",
			killer, clanID
		);
	}

	public void deleteClan(String clanID)
	{
		database.execute("UPDATE " + getTableName() + " SET `clanID` = NULL WHERE `clanID` = ?;", clanID);
	}

	public int getDergonKills(IPlayer player)
	{
		Integer value = database.queryInteger("SELECT COUNT(*) FROM " + getTableName() + " WHERE killer = ?;",  player);
		if (value == null)
			return 0;

		return value;
	}

	public int getClanDergonKills(String clanID, int daysAgo)
	{
		Integer value;
		if (daysAgo < 1)
			value = database.queryInteger("SELECT COUNT(*) FROM " + getTableName() + " WHERE clanID = ?;",  clanID);
		else
			value = database.queryInteger(
				"SELECT COUNT(*) FROM " + getTableName() + " WHERE clanID = ? AND date >= now() - interval ? day;",
				clanID, daysAgo
			);

		if (value == null)
			return 0;

		return value;
	}
}
