package client.Eclipsegay.component.components.sub;

import client.Eclipsegay.component.Component;
import client.Eclipsegay.component.components.Button;
import client.font.Fonts4;
import client.utilites.RenderUtils;
import client.values.BooleanValue;
import com.sun.applet2.preloader.event.ConfigEvent;
import org.lwjgl.opengl.GL11;


import net.minecraft.client.gui.Gui;

import java.awt.*;

public class Checkbox extends Component {
	private boolean hovered;
	private BooleanValue option;
	private Button parent;
	private int offset;
	private int x;
	private int y;
	private ConfigEvent value;
	private String setstrg;
	private float anim;
	private int width;
	private int height;

	public Checkbox(BooleanValue option, Button button, int offset) {
		this.option = option;
		this.parent = button;
		this.x = button.getParent().getX() + button.getParent().getWidth();
		this.y = button.getParent().getY() + button.getOffset();
		this.offset = offset;

	}

	@Override
	public void renderComponent() {
		Gui.drawRect(52.0f, this.parent.getParent().getX() + 2, this.parent.getParent().getY() + this.offset, this.parent.getParent().getX() + this.parent.getParent().getWidth() * 1, 1.0f, this.parent.getParent().getY() + this.offset + 12, this.hovered ? -1 : new Color(0xE2E2E2).getRGB());
		Gui.drawRect(52.0f, this.parent.getParent().getX(), this.parent.getParent().getY() + this.offset, this.parent.getParent().getX() + 2, 1.0f, this.parent.getParent().getY() + this.offset + 12, new Color(0xDDDDE0).getRGB());
		GL11.glPushMatrix();
		GL11.glScalef(0.5f, 0.5f, 0.5f);
		Fonts4.font.drawString(this.option.getName(), ((this.parent.getParent().getX() + 20 + 4) * 2 + 5), ((this.parent.getParent().getY() + this.offset + 2) * 2 + 10), new Color(0x979696).getRGB());
		GL11.glPopMatrix();
		RenderUtils.drawBorderedRect((float) this.x + 11.2F, (float) this.y + 7.0F, (float) this.x + 17.0F, (float) this.y + 11.0F, 1.0F, new Color(110, 114, 114).getRGB(), this.anim < 4.5F ? new Color(110, 114, 114).getRGB() : new Color(110, 114, 114).getRGB());
		GL11.glPushMatrix();
		RenderUtils.drawBorderedCircle((float) ((int) this.x + 11), (float) ((int) this.y + 9), 2.0F, 1, new Color(110, 114, 114).getRGB(), new Color(110, 114, 114).getRGB());
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		RenderUtils.drawBorderedCircle((float) ((int) this.x + 17), (float) ((int) this.y + 9), 2.0F, 1, new Color(110, 114, 114).getRGB(), new Color(110, 114, 114).getRGB());
		GL11.glPopMatrix();
		GL11.glPushMatrix();

		if (this.option.isEnabled()) {
			RenderUtils.drawBorderedCircle((float) this.x + 18.0F + this.anim, (float) this.y + 9.0F, 3.0F, 1, new Color(110, 114, 114).getRGB(), -1);
			GL11.glPopMatrix();
			RenderUtils.drawCircle228((float) this.x + 18.0F + this.anim, (float) this.y + 9.0F, 3.0F, 5, new Color(110, 114, 114).getRGB(), new Color(110, 114, 114).getRGB(), (int) (this.anim *60.0f));

		}else{
			RenderUtils.drawBorderedCircle((float) this.x + 11.0F + this.anim, (float) this.y + 9.0F, 3.0F, 1, new Color(110, 114, 114).getRGB(), -1);
			GL11.glPopMatrix();
			RenderUtils.drawCircle228((float) this.x + 11.0F + this.anim, (float) this.y + 9.0F, 3.0F, 5, new Color(110, 114, 114).getRGB(), new Color(110, 114, 114).getRGB(), (int) (this.anim * 60.0F));

		}
	}

	@Override
	public void setOffset(int newOffset) {
		this.offset = newOffset;
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
			this.option.toggle();
		}
	}

	public boolean isMouseOnButton(int x, int y) {
		return x > this.x && x < this.x + 88 && y > this.y && y < this.y + 12;
	}

	}