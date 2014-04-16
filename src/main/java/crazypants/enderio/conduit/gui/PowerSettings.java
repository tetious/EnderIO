package crazypants.enderio.conduit.gui;

import net.minecraft.client.gui.GuiButton;
import crazypants.enderio.EnderIO;
import crazypants.enderio.conduit.IConduit;
import crazypants.enderio.conduit.packet.PacketExtractMode;
import crazypants.enderio.conduit.power.IPowerConduit;
import crazypants.enderio.gui.ColorButton;
import crazypants.enderio.gui.IconEIO;
import crazypants.enderio.gui.RedstoneModeButton;
import crazypants.enderio.machine.IRedstoneModeControlable;
import crazypants.enderio.machine.RedstoneControlMode;
import crazypants.util.DyeColor;
import crazypants.util.Lang;

public class PowerSettings extends BaseSettingsPanel {

  private static final int ID_REDSTONE_BUTTON = 796;

  private static final int ID_COLOR_BUTTON = 797;

  private IPowerConduit conduit;
  private RedstoneModeButton rsB;
  private ColorButton colorB;

  protected PowerSettings(final GuiExternalConnection gui, IConduit con) {
    super(IconEIO.WRENCH_OVERLAY_POWER, Lang.localize("itemPowerConduit.name"), gui, con);
    conduit = (IPowerConduit) con;

    int x = 38;
    int y = customTop;

    rsB = new RedstoneModeButton(gui, ID_REDSTONE_BUTTON, x, y, new IRedstoneModeControlable() {

      @Override
      public void setRedstoneControlMode(RedstoneControlMode mode) {
        RedstoneControlMode curMode = getRedstoneControlMode();
        conduit.setExtractionRedstoneMode(mode, gui.dir);
        if(curMode != mode) {
          EnderIO.packetPipeline.sendToServer(new PacketExtractMode(conduit, gui.dir));
        }

      }

      @Override
      public RedstoneControlMode getRedstoneControlMode() {
        return conduit.getExtractionRedstoneMode(gui.dir);
      }
    });

    x += rsB.getWidth() + gap;
    colorB = new ColorButton(gui, ID_COLOR_BUTTON, x, y);
    colorB.setToolTipHeading(Lang.localize("gui.conduit.redstone.signalColor"));
    colorB.setColorIndex(conduit.getExtractionSignalColor(gui.dir).ordinal());

  }

  @Override
  public void actionPerformed(GuiButton guiButton) {
    super.actionPerformed(guiButton);
    if(guiButton.id == ID_COLOR_BUTTON) {
      conduit.setExtractionSignalColor(gui.dir, DyeColor.values()[colorB.getColorIndex()]);
      EnderIO.packetPipeline.sendToServer(new PacketExtractMode(conduit, gui.dir));
    }
  }

  @Override
  protected void initCustomOptions() {
    super.initCustomOptions();
    rsB.onGuiInit();
    colorB.onGuiInit();
  }

  @Override
  public void deactivate() {
    super.deactivate();
    rsB.detach();
    colorB.detach();
  }
}