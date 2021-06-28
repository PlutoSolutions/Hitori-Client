package client.Eclipsegay.component.components;

import java.awt.Color;
import java.util.ArrayList;

import client.Eclipsegay.component.Component;
import client.Eclipsegay.component.Frame;
import client.Eclipsegay.component.components.sub.Checkbox;
import client.Eclipsegay.component.components.sub.Keybind;
import client.Eclipsegay.component.components.sub.Slider;
import client.font.Fonts2;
import client.module.Module;
import client.utilites.ColorUtil;
import client.utilites.Render;
import client.values.BooleanValue;
import client.values.NumberValue;
import org.lwjgl.opengl.GL11;




import net.minecraft.client.gui.Gui;

public class Button extends Component {
	private Module mod;
	public Frame parent;
	private int offset;
	private boolean hovered;
	private ArrayList<Component> subcomponents;
	private boolean open;

	public Button(Module mod, Frame parent, int offset) {
		this.mod = mod;
		this.parent = parent;
		this.offset = offset;
		this.subcomponents = new ArrayList<Component>();
		this.open = false;
		int optionY = offset + 12;
		if (!mod.getValues().isEmpty()) {
			for (NumberValue numberValue : mod.getValues()) {
				Slider slider = new Slider(numberValue, this, optionY);
				this.subcomponents.add(slider);
				optionY += 12;
			}
		}
		if (!mod.getOptions().isEmpty()) {
			for (BooleanValue booleanValue : mod.getOptions()) {
				Checkbox checkbox = new Checkbox(booleanValue, this, optionY);
				this.subcomponents.add(checkbox);
				optionY += 12;
			}
		}
		this.subcomponents.add(new Keybind(this, optionY));
	}

	@Override
	public void setOffset(int newOffset) {
		this.offset = newOffset;
		int optionY = this.offset + 12;
		for (Component component : this.subcomponents) {
			component.setOffset(optionY);
			optionY += 12;
		}
	}

	@Override
	public void renderComponent() {
		Gui.drawRect(52.0f, this.parent.getX(), this.parent.getY() + this.offset, this.parent.getX() + this.parent.getWidth(), 1.0f, this.parent.getY() + 12 + this.offset, this.hovered ? (this.mod.getState() ?new Color(0xE6E6E8).getRGB() : new Color(0xDCDCE0).getRGB()) : (this.mod.toggled ? new Color(0xFAD4D4D4, true).getRGB():new Color(0xEDE7E4E4, true).getRGB()));
		GL11.glPushMatrix();
		GL11.glScalef(0.5f, 0.5f, 0.5f);
		Fonts2.font.drawString(this.mod.getName(), ((this.parent.getX() + 2) * 2), ((this.parent.getY() + this.offset + 2) * 2 + 4), this.mod.getState() ?  ColorUtil.Rainbow(8000,0,1) : new Color(0x999898).getRGB());
		if (this.subcomponents.size() > 1) {
			Render.drawArrow(((this.parent.getX() + this.parent.getWidth() - 10) * 2), ((this.parent.getY() + this.offset + 2) * 2 + 5), this.open, this.mod.getState() ? 0xFFab4949 : this.hovered ? -11184811 : -12303292);

		}
		GL11.glPopMatrix();
		if (this.open && !this.subcomponents.isEmpty()) {
			for (Component component : this.subcomponents) {
				component.renderComponent();
			}
			Gui.drawRect(52.0f, this.parent.getX() + 2, this.parent.getY() + this.offset + 12, this.parent.getX() + 3, 1.0f, this.parent.getY() + this.offset + (this.subcomponents.size() + 1) * 12,new Color(0xE2D8D8DB, true).getRGB());
		}
	}

	@Override
	public int getHeight() {
		if (this.open) {
			return 12 * (this.subcomponents.size() + 1);
		}
		return 12;
	}

	@Override
	public void updateComponent(int mouseX, int mouseY) {
		this.hovered = this.isMouseOnButton(mouseX, mouseY);
		if (!this.subcomponents.isEmpty()) {
			for (Component component : this.subcomponents) {
				component.updateComponent(mouseX, mouseY);
			}
		}
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		if (this.isMouseOnButton(mouseX, mouseY) && button == 0) {
			//this.mod.setToggled(!this.mod.getState());
			if (this.mod.toggled)
				this.mod.setToggled(false);
			else
				this.mod.setToggled(true);
		}
		if (this.isMouseOnButton(mouseX, mouseY) && button == 1) {
			this.open = !this.open;
			this.parent.refresh();
		}
		for (Component component : this.subcomponents) {
			component.mouseClicked(mouseX, mouseY, button);
		}
	}

	@Override
	public void keyTyped(char typedChar, int key) {
		for (Component component : this.subcomponents) {
			component.keyTyped(typedChar, key);
		}
	}

	public Module getMod() {
		return this.mod;
	}

	public Frame getParent() {
		return this.parent;
	}

	public int getOffset() {
		return this.offset;
	}

	public boolean isOpen() {
		return this.open;
	}

	public boolean isMouseOnButton(int x, int y) {
		return x > this.parent.getX() && x < this.parent.getX() + this.parent.getWidth() && y > this.parent.getY() + this.offset && y < this.parent.getY() + 12 + this.offset;
	}
	public static Color astolfo(int index, int speed, float saturation, float brightness, float opacity) {
		int angle = (int) ((System.currentTimeMillis() / speed + index) % 360);
		angle = (angle > 180 ? 360 - angle : angle) + 180;
		float hue = angle / 360f;

		int color = Color.HSBtoRGB(brightness, saturation, hue);
		Color obj = new Color(color);
		return new Color(obj.getRed(), obj.getGreen(), obj.getBlue(), Math.max(0, Math.min(255, (int) (opacity * 255))));
	}
}
