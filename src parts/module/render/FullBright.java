package client.module.render;


import client.module.Category;
import client.module.Module;

public class FullBright extends Module {

	public FullBright() {
		super("FullBright", 0, Category.RENDER);
	}

	@Override
	public void onEnable() {
		mc.gameSettings.gammaSetting = 100;
		super.onEnable();
	}

	@Override
	public void onDisable() {
		mc.gameSettings.gammaSetting = 1;
		super.onDisable();
	}
	
}
