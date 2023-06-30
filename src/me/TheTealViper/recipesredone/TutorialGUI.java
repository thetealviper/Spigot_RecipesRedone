package me.TheTealViper.recipesredone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.TheTealViper.recipesredone.util.StringUtils;

public class TutorialGUI implements Listener{
	static RecipesRedone plugin = null;
	static String MAINGUIID = StringUtils.convertToInvisibleString("%ssz")
			,CATEGORYGUIID = StringUtils.convertToInvisibleString("%ssy")
			,RECIPEGUIID = StringUtils.convertToInvisibleString("%ssx");
	static int TOTALMAINPAGES;
	static Map<String, List<ModernRecipe>> recipeTutorials = new HashMap<String, List<ModernRecipe>>();
	static List<String> categoryOrganized = new ArrayList<String>();
	static ItemStack FILLER, MAINHELPER, CATEGORYHELPER, NEXTPAGE, currentPage, PREVIOUSPAGE, BACK;
	
	public static void loadRecipeTutorials(List<ModernRecipe> recipes, RecipesRedone plugin){
		TutorialGUI.plugin = plugin;
		FILLER = plugin.getLoadItemstackFromConfig().getItem(plugin.getConfig().getConfigurationSection("Tutorial_Table_Filler"));
		MAINHELPER = plugin.getLoadItemstackFromConfig().getItem(plugin.getConfig().getConfigurationSection("Tutorial_Table_Main_Helper"));
		CATEGORYHELPER = plugin.getLoadItemstackFromConfig().getItem(plugin.getConfig().getConfigurationSection("Tutorial_Table_Category_Helper"));
		NEXTPAGE = plugin.getLoadItemstackFromConfig().getItem(plugin.getConfig().getConfigurationSection("Tutorial_Table_Next_Page"));
		currentPage = plugin.getLoadItemstackFromConfig().getItem(plugin.getConfig().getConfigurationSection("Tutorial_Table_Current_Page"));
		PREVIOUSPAGE = plugin.getLoadItemstackFromConfig().getItem(plugin.getConfig().getConfigurationSection("Tutorial_Table_Previous_Page"));
		BACK = plugin.getLoadItemstackFromConfig().getItem(plugin.getConfig().getConfigurationSection("Tutorial_Table_Back"));
		for(ModernRecipe mr : recipes){
			if(mr.showRecipeTutorial){
				if(!recipeTutorials.containsKey(mr.category))
					recipeTutorials.put(mr.category, new ArrayList<ModernRecipe>());
				List<ModernRecipe> categoryRecipes = recipeTutorials.get(mr.category);
				categoryRecipes.add(mr);
				recipeTutorials.put(mr.category, categoryRecipes);
			}
		}
		
		String[] categories = new String[recipeTutorials.size()];
		int index = 0;
		for(String s : recipeTutorials.keySet()){
			categories[index] = s;
			index++;
		}
		Arrays.sort(categories);
		for(String s : categories)
			categoryOrganized.add(s);
		
		TOTALMAINPAGES = recipeTutorials.size() / 36;
		if(recipeTutorials.size() % 36 != 0)
			TOTALMAINPAGES++;
	}
	
	public static void reloadRecipeTutorials(List<ModernRecipe> recipes, RecipesRedone plugin){
		recipeTutorials = new HashMap<String, List<ModernRecipe>>();
		categoryOrganized = new ArrayList<String>();
		loadRecipeTutorials(recipes, plugin);
	}
	
