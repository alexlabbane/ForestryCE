package forestry.arboriculture.worldgen;

import forestry.api.arboriculture.ITreeGenData;
import forestry.api.core.genetics.IGenome;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;

public class TreeBlockTypeLeaf implements ITreeBlockType {
	private final ITreeGenData tree;
	private final IGenome genome;
	private final boolean useBlockEntities;

	public TreeBlockTypeLeaf(ITreeGenData tree, IGenome genome) {
		this(tree, genome, false);
	}

	public TreeBlockTypeLeaf(ITreeGenData tree, IGenome genome, boolean useBlockEntities) {
		this.tree = tree;
		this.genome = genome;
		this.useBlockEntities = useBlockEntities;
	}

	@Override
	public void setDirection(Direction facing) {
	}

	@Override
	public boolean setBlock(LevelAccessor level, BlockPos pos) {
		return this.tree.setLeaves(this.genome, level, pos, level.getRandom(), this.useBlockEntities);
	}
}
