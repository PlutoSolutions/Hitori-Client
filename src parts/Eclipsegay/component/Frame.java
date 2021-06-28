package client.Eclipsegay.component;

import java.awt.*;
import java.util.ArrayList;

import client.Eclipsegay.component.components.Button;
import client.font.Fonts;
import client.font.Fonts1;
import client.font.Fonts2;
import client.font.Fonts3;
import client.module.Module;
import client.module.render.Hud;
import client.utilites.RenderUtils;
import client.utilites.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;


import net.minecraft.client.gui.Gui;

public class Frame {
	private ArrayList<Component> components;
	private Module.Category category;
	private boolean open;
	private int width;
	private int y;
	private int x;
	private int barHeight;
	private boolean isDragging;
	public int dragX;
	public int dragY;
	private int anim;
	private int height;
	private boolean close;

	public Frame(Module.Category category) {
		this.components = new ArrayList<Component>();
		this.category = category;
		this.width =70;
		this.x = 5;
		this.y = 5;
		this.barHeight = 13;
		this.dragX = 0;
		this.open = false;
		this.isDragging = false;
		int tY = this.barHeight;
		for (Module mod : Module.getCategoryModules(category)) {
			Button modButton = new Button(mod, this, tY);
			this.components.add(modButton);
			tY += 12;
		}
	}


	public void renderFrame() {
		Color c = Color.getHSBColor((float) Hud.huegui.getValue(), 1.0F, 1.0F);
		float red = c.getRed() / 255.0F;
		float green = c.getGreen() / 255.0F;
		float blue = c.getBlue() / 255.0F;
		Gui.drawRect(52.0f, this.x -2, this.y, this.x + this.width + 2, 51.0f, this.y + this.barHeight,c.getRGB());
		Gui.drawRect(52.0f, this.x -2, this.y, this.x + this.width + 2, 51.0f, this.y + this.barHeight,new Color(0x27FAFAFA, true).getRGB());

		Gui.drawRect(52.0f, this.x - 1, this.y, this.x + this.width + 1, 1.0f, this.y + this.barHeight,new Color(0xEDE5E3E3, true).getRGB());
		Gui.drawRect(52.0f, this.x - 1, this.y, this.x + this.width + 1, 1.0f, this.y + this.barHeight,new Color(0xE4EEEBEB, true).getRGB());
		GL11.glPushMatrix();
		GL11.glScalef(0.5f, 0.5f, 0.4f);
		RenderUtils.drawRect(this.x, this.y, this.x + this.width, this.y + this.height,  new Color(56, 57, 56).getRGB());
		//RenderUtils.drawGradientSideways(this.x, this.y, this.x + this.width - 100.0D, this.y + this.height, -5460820, 14737632);
		Fonts2.font.drawString(this.category.getName(), ((this.x + 18) * 2 + 5), (this.y + 2) * 2 + 5, new Color(0xB4B3B3).getRGB());
	//	Fonts3.font.drawStringWithShadow(this.open ? "a" : "ba" , ((this.x + this.width - 10) * 2 + 5), (this.y + 2) * 2 + 5, new Color(0xACACAC).getRGB());
		if (open) {
			if (anim < getMaxHeight())
				anim += 4;
			if (anim + 3> getMaxHeight())
				anim -= 0;
		} else {
			if (anim > 0)
				anim -= 2;
			if (anim < getMaxHeight())
				anim += 1;
			if (anim + 3 > getMaxHeight())
				anim -= 0;
		}
		GL11.glPopMatrix();
		GL11.glEnable(3089);
		prepareScissorBox(new ScaledResolution(Minecraft.getMinecraft()), getX(), getY() - 3, getWidth(), anim + barHeight + 2);
		if(!this.components.isEmpty()) {
			for(Component component : components) {
				component.renderComponent();
			}

		}
		//Gui.drawRect(getX(), getY(), getX() + 1, getY() + getMaxHeight() + barHeight, Client.color);
		GL11.glDisable(3089);
	}


	
	public ArrayList<Component> getComponents() {
		return this.components;
	}

	public void setX(int newX) {
		this.x = newX;
	}

	public void setY(int newY) {
		this.y = newY;
	}

	public void setDrag(boolean drag) {
		this.isDragging = drag;
	}

	public boolean isOpen() {
		return this.open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}

	public void refresh() {
		int offset = this.barHeight;
		for (Component component : this.components) {
			component.setOffset(offset);
			offset += component.getHeight();
		}
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getWidth() {
		return this.width;
	}

	public void updatePosition(int mouseX, int mouseY) {
		if (this.isDragging) {
			this.setX(mouseX - this.dragX);
			this.setY(mouseY - this.dragY);
		}
	}

	public boolean isWithinHeader(int x, int y) {
		return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.barHeight;
	}

	public int getMaxHeight() {
		int off = 1;
		for (Component comp : components)
			off += comp.getHeight();
		return off;
	}
	public static Color astolfo(int index, int speed, float saturation, float brightness, float opacity) {
		int angle = (int) ((System.currentTimeMillis() / speed + index) % 360);
		angle = (angle > 180 ? 360 - angle : angle) + 180;
		float hue = angle / 360f;

		int color = Color.HSBtoRGB(brightness, saturation, hue);
		Color obj = new Color(color);
		return new Color(obj.getRed(), obj.getGreen(), obj.getBlue(), Math.max(0, Math.min(255, (int) (opacity * 255))));
	}
	public void prepareScissorBox(ScaledResolution sr, float x, float y, float width, float height) {
		float x2 = x + width;
		float y2 = y + height;
		int factor = sr.getScaleFactor();
		GL11.glScissor((int) (x * factor), (int) ((sr.getScaledHeight() - y2) * factor), (int) ((x2 - x) * factor),
				(int) ((y2 - y) * factor));
	}

}
