package me.TheTealViper.recipesredone;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.jline.internal.InputStreamReader;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.TheTealViper.recipesredone.Utils.EnableShit;
import me.TheTealViper.recipesredone.Utils.GuiClick;
import me.TheTealViper.recipesredone.Utils.ItemCreator;
import me.TheTealViper.recipesredone.Utils.PluginFile;
import me.TheTealViper.recipesredone.Utils.StringUtils;

public class RecipesRedone extends JavaPlugin implements Listener{
	public static boolean playSound = false;
	static List<ModernRecipe> recipes = new ArrayList<ModernRecipe>();
	static List<ModernRecipe> singledRecipes = new ArrayList<ModernRecipe>();
	
	public void onEnable(){
		EnableShit.handleOnEnable(this, this, "49733");
		playSound = getConfig().getBoolean("Play_Sound_On_UI_Click");
		
		loadRecipes();
		TutorialGUI.loadRecipeTutorials(singledRecipes, this);
		Bukkit.getPluginManager().registerEvents(new TutorialGUI(), this);
		
		Bukkit.getPluginManager().registerEvents(new ItemCreator(), this);
	}
	
	public void onDisable(){
		Bukkit.getLogger().info(makeColors("RecipesRedone from TheTealViper shutting down. Bshzzzzzz"));
	}
	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e){
		if(getConfig().getBoolean("Use_This_Plugin_For_Crafting_Table")){
			if(e.getMessage().equalsIgnoreCase(getConfig().getString("Crafting_Table_Command"))){
				if(!getConfig().getBoolean("Crafting_Table_Require_Look_At_Workbench") || (getConfig().getBoolean("Crafting_Table_Require_Look_At_Workbench") && e.getPlayer().getTargetBlock(null, 10).getType().equals(Material.CRAFTING_TABLE))){
					e.setCancelled(true);
					ItemStack filler = ItemCreator.createItemFromConfiguration(getConfig().getConfigurationSection("Crafting_Table_Filler"));
					ItemStack craft1 = ItemCreator.createItemFromConfiguration(getConfig().getConfigurationSection("Crafting_Table_Craft1"));
					ItemStack craft16 = ItemCreator.createItemFromConfiguration(getConfig().getConfigurationSection("Crafting_Table_Craft16"));
					ItemStack craft32= ItemCreator.createItemFromConfiguration(getConfig().getConfigurationSection("Crafting_Table_Craft32"));
					ItemStack craft64 = ItemCreator.createItemFromConfiguration(getConfig().getConfigurationSection("Crafting_Table_Craft64"));
					e.getPlayer().openInventory(getCustomGui(makeColors(getConfig().getString("Crafting_Table_Name")), filler, craft1, craft16, craft32, craft64));
				}else{
					e.getPlayer().sendMessage("Please look at a workbench.");
				}
			}
		}
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(sender instanceof Player){
            Player p = (Player) sender;
            boolean explain = false;
            if(label.equalsIgnoreCase("createrecipe") && p.hasPermission("recipesredone.admin")){
            	RecipeCreator rc = new RecipeCreator(this, p);
            	rc.show();
            }else if(label.equalsIgnoreCase("reloadrecipes") && p.hasPermission("recipesredone.admin")){
            	reloadRecipes();
            	p.sendMessage("Reloaded");
            }
        }
        return false;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e){
		if(isCraftingGui(e.getClickedInventory())){
			Inventory inv = e.getInventory();
			if(e.getRawSlot() == 0 || e.getRawSlot() == 1 || e.getRawSlot() == 2
					|| e.getRawSlot() == 9 || e.getRawSlot() == 10 || e.getRawSlot() == 11
					|| e.getRawSlot() == 18 || e.getRawSlot() == 19 || e.getRawSlot() == 20
					|| e.getRawSlot() == 27 || e.getRawSlot() == 28 || e.getRawSlot() == 29
					|| e.getRawSlot() == 36 || e.getRawSlot() == 37 || e.getRawSlot() == 38
					|| e.getRawSlot() == 45 || e.getRawSlot() == 46 || e.getRawSlot() == 47){
				if(!((inv.getItem(6) == null || inv.getItem(6).getType().equals(Material.AIR))
						&& (inv.getItem(7) == null || inv.getItem(7).getType().equals(Material.AIR))
						&& (inv.getItem(8) == null || inv.getItem(8).getType().equals(Material.AIR))
						&& (inv.getItem(15) == null || inv.getItem(15).getType().equals(Material.AIR))
						&& (inv.getItem(16) == null || inv.getItem(16).getType().equals(Material.AIR))
						&& (inv.getItem(17) == null || inv.getItem(17).getType().equals(Material.AIR))
						&& (inv.getItem(24) == null || inv.getItem(24).getType().equals(Material.AIR))
						&& (inv.getItem(25) == null || inv.getItem(25).getType().equals(Material.AIR))
						&& (inv.getItem(26) == null || inv.getItem(26).getType().equals(Material.AIR))
						&& (inv.getItem(33) == null || inv.getItem(33).getType().equals(Material.AIR))
						&& (inv.getItem(34) == null || inv.getItem(34).getType().equals(Material.AIR))
						&& (inv.getItem(35) == null || inv.getItem(35).getType().equals(Material.AIR))
						&& (inv.getItem(42) == null || inv.getItem(42).getType().equals(Material.AIR))
						&& (inv.getItem(43) == null || inv.getItem(43).getType().equals(Material.AIR))
						&& (inv.getItem(44) == null || inv.getItem(44).getType().equals(Material.AIR))
						&& (inv.getItem(51) == null || inv.getItem(51).getType().equals(Material.AIR))
						&& (inv.getItem(52) == null || inv.getItem(52).getType().equals(Material.AIR))
						&& (inv.getItem(53) == null || inv.getItem(53).getType().equals(Material.AIR)))
						&& ((inv.getItem(0) == null || inv.getItem(0).getType().equals(Material.AIR))
						&& (inv.getItem(1) == null || inv.getItem(1).getType().equals(Material.AIR))
						&& (inv.getItem(2) == null || inv.getItem(2).getType().equals(Material.AIR))
						&& (inv.getItem(9) == null || inv.getItem(9).getType().equals(Material.AIR))
						&& (inv.getItem(10) == null || inv.getItem(10).getType().equals(Material.AIR))
						&& (inv.getItem(11) == null || inv.getItem(11).getType().equals(Material.AIR))
						&& (inv.getItem(18) == null || inv.getItem(18).getType().equals(Material.AIR))
						&& (inv.getItem(19) == null || inv.getItem(19).getType().equals(Material.AIR))
						&& (inv.getItem(20) == null || inv.getItem(20).getType().equals(Material.AIR))
						&& (inv.getItem(27) == null || inv.getItem(27).getType().equals(Material.AIR))
						&& (inv.getItem(28) == null || inv.getItem(28).getType().equals(Material.AIR))
						&& (inv.getItem(29) == null || inv.getItem(29).getType().equals(Material.AIR))
						&& (inv.getItem(36) == null || inv.getItem(36).getType().equals(Material.AIR))
						&& (inv.getItem(37) == null || inv.getItem(37).getType().equals(Material.AIR))
						&& (inv.getItem(38) == null || inv.getItem(38).getType().equals(Material.AIR))
						&& (inv.getItem(45) == null || inv.getItem(45).getType().equals(Material.AIR))
						&& (inv.getItem(46) == null || inv.getItem(36).getType().equals(Material.AIR))
						&& (inv.getItem(47) == null || inv.getItem(47).getType().equals(Material.AIR)))){
					e.setCancelled(true);
					e.getWhoClicked().sendMessage("Please clear what has been crafted first.");
				}else{
					Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
						public void run() {
							boolean foundARecipe = false;
							for(int i = 0;i < recipes.size();i++){
								ModernRecipe mr = recipes.get(i);
								if(mr.checkRecipe(inv) && (mr.permission.equals("") || e.getWhoClicked().hasPermission(mr.permission))){
									mr.loadRewards(inv);
									foundARecipe = true;
								}
							}
							if(!foundARecipe){
								inv.remove(inv.getItem(6));inv.remove(inv.getItem(7));inv.remove(inv.getItem(8));
								inv.remove(inv.getItem(15));inv.remove(inv.getItem(16));inv.remove(inv.getItem(17));
								inv.remove(inv.getItem(24));inv.remove(inv.getItem(25));inv.remove(inv.getItem(26));
								inv.remove(inv.getItem(33));inv.remove(inv.getItem(34));inv.remove(inv.getItem(35));
								inv.remove(inv.getItem(42));inv.remove(inv.getItem(43));inv.remove(inv.getItem(44));
								inv.remove(inv.getItem(51));inv.remove(inv.getItem(52));inv.remove(inv.getItem(53));
								((Player) e.getWhoClicked()).updateInventory();
							}
						}
					}, 0L);
				}
			}else if(e.getRawSlot() == 6 || e.getRawSlot() == 7 || e.getRawSlot() == 8
					|| e.getRawSlot() == 15 || e.getRawSlot() == 16 || e.getRawSlot() == 17
					|| e.getRawSlot() == 24 || e.getRawSlot() == 25 || e.getRawSlot() == 26
					|| e.getRawSlot() == 33 || e.getRawSlot() == 34 || e.getRawSlot() == 35
					|| e.getRawSlot() == 42 || e.getRawSlot() == 43 || e.getRawSlot() == 44
					|| e.getRawSlot() == 51 || e.getRawSlot() == 52 || e.getRawSlot() == 53){
				if((inv.getItem(0) == null || inv.getItem(0).getType().equals(Material.AIR))
						&& (inv.getItem(1) == null || inv.getItem(1).getType().equals(Material.AIR))
						&& (inv.getItem(2) == null || inv.getItem(2).getType().equals(Material.AIR))
						&& (inv.getItem(9) == null || inv.getItem(9).getType().equals(Material.AIR))
						&& (inv.getItem(10) == null || inv.getItem(10).getType().equals(Material.AIR))
						&& (inv.getItem(11) == null || inv.getItem(11).getType().equals(Material.AIR))
						&& (inv.getItem(18) == null || inv.getItem(18).getType().equals(Material.AIR))
						&& (inv.getItem(19) == null || inv.getItem(19).getType().equals(Material.AIR))
						&& (inv.getItem(20) == null || inv.getItem(20).getType().equals(Material.AIR))
						&& (inv.getItem(27) == null || inv.getItem(27).getType().equals(Material.AIR))
						&& (inv.getItem(28) == null || inv.getItem(28).getType().equals(Material.AIR))
						&& (inv.getItem(29) == null || inv.getItem(29).getType().equals(Material.AIR))
						&& (inv.getItem(36) == null || inv.getItem(36).getType().equals(Material.AIR))
						&& (inv.getItem(37) == null || inv.getItem(37).getType().equals(Material.AIR))
						&& (inv.getItem(38) == null || inv.getItem(38).getType().equals(Material.AIR))
						&& (inv.getItem(45) == null || inv.getItem(45).getType().equals(Material.AIR))
						&& (inv.getItem(46) == null || inv.getItem(36).getType().equals(Material.AIR))
						&& (inv.getItem(47) == null || inv.getItem(47).getType().equals(Material.AIR))){
					
				}else{
					e.setCancelled(true);
					e.getWhoClicked().sendMessage("Please click the craft button first.");
				}
			}else if(e.getRawSlot() == 13
					|| e.getRawSlot() == 22
					|| e.getRawSlot() == 31
					|| e.getRawSlot() == 40){
				e.setCancelled(true);
				if((inv.getItem(0) == null || inv.getItem(0).getType().equals(Material.AIR))
						&& (inv.getItem(1) == null || inv.getItem(1).getType().equals(Material.AIR))
						&& (inv.getItem(2) == null || inv.getItem(2).getType().equals(Material.AIR))
						&& (inv.getItem(9) == null || inv.getItem(9).getType().equals(Material.AIR))
						&& (inv.getItem(10) == null || inv.getItem(10).getType().equals(Material.AIR))
						&& (inv.getItem(11) == null || inv.getItem(11).getType().equals(Material.AIR))
						&& (inv.getItem(18) == null || inv.getItem(18).getType().equals(Material.AIR))
						&& (inv.getItem(19) == null || inv.getItem(19).getType().equals(Material.AIR))
						&& (inv.getItem(20) == null || inv.getItem(20).getType().equals(Material.AIR))
						&& (inv.getItem(27) == null || inv.getItem(27).getType().equals(Material.AIR))
						&& (inv.getItem(28) == null || inv.getItem(28).getType().equals(Material.AIR))
						&& (inv.getItem(29) == null || inv.getItem(29).getType().equals(Material.AIR))
						&& (inv.getItem(36) == null || inv.getItem(36).getType().equals(Material.AIR))
						&& (inv.getItem(37) == null || inv.getItem(37).getType().equals(Material.AIR))
						&& (inv.getItem(38) == null || inv.getItem(38).getType().equals(Material.AIR))
						&& (inv.getItem(45) == null || inv.getItem(45).getType().equals(Material.AIR))
						&& (inv.getItem(46) == null || inv.getItem(46).getType().equals(Material.AIR))
						&& (inv.getItem(47) == null || inv.getItem(47).getType().equals(Material.AIR))){
					e.getWhoClicked().sendMessage("There is nothing to be crafted.");
				}else{
					if((inv.getItem(6) == null || inv.getItem(6).getType().equals(Material.AIR))
							&& (inv.getItem(7) == null || inv.getItem(7).getType().equals(Material.AIR))
							&& (inv.getItem(8) == null || inv.getItem(8).getType().equals(Material.AIR))
							&& (inv.getItem(15) == null || inv.getItem(15).getType().equals(Material.AIR))
							&& (inv.getItem(16) == null || inv.getItem(16).getType().equals(Material.AIR))
							&& (inv.getItem(17) == null || inv.getItem(17).getType().equals(Material.AIR))
							&& (inv.getItem(24) == null || inv.getItem(24).getType().equals(Material.AIR))
							&& (inv.getItem(25) == null || inv.getItem(25).getType().equals(Material.AIR))
							&& (inv.getItem(26) == null || inv.getItem(26).getType().equals(Material.AIR))
							&& (inv.getItem(33) == null || inv.getItem(33).getType().equals(Material.AIR))
							&& (inv.getItem(34) == null || inv.getItem(34).getType().equals(Material.AIR))
							&& (inv.getItem(35) == null || inv.getItem(35).getType().equals(Material.AIR))
							&& (inv.getItem(42) == null || inv.getItem(42).getType().equals(Material.AIR))
							&& (inv.getItem(43) == null || inv.getItem(43).getType().equals(Material.AIR))
							&& (inv.getItem(44) == null || inv.getItem(44).getType().equals(Material.AIR))
							&& (inv.getItem(51) == null || inv.getItem(51).getType().equals(Material.AIR))
							&& (inv.getItem(52) == null || inv.getItem(52).getType().equals(Material.AIR))
							&& (inv.getItem(53) == null || inv.getItem(53).getType().equals(Material.AIR))){
						e.getWhoClicked().sendMessage("There is nothing to be crafted.");
					}else{
						if(playSound)
							GuiClick.playClick((Player) e.getWhoClicked(), 1);
						int amount = 1;
						if(e.getRawSlot() == 22)
							amount = 16;
						else if(e.getRawSlot() == 31)
							amount = 32;
						else if(e.getRawSlot() == 40)
							amount = 64;
						if((amount == 1) || (amount != 1 && checkRightAmount((Player) e.getWhoClicked(), inv, amount))){
							RecipeRedoneCraftEvent event = new RecipeRedoneCraftEvent((Player) e.getWhoClicked(), inv, new ItemStack[] {inv.getItem(0),inv.getItem(1),inv.getItem(2)
									,inv.getItem(9),inv.getItem(10),inv.getItem(11)
									,inv.getItem(18),inv.getItem(19),inv.getItem(20)
									,inv.getItem(27),inv.getItem(28),inv.getItem(29)
									,inv.getItem(36),inv.getItem(37),inv.getItem(38)
									,inv.getItem(45),inv.getItem(46),inv.getItem(47)}, amount);
							Bukkit.getPluginManager().callEvent(event);
							if(!event.isCancelled()){
								for(int row = 0;row < 6;row++){
									for(int col = 0;col < 3;col++){
										if(inv.getItem(col + (row * 9)) != null)
											inv.getItem(col + (row * 9)).setAmount(0);
									}
								}
								int slotNumber = 6;
								if(inv.getItem(slotNumber) != null)
									inv.getItem(slotNumber).setAmount(inv.getItem(slotNumber).getAmount() * event.getCraftMultiplier());
								slotNumber = 7;
								if(inv.getItem(slotNumber) != null)
									inv.getItem(slotNumber).setAmount(inv.getItem(slotNumber).getAmount() * event.getCraftMultiplier());
								slotNumber = 9;
								if(inv.getItem(slotNumber) != null)
									inv.getItem(slotNumber).setAmount(inv.getItem(slotNumber).getAmount() * event.getCraftMultiplier());
								slotNumber = 15;
								if(inv.getItem(slotNumber) != null)
									inv.getItem(slotNumber).setAmount(inv.getItem(slotNumber).getAmount() * event.getCraftMultiplier());
								slotNumber = 16;
								if(inv.getItem(slotNumber) != null)
									inv.getItem(slotNumber).setAmount(inv.getItem(slotNumber).getAmount() * event.getCraftMultiplier());
								slotNumber = 17;
								if(inv.getItem(slotNumber) != null)
									inv.getItem(slotNumber).setAmount(inv.getItem(slotNumber).getAmount() * event.getCraftMultiplier());
								slotNumber = 24;
								if(inv.getItem(slotNumber) != null)
									inv.getItem(slotNumber).setAmount(inv.getItem(slotNumber).getAmount() * event.getCraftMultiplier());
								slotNumber = 25;
								if(inv.getItem(slotNumber) != null)
									inv.getItem(slotNumber).setAmount(inv.getItem(slotNumber).getAmount() * event.getCraftMultiplier());
								slotNumber = 26;
								if(inv.getItem(slotNumber) != null)
									inv.getItem(slotNumber).setAmount(inv.getItem(slotNumber).getAmount() * event.getCraftMultiplier());
								slotNumber = 33;
								if(inv.getItem(slotNumber) != null)
									inv.getItem(slotNumber).setAmount(inv.getItem(slotNumber).getAmount() * event.getCraftMultiplier());
								slotNumber = 34;
								if(inv.getItem(slotNumber) != null)
									inv.getItem(slotNumber).setAmount(inv.getItem(slotNumber).getAmount() * event.getCraftMultiplier());
								slotNumber = 35;
								if(inv.getItem(slotNumber) != null)
									inv.getItem(slotNumber).setAmount(inv.getItem(slotNumber).getAmount() * event.getCraftMultiplier());
								slotNumber = 42;
								if(inv.getItem(slotNumber) != null)
									inv.getItem(slotNumber).setAmount(inv.getItem(slotNumber).getAmount() * event.getCraftMultiplier());
								slotNumber = 43;
								if(inv.getItem(slotNumber) != null)
									inv.getItem(slotNumber).setAmount(inv.getItem(slotNumber).getAmount() * event.getCraftMultiplier());
								slotNumber = 44;
								if(inv.getItem(slotNumber) != null)
									inv.getItem(slotNumber).setAmount(inv.getItem(slotNumber).getAmount() * event.getCraftMultiplier());
								slotNumber = 51;
								if(inv.getItem(slotNumber) != null)
									inv.getItem(slotNumber).setAmount(inv.getItem(slotNumber).getAmount() * event.getCraftMultiplier());
								slotNumber = 52;
								if(inv.getItem(slotNumber) != null)
									inv.getItem(slotNumber).setAmount(inv.getItem(slotNumber).getAmount() * event.getCraftMultiplier());
								slotNumber = 53;
								if(inv.getItem(slotNumber) != null)
									inv.getItem(slotNumber).setAmount(inv.getItem(slotNumber).getAmount() * event.getCraftMultiplier());
								((Player) e.getWhoClicked()).updateInventory();
								e.getWhoClicked().sendMessage("Successfully Crafted!");
							}
						}
					}
				}
			}else{
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent e){
		if(isCraftingGui(e.getInventory())){
			World w = e.getPlayer().getWorld();
			Location l = e.getPlayer().getLocation();
			Inventory i = e.getInventory();
			if((i.getItem(0) == null || i.getItem(0).getType().equals(Material.AIR))
					&& (i.getItem(1) == null || i.getItem(1).getType().equals(Material.AIR))
					&& (i.getItem(2) == null || i.getItem(2).getType().equals(Material.AIR))
					&& (i.getItem(9) == null || i.getItem(9).getType().equals(Material.AIR))
					&& (i.getItem(10) == null || i.getItem(10).getType().equals(Material.AIR))
					&& (i.getItem(11) == null || i.getItem(11).getType().equals(Material.AIR))
					&& (i.getItem(18) == null || i.getItem(18).getType().equals(Material.AIR))
					&& (i.getItem(19) == null || i.getItem(19).getType().equals(Material.AIR))
					&& (i.getItem(20) == null || i.getItem(20).getType().equals(Material.AIR))
					&& (i.getItem(27) == null || i.getItem(27).getType().equals(Material.AIR))
					&& (i.getItem(28) == null || i.getItem(28).getType().equals(Material.AIR))
					&& (i.getItem(29) == null || i.getItem(29).getType().equals(Material.AIR))
					&& (i.getItem(36) == null || i.getItem(36).getType().equals(Material.AIR))
					&& (i.getItem(37) == null || i.getItem(37).getType().equals(Material.AIR))
					&& (i.getItem(38) == null || i.getItem(38).getType().equals(Material.AIR))
					&& (i.getItem(45) == null || i.getItem(45).getType().equals(Material.AIR))
					&& (i.getItem(46) == null || i.getItem(36).getType().equals(Material.AIR))
					&& (i.getItem(47) == null || i.getItem(47).getType().equals(Material.AIR))){
				if(i.getItem(6) != null)
					w.dropItemNaturally(l, i.getItem(6));
				if(i.getItem(7) != null)
					w.dropItemNaturally(l, i.getItem(7));
				if(i.getItem(8) != null)
					w.dropItemNaturally(l, i.getItem(8));
				if(i.getItem(15) != null)
					w.dropItemNaturally(l, i.getItem(15));
				if(i.getItem(16) != null)
					w.dropItemNaturally(l, i.getItem(16));
				if(i.getItem(17) != null)
					w.dropItemNaturally(l, i.getItem(17));
				if(i.getItem(24) != null)
					w.dropItemNaturally(l, i.getItem(24));
				if(i.getItem(25) != null)
					w.dropItemNaturally(l, i.getItem(25));
				if(i.getItem(26) != null)
					w.dropItemNaturally(l, i.getItem(26));
				if(i.getItem(33) != null)
					w.dropItemNaturally(l, i.getItem(33));
				if(i.getItem(34) != null)
					w.dropItemNaturally(l, i.getItem(34));
				if(i.getItem(35) != null)
					w.dropItemNaturally(l, i.getItem(35));
				if(i.getItem(42) != null)
					w.dropItemNaturally(l, i.getItem(42));
				if(i.getItem(43) != null)
					w.dropItemNaturally(l, i.getItem(43));
				if(i.getItem(44) != null)
					w.dropItemNaturally(l, i.getItem(44));
				if(i.getItem(51) != null)
					w.dropItemNaturally(l, i.getItem(51));
				if(i.getItem(52) != null)
					w.dropItemNaturally(l, i.getItem(52));
				if(i.getItem(53) != null)
					w.dropItemNaturally(l, i.getItem(53));
			}else{
				if(i.getItem(0) != null)
					w.dropItemNaturally(l, i.getItem(0));
				if(i.getItem(1) != null)
					w.dropItemNaturally(l, i.getItem(1));
				if(i.getItem(2) != null)
					w.dropItemNaturally(l, i.getItem(2));
				if(i.getItem(9) != null)
					w.dropItemNaturally(l, i.getItem(9));
				if(i.getItem(10) != null)
					w.dropItemNaturally(l, i.getItem(10));
				if(i.getItem(11) != null)
					w.dropItemNaturally(l, i.getItem(11));
				if(i.getItem(18) != null)
					w.dropItemNaturally(l, i.getItem(18));
				if(i.getItem(19) != null)
					w.dropItemNaturally(l, i.getItem(19));
				if(i.getItem(20) != null)
					w.dropItemNaturally(l, i.getItem(20));
				if(i.getItem(27) != null)
					w.dropItemNaturally(l, i.getItem(27));
				if(i.getItem(28) != null)
					w.dropItemNaturally(l, i.getItem(28));
				if(i.getItem(29) != null)
					w.dropItemNaturally(l, i.getItem(29));
				if(i.getItem(36) != null)
					w.dropItemNaturally(l, i.getItem(36));
				if(i.getItem(37) != null)
					w.dropItemNaturally(l, i.getItem(37));
				if(i.getItem(38) != null)
					w.dropItemNaturally(l, i.getItem(38));
				if(i.getItem(45) != null)
					w.dropItemNaturally(l, i.getItem(45));
				if(i.getItem(46) != null)
					w.dropItemNaturally(l, i.getItem(46));
				if(i.getItem(47) != null)
					w.dropItemNaturally(l, i.getItem(47));
			}
		}
	}
	
	private boolean checkRightAmount(Player p, Inventory inv, int multiplier){
		Map<ItemStack, Integer> invAmountMap = new HashMap<ItemStack, Integer>();
		for(int row = 0;row < 6;row++){
			for(int col = 0;col < 3;col++){
				if(inv.getItem(col + (row * 9)) == null)
					continue;
				ItemStack item = inv.getItem(col + (row * 9)).clone();
				item.setAmount(1);
				if(!invAmountMap.containsKey(item))
					invAmountMap.put(item, 0);
				int amount = invAmountMap.get(item);
				invAmountMap.put(item, amount + inv.getItem(col + (row * 9)).getAmount());
			}
		}
		for(ItemStack i : invAmountMap.keySet()){
			invAmountMap.put(i, invAmountMap.get(i) * (multiplier - 1));
		}
		//Now the necessary amounts are loaded and we check the player
		Map<ItemStack, Integer> playerAmountMap = new HashMap<ItemStack, Integer>();
		for(ItemStack i : p.getInventory().getContents()){
			if(i == null)
				continue;
			ItemStack item = i.clone();
			item.setAmount(1);
			if(!playerAmountMap.containsKey(item))
				playerAmountMap.put(item, 0);
			int amount = playerAmountMap.get(item);
			playerAmountMap.put(item, amount + i.getAmount());
		}
		//Now we have all the amounts saved
		for(ItemStack i : invAmountMap.keySet()){
			//Subtract 1 because there is already 1 of the item in the crafting item
			if(!playerAmountMap.containsKey(i) || invAmountMap.get(i) > playerAmountMap.get(i)){
				return false;
			}
		}
		//Now we remove the items from the players inventory
		for(int index = 9;index < 45;index++){
			ItemStack i = p.getInventory().getItem(index);
			if(i == null)
				continue;
			ItemStack keyItem = i.clone();keyItem.setAmount(1);
			int iAmount = i.getAmount();
			//If the item being checked isn't owed
			if(!invAmountMap.containsKey(keyItem))
				continue;
			int owedAmount = invAmountMap.get(keyItem);
			if(iAmount >= owedAmount){
				p.getInventory().getItem(index).setAmount(iAmount - owedAmount);
				owedAmount = 0;
			}else{
				p.getInventory().getItem(index).setAmount(0);
				owedAmount -= iAmount;
			}
			playerAmountMap.put(keyItem, owedAmount);
			index++;
		}
		return true;
	}
	
	private void loadRecipes(){
		File folder = new File("plugins/RecipesRedone/recipes");
		if(!folder.exists()){
			try {
				YamlConfiguration.loadConfiguration(new InputStreamReader(getResource("simpleExampleRecipe.yml"))).save("plugins/RecipesRedone/recipes/simpleExampleRecipe.yml");
				YamlConfiguration.loadConfiguration(new InputStreamReader(getResource("intermediateExampleRecipe.yml"))).save("plugins/RecipesRedone/recipes/intermediateExampleRecipe.yml");
				YamlConfiguration.loadConfiguration(new InputStreamReader(getResource("complicatedExampleRecipe1.yml"))).save("plugins/RecipesRedone/recipes/complicatedExampleRecipe1.yml");
				YamlConfiguration.loadConfiguration(new InputStreamReader(getResource("complicatedExampleRecipe2.yml"))).save("plugins/RecipesRedone/recipes/complicatedExampleRecipe2.yml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			folder.mkdirs();
		}
		String defaultString = "123456789abcdefghi";
		for(File recipeFile : folder.listFiles()){
			String[] recipe = new String[] {"123","456","789", "abc", "def", "ghi"};
			Map<Character, ItemStack> recipeMap = new HashMap<Character, ItemStack>();
			String[] rewards = new String[] {"123","456","789", "abc", "def", "ghi"};
			Map<Character, ItemStack> rewardMap = new HashMap<Character, ItemStack>();
			PluginFile recipeConfig = new PluginFile(this, "recipes/" + recipeFile.getName());
			for(int i = 0;i < 18;i++){
				if(recipeConfig.contains("ingame") && recipeConfig.getBoolean("ingame")){
					ItemStack recipeItem = recipeConfig.getItemStack("recipe." + i);
					recipeMap.put(defaultString.charAt(i), recipeItem);
					ItemStack rewardItem = recipeConfig.getItemStack("reward." + i);
					rewardMap.put(defaultString.charAt(i), rewardItem);
				}else{
					ItemStack recipeItem = ItemCreator.createItemFromConfiguration(recipeConfig.getConfigurationSection("recipe." + i));
					recipeMap.put(defaultString.charAt(i), recipeItem);
					ItemStack rewardItem = ItemCreator.createItemFromConfiguration(recipeConfig.getConfigurationSection("reward." + i));
					rewardMap.put(defaultString.charAt(i), rewardItem);
				}
			}
			ModernRecipe mr = new ModernRecipe(recipe, recipeMap, rewards, rewardMap, recipeConfig.getString("permission"));
			if(recipeConfig.contains("category"))
				mr.category = recipeConfig.getString("category");
			if(recipeConfig.contains("tutorial"))
				mr.showRecipeTutorial = recipeConfig.getBoolean("tutorial");
			Bukkit.getLogger().info("Loaded " + recipeFile.getName() + " as a recipe.");
		}
	}
	
	public void reloadRecipes(){
		recipes = new ArrayList<ModernRecipe>();
		singledRecipes = new ArrayList<ModernRecipe>();
		loadRecipes();
		TutorialGUI.reloadRecipeTutorials(singledRecipes, this);
	}
	
	public static void registerRecipe(ModernRecipe mr){
		recipes.add(mr);
	}
	
	public static void unregisterRecipe(ModernRecipe mr){
		if(recipes.contains(mr))
			recipes.remove(mr);
	}
	
	public static Inventory getCustomGui(String title, ItemStack middle, ItemStack craft1, ItemStack craft16, ItemStack craft32, ItemStack craft64){
		Inventory inv = Bukkit.createInventory(null, 54, StringUtils.makeColors(title) + StringUtils.convertToInvisibleString("%ssw"));
		inv.setItem(3, middle);inv.setItem(4, middle);inv.setItem(5, middle);
		inv.setItem(12, middle);inv.setItem(13, craft1);inv.setItem(14, middle);
		inv.setItem(21, middle);inv.setItem(22, craft16);inv.setItem(23, middle);
		inv.setItem(30, middle);inv.setItem(31, craft32);inv.setItem(32, middle);
		inv.setItem(39, middle);inv.setItem(40, craft64);inv.setItem(41, middle);
		inv.setItem(48, middle);inv.setItem(49, middle);inv.setItem(50, middle);
		return inv;
	}
	
	public boolean isCraftingGui(Inventory inv){
		String identifier = StringUtils.convertToInvisibleString("%ssw");
		if(inv != null && inv.getTitle() != null && inv.getTitle().length() >= identifier.length()
				&& inv.getTitle().substring(inv.getTitle().length() - identifier.length()).equalsIgnoreCase(identifier)){
			return true;
		}
		return false;
	}
	
	public static String makeColors(String s){
        String replaced = s
                .replaceAll("&0", "" + ChatColor.BLACK)
                .replaceAll("&1", "" + ChatColor.DARK_BLUE)
                .replaceAll("&2", "" + ChatColor.DARK_GREEN)
                .replaceAll("&3", "" + ChatColor.DARK_AQUA)
                .replaceAll("&4", "" + ChatColor.DARK_RED)
                .replaceAll("&5", "" + ChatColor.DARK_PURPLE)
                .replaceAll("&6", "" + ChatColor.GOLD)
                .replaceAll("&7", "" + ChatColor.GRAY)
                .replaceAll("&8", "" + ChatColor.DARK_GRAY)
                .replaceAll("&9", "" + ChatColor.BLUE)
                .replaceAll("&a", "" + ChatColor.GREEN)
                .replaceAll("&b", "" + ChatColor.AQUA)
                .replaceAll("&c", "" + ChatColor.RED)
                .replaceAll("&d", "" + ChatColor.LIGHT_PURPLE)
                .replaceAll("&e", "" + ChatColor.YELLOW)
                .replaceAll("&f", "" + ChatColor.WHITE)
                .replaceAll("&r", "" + ChatColor.RESET)
                .replaceAll("&l", "" + ChatColor.BOLD)
                .replaceAll("&o", "" + ChatColor.ITALIC)
                .replaceAll("&k", "" + ChatColor.MAGIC)
                .replaceAll("&m", "" + ChatColor.STRIKETHROUGH)
                .replaceAll("&n", "" + ChatColor.UNDERLINE)
                .replaceAll("\\\\", " ");
        return replaced;
    }
	
}