	public static void openMainGUI(Player p, int page, int totalPages){
		Inventory inv = Bukkit.createInventory(null, 54, StringUtils.makeColors(plugin.getConfig().getString("Tutorial_Table_Name")) + MAINGUIID);
		ItemStack currentPage = TutorialGUI.currentPage.clone();
		ItemMeta meta = currentPage.getItemMeta();
		meta.setDisplayName(meta.getDisplayName().replace("%#%", page + ""));
		currentPage.setItemMeta(meta);
		for(int i = 36;i < 45;i++){
			inv.setItem(i, FILLER);
		}
		inv.setItem(45, BACK);
		inv.setItem(46, FILLER);
		inv.setItem(47, FILLER);
		if(page > 1)
			inv.setItem(48, PREVIOUSPAGE);
		else
			inv.setItem(48, FILLER);
		inv.setItem(49, currentPage);
		if(page != totalPages)
			inv.setItem(50, NEXTPAGE);
		else
			inv.setItem(50, FILLER);
		inv.setItem(51, FILLER);
		inv.setItem(52, FILLER);
		inv.setItem(53, MAINHELPER);
		//BASE INVENTORY SET
		for(int i = 0;i < 36;i++){
			int actualIndex = i + ((page - 1) * 36);
			if(categoryOrganized.size() <= actualIndex)
				continue;
			String category = categoryOrganized.get(actualIndex);
			ItemStack categoryItem = new ItemStack(Material.STONE_BUTTON);
			ItemMeta categoryMeta = Bukkit.getItemFactory().getItemMeta(Material.STICK);
			categoryMeta.setDisplayName(category);
			List<String> categoryLore = new ArrayList<String>();
			categoryLore.add(ChatColor.RESET + "" + recipeTutorials.get(category).size() + " Recipes Inside");
			categoryMeta.setLore(categoryLore);
			categoryItem.setItemMeta(categoryMeta);
			inv.setItem(i, categoryItem);
		}
		//ALL INVENTORY SET
		p.openInventory(inv);
	}
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e){
		if(e.getClickedInventory() != null && e.getClickedInventory().equals(e.getInventory()) && e.getView().getTitle().contains(MAINGUIID)){
			e.setCancelled(true);
			ItemStack clicked = e.getCurrentItem();
			//Get current page info here
			String[] info = plugin.getConfig().getString("Tutorial_Table_Current_Page.name").split("(%#%)");
			String currentPage_String = e.getInventory().getItem(49).getItemMeta().getDisplayName()
					.replace(plugin.getConfig().getString("Tutorial_Table_Current_Page.name").split("(%#%)")[0], "");
			if(info.length == 2)
					currentPage_String = currentPage_String.replace(plugin.getConfig().getString("Tutorial_Table_Current_Page.name").split("(%#%)")[1], "");
			int currentPage = Integer.valueOf(currentPage_String);
			//
			if(clicked == null || clicked.getType().equals(Material.AIR))
				return;
			else if(clicked.isSimilar(MAINHELPER))
				return;
			else if(clicked.isSimilar(NEXTPAGE)){
				openMainGUI((Player) e.getWhoClicked(), currentPage++, TOTALMAINPAGES);
				if(RecipesRedone.playSound)
					GuiClick.playClick((Player) e.getWhoClicked(), 1);
			}else if(e.getRawSlot() == 49)
				return; // If clicked currentPage
			else if(clicked.isSimilar(PREVIOUSPAGE)){
				openMainGUI((Player) e.getWhoClicked(), currentPage--, TOTALMAINPAGES);
				if(RecipesRedone.playSound)
					GuiClick.playClick((Player) e.getWhoClicked(), 1);
			}else if(clicked.isSimilar(BACK)){
				e.getWhoClicked().closeInventory();
				if(RecipesRedone.playSound)
					GuiClick.playClick((Player) e.getWhoClicked(), 1);
			}else if(clicked.isSimilar(FILLER))
				return;
			else{
				//Now we know they are clicking a category
				String category = clicked.getItemMeta().getDisplayName();
				int totalPages = recipeTutorials.get(category).size() / 36;
				if(recipeTutorials.get(category).size() % 36 != 0)
					totalPages++;
				openCategoryGUI(category, (Player) e.getWhoClicked(), 1, totalPages);
				if(RecipesRedone.playSound)
					GuiClick.playClick((Player) e.getWhoClicked(), 1);
			}
		}else if(e.getClickedInventory() != null && e.getClickedInventory().equals(e.getInventory()) && e.getView().getTitle().contains(CATEGORYGUIID)){
			e.setCancelled(true);
			String category = e.getView().getTitle().replace(CATEGORYGUIID, "");
			int totalPages = recipeTutorials.get(category).size() / 36;
			if(recipeTutorials.get(category).size() % 36 != 0)
				totalPages++;
			ItemStack clicked = e.getCurrentItem();
			//Get current page info here
			String[] info = plugin.getConfig().getString("Tutorial_Table_Current_Page.name").split("(%#%)");
			String currentPage_String = e.getInventory().getItem(49).getItemMeta().getDisplayName()
					.replace(plugin.getConfig().getString("Tutorial_Table_Current_Page.name").split("(%#%)")[0], "");
			if(info.length == 2)
					currentPage_String = currentPage_String.replace(plugin.getConfig().getString("Tutorial_Table_Current_Page.name").split("(%#%)")[1], "");
			int currentPage = Integer.valueOf(currentPage_String);
			//
			if(clicked == null || clicked.getType().equals(Material.AIR))
				return;
			else if(clicked.isSimilar(CATEGORYHELPER))
				return;
			else if(clicked.isSimilar(NEXTPAGE)){
				openCategoryGUI(category, (Player) e.getWhoClicked(), currentPage++, totalPages);
				if(RecipesRedone.playSound)
					GuiClick.playClick((Player) e.getWhoClicked(), 1);
			}else if(e.getRawSlot() == 49)
				return; // If clicked currentPage
			else if(clicked.isSimilar(PREVIOUSPAGE)){
				openCategoryGUI(category, (Player) e.getWhoClicked(), currentPage--, totalPages);
				if(RecipesRedone.playSound)
					GuiClick.playClick((Player) e.getWhoClicked(), 1);
			}else if(clicked.isSimilar(BACK)){
				openMainGUI((Player) e.getWhoClicked(), 1, TOTALMAINPAGES);
				if(RecipesRedone.playSound)
					GuiClick.playClick((Player) e.getWhoClicked(), 1);
			}else if(clicked.isSimilar(FILLER))
				return;
			else{
				//Now we know they are clicking a recipe
				int recipeNumber = (Integer.valueOf(clicked.getItemMeta().getDisplayName().replace("Recipe ", "")) - 1);
				ModernRecipe mr = recipeTutorials.get(category).get(recipeNumber);
				totalPages = recipeTutorials.get(category).size() / 36;
				if(recipeTutorials.get(category).size() % 36 != 0)
					totalPages++;
				openRecipeGUI(category, (Player) e.getWhoClicked(), mr, recipeNumber + 1);
				if(RecipesRedone.playSound)
					GuiClick.playClick((Player) e.getWhoClicked(), 1);
			}
		}else if(e.getClickedInventory() != null && e.getClickedInventory().equals(e.getInventory()) && e.getView().getTitle().contains(RECIPEGUIID)){
			e.setCancelled(true);
			String category = e.getView().getTitle().replace(RECIPEGUIID, "");
			int totalPages = recipeTutorials.get(category).size();
			ItemStack clicked = e.getCurrentItem();
			//Get current page info here
			String[] info = plugin.getConfig().getString("Tutorial_Table_Current_Page.name").split("(%#%)");
			String currentPage_String = e.getInventory().getItem(31).getItemMeta().getDisplayName()
					.replace(plugin.getConfig().getString("Tutorial_Table_Current_Page.name").split("(%#%)")[0], "");
			if(info.length == 2)
					currentPage_String = currentPage_String.replace(plugin.getConfig().getString("Tutorial_Table_Current_Page.name").split("(%#%)")[1], "");
			int currentPage = Integer.valueOf(currentPage_String);
			//
			if(clicked == null || clicked.getType().equals(Material.AIR))
				return;
			else if(clicked.isSimilar(NEXTPAGE)){
				openRecipeGUI(category, (Player) e.getWhoClicked(), recipeTutorials.get(category).get(currentPage), currentPage + 1);
				if(RecipesRedone.playSound)
					GuiClick.playClick((Player) e.getWhoClicked(), 1);
			}else if(e.getRawSlot() == 49)
				return; // If clicked currentPage
			else if(clicked.isSimilar(PREVIOUSPAGE)){
				openRecipeGUI(category, (Player) e.getWhoClicked(), recipeTutorials.get(category).get(currentPage - 2), currentPage - 1);
				if(RecipesRedone.playSound)
					GuiClick.playClick((Player) e.getWhoClicked(), 1);
			}else if(clicked.isSimilar(BACK)){
				totalPages = recipeTutorials.get(category).size() / 36;
				if(recipeTutorials.get(category).size() % 36 != 0)
					totalPages++;
				openCategoryGUI(category, (Player) e.getWhoClicked(), 1, totalPages);
				if(RecipesRedone.playSound)
					GuiClick.playClick((Player) e.getWhoClicked(), 1);
			}else if(clicked.isSimilar(FILLER))
				return;
			else{
				return;
			}
		}
	}
	
	public void openCategoryGUI(String category, Player p, int page, int totalPages){
		Inventory inv = Bukkit.createInventory(null, 54, category + CATEGORYGUIID);
		ItemStack currentPage = TutorialGUI.currentPage.clone();
		ItemMeta meta = currentPage.getItemMeta();
		meta.setDisplayName(meta.getDisplayName().replace("%#%", page + ""));
		currentPage.setItemMeta(meta);
		for(int i = 36;i < 45;i++){
			inv.setItem(i, FILLER);
		}
		inv.setItem(45, BACK);
		inv.setItem(46, FILLER);
		inv.setItem(47, FILLER);
		if(page > 1)
			inv.setItem(48, PREVIOUSPAGE);
		else
			inv.setItem(48, FILLER);
		inv.setItem(49, currentPage);
		if(page != totalPages)
			inv.setItem(50, NEXTPAGE);
		else
			inv.setItem(50, FILLER);
		inv.setItem(51, FILLER);
		inv.setItem(52, FILLER);
		inv.setItem(53, CATEGORYHELPER);
		//BASE INVENTORY SET
		for(int i = 0;i < 36;i++){
			int actualIndex = i + ((page - 1) * 36);
			if(recipeTutorials.get(category).size() <= actualIndex)
				continue;
			ModernRecipe mr = recipeTutorials.get(category).get(actualIndex);
			inv.setItem(i, getRecipeRepresentation(mr, actualIndex));
		}
		p.openInventory(inv);
	}
	
	private ItemStack getRecipeRepresentation(ModernRecipe mr, int recipeNumber){
		ItemStack item = new ItemStack(Material.STONE_BUTTON);
		ItemMeta meta = Bukkit.getItemFactory().getItemMeta(Material.STICK);
		meta.setDisplayName("Recipe " + (recipeNumber + 1));
		//Magic happens here
		List<String> lore = new ArrayList<String>();
		for(ItemStack i : mr.rewardsMap.values()){
			if(i == null)
				continue;
			if(i.hasItemMeta() && i.getItemMeta().hasDisplayName())
				lore.add(i.getAmount() + "x " + i.getItemMeta().getDisplayName());
			else
				lore.add(i.getAmount() + "x " + i.getType().name());
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public void openRecipeGUI(String category, Player p, ModernRecipe mr, int recipeNumber){
		Inventory inv = Bukkit.createInventory(null, 54, category + RECIPEGUIID);
		ItemStack currentPage = TutorialGUI.currentPage.clone();
		ItemMeta meta = currentPage.getItemMeta();
		meta.setDisplayName(meta.getDisplayName().replace("%#%", recipeNumber + ""));
		currentPage.setItemMeta(meta);
		int totalPages = recipeTutorials.get(category).size();
		for(int row = 0;row < 6;row++){
			for(int col = 3;col < 6;col++){
				inv.setItem(col + (row * 9), FILLER);
			}
		}
		inv.setItem(13, BACK);
		if(recipeNumber > 1)
			inv.setItem(22, PREVIOUSPAGE);
		inv.setItem(31, currentPage);
		if(recipeNumber != totalPages)
			inv.setItem(40, NEXTPAGE);
		//BASE INVENTORY SET
		mr.loadRewards(inv);
		ItemStack[] data = mr.getDataArray();
		if(data[0] != null)
			inv.setItem(0, data[0]);
		if(data[1] != null)
			inv.setItem(1, data[1]);
		if(data[2] != null)
			inv.setItem(2, data[2]);
		if(data[3] != null)
			inv.setItem(9, data[3]);
		if(data[4] != null)
			inv.setItem(10, data[4]);
		if(data[5] != null)
			inv.setItem(11, data[5]);
		if(data[6] != null)
			inv.setItem(18, data[6]);
		if(data[7] != null)
			inv.setItem(19, data[7]);
		if(data[8] != null)
			inv.setItem(20, data[8]);
		if(data[9] != null)
			inv.setItem(27, data[9]);
		if(data[10] != null)
			inv.setItem(28, data[10]);
		if(data[11] != null)
			inv.setItem(29, data[11]);
		if(data[12] != null)
			inv.setItem(36, data[12]);
		if(data[13] != null)
			inv.setItem(37, data[13]);
		if(data[14] != null)
			inv.setItem(38, data[14]);
		if(data[15] != null)
			inv.setItem(45, data[15]);
		if(data[16] != null)
			inv.setItem(46, data[16]);
		if(data[17] != null)
			inv.setItem(47, data[17]);
		p.openInventory(inv);
	}
	
}
