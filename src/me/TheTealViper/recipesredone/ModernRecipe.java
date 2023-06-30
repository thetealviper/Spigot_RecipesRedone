package me.TheTealViper.recipesredone;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ModernRecipe {
	RecipesRedone MainPlugin = null;
	
	String[] recipe = new String[6];
	Map<Character, ItemStack> recipeMap = new HashMap<Character, ItemStack>();
	String[] rewards = new String[6];
	Map<Character, ItemStack> rewardsMap = new HashMap<Character, ItemStack>();
	String permission = "";
	public boolean showRecipeTutorial = true;
	public String category = "Default";
	
	public ModernRecipe(String[] recipe, Map<Character, ItemStack> recipeMap, String[] rewards, Map<Character, ItemStack> rewardsMap, String permission){
		this(recipe[0], recipe[1], recipe[2], recipe[3], recipe[4], recipe[5], recipeMap
				, rewards[0], rewards[1], rewards[2], rewards[3], rewards[4], rewards[5], rewardsMap, permission,  false);
	}
	public ModernRecipe(String recipeRow1, String recipeRow2, String recipeRow3, String recipeRow4, String recipeRow5, String recipeRow6
			, Map<Character, ItemStack> recipeMap
			, String rewardRow1, String rewardRow2, String rewardRow3, String rewardRow4, String rewardRow5, String rewardRow6
			, Map<Character, ItemStack> rewardsMap, String permission, boolean makeThisFalse){
		this.recipe = new String[] {recipeRow1, recipeRow2, recipeRow3, recipeRow4, recipeRow5, recipeRow6};
		this.recipeMap = recipeMap;
		this.rewards = new String[] {rewardRow1, rewardRow2, rewardRow3, rewardRow4, rewardRow5, rewardRow6};
		this.rewardsMap = rewardsMap;
		this.permission = permission;
		RecipesRedone.recipes.add(this);
		if(makeThisFalse)
			return;
		RecipesRedone.singledRecipes.add(this);
		ItemStack[][] data = new ItemStack[6][3];
		data[0][0] = recipeMap.get(recipe[0].charAt(0));data[0][1] = recipeMap.get(recipe[0].charAt(1));data[0][2] = recipeMap.get(recipe[0].charAt(2));
		data[1][0] = recipeMap.get(recipe[1].charAt(0));data[1][1] = recipeMap.get(recipe[1].charAt(1));data[1][2] = recipeMap.get(recipe[1].charAt(2));
		data[2][0] = recipeMap.get(recipe[2].charAt(0));data[2][1] = recipeMap.get(recipe[2].charAt(1));data[2][2] = recipeMap.get(recipe[2].charAt(2));
		data[3][0] = recipeMap.get(recipe[3].charAt(0));data[3][1] = recipeMap.get(recipe[3].charAt(1));data[3][2] = recipeMap.get(recipe[3].charAt(2));
		data[4][0] = recipeMap.get(recipe[4].charAt(0));data[4][1] = recipeMap.get(recipe[4].charAt(1));data[4][2] = recipeMap.get(recipe[4].charAt(2));
		data[5][0] = recipeMap.get(recipe[5].charAt(0));data[5][1] = recipeMap.get(recipe[5].charAt(1));data[5][2] = recipeMap.get(recipe[5].charAt(2));
		ItemStack[][] data1 = new ItemStack[6][3];
		copyarray(data, data1);
		if(attemptShiftRight(data1))
			addDataAsRecipeData(data1, rewards, rewardsMap, permission);
		if(attemptShiftRight(data1))
			addDataAsRecipeData(data1, rewards, rewardsMap, permission);
		if(attemptShiftDown(data)){
//					Bukkit.getLogger().info("made it 1");
			addDataAsRecipeData(data, rewards, rewardsMap, permission);
			data1 = new ItemStack[6][3];
			copyarray(data, data1);
			if(attemptShiftRight(data1))
				addDataAsRecipeData(data1, rewards, rewardsMap, permission);
			if(attemptShiftRight(data1))
				addDataAsRecipeData(data1, rewards, rewardsMap, permission);
			if(attemptShiftDown(data)){
//						Bukkit.getLogger().info("made it BLEH");
				addDataAsRecipeData(data, rewards, rewardsMap, permission);
				data1 = new ItemStack[6][3];
				copyarray(data, data1);
				if(attemptShiftRight(data1))
					addDataAsRecipeData(data1, rewards, rewardsMap, permission);
				if(attemptShiftRight(data1))
					addDataAsRecipeData(data1, rewards, rewardsMap, permission);
				if(attemptShiftDown(data)){
//					Bukkit.getLogger().info("made it BLEH");
					addDataAsRecipeData(data, rewards, rewardsMap, permission);
					data1 = new ItemStack[6][3];
					copyarray(data, data1);
					if(attemptShiftRight(data1))
						addDataAsRecipeData(data1, rewards, rewardsMap, permission);
					if(attemptShiftRight(data1))
						addDataAsRecipeData(data1, rewards, rewardsMap, permission);
					if(attemptShiftDown(data)){
//						Bukkit.getLogger().info("made it BLEH");
						addDataAsRecipeData(data, rewards, rewardsMap, permission);
						data1 = new ItemStack[6][3];
						copyarray(data, data1);
						if(attemptShiftRight(data1))
							addDataAsRecipeData(data1, rewards, rewardsMap, permission);
						if(attemptShiftRight(data1))
							addDataAsRecipeData(data1, rewards, rewardsMap, permission);
						if(attemptShiftDown(data)){
//							Bukkit.getLogger().info("made it BLEH");
							addDataAsRecipeData(data, rewards, rewardsMap, permission);
							data1 = new ItemStack[6][3];
							copyarray(data, data1);
							if(attemptShiftRight(data1))
								addDataAsRecipeData(data1, rewards, rewardsMap, permission);
							if(attemptShiftRight(data1))
								addDataAsRecipeData(data1, rewards, rewardsMap, permission);
						}
					}
				}
			}
		}
	}
	
	private void addDataAsRecipeData(ItemStack[][] data, String[] rewards, Map<Character, ItemStack> rewardsMap, String permission){
		Map<Character, ItemStack> recipeMap = new HashMap<Character, ItemStack>();
		recipeMap.put('1', data[0][0]);recipeMap.put('2', data[0][1]);recipeMap.put('3', data[0][2]);
		recipeMap.put('4', data[1][0]);recipeMap.put('5', data[1][1]);recipeMap.put('6', data[1][2]);
		recipeMap.put('7', data[2][0]);recipeMap.put('8', data[2][1]);recipeMap.put('9', data[2][2]);
		recipeMap.put('a', data[3][0]);recipeMap.put('b', data[3][1]);recipeMap.put('c', data[3][2]);
		recipeMap.put('d', data[4][0]);recipeMap.put('e', data[4][1]);recipeMap.put('f', data[4][2]);
		recipeMap.put('g', data[5][0]);recipeMap.put('h', data[5][1]);recipeMap.put('i', data[5][2]);
		
		new ModernRecipe("123", "456", "789", "abc", "def", "ghi", recipeMap, rewards[0], rewards[1], rewards[2], rewards[3], rewards[4], rewards[5], rewardsMap, permission, true);
	}
	
	private boolean attemptShiftRight(ItemStack[][] data){
//		Bukkit.getLogger().info(recipeAsString(ArrayToRecipe(null, data)) + " ->");
		ItemStack[][] clone = new ItemStack[6][3];
		copyarray(data, clone);
		for(int Row = 0;Row <= 5;Row++){
			if(clone[Row][2] != null)
				return false;
			else{
				clone[Row][2] = clone[Row][1];
				clone[Row][1] = clone[Row][0];
				clone[Row][0] = null;
			}
		}
		for(int Row = 0;Row <= 5;Row++){
			data[Row][2] = data[Row][1];
			data[Row][1] = data[Row][0];
			data[Row][0] = null;
		}
//		Bukkit.getLogger().info(recipeAsString(ArrayToRecipe(null, data)));
		return true;
	}
	
	private void copyarray(ItemStack[][] from, ItemStack[][] to){
		for(int row = 0;row < 6;row++){
			for(int col = 0;col < 3;col++){
				to[row][col] = from[row][col];
			}
		}
	}
	
	private boolean attemptShiftDown(ItemStack[][] data){
//		Bukkit.getLogger().info(recipeAsString(ArrayToRecipe(null, data)) + " -^");
		ItemStack[][] clone = new ItemStack[6][3];
		copyarray(data, clone);
		for(int Col = 0;Col <= 2;Col++){
			if(clone[5][Col] != null)
				return false;
			else{
				clone[5][Col] = clone[4][Col];
				clone[4][Col] = clone[3][Col];
				clone[3][Col] = clone[2][Col];
				clone[2][Col] = clone[1][Col];
				clone[1][Col] = clone[0][Col];
				clone[0][Col] = null;
			}
		}
		for(int Col = 0;Col <= 2;Col++){
			data[5][Col] = data[4][Col];
			data[4][Col] = data[3][Col];
			data[3][Col] = data[2][Col];
			data[2][Col] = data[1][Col];
			data[1][Col] = data[0][Col];
			data[0][Col] = null;
		}
//		Bukkit.getLogger().info(recipeAsString(ArrayToRecipe(null, data)));
		return true;
	}
	
	public ItemStack[] getDataArray(){
		ItemStack[] dataArray = new ItemStack[36];
		int index = 0;
		for(String recipeRow : recipe){
			for(char recipeIdentifier : recipeRow.toCharArray()){
				if(recipeMap.containsKey(recipeIdentifier)){
					dataArray[index] = recipeMap.get(recipeIdentifier);
					index++;
				}else{
					Bukkit.getLogger().severe("RECIPE FORMATTED INCORRECTLY. ABORTING.");
					return null;
				}
			}
		}
		for(String rewardRow : rewards){
			for(char rewardIdentifier : rewardRow.toCharArray()){
				if(rewardsMap.containsKey(rewardIdentifier)){
					dataArray[index] = rewardsMap.get(rewardIdentifier);
					index++;
				}else{
					Bukkit.getLogger().severe("RECIPE FORMATTED INCORRECTLY. ABORTING.");
					return null;
				}
			}
		}
		return dataArray;
	}
	
	public boolean checkRecipe(Inventory inv){
		ItemStack[] data = getDataArray();
		if(checkItem(inv.getItem(0), data[0])
				&& checkItem(inv.getItem(1), data[1])
				&& checkItem(inv.getItem(2), data[2])
				&& checkItem(inv.getItem(9), data[3])
				&& checkItem(inv.getItem(10), data[4])
				&& checkItem(inv.getItem(11), data[5])
				&& checkItem(inv.getItem(18), data[6])
				&& checkItem(inv.getItem(19), data[7])
				&& checkItem(inv.getItem(20), data[8])
				&& checkItem(inv.getItem(27), data[9])
				&& checkItem(inv.getItem(28), data[10])
				&& checkItem(inv.getItem(29), data[11])
				&& checkItem(inv.getItem(36), data[12])
				&& checkItem(inv.getItem(37), data[13])
				&& checkItem(inv.getItem(38), data[14])
				&& checkItem(inv.getItem(45), data[15])
				&& checkItem(inv.getItem(46), data[16])
				&& checkItem(inv.getItem(47), data[17])){
			return true;
		}
		return false;
	}
	
	public void loadRewards(Inventory inv){
		ItemStack[] data = getDataArray();
		if(data[0+18] != null)
			inv.setItem(6, data[0+18]);
		if(data[1+18] != null)
			inv.setItem(7, data[1+18]);
		if(data[2+18] != null)
			inv.setItem(8, data[2+18]);
		if(data[3+18] != null)
			inv.setItem(15, data[3+18]);
		if(data[4+18] != null)
			inv.setItem(16, data[4+18]);
		if(data[5+18] != null)
			inv.setItem(17, data[5+18]);
		if(data[6+18] != null)
			inv.setItem(24, data[6+18]);
		if(data[7+18] != null)
			inv.setItem(25, data[7+18]);
		if(data[8+18] != null)
			inv.setItem(26, data[8+18]);
		if(data[9+18] != null)
			inv.setItem(33, data[9+18]);
		if(data[10+18] != null)
			inv.setItem(34, data[10+18]);
		if(data[11+18] != null)
			inv.setItem(35, data[11+18]);
		if(data[12+18] != null)
			inv.setItem(42, data[12+18]);
		if(data[13+18] != null)
			inv.setItem(43, data[13+18]);
		if(data[14+18] != null)
			inv.setItem(44, data[14+18]);
		if(data[15+18] != null)
			inv.setItem(51, data[15+18]);
		if(data[16+18] != null)
			inv.setItem(52, data[16+18]);
		if(data[17+18] != null)
			inv.setItem(53, data[17+18]);
			
	}
	
	public boolean checkItem(ItemStack source, ItemStack compareTo){
		if((source == null && compareTo != null) || (source != null && compareTo == null)){
			return false;
		}else if((source != null && compareTo != null) && (!source.isSimilar(compareTo))){
			return false;
		}else if(source == null && compareTo == null)
			return true;
		else if(source.getAmount() != compareTo.getAmount())
			return false;
		return true;
	}
}
