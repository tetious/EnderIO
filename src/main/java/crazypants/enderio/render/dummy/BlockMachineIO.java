package crazypants.enderio.render.dummy;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import crazypants.enderio.render.IOMode;

public class BlockMachineIO extends Block {

  public static BlockMachineIO block;

  public static String name() {
    return "machineIO";
  }

  public static void create() {
    GameRegistry.registerBlock(block = new BlockMachineIO(), null, name());
  }

  public BlockMachineIO() {
    super(Material.circuits);
    setUnlocalizedName(name());
    this.setDefaultState(this.blockState.getBaseState().withProperty(IOMode.IO, IOMode.get(EnumFacing.DOWN, IOMode.EnumIOMode.NONE)));
    setCreativeTab(null);
    disableStats();
  }

  @Override
  protected BlockState createBlockState() {
    return new BlockState(this, new IProperty[] { IOMode.IO });
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    return getDefaultState();
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return 0;
  }

  @Override
  public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
    return getDefaultState();
  }

  @Override
  @SideOnly(Side.CLIENT)
  public EnumWorldBlockLayer getBlockLayer() {
    return EnumWorldBlockLayer.CUTOUT;
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
    if (tab != null) {
      super.getSubBlocks(itemIn, tab, list);
    }
  }

}
