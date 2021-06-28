package client.Eclipsegay.component.components.sub;

import client.Client;
import client.Eclipsegay.component.Component;
import client.Eclipsegay.component.components.Button;
import client.font.Fonts;
import client.font.Fonts4;
import client.utilites.FontUtil;
import client.utilites.Wrapper;
import com.google.gson.JsonSyntaxException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;


import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.io.IOException;

public class Keybind extends Component {
	private boolean hovered;
	private boolean binding;
	private Button parent;
	private int offset;
	private int x;
	private int y;
	private static final Minecraft MC = Minecraft.getMinecraft();
	private ShaderGroup shaderGroup;
	private Framebuffer framebuffer;
	private static FontRenderer fontRenderer;

	private int lastFactor;
	private int lastWidth;
	private int lastHeight;
	private ResourceLocation resourceLocation;

	public Keybind(Button button, int offset) {
		this.parent = button;
		this.x = button.getParent().getX() + button.getParent().getWidth();
		this.y = button.getParent().getY() + button.getOffset();
		this.offset = offset;
	}

	@Override
	public void renderComponent() {
//		Gui.drawRect2(0, 0, this.lastWidth, this.lastHeight, 0x88101010);
//		GL11.glPushMatrix();
//		GL11.glScalef(4.0F, 4.0F, 0F);
//		FontUtil.drawTotalCenteredStringWithShadow("Listening...", 0, -10, 0xffffffff);
//		GL11.glScalef(0.5F, 0.5F, 0F);
//		FontUtil.drawTotalCenteredStringWithShadow("Press 'ESCAPE' to unbind " +this.parent.getMod().getName() + (this.parent.getMod().getKey() > -1 ? " (" + Keyboard.getKeyName(this.parent.getMod().getKey()) + ")" : ""), 0, 0, 0xffffffff);
//		GL11.glScalef(0.25F, 0.25F, 0F);
//		GL11.glPopMatrix();

		/*
		 * Nicht bentigt, aber es ist so einfach sauberer ;)
		 * Und ohne diesen call knnen keine GUIButtons/andere Elemente
		 * gerendert werden
		 */
		Gui.drawRect(52.0f, this.parent.getParent().getX() + 2, this.parent.getParent().getY() + this.offset, this.parent.getParent().getX() + this.parent.getParent().getWidth() * 1, 1.0f, this.parent.getParent().getY() + this.offset + 12, this.hovered ? -14540254 : new Color(0xE2E2E2).getRGB());
		Gui.drawRect(52.0f, this.parent.getParent().getX(), this.parent.getParent().getY() + this.offset, this.parent.getParent().getX() + 2, 1.0f, this.parent.getParent().getY() + this.offset + 12, new Color(0x8E8B8B).getRGB());
		GL11.glPushMatrix();
		GL11.glScalef(0.5f, 0.5f, 0.5f);
		Fonts4.font.drawString(this.binding ? "Listening" : ("Keybind: " + Keyboard.getKeyName(this.parent.getMod().getKey())), ((this.parent.getParent().getX() + 7) * 2), ((this.parent.getParent().getY() + this.offset + 2) * 2 + 4), new Color(0x979696).getRGB());
		GL11.glPopMatrix();
	}

	@Override
	public void updateComponent(int mouseX, int mouseY) {
		this.hovered = this.isMouseOnButton(mouseX, mouseY);
		this.y = this.parent.getParent().getY() + this.offset;
		this.x = this.parent.getParent().getX();
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		if (this.isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.isOpen()) {
			this.binding = !this.binding;
		}
	}

	@Override
	public void keyTyped(char typedChar, int key) {
		if (this.binding) {
			if (key == 14) {
				this.parent.getMod().setKey(0);
				this.binding = false;
				return;
			} else {
				this.parent.getMod().setKey(key);
				this.binding = false;
			}
		}
	}

	@Override
	public void setOffset(int newOffset) {
		this.offset = newOffset;
	}

	public boolean isMouseOnButton(int x, int y) {
		return x > this.x && x < this.x + 88 && y > this.y && y < this.y + 12;
	}
}