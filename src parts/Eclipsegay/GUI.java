package client.Eclipsegay;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import client.Eclipsegay.component.Component;
import client.Eclipsegay.component.Frame;

import client.event.ArrayHelper;
import client.module.Module;
import client.module.render.Hud;
import client.utilites.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.input.Mouse;

public class 	GUI extends GuiScreen {
	public ArrayList<Frame> frames;
	float anim = 0;
	public static int color = -1;
	public float scroll;

	public float scrollTicks;
	public float lastScrollTicks;
	public static transient ArrayList<Snowflake> snowflakes = new ArrayList<Snowflake>();
	private int mouseWheel;

	public GUI() {
		this.frames = new ArrayList<Frame>();
		//		this.drawDefaultBackground();

		int frameX = 5;
		Module.Category[] values;

		for (int length = (values = Module.Category.values()).length, i = 0; i < length; ++i) {
			Module.Category category = values[i];
			Frame frame = new Frame(category);
			frame.setX(frameX);
			this.frames.add(frame);
			frameX += frame.getWidth() + 1;
		}

	}

	@Override
	public void initGui() {

		if (!mc.entityRenderer.isShaderActive())
			mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));

		snowflakes.clear();

		ScaledResolution sr = new ScaledResolution(mc);

		for (int i = snowflakes.size(); i < 8; i++) {

			snowflakes.add(new Snowflake(new Random().nextInt(sr.getScaledWidth()),
					new Random().nextInt(sr.getScaledHeight())));
		}
	}

	@Override
	public void onGuiClosed() {
		anim = 0;
		if (mc.entityRenderer.isShaderActive())
			mc.entityRenderer.func_181022_b();
	}
	public static transient ArrayList<Snowflake> snowflakes1 = new ArrayList<Snowflake>();
	public static transient int snowflakeAmount = 1000;

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		ScaledResolution sr = new ScaledResolution(mc);
		int count = 0;
		int x = sr.getScaledWidth();
		int y = sr.getScaledHeight();
		boolean update = true;
		for (Frame frame : this.frames) {
			frame.renderFrame();
			frame.updatePosition(mouseX, mouseY);
			for (Component comp : frame.getComponents()) {
				comp.updateComponent(mouseX, mouseY);

					float speed = 0.1f;
					if (update) {
						anim += animSpeed(speed);
						if (anim > 1)
							anim = 1;
					} else {
						anim -= animSpeed(speed);
						if (anim < 0) {
							anim = 6;
							mc.displayGuiScreen(null);
						}

					}

				}

			}
		Color c = Color.getHSBColor((float) Hud.huegui.getValue(), 1.0F, 1.0F);
		float red = c.getRed() / 255.0F;
		float green = c.getGreen() / 255.0F;
		float blue = c.getBlue() / 255.0F;
			drawGradientRect(0, 200, x, y, new Color(0, 0, 0, 0).getRGB(), c.getRGB());
		drawGradientRect(0, 0, x, y, new Color(0, 0, 0, 22).getRGB(), new Color(0, 0, 0, 0).getRGB());
		drawGradientRect(0, 0, x, y, new Color(0, 0, 0, 0).getRGB(), new Color(0, 0, 0, 0).getRGB());

		float speed = 0.1f;
			if (update) {
				anim += animSpeed(speed);
				if (anim > 1)
					anim = 1;
			} else {
				anim -= animSpeed(speed);
				if (anim < 0) {
					anim = 6;
					mc.displayGuiScreen(null);
				}
			}


	}

		protected void mouseClicked ( int mouseX, int mouseY, int mouseButton){
			for (Frame frame : this.frames) {
				if (frame.isWithinHeader(mouseX, mouseY) && mouseButton == 0) {
					frame.setDrag(true);
					frame.dragX = mouseX - frame.getX();
					frame.dragY = mouseY - frame.getY();
				}
				if (frame.isWithinHeader(mouseX, mouseY) && mouseButton == 1) {
					frame.setOpen(!frame.isOpen());
				}
				if (frame.isOpen() && !frame.getComponents().isEmpty()) {
					for (Component component : frame.getComponents()) {
						component.mouseClicked(mouseX, mouseY, mouseButton);
					}
				}
			}
		}

		protected void keyTyped ( char typedChar, int keyCode){


			for (Frame frame : this.frames) {
				if (frame.isOpen() && keyCode != 1 && !frame.getComponents().isEmpty()) {
					for (Component component : frame.getComponents()) {
						component.keyTyped(typedChar, keyCode);
					}
				}

			}
			if (keyCode == 1) {
				this.mc.displayGuiScreen(null);
			}
		}

		protected void mouseReleased ( int mouseX, int mouseY, int state){
			for (Frame frame : this.frames) {
				frame.setDrag(false);
			}
		}

		public boolean doesGuiPauseGame () {
			return false;
		}

		public static double animSpeed ( double speed){
			return 50f / Minecraft.getDebugFPS() * speed;
		}

		public void drawGradientRect ( double left, double top, float right, float bottom, int startColor, int endColor)
		{
			float f = (float) (startColor >> 24 & 255) / 255.0F;
			float f1 = (float) (startColor >> 16 & 255) / 255.0F;
			float f2 = (float) (startColor >> 8 & 255) / 255.0F;
			float f3 = (float) (startColor & 255) / 255.0F;
			float f4 = (float) (endColor >> 24 & 255) / 255.0F;
			float f5 = (float) (endColor >> 16 & 255) / 255.0F;
			float f6 = (float) (endColor >> 8 & 255) / 255.0F;
			float f7 = (float) (endColor & 255) / 255.0F;
			GlStateManager.disableTexture2D();
			GlStateManager.enableBlend();
			GlStateManager.disableAlpha();
			GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
			GlStateManager.shadeModel(7425);
			Tessellator tessellator = Tessellator.getInstance();
			WorldRenderer worldrenderer = tessellator.getWorldRenderer();
			worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
			worldrenderer.func_181662_b((double) right, (double) top, (double) this.zLevel).func_181666_a(f1, f2, f3, f).func_181675_d();
			worldrenderer.func_181662_b((double) left, (double) top, (double) this.zLevel).func_181666_a(f1, f2, f3, f).func_181675_d();
			worldrenderer.func_181662_b((double) left, (double) bottom, (double) this.zLevel).func_181666_a(f5, f6, f7, f4).func_181675_d();
			worldrenderer.func_181662_b((double) right, (double) bottom, (double) this.zLevel).func_181666_a(f5, f6, f7, f4).func_181675_d();
			tessellator.draw();
			GlStateManager.shadeModel(7424);
			GlStateManager.disableBlend();
			GlStateManager.enableAlpha();
			GlStateManager.enableTexture2D();
		}
		public static Color astolfo ( int index, int speed, float saturation, float brightness, float opacity){
			int angle = (int) ((System.currentTimeMillis() / speed + index) % 360);
			angle = (angle > 180 ? 360 - angle : angle) + 180;
			float hue = angle / 360f;

			int color = Color.HSBtoRGB(brightness, saturation, hue);
			Color obj = new Color(color);
			return new Color(obj.getRed(), obj.getGreen(), obj.getBlue(), Math.max(0, Math.min(255, (int) (opacity * 255))));
		}

		public class Snowflake {

			public double x = 0, y = 0;

			public Snowflake(double x, double y) {

				this.x = x;
				this.y = y;

			}
		}
		public boolean isMouseHoveringRect2 ( float x, float y, float x2, float y2, int mouseX, int mouseY){
			return mouseX >= x && mouseY >= y && mouseX <= x2 && mouseY <= y2;
		}

	}
 
