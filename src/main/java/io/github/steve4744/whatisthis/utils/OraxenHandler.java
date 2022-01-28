/*
 * MIT License

Copyright (c) 2021 steve4744

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

 */
package io.github.steve4744.whatisthis.utils;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import io.th0rgal.oraxen.OraxenPlugin;
import io.th0rgal.oraxen.mechanics.provided.gameplay.block.BlockMechanic;
import io.th0rgal.oraxen.mechanics.provided.gameplay.block.BlockMechanicFactory;
import io.th0rgal.oraxen.mechanics.provided.gameplay.furniture.FurnitureFactory;
import io.th0rgal.oraxen.mechanics.provided.gameplay.furniture.FurnitureMechanic;
import io.th0rgal.oraxen.mechanics.provided.gameplay.noteblock.NoteBlockMechanic;
import io.th0rgal.oraxen.mechanics.provided.gameplay.noteblock.NoteBlockMechanicFactory;
import io.th0rgal.oraxen.shaded.customblockdata.CustomBlockData;
import static io.th0rgal.oraxen.mechanics.provided.gameplay.furniture.FurnitureMechanic.FURNITURE_KEY;

public class OraxenHandler {

	@SuppressWarnings("deprecation")
	public static boolean isOraxen(Block block) {
		if (block.getBlockData() instanceof NoteBlock) {
			final NoteBlock noteBlock = (NoteBlock) block.getBlockData();

			return NoteBlockMechanicFactory.getBlockMechanic((int) (noteBlock
                    .getInstrument().getType()) * 25 + (int) noteBlock.getNote().getId()
                    + (noteBlock.isPowered() ? 400 : 0) - 26) != null;

		} else if (block.getType() == Material.MUSHROOM_STEM) {
			final MultipleFacing blockFacing = (MultipleFacing) block.getBlockData();

			return BlockMechanicFactory.getBlockMechanic(BlockMechanic.getCode(blockFacing)) != null;

		} else if (block.getType() == Material.BARRIER) {
			final PersistentDataContainer customBlockData = new CustomBlockData(block, OraxenPlugin.get());
            return customBlockData.has(FURNITURE_KEY, PersistentDataType.STRING);
		}

		return false;
	}

	@SuppressWarnings("deprecation")
	public static String getDisplayName(Block block) {
		if (block.getBlockData() instanceof NoteBlock) {
			final NoteBlock noteBlock = (NoteBlock) block.getBlockData();
			final NoteBlockMechanic mech = NoteBlockMechanicFactory.getBlockMechanic((int) (noteBlock
					.getInstrument().getType()) * 25 + (int) noteBlock.getNote().getId()
					+ (noteBlock.isPowered() ? 400 : 0) - 26);

			return mech != null ? mech.getItemID() : "";

		} else if (block.getType() == Material.MUSHROOM_STEM) {
			final MultipleFacing blockFacing = (MultipleFacing) block.getBlockData();

			final BlockMechanic mech = BlockMechanicFactory.getBlockMechanic(BlockMechanic.getCode(blockFacing));
			
			return mech != null ? mech.getItemID() : "";

		} else if (block.getType() == Material.BARRIER) {
			final PersistentDataContainer customBlockData = new CustomBlockData(block, OraxenPlugin.get());

			final String mechanicID = customBlockData.get(FURNITURE_KEY, PersistentDataType.STRING);
			final FurnitureMechanic mech = (FurnitureMechanic) FurnitureFactory.getInstance().getMechanic(mechanicID);

			return mech != null ? mech.getItemID() : "";
		}

		return "";
	}

}
