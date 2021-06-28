package client.module.render;


import client.module.Category;
import client.module.Module;
import net.minecraft.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class NoRender extends Module {
	public List<Entity> glowed = new ArrayList<>();

	public NoRender() {
		super("NoRender", 0, Category.RENDER);
	}
	
	@Override
	public void onUpdate() {
		
		super.onUpdate();
	}

	@Override
	public void onDisable() {
		
		super.onDisable();
	}
	
}
