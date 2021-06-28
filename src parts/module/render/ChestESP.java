package client.module.render;


import client.Client;
import client.Hero.settings.Setting;
import client.event.EventTarget;
import client.event.events.Event3D;
import client.module.Category;
import client.module.Module;
import client.utilites.ColorUtil;
import client.values.BooleanValue;
import client.values.NumberValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Objects;

public class ChestESP extends Module {
    static Minecraft mc = Minecraft.getMinecraft();

    public ChestESP() {
        super("ChestESP", Keyboard.KEY_O, Category.RENDER);
//        this.addValue(this.hue = new NumberValue("Color", 1, 0.0, 1.0));


    }
    @Override
    public void setup() {
        Client.instance.settingsManager.rSetting(new Setting("Hue", this, 1, 0.0, 1.0, true));

    }
    @EventTarget
    public void onEvent(Event3D e) {
            for (TileEntity tileEntity : mc.theWorld.loadedTileEntityList) {
                if (!(tileEntity instanceof TileEntityChest))
                    continue;
                Color c = Color.getHSBColor((float) Client.instance.settingsManager.getSettingByName("Hue").getValDouble(), 1.0F, 1.0F);
                float red = c.getRed() / 255.0F;
                float green = c.getGreen() / 255.0F;
                float blue = c.getBlue() / 255.0F;
                chestESPBox(tileEntity, 0, c);
            }
        }



    public static void chestESPBox(TileEntity entity, int mode, Color color) {
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(2);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);


        double x = entity.getPos().getX();
        double y = entity.getPos().getY();
        double z = entity.getPos().getZ();
        GlStateManager.translate(x - mc.getRenderManager().renderPosX, y - mc.getRenderManager().renderPosY, z - mc.getRenderManager().renderPosZ);
        GlStateManager.translate(-(x - mc.getRenderManager().renderPosX), -(y - mc.getRenderManager().renderPosY), -(z - mc.getRenderManager().renderPosZ));
        GL11.glEnable(GL11.GL_LINE_SMOOTH);


        GlStateManager.color(Objects.requireNonNull(color).getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f);
        RenderGlobal.func_181561_a(
                new AxisAlignedBB(
                        x - 1 / 2 + 0.05 - x + (x - mc.getRenderManager().renderPosX),
                        y - y + (y - mc.getRenderManager().renderPosY),
                        z - 1 / 2 + 0.05 - z + (z - mc.getRenderManager().renderPosZ),
                        x + 1 - 0.05 - x + (x - mc.getRenderManager().renderPosX),
                        y + 1 - 0.05 - y + (y - mc.getRenderManager().renderPosY),
                        z + 1 - 0.05 - z + (z - mc.getRenderManager().renderPosZ)
                ));
        GlStateManager.translate(x - mc.getRenderManager().renderPosX, y - mc.getRenderManager().renderPosY, z - mc.getRenderManager().renderPosZ);
        GlStateManager.translate(-(x - mc.getRenderManager().renderPosX), -(y - mc.getRenderManager().renderPosY), -(z - mc.getRenderManager().renderPosZ));
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
    }
    }


