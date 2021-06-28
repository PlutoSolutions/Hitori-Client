package client.module.render;

import java.awt.*;
import java.util.Comparator;


import client.Client;
import client.Hero.settings.Setting;
import client.event.EventTarget;
import client.event.events.Event2D;
import client.event.events.Event3D;
import client.font.Fonts;
import client.font.Fonts4;
import client.module.Category;
import client.module.Module;
import client.module.combat.KillAura;
import client.utilites.ColorUtil;
import client.utilites.Render;
import client.utilites.Wrapper;
import client.utils.client.Rainbow;
import client.values.BooleanValue;
import client.values.NumberValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

public class Hud extends  Module {


	public Minecraft mc = Minecraft.getMinecraft();

	public Hud() {
		super("Hud", 0, Category.RENDER);
//		this.addOption(this.Hotbar = new BooleanValue("HotBar", true));
//		this.addOption(this.Pulsing = new BooleanValue("Pulsing", false));
//		this.addOption(this.hitori = new BooleanValue("Hitori", false));
//		this.addValue(this.hue = new NumberValue("HueCircle", 1, 0.0, 1.0));
//		this.addValue(this.Render = new NumberValue("RenderHeightCircle", 1, 0.005D, 1.0D));
//
//		this.addOption(this.circle = new BooleanValue("Circle", false));
//		this.addValue(this.Radious = new NumberValue("Radius", 1, 1, 4));
//		this.addOption(this.na1 = new BooleanValue("ClientName", true));
//		this.addValue(this.speed = new NumberValue("Speed", 0, 1, 6));
//		this.addValue(this.offset = new NumberValue("Offset", 0, 1, 6));
//		this.addValue(this.y = new NumberValue("X", 0, 0, 897));
//		this.addValue(this.x = new NumberValue("Y", 0, 0, 340));
//		this.addValue(this.saturariton = new NumberValue("Saturation", 0, 0, 10));
//		this.addValue(this.b12 = new NumberValue("Brightness", 0, 0.5, 1));


	//	this.addOption(this.RainbowBlink = new BooleanValue("Custom", false));


	}
	@Override
	public void setup() {
		Client.instance.settingsManager.rSetting(new Setting("X", this, 0, 0, 897, true));
		Client.instance.settingsManager.rSetting(new Setting("Y", this, 0, 0, 340, true));
		Client.instance.settingsManager.rSetting(new Setting("FOV", this, 360, 0, 360, true));
		Client.instance.settingsManager.rSetting(new Setting("HotBar", this, true));
		Client.instance.settingsManager.rSetting(new Setting("Pulsing", this, false));
		Client.instance.settingsManager.rSetting(new Setting("Hitori", this, true));
		Client.instance.settingsManager.rSetting(new Setting("ClientName", this, false));
		Client.instance.settingsManager.rSetting(new Setting("Custom", this, false));
		Client.instance.settingsManager.rSetting(new Setting("Villagers", this, false));
		Client.instance.settingsManager.rSetting(new Setting("Teams", this, false));
		Client.instance.settingsManager.rSetting(new Setting("Speed", this, 0, 1, 6, true));
		Client.instance.settingsManager.rSetting(new Setting("Offset", this, 0, 1, 6, true));
		Client.instance.settingsManager.rSetting(new Setting("Saturation", this, 0, 0, 10, true));
		Client.instance.settingsManager.rSetting(new Setting("Brightness", this, 0, 0.5, 1, true));
	}
	@EventTarget
	public void HudFluxSoon2(Event2D e) {
		if (Client.instance.settingsManager.getSettingByName("Custom").getValBoolean()) {
			ScaledResolution sr = new ScaledResolution(mc);
			FontRenderer fr = mc.fontRendererObj;
			Client.moduleManager.modules.sort(Comparator.comparingInt(m -> mc.fontRendererObj.getStringWidth(((Module) m).name)).reversed());
			int count = 0;
			for (Module m : Client.moduleManager.modules) {
				if (!m.toggled || m.name.equals("TabGUI"))
					continue;
				int yText = 2;

				int offset = count * (fr.FONT_HEIGHT + 6);
				//	int astolfo = getGradientOffset(new Color(100, 4, 193, 178), new Color(69, 0, 144), (Math.abs(((System.currentTimeMillis()) / 16)) / 60D) + (yText / ((Fonts.font.getHeight() + 135)))).getRGB();


				Gui.drawRect(52.0f, (int) (sr.getScaledWidth() - fr.getStringWidth(m.name) - 8.5), offset, sr.getScaledWidth() - fr.getStringWidth(m.name) - 8, 1.0f, 6 + fr.FONT_HEIGHT + offset, getRainbow(6000, -15 * yText));
				;
				Gui.drawRect(52.0f, sr.getScaledWidth() - fr.getStringWidth(m.name) - 8, offset, sr.getScaledWidth(), 2.0f, 6 + fr.FONT_HEIGHT + offset, new Color(0xF0030303, true).getRGB());
//				Gui.drawRect2(sr.getScaledWidth() - 1, yText - 3, sr.getScaledWidth(),
//						yText + mc.fontRendererObj.FONT_HEIGHT + 1, astolfo);
				Fonts.font.drawString(m.name, sr.getScaledWidth() - fr.getStringWidth(m.name) - 4, 4 + count * (fr.FONT_HEIGHT + 6), getRainbow(6000, -15 * yText));


				count++;

			}

		}

	}
	@EventTarget
	public void Watemarkclientname(Event2D e) {
		if (Client.instance.settingsManager.getSettingByName("ClientName").getValBoolean()) {
			int x = (int) Client.instance.settingsManager.getSettingByName("X").getValDouble();
			int y = (int) Client.instance.settingsManager.getSettingByName("Y").getValDouble();
			Fonts4.font.drawString("H ", x, y, ColorUtil.astolfo(-1, 1, 1, 1, 1).getRGB());
			Fonts4.font.drawString("   itori Client", x, y, -1);


		}
	}

