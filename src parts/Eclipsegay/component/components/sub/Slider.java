package client.Eclipsegay.component.components.sub;

import client.Eclipsegay.component.Component;
import client.Eclipsegay.component.components.Button;
import client.font.*;
import client.values.NumberValue;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ibm.icu.math.BigDecimal;


import net.minecraft.client.gui.Gui;

import java.awt.*;

public class Slider extends Component {
	private boolean hovered;
	private NumberValue value;
	private Button parent;
	private int offset;
	private int x;
	private int y;
	private int height;
	private double width;
	private Object anim;

	public Slider(NumberValue value, Button button, int offset) {
		this.value = value;
		this.parent = button;
		this.x = button.getParent().getX() + button.getParent().getWidth();
		this.y = button.getParent().getY() + button.getOffset();
		this.offset = offset;
	}

	@Override
	public void renderComponent() {
		Gui.drawRect(52.0f, this.parent.getParent().getX() + 2, this.parent.getParent().getY() + this.offset, this.parent.getParent().getX() + this.parent.getParent().getWidth(), 1.0f, this.parent.getParent().getY() + this.offset + 12, this.hovered ?new Color(0xC6C6C9).getRGB() : new Color(0xE0E0E0).getRGB());
		int drag = (int) (this.value.getValue() / this.value.getMax() * this.parent.getParent().getWidth());
		Gui.drawRect(52.0f, this.parent.getParent().getX() + 2, this.parent.getParent().getY() + this.offset, this.parent.getParent().getX() + drag, 10.0f, (int) (this.parent.getParent().getY() + this.offset + 1.90), this.hovered ? new Color(0x8BD7D7D7, true).getRGB() : new Color(0xE5E5E5).getRGB());
		Gui.drawRect(52.0f, this.parent.getParent().getX(), this.parent.getParent().getY() + this.offset, this.parent.getParent().getX() + 2, 1.0f, this.parent.getParent().getY() + this.offset + 12, new Color(0xDDDDE0).getRGB());
		GL11.glPushMatrix();
		GL11.glScalef(0.5f, 0.5f, 0.5f);

		Fonts4.font.drawString(String.valueOf(this.value.getName()) + ": " + this.value.getValue(), (this.parent.getParent().getX() * 2 + 15), ((this.parent.getParent().getY() + this.offset + 2) * 2 + 5), new Color(0x929292).getRGB());
		GL11.glPopMatrix();
	}

	@Override
	public void setOffset(int newOffset) {
		this.offset = newOffset;
	}

	@Override
	public void updateComponent(int mouseX, int mouseY) {
		this.hovered = (this.isMouseOnButtonD(mouseX, mouseY) || this.isMouseOnButtonI(mouseX, mouseY));
		this.y = this.parent.getParent().getY() + this.offset;
		this.x = this.parent.getParent().getX();
		if (this.hovered && this.parent.isOpen() && Mouse.isButtonDown(0)) {
			double diff = mouseX - this.parent.getParent().getX();
			double value = this.round(diff / (this.parent.getParent().getWidth() - 1) * this.value.getMax(), 1);
			this.value.setValue(value);
		}
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		if (this.isMouseOnButtonD(mouseX, mouseY) && button == 0 && this.parent.isOpen()) {
			NumberValue numberValue = this.value;
			double value = numberValue.getValue() - 0.1;
			numberValue.setValue(Math.round(value * 10.0) / 10.0);
		}
		if (this.isMouseOnButtonI(mouseX, mouseY) && button == 0 && this.parent.isOpen()) {
			NumberValue numberValue = this.value;
			double value = numberValue.getValue() + 0.1;
			numberValue.setValue(Math.round(value * 10.0) / 10.0);
		}
	}
	
    private double round(double doubleValue, int numOfDecimals)  {
        BigDecimal bigDecimal = new BigDecimal(doubleValue);
        bigDecimal = bigDecimal.setScale(numOfDecimals, BigDecimal.ROUND_HALF_UP);
        return bigDecimal.doubleValue();
    }

	public boolean isMouseOnButtonD(int x, int y) {
		return x > this.x && x < this.x + (this.parent.getParent().getWidth() / 2 + 1) && y > this.y && y < this.y + 12;
	}

	public boolean isMouseOnButtonI(int x, int y) {
		return x > this.x + this.parent.getParent().getWidth() / 2 && x < this.x + this.parent.getParent().getWidth() && y > this.y && y < this.y + 12;
	}
}
