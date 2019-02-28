package tk.dblosevn.bukkit.subtopewdiepie.areashoplwc.listeners;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

import com.griefcraft.lwc.LWC;
import me.wiefferink.areashop.events.notify.ResoldRegionEvent;
import me.wiefferink.areashop.events.notify.UnrentedRegionEvent;
import tk.dblosevn.bukkit.subtopewdiepie.areashoplwc.AreaShopLWCHook;
import static tk.dblosevn.bukkit.subtopewdiepie.areashoplwc.AreaShopLWCHook.LogEvent;

public class AreaShopListener implements Listener {
	@EventHandler
	public void onResell(ResoldRegionEvent e) {
		
	}
	@EventHandler
	public void onUnRent(final UnrentedRegionEvent e) {
		final Vector max = e.getRegion().getMaximumPoint();
		final Vector min = e.getRegion().getMinimumPoint();
		
		Bukkit.getScheduler().runTaskAsynchronously(AreaShopLWCHook.get(), new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Connection connection = LWC.getInstance().getPhysicalDatabase().getConnection();
				PreparedStatement ps = null;
				ResultSet rs = null;
				
				try {
					ps = connection.prepareStatement("Select * From " + LWC.getInstance().getPhysicalDatabase().getPrefix() + "protections Where world = ? And (x >= ? And y >= ? And z >= ?) And (x <= ? And y <= ? And z <= ?)");
					ps.setString(1, e.getRegion().getWorldName());
					ps.setInt(2, min.getBlockX());
					ps.setInt(3, min.getBlockY());
					ps.setInt(4, min.getBlockZ());
					ps.setInt(5, max.getBlockX());
					ps.setInt(6, max.getBlockY());
					ps.setInt(7, max.getBlockZ());
					
					rs = ps.executeQuery();
					while (rs.next()) {
						LogEvent(String.format("Removing %s's protected %s in region %s", rs.getString("owner"), LWC.materialToString(rs.getInt("blockId")), e.getRegion().getName()));
						LWC.getInstance().findProtection(e.getRegion().getWorld(), rs.getInt("x"), rs.getInt("y"), rs.getInt("z")).remove();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} finally {
					try {
						connection.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
					}
					try {
						ps.close();
					} catch (SQLException e) {
					}
					try {
						rs.close();
					} catch (SQLException e) {
					}
				}
			}
		});
		
	}
}
