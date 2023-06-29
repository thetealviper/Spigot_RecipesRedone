package me.TheTealViper.recipesredone;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.TheTealViper.recipesredone.Utils.ItemCreator;
import me.TheTealViper.recipesredone.Utils.PluginFile;
import me.TheTealViper.recipesredone.Utils.StringUtils;

public class RecipeCreator implements Listener{
	private RecipesRedone plugin = null;
	private Player p = null;
	private Inventory inv = null;
	
	private boolean namingRecipe = false;
	private String[] iString = new String[] {"123","456","789","abc","def","ghi"};
	private Map<Character, ItemStack> iMap = new HashMap<Character, ItemStack>();
	private Map<Character, ItemStack> oMap = new HashMap<Character, ItemStack>();
	private PluginFile pf = null;
	
	private static ItemStack FILLER = null, HELPER = null, CREATE = null;
	private static String CREATORIDENTIFIER = StringUtils.encodeString("%RR4");
	
	public RecipeCreator(RecipesRedone plugin, Player p){
		this.plugin = plugin;
		this.p = p;
		if(FILLER == null){
			FILLER = ItemCreator.createItemFromConfiguration(plugin.getConfig().getConfigurationSection("Recipe_Creator_Filler"));
			HELPER = ItemCreator.createItemFromConfiguration(plugin.getConfig().getConfigurationSection("Recipe_Creator_Helper"));
			CREATE = ItemCreator.createItemFromConfiguration(plugin.getConfig().getConfigurationSection("Recipe_Creator_Create"));
		}
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	public void show(){
		inv = Bukkit.createInventory(null, 54, "Recipe Creator" + CREATORIDENTIFIER);
		for(int row = 0;row < 6;row++){
			for(int col = 3;col < 6;col++){
				inv.setItem(col + (row * 9), FILLER);
			}
		}
		inv.setItem(13, CREATE);
		inv.setItem(40, HELPER);
		p.openInventory(inv);
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent e){
		if(e.getInventory().equals(inv)){
			for(int row = 0;row < 6;row++){
				for(int col = 0;col < 3;col++){
					ItemStack item = inv.getItem(col + (row * 9));
					if(item != null)
						e.getPlayer().getInventory().addItem(item);
				}
			}
		}
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e){
		if(e.getClickedInventory().equals(e.getInventory())){
			int slot = e.getRawSlot();
			if(slot == 3|| slot == 4|| slot == 5||
					slot == 12|| slot == 13|| slot == 14||
					slot == 21|| slot == 30|| slot == 31||
					slot == 30|| slot == 31|| slot == 32||
					slot == 39|| slot == 40|| slot == 41||
					slot == 48|| slot == 49|| slot == 50)
				e.setCancelled(true);
			if(e.getCurrentItem().isSimilar(CREATE)){
				int fileNumber = 1;
				File file = new File("plugins/RecipesRedone/recipes/In Game Recipe " + fileNumber + ".yml");
				while(file.exists()){
					fileNumber++;
					file = new File("plugins/RecipesRedone/recipes/In Game Recipe " + fileNumber + ".yml");
				}
				pf = new PluginFile(plugin, "recipes/In Game Recipe " + fileNumber + ".yml");
				pf.set("permission", "");
				pf.set("ingame", true);
				String defaultString = "123456789abcdefghi";
				for(int row = 0;row < 6;row++){
					for(int col = 0;col < 3;col++){
						int index = col + (row * 3);
						ItemStack item = inv.getItem(col + (row * 9));
						iMap.put(defaultString.charAt(index), item);
						pf.set("recipe." + index, item);
					}
				}
				for(int row = 0;row < 6;row++){
					for(int col = 6;col < 9;col++){
						int index = (col - 6) + (row * 3);
						ItemStack item = inv.getItem(col + (row * 9));
						oMap.put(defaultString.charAt(index), item);
						pf.set("reward." + index, item);
					}
				}
				pf.save();
				p.closeInventory();
				p.sendMessage("Please send the recipe category name as a chat message. All recipes are put into 'Default' by default so if you have no preference type 'Default'. 'cancel' to cancel.");
				namingRecipe = true;
			}
		}
	}
	
	@EventHandler
	public void onChat(PlayerChatEvent e){
		if(namingRecipe){
			e.setCancelled(true);
			if(e.getMessage().equalsIgnoreCase("cancel")){
				p.sendMessage("Successfully cancelled.");
				for(ItemStack i : iMap.values())
					if(i != null)
						p.getInventory().addItem(i);
			}else{
				String categoryName = StringUtils.makeColors(e.getMessage());
				pf.set("category", categoryName);
				pf.save();
				ModernRecipe mr = new ModernRecipe(iString, iMap, iString, oMap, "");
				mr.category = categoryName;
				p.sendMessage("Successfully set category!");
				plugin.reloadRecipes();
			}
			namingRecipe = false;
		}
	}
}
