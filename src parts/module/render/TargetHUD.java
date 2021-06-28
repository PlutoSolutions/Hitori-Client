package client.module.render;

import client.Client;
import client.Hero.settings.Setting;
import client.event.EventTarget;
import client.event.events.Event2D;
import client.font.Fonts;
import client.module.Category;
import client.module.Module;
import client.module.ModuleManager;
import client.module.combat.KillAura;
import client.utilites.ColorUtil;
import client.utilites.Timer;
import client.utils.client.AnimationUtil;
import client.utils.client.Stopwatch;
import client.values.BooleanValue;
import com.sun.xml.internal.ws.api.config.management.policy.ManagementAssertion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;


import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class TargetHUD extends Module {
    public static Minecraft mc = Minecraft.getMinecraft();
    private static Object zLevel;
    private String mode;
    private static final Color COLOR;
    private EntityOtherPlayerMP target;
    private double healthBarWidth;
    private double hudHeight;
    public EntityLivingBase lastEnt;
    public float lastHealth;
    public float damageDelt;
    public float lastPlayerHealth;
    public float damageDeltToPlayer;
    public DecimalFormat format;
    public double animation;
    private long lastMS;


    public TargetHUD() {
        super("TargetHud", 0, Category.RENDER);

//        this.addOption(this.Autumn = new BooleanValue("Autumn", false));
//        this.addOption(this.Novo = new BooleanValue("Novo", false));
//        this.addOption(this.Summer = new BooleanValue("Summer", false));
//        this.addOption(this.Exhibition = new BooleanValue("Exhibition", false));


    }
    @Override
    public void setup() {
        Client.instance.settingsManager.rSetting(new Setting("Autumn", this, false));
        Client.instance.settingsManager.rSetting(new Setting("Novo", this, false));
        Client.instance.settingsManager.rSetting(new Setting("Summer", this, false));
        Client.instance.settingsManager.rSetting(new Setting("Exhibition", this, false));
    }

    @EventTarget
    public void onUpdate(Event2D event) {
        if (Client.instance.settingsManager.getSettingByName("Autumn").getValBoolean()) {
            ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
            final float scaledWidth = sr.getScaledWidth();
            final float scaledHeight = sr.getScaledHeight();
            if (KillAura.target != null) {
                if (KillAura.target instanceof EntityOtherPlayerMP) {
                    this.target = (EntityOtherPlayerMP) KillAura.target;
                    final float width = 140.0f;
                    final float height = 40.0f;
                    final float xOffset = 40.0f;
                    final float x = scaledWidth / 2.0f - 70.0f;
                    final float y = scaledHeight / 2.0f + 80.0f;
                    final float health = this.target.getHealth();
                    double hpPercentage = health / this.target.getMaxHealth();
                    hpPercentage = MathHelper.clamp_double(hpPercentage, 0.0, 1.0);
                    final double hpWidth = 92.0 * hpPercentage;
                    final int healthColor = ColorUtil.getHealthColor(this.target.getHealth(), this.target.getMaxHealth())
                            .getRGB();
                    final String healthStr = String.valueOf((int) this.target.getHealth() / 1.0f);
                    if (this.hasReached(15L)) {
                        this.healthBarWidth = AnimationUtil.animate(hpWidth, this.healthBarWidth, 0.3529999852180481);
                        this.hudHeight = AnimationUtil.animate(40.0, this.hudHeight, 0.10000000149011612);
                        this.reset();
                    }
                    GL11.glEnable(3089);
                    prepareScissorBox(x, y, x + 140.0f, (float) (y + this.hudHeight));
                    Gui.drawRect2(x, y, x + 140.0f, y + 40.0f, TargetHUD.COLOR.getRGB());
                    Gui.drawRect2(x + 32.0f, y + 38.0f, (float) (x + 34.0f + this.healthBarWidth), y + 40.0f, healthColor);
                    //   mc.fontRendererObj.drawStringWithShadow("| | | | | | | | | | | | | | | | | | |", x + 5.0f + 46.0f - mc.fontRendererObj.getStringWidth(healthStr) / 2.0f, y + 16.0f, new Color(0,0,0).getRGB());
                    mc.fontRendererObj.drawStringWithShadow(healthStr, x + 33.0f + 46.0f - mc.fontRendererObj.getStringWidth(healthStr) / 2.0f, y + 16.0f, -1);
                    mc.fontRendererObj.drawStringWithShadow(this.target.getNameClear(), x + 50.0f, y + 2.0f, -1);
                    mc.fontRendererObj.drawStringWithShadow("OnGround : " + KillAura.target.onGround + " | ", x + 30.0f, y + 26.0f, -1);
                    mc.fontRendererObj.drawStringWithShadow(" Pitch :" + (int) KillAura.target.rotationYaw, x + 83.0f, y + 26.0f, -1);
                    // mc.fontRendererObj.drawStringWithShadow(""+Killaura.target.hurtTime, x + 120.0f, y + 30.0f, -1);
                    GuiInventory.drawEntityOnScreen((int) (x + 13.333333f), (int) (y + 40.0f), 20, this.target.rotationYaw, this.target.rotationPitch, this.target);
                    GL11.glDisable(3089);
                }
            } else {
                this.healthBarWidth = 92.0;
                this.hudHeight = 0.0;
                this.target = null;
            }
        }
        if ( Client.instance.settingsManager.getSettingByName("Novo").getValBoolean()) {
            ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
            if (KillAura.target != null) {
                if (KillAura.target instanceof EntityOtherPlayerMP) {
                    float healthPercentage = KillAura.target.getHealth() / KillAura.target.getMaxHealth();
                    float startX = 20;
                    float renderX = (sr.getScaledWidth() / 2) + startX;
                    float renderY = (sr.getScaledHeight() / 2) + 10;
                    int maxX2 = 30;
                    if (KillAura.target.getCurrentArmor(3) != null) {
                        maxX2 += 15;
                    }
                    if (KillAura.target.getCurrentArmor(2) != null) {
                        maxX2 += 15;
                    }
                    if (KillAura.target.getCurrentArmor(1) != null) {
                        maxX2 += 15;
                    }
                    if (KillAura.target.getCurrentArmor(0) != null) {
                        maxX2 += 15;
                    }
                    if (KillAura.target.getHeldItem() != null) {
                        maxX2 += 15;
                    }
                    final int healthColor = ColorUtil.getHealthColor(KillAura.target.getHealth(), KillAura.target.getMaxHealth())
                            .getRGB();
                    float maxX = Math.max(maxX2, mc.fontRendererObj.getStringWidth(((EntityOtherPlayerMP) KillAura.target).getNameClear()) + 30);
                    Gui.drawRect2(renderX, renderY, renderX + maxX, renderY + 40, new Color(0, 0, 0, 0.3f).getRGB());
                    Gui.drawRect2(renderX, renderY + 38, renderX + (maxX * healthPercentage), renderY + 40, healthColor);
                    mc.fontRendererObj.drawStringWithShadow(((EntityOtherPlayerMP) KillAura.target).getNameClear(), renderX + 25, renderY + 7, -1);
                    int xAdd = 0;
                    double multiplier = 0.85;
                    GlStateManager.pushMatrix();
                    GlStateManager.scale(multiplier, multiplier, multiplier);
                    if (KillAura.target.getCurrentArmor(3) != null) {
                        mc.getRenderItem().renderItemAndEffectIntoGUI(KillAura.target.getCurrentArmor(3), (int) ((((sr.getScaledWidth() / 2) + startX + 23) + xAdd) / multiplier), (int) (((sr.getScaledHeight() / 2) + 28) / multiplier));
                        xAdd += 15;
                    }
                    if (KillAura.target.getCurrentArmor(2) != null) {
                        mc.getRenderItem().renderItemAndEffectIntoGUI(KillAura.target.getCurrentArmor(2), (int) ((((sr.getScaledWidth() / 2) + startX + 23) + xAdd) / multiplier), (int) (((sr.getScaledHeight() / 2) + 28) / multiplier));
                        xAdd += 15;
                    }
                    if (KillAura.target.getCurrentArmor(1) != null) {
                        mc.getRenderItem().renderItemAndEffectIntoGUI(KillAura.target.getCurrentArmor(1), (int) ((((sr.getScaledWidth() / 2) + startX + 23) + xAdd) / multiplier), (int) (((sr.getScaledHeight() / 2) + 28) / multiplier));
                        xAdd += 15;
                    }
                    if (KillAura.target.getCurrentArmor(0) != null) {
                        mc.getRenderItem().renderItemAndEffectIntoGUI(KillAura.target.getCurrentArmor(0), (int) ((((sr.getScaledWidth() / 2) + startX + 23) + xAdd) / multiplier), (int) (((sr.getScaledHeight() / 2) + 28) / multiplier));
                        xAdd += 15;
                    }
                    if (KillAura.target.getHeldItem() != null) {
                        mc.getRenderItem().renderItemAndEffectIntoGUI(KillAura.target.getHeldItem(), (int) ((((sr.getScaledWidth() / 2) + startX + 23) + xAdd) / multiplier), (int) (((sr.getScaledHeight() / 2) + 28) / multiplier));
                    }
                    GlStateManager.popMatrix();
                    GuiInventory.drawEntityOnScreen((int) renderX + 12, (int) renderY + 33, 15, KillAura.target.rotationYaw, KillAura.target.rotationPitch, KillAura.target);
                } else {
                    this.healthBarWidth = 92.0;
                    this.hudHeight = 0.0;
                    this.target = null;
                }
            }
        }
        if (Client.instance.settingsManager.getSettingByName("Summer").getValBoolean()) {
            ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
            final float scaledWidth = sr.getScaledWidth();
            final float scaledHeight = sr.getScaledHeight();
            if (KillAura.target != null) {
                if (KillAura.target instanceof EntityOtherPlayerMP) {
                    this.target = (EntityOtherPlayerMP) KillAura.target;
                    final float width = 140.0f;
                    final float height = 40.0f;
                    final float xOffset = 40.0f;
                    final float x = scaledWidth / 2.0f - 70.0f;
                    final float y = scaledHeight / 2.0f + 80.0f;
                    final float health = this.target.getHealth();
                    double hpPercentage = health / this.target.getMaxHealth();
                    hpPercentage = MathHelper.clamp_double(hpPercentage, 0.0, 1.0);
                    final double hpWidth = 92.0 * hpPercentage;
                    final int healthColor = ColorUtil.getHealthColor(this.target.getHealth(), this.target.getMaxHealth())
                            .getRGB();
                    final String healthStr = String.valueOf((int) this.target.getHealth() / 1.0f);

                    this.healthBarWidth = AnimationUtil.animate(hpWidth, this.healthBarWidth, 0.3529999852180481);
                    this.hudHeight = AnimationUtil.animate(40.0, this.hudHeight, 0.10000000149011612);
                    GL11.glEnable(3089);
                    prepareScissorBox(x, y, x + 140.0f, (float) (y + this.hudHeight));
                    Gui.drawRect2(x + 15, y, x + 140.0f, y + 40.0f, new Color(0, 0, 0, 240).getRGB());
                    Gui.drawRect2(x + 30.0f, y + 26.0f, (float) (x + 35.0f + this.healthBarWidth), y + 35.0f, new Color(219, 255, 1).getRGB());
                    //   mc.fontRendererObj.drawStringWithShadow("| | | | | | | | | | | | | | | | | | |", x + 5.0f + 46.0f - mc.fontRendererObj.getStringWidth(healthStr) / 2.0f, y + 16.0f, new Color(0,0,0).getRGB());
                    Fonts.font.drawStringWithShadow(healthStr, x + 33.0f + 46.0f - mc.fontRendererObj.getStringWidth(healthStr) / 2.0f, y + 28.0f, new Color(245, 255, 0).getRGB());
                    Fonts.font.drawStringWithShadow(this.target.getNameClear(), x + 38.0f, y + 14.0f, new Color(245, 255, 0).getRGB());
                    //Client.instance.fm.getFont("EVO 12").drawStringWithShadow("OnGround : " + Killaura.target.onGround + " | ", x + 30.0f, y + 26.0f, -1);
                    //	Client.instance.fm.getFont("EVO 12").drawStringWithShadow(" Pitch : " + (int)Killaura.target.rotationYaw, x + 83.0f, y + 26.0f, -1);
                    //    Draw.drawImg(new ResourceLocation("client/icons/unnamed.png"), -2, 83, 83, 20);
                    // mc.fontRendererObj.drawStringWithShadow(""+Killaura.target.hurtTime, x + 120.0f, y + 30.0f, -1);
                    //GuiInventory.drawEntityOnScreen((int)(x + 13.333333f), (int)(y + 40.0f), 20, this.target.rotationYaw, this.target.rotationPitch, this.target);
                    GL11.glDisable(3089);
                }
            } else {
                this.healthBarWidth = 92.0;
                this.hudHeight = 0.0;
                this.target = null;
            }
        }
        if ( Client.instance.settingsManager.getSettingByName("Exhibition").getValBoolean()) {
            ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
            final float scaledWidth = sr.getScaledWidth();
            final float scaledHeight = sr.getScaledHeight();
            if (target != null ) {
                if (target instanceof EntityOtherPlayerMP) {
                    float startX = 20;
                    float enderX = (sr.getScaledWidth() / 2) + startX;
                    float renderY = (sr.getScaledHeight() / 2) + 10;
                    int maxX2 = 30;
                    if (target.getCurrentArmor(3) != null) {
                        maxX2 += 15;
                    }
                    if (target.getCurrentArmor(2) != null) {
                        maxX2 += 15;
                    }
                    if (target.getCurrentArmor(1) != null) {
                        maxX2 += 15;
                    }
                    if (target.getCurrentArmor(0) != null) {
                        maxX2 += 15;
                    }
                    if (target.getHeldItem() != null) {
                        maxX2 += 15;
                    }
                    this.target = (EntityOtherPlayerMP) target;
                    final float width = 140.0f;
                    final float height = 40.0f;
                    final float xOffset = 40.0f;
                    final float x = scaledWidth / 2.0f + 30.0f;
                    final float y = scaledHeight / 2.0f + 30.0f;
                    final float health = this.target.getHealth();
                    double hpPercentage = health / this.target.getMaxHealth();
                    hpPercentage = MathHelper.clamp_double(hpPercentage, 0.0, 1.0);
                    final double hpWidth = 60.0 * hpPercentage;
                    final int healthColor = ColorUtil.getHealthColor(this.target.getHealth(), this.target.getMaxHealth())
                            .getRGB();
                    final String healthStr = String.valueOf((int) this.target.getHealth() / 1.0f);
                    int xAdd = 0;
                    double multiplier = 0.85;
                    GlStateManager.pushMatrix();
                    GlStateManager.scale(multiplier, multiplier, multiplier);
                    if (target.getCurrentArmor(3) != null) {
                        mc.getRenderItem().renderItemAndEffectIntoGUI(target.getCurrentArmor(3), (int) ((((sr.getScaledWidth() / 2) + startX + 33) + xAdd) / multiplier), (int) (((sr.getScaledHeight() / 2) + 56) / multiplier));
                        xAdd += 15;
                    }
                    if (target.getCurrentArmor(2) != null) {
                        mc.getRenderItem().renderItemAndEffectIntoGUI(target.getCurrentArmor(2), (int) ((((sr.getScaledWidth() / 2) + startX + 33) + xAdd) / multiplier), (int) (((sr.getScaledHeight() / 2) + 56) / multiplier));
                        xAdd += 15;
                    }
                    if (target.getCurrentArmor(1) != null) {
                        mc.getRenderItem().renderItemAndEffectIntoGUI(target.getCurrentArmor(1), (int) ((((sr.getScaledWidth() / 2) + startX + 33) + xAdd) / multiplier), (int) (((sr.getScaledHeight() / 2) + 56) / multiplier));
                        xAdd += 15;
                    }
                    if (target.getCurrentArmor(0) != null) {
                        mc.getRenderItem().renderItemAndEffectIntoGUI(target.getCurrentArmor(0), (int) ((((sr.getScaledWidth() / 2) + startX + 33) + xAdd) / multiplier), (int) (((sr.getScaledHeight() / 2) + 56) / multiplier));
                        xAdd += 15;
                    }
                    if (target.getHeldItem() != null) {
                        mc.getRenderItem().renderItemAndEffectIntoGUI(target.getHeldItem(), (int) ((((sr.getScaledWidth() / 2) + startX + 33) + xAdd) / multiplier), (int) (((sr.getScaledHeight() / 2) + 56) / multiplier));
                    }
                    GlStateManager.popMatrix();
                    this.healthBarWidth = AnimationUtil.animate(hpWidth, this.healthBarWidth, 0.1);
                    Gui.drawRect2(x - 3.5f, y - 3.5f, x + 105.5f, y + 42.4f, new Color(10, 10, 10, 255).getRGB());
                    // RenderUtils.prepareScissorBox(x, y, x + 140.0f, (float) (y + this.hudHeight));
                    Gui.drawRect2(x - 3f, y - 3.2f, x + 104.8f, y + 41.8f, new Color(40, 40, 40, 255).getRGB());
                    Gui.drawRect2(x - 1.4f, y - 1.5f, x + 103.5f, y + 40.5f, new Color(74, 74, 74, 255).getRGB());
                    Gui.drawRect2(x - 1, y - 1, x + 103.0f, y + 40.0f, new Color(32, 32, 32, 255).getRGB());
                    Gui.drawRect2(x + 25.0f, y + 11.0f, x + 87f, y + 14.29f, new Color(105, 105, 105, 40).getRGB());
                    Gui.drawRect2(x + 25.0f, y + 11.0f, (float) (x + 27f + this.healthBarWidth), y + 14.29f, getColorFromPercentage(this.target.getHealth(), this.target.getMaxHealth()));
                    Fonts.font.drawStringWithShadow(this.target.getNameClear(), x + 24.8f, y + 1.9f, new Color(255, 255, 255).getRGB());
                    Fonts.font.drawStringWithShadow("l   " + "l   " + "l   " + "l   " + "l" + "" + "" + "", x + 30.0f, y + 10.2f, new Color(50, 50, 50).getRGB());
                    Fonts.font.drawStringWithShadow("HP:" + healthStr + " l " + "Dist:" + ((int) target.getDistanceToEntity(mc.thePlayer)), x - 11.2f + 44.0f - Fonts.font.getStringWidth(healthStr) / 2.0f, y + 17.0f, -1);
                    GuiInventory.drawEntityOnScreen((int) (x + 12f), (int) (y + 34.0f), 15, this.target.rotationYaw, this.target.rotationPitch, this.target);
                }
            } else {
                this.healthBarWidth = 92.0;
                this.hudHeight = 0.0;
                this.target = null;
            }
        }
    }

    static {
        COLOR = new Color(0, 0, 0, 180);
    }

    public static int getColorFromPercentage(float current, float max) {
        float percentage = (current / max) / 3;
        return Color.HSBtoRGB(percentage, 1.0F, 1.0F);
    }

    public static void prepareScissorBox(final float x, final float y, final float x2, final float y2) {
        final ScaledResolution scale = new ScaledResolution(mc);
        final int factor = scale.getScaleFactor();
        GL11.glScissor((int) (x * factor), (int) ((scale.getScaledHeight() - y2) * factor), (int) ((x2 - x) * factor),
                (int) ((y2 - y) * factor));
    }

    public boolean hasReached(final double milliseconds) {
        return this.getCurrentMS() - this.lastMS >= milliseconds;
    }

    public void reset() {
        this.lastMS = this.getCurrentMS();
    }

    public boolean delay(final float milliSec) {
        return this.getTime() - this.lastMS >= milliSec;
    }

    public long getTime() {
        return getCurrentMS() - this.lastMS;//System.nanoTime() / 1000000L;
    }

    private long getCurrentMS() {
        return System.nanoTime() / 1000000L;

    }
}