	@EventTarget
	public void onDdoasj(Event2D event2D) {
		if (Client.instance.settingsManager.getSettingByName("HotBar").getValBoolean()) {
			FontRenderer fr = mc.fontRendererObj;
			int count = 0;
			int offset = 1 * (fr.FONT_HEIGHT - 6);
			if (mc.thePlayer == null) return;
			ScaledResolution sr = new ScaledResolution(mc);
			drawRect(0, sr.getScaledHeight() - 23, sr.getScaledWidth(), sr.getScaledHeight(), new Color(0, 0, 0, 221).hashCode());
			drawRect(0, sr.getScaledHeight() - 1, sr.getScaledWidth(), sr.getScaledHeight(), ColorUtil.astolfo(-1, 1, 1, 1, 1).getRGB());
			double prevX = mc.thePlayer.posX - mc.thePlayer.prevPosX;
			double prevZ = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
			int xd = mc.currentScreen != null && mc.currentScreen instanceof GuiChat ? sr.getScaledHeight() - 22 : sr.getScaledHeight() - 9;
			double lastDist = Math.sqrt(prevX * prevX + prevZ * prevZ);
			double currSpeed = lastDist * 15.3571428571D;
			String speed = String.format("%.2f blocks/sec", currSpeed);
			Fonts.font.drawStringWithShadow("Cord " + Math.round(mc.thePlayer.posX) + " " + Math.round(mc.thePlayer.posY) + " " + Math.round(mc.thePlayer.posZ), 2.0F, (float) xd, -1);


		}

	}

	public static void drawRect(float left, float top, float right, float bottom, int color) {

		float var5;

		if (left < right) {
			var5 = left;
			left = right;
			right = var5;
		}

		if (top < bottom) {
			var5 = top;
			top = bottom;
			bottom = var5;
		}

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glPushMatrix();
		glColor(color);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2d(left, bottom);
		GL11.glVertex2d(right, bottom);
		GL11.glVertex2d(right, top);
		GL11.glVertex2d(left, top);
		GL11.glEnd();
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
	}

	public static void glColor(int color) {

		GlStateManager.color((float) (color >> 16 & 255) / 255F, (float) (color >> 8 & 255) / 255F, (float) (color & 255) / 255F, (float) (color >> 24 & 255) / 255F);
	}

	private void drawRect1(int posX, int posY, int width, int height, int color) {
		Gui.drawRect(52.0f, posX, posY, posX + width, 1.0f, posY + height, color);

	}

	public Color getGradientOffset(Color color, Color color1, Color color2, double offset) {
		if (offset > 1) {
			double left = offset % 1;
			int off = (int) offset;
			offset = off % 2 == 0 ? left : 1 - left;
		}
		double inverse_percent = 1 - offset;
		int redPart = (int) (color1.getRed() * inverse_percent + color2.getRed() * offset);
		int greenPart = (int) (color1.getGreen() * inverse_percent + color2.getGreen() * offset);
		int bluePart = (int) (color1.getBlue() * inverse_percent + color2.getBlue() * offset);
		return new Color(redPart, greenPart, bluePart);
	}

	public static void drawHorizontalLine(int startX, int endX, int y, int color) {
		if (endX < startX) {
			int i = startX;
			startX = endX;
			endX = i;
		}

		drawRect(startX, y, endX + 1, y + 1, color);
	}

