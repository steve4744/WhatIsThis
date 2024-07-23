/*
 * MIT License

Copyright (c) 2022 steve4744

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
package io.github.steve4744.whatisthis.data;

import java.util.Map;

public enum ItemDropRanges {
	ACACIA_LEAVES(Map.of("Acacia_Sapling", "0-1", "Stick", "0 -> 2")),
	ATTACHED_MELON_STEM(Map.of("Melon_Seeds", "0 -> 3")),
	ATTACHED_PUMPKIN_STEM(Map.of("Pumpkin_Seeds", "0 -> 3")),
	AZALEA_LEAVES(Map.of("Azalea", "0 -> 1", "Stick", "0 -> 2")),
	BIRCH_LEAVES(Map.of("Birch_Sapling", "0 -> 1", "Stick", "0 -> 2")),
	BROWN_MUSHROOM_BLOCK(Map.of("Brown_Mushroom", "0 -> 2")),
	CHISELED_BOOKSHELF(Map.of("Book", "0 -> 6")),
	CHORUS_PLANT(Map.of("Chorus_Fruit", "0 -> 1")),
	COPPER_ORE(Map.of("Raw_copper", "2 -> 5")),
	DARK_OAK_LEAVES(Map.of("Dark_Oak_Sapling", "0 -> 1", "Apple", "0 -> 1", "Stick", "0 -> 2")),
	DEAD_BUSH(Map.of("Stick", "0 -> 2")),
	DEEPSLATE_COPPER_ORE(Map.of("Raw_copper", "2 -> 5")),
	DEEPSLATE_LAPIS_ORE(Map.of("Lapis_Lazuli", "4 -> 9")),
	DEEPSLATE_REDSTONE_ORE(Map.of("Redstone", "4 -> 9")),
	FERN(Map.of("Wheat_Seeds", "0 -> 1")),
	FLOWERING_AZALEA_LEAVES(Map.of("Flowering_Azalea", "0 -> 1", "Stick", "0 -> 2")),
	GILDED_BLACKSTONE(Map.of("Gilded_Blackstone", "0 -> 1", "Gold_Nugget", "0, 2 -> 5")),
	GLOWSTONE(Map.of("Glowstone_Dust", "2 -> 4")),
	GRASS(Map.of("Wheat_Seeds", "0 -> 1")),
	GRAVEL(Map.of("Gravel", "0 -> 1", "Flint", "0 -> 1")),
	JUNGLE_LEAVES(Map.of("Jungle_Sapling", "0 -> 1", "Stick", "0 -> 2")),
	LAPIS_ORE(Map.of("Lapis_Lazuli", "4 -> 9")),
	LARGE_FERN(Map.of("Wheat_Seeds", "0 -> 1")),
	MELON(Map.of("Melon_Slice", "3 -> 7")),
	MELON_STEM(Map.of("Melon_Seeds", "0 -> 3")),
	NETHER_GOLD_ORE(Map.of("Gold_Nugget", "2 -> 6")),
	OAK_LEAVES(Map.of("Oak_Sapling", "0 -> 1", "Apple", "0 -> 1", "Stick", "0 -> 2")),
	PUMPKIN_STEM(Map.of("Pumpkin_Seeds", "0 -> 3")),
	RED_MUSHROOM_BLOCK(Map.of("Red_Mushroom", "0 -> 2")),
	REDSTONE_ORE(Map.of("Redstone", "4 -> 9")),
	SEA_LANTERN(Map.of("Prismarine_Crystals", "2 -> 3")),
	SPRUCE_LEAVES(Map.of("Spruce_Sapling", "0 -> 1", "Stick", "0 -> 2")),
	TALL_GRASS(Map.of("Wheat_Seeds", "0 -> 1")),
	TWISTING_VINES(Map.of("Twisting_Vines", "0 -> 1")),
	TWISTING_VINES_PLANT(Map.of("Twisting_Vines", "0 -> 1")),
	WEEPING_VINES(Map.of("Weeping_Vines", "0 -> 1")),
	WEEPING_VINES_PLANT(Map.of("Weeping_Vines", "0 -> 1"));

	private final Map<String, String> map;

	ItemDropRanges(Map<String, String> map) {
		this.map = map;
	}

	public Map<String, String> getMap() {
		return map;
	}
}
