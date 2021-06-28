package client.module.player;

import client.Client;
import client.Hero.settings.Setting;
import client.event.Event;
import client.event.EventTarget;
import client.event.events.Event2D;
import client.event.events.EventSafewalk;
import client.event.events.EventUpdate;
import client.font.Fonts;
import client.module.Category;
import client.module.Module;
import client.utils.client.BlockUtils;
import client.utils.client.RotationUtils2;
import client.values.BooleanValue;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovementInput;
import net.minecraft.util.Vec3;

import java.awt.*;
import java.util.List;
import java.util.Random;

/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*    *
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */

/*     */
/*     */ public class Scaffold extends Module
        /*     */ {
    private float oldYaw;
    /*     */   private float oldPitch;


    /*     */   private List<Block> invalid;
    /*     */   private BlockData blockData;
    /*     */   private int ticks;
    /*     */   private int ticks1;
    /*     */   public static double moveSpeed;
    /*  48 */   private final boolean shouldDelay = false;
    /*     */   private int ticks2;
    /*     */   private int slot;
    /*  51 */   private final boolean shouldHandleSpeed = false;
    /*  52 */   private final boolean shouldHandleLongjump = false;
    /*  53 */   private double offsetToUse = 0.0D;
    /*  54 */ double groundy = 0.0D;
    /*  55 */   public static boolean shouldPerform = true;
    private static final Minecraft mc = Minecraft.getMinecraft();

    /*  59 */   public final Timer timer = new Timer();
    /*  60 */   public final Timer towertimer = new Timer();
    /*  61 */   public boolean hypixel = false;
    /*     */
    /*     */   private BlockData blockBelowData;

    /*     */
    /*
    /*     */
    /*     */
    public Scaffold() {
        /*  74 */
        super("Scaffold", 0, Category.PLAYER);
//        this.addOption(this.AAC = new BooleanValue("AAC", false));
//        this.addOption(this.TOWER = new BooleanValue("Tower", true));
//        this.addOption(this.Swing = new BooleanValue("Swing", true));
//        this.addOption(this.SafeWalk = new BooleanValue("SafeWalk", true));
//        this.addOption(this.autojump = new BooleanValue("AutoJump", true));



        /*     */
    }
@Override
public void setup() {
    Client.instance.settingsManager.rSetting(new Setting("AAC", this, false));
    Client.instance.settingsManager.rSetting(new Setting("Tower", this, false));
    Client.instance.settingsManager.rSetting(new Setting("Swing", this, false));
    Client.instance.settingsManager.rSetting(new Setting("SafeWalk", this, false));
}
    /*     */
    /*     */
    @Override
    /*     */ public void onEnable()
    /*     */ {
        /*  79 */
        this.invalid = java.util.Arrays.asList(Blocks.air, Blocks.water, Blocks.fire, Blocks.flowing_water, Blocks.lava,
                /*  80 */       Blocks.flowing_lava, Blocks.ice, Blocks.packed_ice, Blocks.noteblock, Blocks.chest, Blocks.anvil);
        /*  81 */
        super.onEnable();
        /*     */
    }


    /*     */
    /*     */
    @EventTarget
    /*     */ public void onWalk(EventSafewalk event) {
        /*  86 */
        if (Client.instance.settingsManager.getSettingByName("SafeWalk").getValBoolean() ) {
            /*  87 */
            event.setCancelled(true);
            /*     */
        }
        /*     */
    }

    @EventTarget
    public void onRender(Event2D e) {
        ScaledResolution sr = new ScaledResolution(mc);
        int blockCount = getBlockCount();
        Color color = new Color(0, 255, 0);
        if (this.getBlockCount() > 128) {
            color = new Color(0, 255, 0);
        }
        if (this.getBlockCount() < 128) {
            color = new Color(255, 255, 0);
        }
        if (this.getBlockCount() < 64) {
            color = new Color(255, 0, 0);
        }
        Fonts.font.drawStringWithShadow(blockCount + "", (sr.getScaledWidth() / 2 - -10) - Fonts.font.getStringWidth(blockCount + "") / 2, (sr.getScaledHeight() / 2 + 30) + -21, color.getRGB());
    }

    public int getBlockCount() {
        int blockCount = 0;
        for (int i = 0; i < 45; ++i) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                final Item item = is.getItem();
                if (is.getItem() instanceof ItemBlock && canIItemBePlaced(item)) {
                    blockCount += is.stackSize;
                }
            }
        }
        return blockCount;
    }

    /*     */
    /*     *
        /*     */
    /*     */
    /*     */
    @EventTarget
    /*     */ public void onUpdate(EventUpdate event) {
            /* 107 */
            this.hypixel = true;
            /*     */
            /* 109 */
            if (Client.instance.settingsManager.getSettingByName("AAC").getValBoolean() ) {
                /* 110 */
                mc.thePlayer.setSprinting(false);
                /*     */
            /*     */
            /* 113 */
            int block = blockInHotbar();
            /*     */
            /* 115 */
            if (block == 0)
                /*     */ {
                /* 117 */
                if (grabBlock())
                    /*     */ {
                    /* 119 */
                    block = blockInHotbar();
                    /*     */
                    /* 121 */
                    if (block != 0) {
                    }
                    /*     */
                    /*     */
                }
                /*     */
                else
                    /*     */ {
                    /* 126 */
                    return;
                    /*     */
                }
                /*     */
            }
        }
        /*     */
        /*     */
        /*     */
        /* 132 */
        if (this.hypixel)
            /*     */ {
            /*     */
            /*     */
            /* 136 */
            if (event.getState() == Event.State.PRE)
                /*     */ {
                /* 138 */
                int tempSlot = getBlockSlot();
                /* 139 */
                this.blockData = null;
                /* 140 */
                this.slot = -1;
                /*     */
                /* 142 */
                if (tempSlot == -1)
                    /*     */ {
                    /* 144 */
                    return;
                    /*     */
                }
                /*     */
                /*     */
                /* 148 */
                if (!Minecraft.getMinecraft().gameSettings.keyBindJump.pressed)
                    /*     */ {
                    /*     */
                }
                /*     */
                /*     */
                /* 154 */
                if (tempSlot != -1) {
                    /* 155 */
                    double forward = MovementInput.moveForward;
                    /* 156 */
                    double strafe = MovementInput.moveStrafe;
                    /*     */
                    /* 158 */
                    double x2 = Math.cos(Math.toRadians(mc.thePlayer.rotationYaw + 90.0F));
                    /* 159 */
                    double z2 = Math.sin(Math.toRadians(mc.thePlayer.rotationYaw + 90.0F));
                    /*     */
                    /* 161 */
                    Random random = new Random();
                    /*     */
                    /* 163 */
                    double xOffset = MovementInput.moveForward * (
                            /* 164 */             this.hypixel ? 0.0D : mc.gameSettings.keyBindJump.pressed ? 1.0D : this.offsetToUse * x2);
                    /* 165 */
                    double zOffset = MovementInput.moveForward * (
                            /* 166 */             this.hypixel ? 0.0D : mc.gameSettings.keyBindJump.pressed ? 1.0D : this.offsetToUse * z2);
                    /*     */
                    /* 168 */
                    double x = mc.thePlayer.posX + (
                            /* 169 */             this.hypixel ? 0.0D : mc.gameSettings.keyBindJump.pressed ? 0.1D : xOffset);
                    /* 170 */
                    double y = mc.thePlayer.posY - 0.1D;
                    /* 171 */
                    double z = mc.thePlayer.posZ + (
                            /* 172 */             this.hypixel ? 0.0D : mc.gameSettings.keyBindJump.pressed ? 0.1D : zOffset);
                    /* 173 */
                    float yaww = mc.thePlayer.rotationYaw;
                    /* 174 */
                    x += forward * 0.45D * Math.cos(Math.toRadians(yaww + 90.0F)) +
                            /* 175 */             strafe * 0.45D * Math.sin(Math.toRadians(yaww + 90.0F));
                    /* 176 */
                    z += forward * 0.45D * Math.sin(Math.toRadians(yaww + 90.0F)) -
                            /* 177 */             strafe * 0.45D * Math.cos(Math.toRadians(yaww + 90.0F));
                    /*     */
                    /* 179 */
                    BlockPos blockBelow1 = new BlockPos(x, y, z);
                    /*     */
                    /* 181 */
                    if (mc.theWorld.getBlockState(blockBelow1).getBlock() == Blocks.air)
                        /*     */ {
                        /* 183 */
                        this.offsetToUse = 0.0D;
                        /* 184 */
                        this.blockData = getBlockData(blockBelow1);
                        /* 185 */
                        this.slot = tempSlot;
                        /*     */
                        /* 187 */
                        if (this.blockData != null)
                            /*     */ {
                            /* 189 */
                            float yaw = BlockUtils.aimAtBlock(this.blockData.position)[0];
                            /* 190 */
                            float pitch = BlockUtils.aimAtBlock(this.blockData.position)[1];
                            /*     */
                            /* 192 */
                            /* 194 */
                            this.oldYaw = yaw;
                            /* 195 */
                            this.oldPitch = pitch;
                            /*     */
                        }
                        /*     */
                    }
                    /*     */
                }
                /*     */
            }
            /*     */
            /*     */
            /* 202 */
            if ((event.getState() == Event.State.POST) && (this.blockData != null))
                /*     */ {
                /*     */
                int possible;
                /* 205 */
                int bl = possible = mc.thePlayer.inventory.currentItem != this.slot ? 1 : 0;
                /* 206 */
                boolean wasSprintng = mc.thePlayer.isSprinting();
                /*     */
                /* 208 */
                if (wasSprintng) {
                    /* 209 */
                    mc.thePlayer.setSprinting(true);
                    /*     */
                }
                /*     */
                /* 212 */
                int oldSlot = mc.thePlayer.inventory.currentItem;
                /*     */
                /* 214 */
                if (wasSprintng)
                    /*     */ {
                    /* 216 */
                    mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(this.slot));
                    /*     */
                }
                /*     */
                /*     */
                /*     */
                /*     */
                /*     */
                /* 223 */
                if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventoryContainer.getSlot(36 + this.slot).getStack(), this.blockData.position, this.blockData.face, new Vec3(this.blockData.position.getX(), this.blockData.position.getY(), this.blockData.position.getZ())))
                    /*     */ {
                    /* 225 */
                    if ((Client.instance.settingsManager.getSettingByName("Tower").getValBoolean()))
                        /* 226 */
                        /*     */ {
                        /* 228 */
                        if (mc.thePlayer.movementInput.jump) {
                            /* 229 */
                            mc.thePlayer.motionY = 0.4199382043D;
                            /* 230 */
                            mc.thePlayer.motionX = 0.0D;
                            /* 231 */
                            mc.thePlayer.motionZ = 0.0D;
                            /* 233 */
                            mc.thePlayer.motionY = -0.279929103D;
                            /* 236 */
                            mc.thePlayer.motionY = 0.4199382043D;
                            /*     */
                        }
                        /*     */
                        /*     */
                        /*     */
                    }
                    /*     */
                    /*     */
                    /* 243 */
                    if (Client.instance.settingsManager.getSettingByName("Swing").getValBoolean()) {
                        /* 244 */
                        mc.thePlayer.swingItem();
                        /*     */
                    } else {
                        /* 246 */
                        mc.getNetHandler().getNetworkManager().sendPacket(new net.minecraft.network.play.client.C0APacketAnimation());
                        /*     */
                    }
                    /*     */
                }
                /*     */
                /* 250 */
                if (wasSprintng)
                    /*     */ {
                    /*     */
                    /* 253 */
                    mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                    /*     */
                }
                /*     */
                /*     */
                /* 257 */
                if ((wasSprintng) && (canSprint()) && (mc.gameSettings.keyBindForward.pressed) && (!Client.instance.settingsManager.getSettingByName("AAC").getValBoolean()))
                    /*     */ {
                    /* 259 */
                    mc.thePlayer.setSprinting(true);
                    /*     */
                }
                /*     */
                /*     */
            }
            /*     */
            else
                /*     */ {
                /* 265 */
                int tempSlot = getBlockSlot();
                /*     */
                /* 267 */
                this.blockData = null;
                /* 268 */
                this.slot = -1;
                /*     */
                /* 270 */
                if (tempSlot == -1)
                    /*     */ {
                    /* 272 */
                    return;
                    /*     */
                }
                /*     */
                /*     */
                /* 276 */
                double x = mc.thePlayer.posX;
                /* 277 */
                double y = mc.thePlayer.posY - 0.1D;
                /* 278 */
                double z = mc.thePlayer.posZ;
                /*     */
                /* 280 */
                BlockPos blockBelow1 = new BlockPos(x, y, z);
                /* 281 */
                BlockPos belowPlayer = new BlockPos(mc.thePlayer).down();
                /*     */
                /* 283 */
                boolean wasSprintng = mc.thePlayer.isSprinting();
                /*     */
                /* 285 */
                if (wasSprintng)
                    /*     */ {
                    /* 287 */
                    mc.thePlayer.setSprinting(true);
                    /*     */
                }
                /*     */
                /*     */
                /* 291 */
                if (event.getState() == Event.State.PRE)
                    /*     */ {
                    /* 293 */
                    if (!Minecraft.getMinecraft().gameSettings.keyBindJump.pressed)
                        /*     */ {
                        /*     */
                        /*     */
                    }
                    /*     */
                    /*     */
                    /*     */
                }
                /* 301 */
                else if (getMaterial(belowPlayer).isReplaceable())
                    /*     */ {
                    /* 303 */
                    this.blockData = getBlockData(belowPlayer);
                    /* 304 */
                    this.slot = tempSlot;
                    /*     */
                    /* 306 */
                    if (this.blockData != null)
                        /*     */ {
                        /*     */
                        int possible;
                        /* 309 */
                        int bl = possible = mc.thePlayer.inventory.currentItem != this.slot ? 1 : 0;
                        /*     */
                        /* 311 */
                        int oldSlot = mc.thePlayer.inventory.currentItem;
                        /*     */
                        /* 313 */
                        if (wasSprintng)
                            /*     */ {
                            /* 315 */
                            mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(this.slot));
                            /*     */
                        }
                        /*     */
                        /*     */
                        /* 319 */
                        placeBlockHypixel(belowPlayer);
                        /*     */
                        /* 321 */
                        if (wasSprintng)
                            /*     */ {
                            /* 323 */
                            mc.thePlayer.sendQueue.addToSendQueue(
                                    /* 324 */                 new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                            /*     */
                        }
                        /*     */
                    }
                    /*     */
                }
                /*     */
                /* 334 */
                if ((wasSprintng) && (canSprint()) && (mc.gameSettings.keyBindForward.pressed))
                    /*     */ {
                    /* 336 */
                    mc.thePlayer.setSprinting(true);
                    /*     */
                }
                /*     */
            }
            /*     */
        }
        /*     */
    }

    /*     */
    /*     */
    /*     */
    private boolean canSprint()
    /*     */ {
        /* 345 */
        return (!mc.thePlayer.isCollidedHorizontally) && (!mc.thePlayer.isSneaking()) && (
                /* 346 */       mc.thePlayer.getFoodStats().getFoodLevel() > 6);
        /*     */
    }

    /*     */
    /*     */
    /*     */
    public static double getBaseMoveSpeed()
    /*     */ {
        /* 352 */
        double baseSpeed = 0.2873D;
        /*     */
        /* 354 */
        if ((Minecraft.getMinecraft().theWorld != null) &&
                /* 355 */       (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSpeed)))
            /*     */ {
            /* 357 */
            int amplifier = Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            /* 358 */
            baseSpeed *= (1.0D + 0.2D * (amplifier + 1));
            /*     */
        }
        /*     */
        /*     */
        /* 362 */
        return baseSpeed;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    public boolean placeBlockHypixel(BlockPos pos)
    /*     */ {
        /* 368 */
        Vec3 eyesPos = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(),
                /* 369 */       mc.thePlayer.posZ);
        /*     */
        /*     */
        EnumFacing[] values;
        /*     */
        /* 373 */
        int length = (values = EnumFacing.values()).length;
        for (int i = 0; i < length; i++)
            /*     */ {
            /* 375 */
            EnumFacing side = values[i];
            /* 376 */
            BlockPos neighbor = pos.offset(side);
            /* 377 */
            EnumFacing side2 = side.getOpposite();
            /*     */
            /*     */
            /* 380 */
            if (eyesPos.squareDistanceTo(new Vec3(pos).addVector(0.5D, 0.5D, 0.5D)) < eyesPos.squareDistanceTo(new Vec3(neighbor).addVector(0.5D, 0.5D, 0.5D)))
                /*     */ {
                /* 382 */
                if (canBeClicked(neighbor))
                    /*     */ {
                    /* 384 */
                    Vec3 hitVec = new Vec3(neighbor).addVector(0.5D, 0.5D, 0.5D)
/* 385 */.add(new Vec3(side2.getDirectionVec()).scale(0.5D));
                    /*     */
                    /* 387 */
                    if (eyesPos.squareDistanceTo(hitVec) <= 18.0624D) {
                        /* 388 */
                        RotationUtils2.getBlockRotations(hitVec);
                        /*     */
                        /* 390 */
                        mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld,
                                /* 391 */               mc.thePlayer.inventoryContainer.getSlot(36 + this.slot).getStack(), neighbor, side2,
                                /* 392 */               hitVec);
                        /*     */
                        /* 394 */
                        if ((Client.instance.settingsManager.getSettingByName("Tower").getValBoolean() ) &&
                                /* 395 */               (!mc.thePlayer.isMoving()))
                            /*     */ {
                            /* 397 */
                            if (mc.thePlayer.movementInput.jump) {
                                /* 398 */
                                mc.timer.timerSpeed = 0.8765432F;
                                /* 399 */
                                mc.thePlayer.motionY = 0.4199382043D;
                                /* 400 */
                                mc.thePlayer.motionX = 0.0D;
                                /* 401 */
                                mc.thePlayer.motionZ = 0.0D;
                                /* 403 */
                                mc.thePlayer.motionY = -0.279929103D;
                                /* 406 */
                                mc.thePlayer.motionY = 0.4199382043D;
                                /*     */
                            }
                            /*     */
                        }
                        /*     */
                    }
                    /* 410 */
                    else if (!mc.thePlayer.movementInput.jump) {
                        /* 411 */
                        mc.timer.timerSpeed = 1.0F;
                        /*     */
                        /*     */
                        /*     */
                        /*     */
                        /* 417 */
                        if (Client.instance.settingsManager.getSettingByName("Tower").getValBoolean() ) {
                            /* 418 */
                            mc.thePlayer.swingItem();
                            /*     */
                        } else {
                            /* 420 */
                            mc.getNetHandler().getNetworkManager().sendPacket(new net.minecraft.network.play.client.C0APacketAnimation());
                            /*     */
                        }
                        /* 422 */
                        /*     */
                        /* 424 */
                        return true;
                        /*     */
                    }
                    /*     */
                }
                /*     */
            }
            /*     */
        }
        /*     */
        /*     */
        /*     */
        /* 434 */
        return false;
        /*     */
    }

    /*     */
    /*     */
    public static float[] getRotationsBlock(BlockPos pos, EnumFacing facing)
    /*     */ {
        /* 439 */
        double d0 = pos.getX() - mc.thePlayer.posX;
        /* 440 */
        double d1 = pos.getY() - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        /* 441 */
        double d2 = pos.getZ() - mc.thePlayer.posZ;
        /* 442 */
        double d3 = net.minecraft.util.MathHelper.sqrt_double(d0 * d0 + d2 * d2);
        /* 443 */
        float f = (float) (Math.atan2(d2, d0) * 180.0D / 3.141592653589793D) - 90.0F;
        /* 444 */
        float f1 = (float) -(Math.atan2(d1, d3) * 180.0D / 3.141592653589793D);
        /* 445 */
        return new float[]{f, f1};
        /*     */
    }

    /*     */
    /*     */
    public boolean canBeClicked(BlockPos pos)
    /*     */ {
        /* 450 */
        return getBlock(pos).canCollideCheck(getState(pos), false);
        /*     */
    }

    /*     */
    /*     */
    /*     */
    public IBlockState getState(BlockPos pos)
    /*     */ {
        /* 456 */
        return mc.theWorld.getBlockState(pos);
        /*     */
    }

    /*     */
    /*     */
    /*     */
    public Block getBlock(BlockPos pos)
    /*     */ {
        /* 462 */
        return getState(pos).getBlock();
        /*     */
    }

    /*     */
    /*     */
    /*     */
    public Material getMaterial(BlockPos pos)
    /*     */ {
        /* 468 */
        return getBlock(pos).getMaterial();
        /*     */
    }

    /*     */
    /*     */
    private void grabBlocks()
    /*     */ {
        /* 474 */
        for (int i = 9; i < 36; i++)
            /*     */ {
            /* 476 */
            ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            /*     */
            /* 478 */
            if ((stack != null) && ((stack.getItem() instanceof ItemBlock)) && (stack.stackSize >= 1) &&
                    /* 479 */         (Block.getBlockFromItem(stack.getItem()).getDefaultState().getBlock().isFullBlock()))
                /*     */ {
                /* 481 */
                PlayerControllerMP playerController = mc.playerController;
                /*     */
                /* 483 */
                int windowId = mc.thePlayer.openContainer.windowId;
                /* 484 */
                int slotId = i;
                /* 485 */
                int p_78753_3_ = 1;
                /* 486 */
                int p_78753_4_ = 2;
                /*     */
                /* 488 */
                playerController.windowClick(windowId, slotId, 1, 2, mc.thePlayer);
                /*     */
                /* 490 */
                break;
                /*     */
            }
            /*     */
        }
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    private int getBlockSlot()
    /*     */ {
        /* 500 */
        int i = 36;
        /*     */
        /* 502 */
        while (i < 45)
            /*     */ {
            /* 504 */
            ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            /*     */
            /* 506 */
            if ((itemStack != null) && ((itemStack.getItem() instanceof ItemBlock)) && (itemStack.stackSize >= 1) &&
                    /* 507 */         (Block.getBlockFromItem(itemStack.getItem()).getDefaultState().getBlock().isFullBlock()))
                /*     */ {
                /* 509 */
                return i - 36;
                /*     */
            }
            /*     */
            /*     */
            /* 513 */
            i++;
            /*     */
        }
        /*     */
        /*     */
        /* 517 */
        return -1;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    private int getTotalBlocks()
    /*     */ {
        /* 523 */
        int totalCount = 0;
        /* 524 */
        int i = 9;
        /*     */
        /* 526 */
        while (i < 45)
            /*     */ {
            /* 528 */
            ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            /*     */
            /* 530 */
            if ((itemStack != null) && ((itemStack.getItem() instanceof ItemBlock)) && (itemStack.stackSize >= 1))
                /*     */ {
                /* 532 */
                totalCount += itemStack.stackSize;
                /*     */
            }
            /*     */
            /*     */
            /* 536 */
            i++;
            /*     */
        }
        /*     */
        /*     */
        /* 540 */
        return totalCount;
        /*     */
    }

    /*     */
    /*     */
    private BlockData getBlockData(BlockPos pos)
    /*     */ {
        /* 545 */
        if (!this.invalid.contains(mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock())) {
            /* 546 */
            return new BlockData(pos.add(0, -1, 0), EnumFacing.UP);
            /*     */
        }
        /* 548 */
        if (!this.invalid.contains(mc.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock())) {
            /* 549 */
            return new BlockData(pos.add(-1, 0, 0), EnumFacing.EAST);
            /*     */
        }
        /* 551 */
        if (!this.invalid.contains(mc.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock())) {
            /* 552 */
            return new BlockData(pos.add(1, 0, 0), EnumFacing.WEST);
            /*     */
        }
        /* 554 */
        if (!this.invalid.contains(mc.theWorld.getBlockState(pos.add(0, 0, -1)).getBlock())) {
            /* 555 */
            return new BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH);
            /*     */
        }
        /* 557 */
        if (!this.invalid.contains(mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock())) {
            /* 558 */
            return new BlockData(pos.add(0, 0, 1), EnumFacing.NORTH);
            /*     */
        }
        /* 560 */
        BlockPos add = pos.add(-1, 0, 0);
        /* 561 */
        if (!this.invalid.contains(mc.theWorld.getBlockState(add.add(-1, 0, 0)).getBlock())) {
            /* 562 */
            return new BlockData(add.add(-1, 0, 0), EnumFacing.EAST);
            /*     */
        }
        /* 564 */
        if (!this.invalid.contains(mc.theWorld.getBlockState(add.add(1, 0, 0)).getBlock())) {
            /* 565 */
            return new BlockData(add.add(1, 0, 0), EnumFacing.WEST);
            /*     */
        }
        /* 567 */
        if (!this.invalid.contains(mc.theWorld.getBlockState(add.add(0, 0, -1)).getBlock())) {
            /* 568 */
            return new BlockData(add.add(0, 0, -1), EnumFacing.SOUTH);
            /*     */
        }
        /* 570 */
        if (!this.invalid.contains(mc.theWorld.getBlockState(add.add(0, 0, 1)).getBlock())) {
            /* 571 */
            return new BlockData(add.add(0, 0, 1), EnumFacing.NORTH);
            /*     */
        }
        /* 573 */
        BlockPos add2 = pos.add(1, 0, 0);
        /* 574 */
        if (!this.invalid.contains(mc.theWorld.getBlockState(add2.add(-1, 0, 0)).getBlock())) {
            /* 575 */
            return new BlockData(add2.add(-1, 0, 0), EnumFacing.EAST);
            /*     */
        }
        /* 577 */
        if (!this.invalid.contains(mc.theWorld.getBlockState(add2.add(1, 0, 0)).getBlock())) {
            /* 578 */
            return new BlockData(add2.add(1, 0, 0), EnumFacing.WEST);
            /*     */
        }
        /* 580 */
        if (!this.invalid.contains(mc.theWorld.getBlockState(add2.add(0, 0, -1)).getBlock())) {
            /* 581 */
            return new BlockData(add2.add(0, 0, -1), EnumFacing.SOUTH);
            /*     */
        }
        /* 583 */
        if (!this.invalid.contains(mc.theWorld.getBlockState(add2.add(0, 0, 1)).getBlock())) {
            /* 584 */
            return new BlockData(add2.add(0, 0, 1), EnumFacing.NORTH);
            /*     */
        }
        /* 586 */
        BlockPos add3 = pos.add(0, 0, -1);
        /* 587 */
        if (!this.invalid.contains(mc.theWorld.getBlockState(add3.add(-1, 0, 0)).getBlock())) {
            /* 588 */
            return new BlockData(add3.add(-1, 0, 0), EnumFacing.EAST);
            /*     */
        }
        /* 590 */
        if (!this.invalid.contains(mc.theWorld.getBlockState(add3.add(1, 0, 0)).getBlock())) {
            /* 591 */
            return new BlockData(add3.add(1, 0, 0), EnumFacing.WEST);
            /*     */
        }
        /* 593 */
        if (!this.invalid.contains(mc.theWorld.getBlockState(add3.add(0, 0, -1)).getBlock())) {
            /* 594 */
            return new BlockData(add3.add(0, 0, -1), EnumFacing.SOUTH);
            /*     */
        }
        /* 596 */
        if (!this.invalid.contains(mc.theWorld.getBlockState(add3.add(0, 0, 1)).getBlock())) {
            /* 597 */
            return new BlockData(add3.add(0, 0, 1), EnumFacing.NORTH);
            /*     */
        }
        /* 599 */
        BlockPos add4 = pos.add(0, 0, 1);
        /* 600 */
        if (!this.invalid.contains(mc.theWorld.getBlockState(add4.add(-1, 0, 0)).getBlock())) {
            /* 601 */
            return new BlockData(add4.add(-1, 0, 0), EnumFacing.EAST);
            /*     */
        }
        /* 603 */
        if (!this.invalid.contains(mc.theWorld.getBlockState(add4.add(1, 0, 0)).getBlock())) {
            /* 604 */
            return new BlockData(add4.add(1, 0, 0), EnumFacing.WEST);
            /*     */
        }
        /* 606 */
        if (!this.invalid.contains(mc.theWorld.getBlockState(add4.add(0, 0, -1)).getBlock())) {
            /* 607 */
            return new BlockData(add4.add(0, 0, -1), EnumFacing.SOUTH);
            /*     */
        }
        /* 609 */
        if (!this.invalid.contains(mc.theWorld.getBlockState(add4.add(0, 0, 1)).getBlock())) {
            /* 610 */
            return new BlockData(add4.add(0, 0, 1), EnumFacing.NORTH);
            /*     */
        }
        /* 612 */
        return null;
        /*     */
    }

    /*     */
    /*     */   private class BlockData
            /*     */ {
        /*     */     public BlockPos position;
        /*     */     public EnumFacing face;

        /*     */
        /*     */
        public BlockData(BlockPos position, EnumFacing face) {
            /* 621 */
            this.position = position;
            /* 622 */
            this.face = face;
            /*     */
        }
        /*     */
    }

    /*     */
    /*     */
    private boolean grabBlock()
    /*     */ {
        /* 628 */
        for (int i = 0; i < 36; i++) {
            /* 629 */
            ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            /* 630 */
            if ((stack != null) && ((stack.getItem() instanceof ItemBlock))) {
                /* 631 */
                for (int x = 36; x < 45; x++) {
                    /*     */
                    try {
                        /* 633 */
                        net.minecraft.item.Item localItem = mc.thePlayer.inventoryContainer.getSlot(x).getStack().getItem();
                        /*     */
                    } catch (NullPointerException ex) {
                        /* 635 */
                        swap(i, x - 36);
                        /* 636 */
                        return true;
                        /*     */
                    }
                    /*     */
                }
                /* 639 */
                swap(i, 1);
                /* 640 */
                return true;
                /*     */
            }
            /*     */
        }
        /*     */
        /* 644 */
        return false;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    private void swap(int slot, int hotbarNum)
    /*     */ {
        /* 650 */
        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, hotbarNum, 2, mc.thePlayer);
        /*     */
    }

    /*     */
    /*     */
    /*     */
    private int blockInHotbar()
    /*     */ {
        /* 656 */
        for (int i = 36; i < 45; i++) {
            /* 657 */
            ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            /* 658 */
            if ((stack != null) && ((stack.getItem() instanceof ItemBlock))) {
                /* 659 */
                return i;
                /*     */
            }
            /*     */
        }
        /* 662 */
        return 0;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    public void onDisable()
    /*     */ {
        /* 668 */
        this.oldYaw = 0.0F;
        /* 669 */
        this.oldPitch = 0.0F;
        /* 670 */
        super.onDisable();
        /*     */
    }

    private boolean canIItemBePlaced(Item item) {
        if (Item.getIdFromItem(item) == 116)
            return false;
        if (Item.getIdFromItem(item) == 30)
            return false;
        if (Item.getIdFromItem(item) == 31)
            return false;
        if (Item.getIdFromItem(item) == 175)
            return false;
        if (Item.getIdFromItem(item) == 28)
            return false;
        if (Item.getIdFromItem(item) == 27)
            return false;
        if (Item.getIdFromItem(item) == 66)
            return false;
        if (Item.getIdFromItem(item) == 157)
            return false;
        if (Item.getIdFromItem(item) == 31)
            return false;
        if (Item.getIdFromItem(item) == 6)
            return false;
        if (Item.getIdFromItem(item) == 31)
            return false;
        if (Item.getIdFromItem(item) == 32)
            return false;
        if (Item.getIdFromItem(item) == 140)
            return false;
        if (Item.getIdFromItem(item) == 390)
            return false;
        if (Item.getIdFromItem(item) == 37)
            return false;
        if (Item.getIdFromItem(item) == 38)
            return false;
        if (Item.getIdFromItem(item) == 39)
            return false;
        if (Item.getIdFromItem(item) == 40)
            return false;
        if (Item.getIdFromItem(item) == 69)
            return false;
        if (Item.getIdFromItem(item) == 50)
            return false;
        if (Item.getIdFromItem(item) == 75)
            return false;
        if (Item.getIdFromItem(item) == 76)
            return false;
        if (Item.getIdFromItem(item) == 54)
            return false;
        if (Item.getIdFromItem(item) == 130)
            return false;
        if (Item.getIdFromItem(item) == 146)
            return false;
        if (Item.getIdFromItem(item) == 342)
            return false;
        if (Item.getIdFromItem(item) == 12)
            return false;
        if (Item.getIdFromItem(item) == 77)
            return false;
        return Item.getIdFromItem(item) != 143;
    }

    /*     */
}


