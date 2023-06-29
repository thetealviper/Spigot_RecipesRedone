package me.TheTealViper.recipesredone;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class RecipeRedoneCraftEvent extends Event{
	private static final HandlerList handlers = new HandlerList();
	private Player player;
	private Inventory inventory;
	private ItemStack[] itemsToCraft;
	private int craftMultiplier;
	private boolean isCancelled = false;
	
	public RecipeRedoneCraftEvent(Player player, Inventory inventory, ItemStack[] itemsToCraft, int craftMultiplier){
		this.player = player;
		this.inventory = inventory;
		this.itemsToCraft = itemsToCraft;
		this.craftMultiplier = craftMultiplier;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public Inventory getInventory(){
		return inventory;
	}
	
	public ItemStack[] getItemsToCraft(){
		return itemsToCraft;
	}
	
	public int getCraftMultiplier(){
		return craftMultiplier;
	}
	
	public void setCraftMultiplier(int craftMultiplier){
		this.craftMultiplier = craftMultiplier;
	}
	
	public boolean isCancelled(){
		return isCancelled;
	}
	
	public void setCancelled(boolean isCancelled){
		this.isCancelled = isCancelled;
	}

	public HandlerList getHandlers() {
	    return handlers;
	}

	public static HandlerList getHandlerList() {
	    return handlers;
	}
}