	@EventTarget
	public void HudFlux1(Event2D e) {
		if (Client.instance.settingsManager.getSettingByName("Pulsing").getValBoolean()) {
			ScaledResolution sr = new ScaledResolution(mc);
			FontRenderer fr = mc.fontRendererObj;
			Client.moduleManager.modules.sort(Comparator.comparingInt(m -> mc.fontRendererObj.getStringWidth(((Module) m).name)).reversed());
			int count = 0;
			for (Module m : Client.moduleManager.modules) {
				if (!m.toggled || m.name.equals("TabGUI"))
					continue;
				int yText = 2;

				int offset = count * (fr.FONT_HEIGHT + 6);
				int astolfo = getGradientOffset(new Color(133, 6, 246, 178), new Color(148, 55, 224, 112), new Color(0x75CAADD2, true), (Math.abs(((System.currentTimeMillis()) / 16)) / 60D) + (yText / ((Fonts.font.getHeight() + 135)))).getRGB();


				Gui.drawRect(52.0f, (int) (sr.getScaledWidth() - fr.getStringWidth(m.name) - 8.5), offset, sr.getScaledWidth() - fr.getStringWidth(m.name) - 8, 1.0f, 6 + fr.FONT_HEIGHT + offset, astolfo);
				Gui.drawRect(52.0f, sr.getScaledWidth() - fr.getStringWidth(m.name) - 8, offset, sr.getScaledWidth(), 2.0f, 6 + fr.FONT_HEIGHT + offset, new Color(0xFF030303, true).getRGB());
//				Gui.drawRect2(sr.getScaledWidth() - 1, yText - 3, sr.getScaledWidth(),
//						yText + mc.fontRendererObj.FONT_HEIGHT + 1, astolfo);
				Gui.drawRect2(sr.getScaledWidth() + fr.getStringWidth(m.name), offset, (float) (sr.getScaledWidth() - 0.8), 6 + fr.FONT_HEIGHT + offset, astolfo);
				Fonts.font.drawStringWithShadow(m.name, sr.getScaledWidth() - fr.getStringWidth(m.name) - 4, 4 + count * (fr.FONT_HEIGHT + 6), astolfo);

				count++;
			}
		}


	}

	@EventTarget
	public void setHitori(Event2D e) {
		if (Client.instance.settingsManager.getSettingByName("Hitori").getValBoolean()) {
			ScaledResolution sr = new ScaledResolution(mc);
			FontRenderer fr = mc.fontRendererObj;
			Client.moduleManager.modules.sort(Comparator.comparingInt(m -> mc.fontRendererObj.getStringWidth(((Module) m).name)).reversed());
			int count = 0;
			for (Module m : Client.moduleManager.modules) {
				if (!m.toggled || m.name.equals("TabGUI"))
					continue;
				int yText = 2;

				int offset = count * (fr.FONT_HEIGHT + 6);
				int astolfo = getGradientOffset(new Color(133, 6, 246, 178), new Color(148, 55, 224, 112), new Color(0x75CAADD2, true), (Math.abs(((System.currentTimeMillis()) / 16)) / 60D) + (yText / ((Fonts.font.getHeight() + 135)))).getRGB();


				Gui.drawRect(52.0f, (int) (sr.getScaledWidth() - fr.getStringWidth(m.name) - 8.2), offset, sr.getScaledWidth() - fr.getStringWidth(m.name) - 8, 1.0f, 6 + fr.FONT_HEIGHT + offset,ColorUtil.astolfo(-50,5,1,100,1).getRGB());
				Gui.drawRect(52.0f, sr.getScaledWidth() - fr.getStringWidth(m.name) - 8, offset, sr.getScaledWidth(), 2.0f, 6 + fr.FONT_HEIGHT + offset, new Color(0xFF030303, true).getRGB());
//				Gui.drawRect2(sr.getScaledWidth() - 1, yText - 3, sr.getScaledWidth(),
//						yText + mc.fontRendererObj.FONT_HEIGHT + 1, astolfo);
				Gui.drawRect2(sr.getScaledWidth() + fr.getStringWidth(m.name), offset, (float) (sr.getScaledWidth() - 0.8), 6 + fr.FONT_HEIGHT + offset,ColorUtil.astolfo(-50,10,1,100,1).getRGB());
				mc.fontRendererObj.drawStringWithShadow(m.name, sr.getScaledWidth() - fr.getStringWidth(m.name) - 4, 4 + count * (fr.FONT_HEIGHT + 6), ColorUtil.astolfo(count * 400, 4, 1f, 255, 1).getRGB());

				count++;
			}
		}


	}




	public int getRainbow(int speed, int offset) {
		long rainspeed = (long) Client.instance.settingsManager.getSettingByName("Speed").getValDouble();
		long rainoffset = (long) Client.instance.settingsManager.getSettingByName("Offset").getValDouble();
		float hue = (float) ((System.currentTimeMillis() * rainspeed + offset / rainoffset) % speed * 2);
		float saturation = (float) Client.instance.settingsManager.getSettingByName("Saturation").getValDouble();
		float brightness = (float) Client.instance.settingsManager.getSettingByName("Brightness").getValDouble();
		hue /= speed;
		return Color.getHSBColor(hue, saturation, brightness).getRGB();
	}


}