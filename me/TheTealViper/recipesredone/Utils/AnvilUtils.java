package me.TheTealViper.recipesredone.Utils;

public class AnvilUtils {
//Item Renaming
//	@EventHandler(priority = EventPriority.MONITOR)
//	public void onInventoryClick(InventoryClickEvent e){
//		// check if the event has been cancelled by another plugin
//		if(!e.isCancelled()){
//			HumanEntity ent = e.getWhoClicked();
//			 
//			// not really necessary
//			if(ent instanceof Player){
//				Player player = (Player)ent;
//				Inventory inv = e.getInventory();
//				 
//				// see if the event is about an anvil
//				if(inv instanceof AnvilInventory){
//					InventoryView view = e.getView();
//					int rawSlot = e.getRawSlot();
//					 
//					// compare the raw slot with the inventory view to make sure we are talking about the upper inventory
//					if(rawSlot == view.convertSlot(rawSlot)){
//						/*
//						slot 0 = left item slot
//						slot 1 = right item slot
//						slot 2 = result item slot
//						 
//						see if the player clicked in the result item slot of the anvil inventory
//						*/
//						if(rawSlot == 2){
//							/*
//							get the current item in the result slot
//							I think inv.getItem(rawSlot) would be possible too
//							*/
//							ItemStack item = e.getCurrentItem();
//							 
//							// check if there is an item in the result slot
//							if(item != null){
//								ItemMeta meta = item.getItemMeta();
//								 
//								// it is possible that the item does not have meta data
//								if(meta != null){
//									// see whether the item is beeing renamed
//									if(meta.hasDisplayName()){
//										String displayName = meta.getDisplayName();
//										 
//										// do something
//									}
//								}
//							}
//						}
//					}
//				}
//			}
//		}
//	}
	
	
//Item Repairing
//	@EventHandler
//	public static void onInventoryClick(InventoryClickEvent e){
//	    // check whether the event has been cancelled by another plugin
//	if(!e.isCancelled()){
//	HumanEntity ent = e.getWhoClicked();
//	 
//	// not really necessary
//	if(ent instanceof Player){
//	Player player = (Player)ent;
//	Inventory inv = e.getInventory();
//	 
//	// see if we are talking about an anvil here
//	if(inv instanceof AnvilInventory){
//	AnvilInventory anvil = (AnvilInventory)inv;
//	InventoryView view = e.getView();
//	int rawSlot = e.getRawSlot();
//	 
//	// compare raw slot to the inventory view to make sure we are in the upper inventory
//	if(rawSlot == view.convertSlot(rawSlot)){
//	  // 2 = result slot
//	if(rawSlot == 2){
//	  // all three items in the anvil inventory
//	ItemStack[] items = anvil.getContents();
//	 
//	// item in the left slot
//	ItemStack item1 = items[0];
//	 
//	// item in the right slot
//	ItemStack item2 = items[1];
//	 
//	// I do not know if this is necessary
//	if(item1 != null && item2 != null){
//	int id1 = item1.getTypeId();
//	int id2 = item2.getTypeId();
//	 
//	// if the player is repairing something the ids will be the same
//	if(id1 != 0 && id1 == id2){
//	  // item in the result slot
//	ItemStack item3 = e.getCurrentItem();
//	 
//	// check if there is an item in the result slot
//	if(item3 != null){
//	ItemMeta meta = item3.getItemMeta();
//	 
//	// meta data could be null
//	if(meta != null){
//	  // get the repairable interface to obtain the repair cost
//	if(meta instanceof Repairable){
//	Repairable repairable = (Repairable)meta;
//	int repairCost = repairable.getRepairCost();
//	 
//	// can the player afford to repair the item
//	if(player.getLevel() >= repairCost){
//	// success
//	}else{
//	// bugger
//	}
//	}
//	}
//	}
//	}
//	}
//	}
//	}
//	}
//	}
//	}
//	}
}
