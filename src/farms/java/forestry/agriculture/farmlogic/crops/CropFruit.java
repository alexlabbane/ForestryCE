package forestry.agriculture.farmlogic.crops;

import forestry.api.core.genetics.IFruitBearer;
import forestry.arboriculture.leaves.DefaultFruitLeavesBlock;
import forestry.core.platform.tile.TileUtil;
import forestry.core.platform.util.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class CropFruit extends Crop {
	public CropFruit(Level world, BlockPos position) {
		super(world, position);
	}

	@Override
	protected boolean isCrop(Level world, BlockPos pos) {
		// Try block entity first (for BlockForestryLeaves)
		IFruitBearer bearer = TileUtil.getTile(world, pos, IFruitBearer.class);
		if (bearer != null) {
			return bearer.hasFruit() && bearer.getRipeness() >= 1.0f;
		}
		// Fallback: DefaultFruitLeavesBlock has no tile entity, but is a fruit leaf
		// The block itself indicates ripe fruit (it converts to non-fruit after harvest)
		BlockState state = world.getBlockState(pos);
		return state.getBlock() instanceof DefaultFruitLeavesBlock;
	}

	@Override
	protected List<ItemStack> harvestBlock(Level level, BlockPos pos) {
		// Try block entity first
		IFruitBearer tile = TileUtil.getTile(level, pos, IFruitBearer.class);
		if (tile != null) {
			BlockUtil.sendDestroyEffects(level, pos, level.getBlockState(pos));
			return tile.pickFruit(ItemStack.EMPTY);
		}

		// Fallback: DefaultFruitLeavesBlock (no tile entity)
		BlockState state = level.getBlockState(pos);
		Block block = state.getBlock();
		if (block instanceof DefaultFruitLeavesBlock fruitLeaves) {
			BlockUtil.sendDestroyEffects(level, pos, state);
			return fruitLeaves.harvestFruit(level, pos, state);
		}

		return NonNullList.create();
	}
}
