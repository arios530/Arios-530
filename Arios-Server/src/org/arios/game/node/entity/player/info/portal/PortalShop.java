package org.arios.game.node.entity.player.info.portal;

import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.arios.game.system.mysql.SQLManager;
import org.arios.parser.player.SavingModule;

/**
 * Manages the portal shop.
 * @author Vexia
 */
public final class PortalShop implements SavingModule {

	/**
	 * The mapping of portal rewards.
	 */
	private final Map<String, Object> rewards = new HashMap<>();

	/**
	 * The list of perks this player has.
	 */
	private final List<Perks> perks = new ArrayList<>();

	/**
	 * The player portal.
	 */
	private final PlayerPortal portal;

	/**
	 * The amount of credits the player has.
	 */
	private int credits;

	/**
	 * Constructs a new {@code PortalRewards} {@code Object}.
	 * @param portal the portal.
	 */
	public PortalShop(final PlayerPortal portal) {
		this.portal = portal;
	}

	@Override
	public void save(ByteBuffer buffer) {
		if (credits != 0) {
			buffer.put((byte) 1);
			buffer.putInt(credits);
		}
		if (get("double_xp", 0L) != 0L) {
			buffer.put((byte) 2);
			buffer.putLong(get("double_xp", 0L));
		}
		if (get("borkKills", (byte) 0) != 0) {
			buffer.put((byte) 6);
			buffer.put((byte) get("borkKills", (byte) 0));
		}
		if (get("spellSwap", 0L) != 0L) {
			buffer.put((byte) 7);
			buffer.putLong(get("spellSwap", 0L));
		}
		buffer.put((byte) 0);
	}

	@Override
	public void parse(ByteBuffer buffer) {
		int opcode;
		while ((opcode = buffer.get() & 0xFF) != 0) {
			switch (opcode) {
			case 1:
				credits = buffer.getInt();
				break;
			case 2:
				rewards.put("double_xp", buffer.getLong());
				break;
			case 6:
				rewards.put("borkKills", buffer.get());
				break;
			case 7:
				rewards.put("spellSwap", buffer.getLong());
				break;
			}
		}
	}

	/**
	 * Parses the perks from the db.
	 */
	public void parsePerks(String val) {
		perks.clear();
		String[] tokens = (val.trim()).split(",");
		if (val != null && val.length() > 0 && !val.contains(",")) {
			perks.add(Perks.forId(Integer.parseInt(val.trim())));
			return;
		}
		if (tokens == null) {
			return;
		}
		Perks p = null;
		for (String perk : tokens) {
			if (perk == null || perk == "" || perk.length() == 0) {
				continue;
			}
			p = Perks.forId(Integer.parseInt(perk));
			if (p == null) {
				continue;
			}
			addPerk(p);
		}
	}

	/**
	 * Removes the perk.
	 * @param perk the perk.
	 * @param update if we update sql.
	 */
	public boolean removePerk(Perks perk) {
		if (perk == null || !perks.contains(perk)) {
			return false;
		}
		if (!syncPerks()) {
			return false;
		}
		if (!perks.contains(perk)) {
			return false;
		}
		perks.remove(perk);
		portal.getDetails().getSqlManager().getTable().getColumn("perks").updateValue(getPerkSyntax());
		portal.getDetails().getSqlManager().save();
		return true;
	}

	/**
	 * Gets the perk syntax.
	 * @return the perk syntax.
	 */
	private String getPerkSyntax() {
		String value = "";
		for (Perks perk : perks) {
			value += perk.getProductId() + ",";
		}
		if (value.length() > 0 && value.charAt(value.length() - 1) == ',') {
			value = value.substring(0, value.length() - 1);
		}
		return value;
	}

	/**
	 * Adds a perk to the list.
	 * @param perk the perk.
	 */
	public void addPerk(Perks perk) {
		if (perk == null || perks.contains(perk)) {
			return;
		}
		perks.add(perk);
	}

	/**
	 * Checks if the player has a perk.
	 * @param perk the perk.
	 * @return {@code True} if so.
	 */
	public boolean hasPerk(Perks perk) {
		return perks.contains(perk);
	}

	/**
	 * Syncs the credits.
	 */
	public boolean syncCredits() {
		if (!SQLManager.isInitialized()) {
			return false;
		}
		Connection connection = SQLManager.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM members WHERE username = '" + portal.getDetails().getUsername() + "'");
			ResultSet set = statement.executeQuery();
			if (set.next()) {
				setCredits(set.getInt("credits"));
				SQLManager.close(connection);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SQLManager.close(connection);
		return false;
	}

	/**
	 * Syncs the perks.
	 */
	public boolean syncPerks() {
		if (!SQLManager.isInitialized()) {
			return false;
		}
		Connection connection = SQLManager.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM members WHERE username = '" + portal.getDetails().getUsername() + "'");
			ResultSet set = statement.executeQuery();
			if (set.next()) {
				parsePerks(set.getString("perks"));
				SQLManager.close(connection);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SQLManager.close(connection);
		return false;
	}

	/**
	 * Sets the player's arios credits.
	 * @param credits The amount of credits.
	 * @param sqlUpdate If the SQL table should be updated.
	 */
	public void setCredits(int credits, boolean sqlUpdate) {
		setCredits(credits);
		portal.getDetails().getSqlManager().getTable().getColumn("credits").updateValue(credits);
		portal.getDetails().getSqlManager().getTable().getColumn("credits").setChanged(true);
		if (sqlUpdate) {
			portal.getDetails().getSqlManager().save();
		}
	}

	/**
	 * Sets the double exp.
	 * @param time the time.
	 */
	public void setDoubleExp(long time) {
		portal.getShop().getMapping().remove("double_xp");
		portal.getShop().getMapping().put("double_xp", time);
	}

	/**
	 * Gets the double exp.
	 * @return the time it's lasting for.
	 */
	public long getDoubleExp() {
		return get("double_xp", 0L);
	}

	/**
	 * Checks if the player has double exp.
	 * @return {@code True} if so.
	 */
	public boolean hasDoubleXp() {
		return getDoubleExp() > System.currentTimeMillis();
	}

	/**
	 * Gets the credits.
	 * @return The credits.
	 */
	public int getCredits() {
		return credits;
	}

	/**
	 * Sets the credits.
	 * @param credits The credits to set.
	 */
	@Deprecated
	public void setCredits(int credits) {
		this.credits = credits;
	}

	/**
	 * Gets a reward.
	 * @param key The attribute name.
	 * @return The attribute value.
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		if (!rewards.containsKey(key)) {
			return null;
		}
		return (T) rewards.get(key);
	}

	/**
	 * Gets a reward.
	 * @param string The attribute name.
	 * @param fail The value to return if the attribute is null.
	 * @return The attribute value, or the fail argument when null.
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(String string, T fail) {
		Object object = rewards.get(string);
		if (object != null) {
			return (T) object;
		}
		return fail;
	}

	/**
	 * Gets the rewards.
	 * @return The rewards.
	 */
	public Map<String, Object> getMapping() {
		return rewards;
	}

	/**
	 * Gets the perks.
	 * @return The perks.
	 */
	public List<Perks> getPerks() {
		return perks;
	}

}
