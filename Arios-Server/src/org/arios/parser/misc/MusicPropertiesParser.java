package org.arios.parser.misc;

import java.nio.ByteBuffer;

import org.arios.cache.ServerStore;
import org.arios.cache.misc.buffer.ByteBufferUtils;
import org.arios.game.node.entity.player.link.music.MusicEntry;
import org.arios.game.node.entity.player.link.music.MusicZone;
import org.arios.game.world.map.RegionManager;
import org.arios.game.world.map.zone.ZoneBorders;
import org.arios.parser.Parser;

/**
 * Parses the music properties.
 * @author Emperor
 */
public final class MusicPropertiesParser implements Parser {

	@Override
	public boolean parse() throws Throwable {
		ByteBuffer buf = ServerStore.getArchive("music");
		int musicId;
		while ((musicId = buf.getShort()) != -1) {
			String name = ByteBufferUtils.getString(buf);
			int index = buf.getShort();
			MusicEntry entry = new MusicEntry(musicId, name, index);
			MusicEntry.getSongs().put(musicId, entry);
			int length = buf.get() & 0xFF;
			ZoneBorders borders = null;
			for (int i = 0; i < length; i++) {
				int exceptions = buf.get() & 0xFF;
				int southWestX = buf.getShort();
				int southWestY = buf.getShort();
				int northEastX = buf.getShort();
				int northEastY = buf.getShort();
				borders = new ZoneBorders(southWestX, southWestY, northEastX, northEastY);
				for (int j = 0; j < exceptions; j++) {
					southWestX = buf.getShort();
					southWestY = buf.getShort();
					northEastX = buf.getShort();
					northEastY = buf.getShort();
					borders.addException(new ZoneBorders(southWestX, southWestY, northEastX, northEastY));
				}
				MusicZone zone = new MusicZone(musicId, borders);
				for (Integer id : borders.getRegionIds()) {
					RegionManager.forId(id).getMusicZones().add(zone);
				}
			}
		}
		return true;
	}

}