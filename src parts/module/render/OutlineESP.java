package client.module.render;

import client.Client;
import client.Hero.settings.Setting;
import client.event.EventTarget;
import client.event.events.Event2D;
import client.module.Category;
import client.module.Module;
import client.values.NumberValue;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import org.lwjgl.opengl.GL11;

public class OutlineESP extends Module {
    public static NumberValue
            Outline;

    public OutlineESP() {
        super("OutlineESP", 0, Category.RENDER);
      //  this.addValue(this.Outline = new NumberValue("OutlineScale", 3.0, 1.0, 6.0));

    }
    @Override
    public void setup() {
        Client.instance.settingsManager.rSetting(new Setting("Existed", this, 3.0, 1.0, 6.0, true));

    }
    @EventTarget
    public void eq(Event2D event2D) {

    }
}

