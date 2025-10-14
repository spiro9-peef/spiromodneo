package com.github.peeftube.spiromodneo.datagen.modules.lang;

import com.github.peeftube.spiromodneo.SpiroMod;
import com.github.peeftube.spiromodneo.core.init.Registrar;
import com.github.peeftube.spiromodneo.core.init.registry.data.*;
import com.github.peeftube.spiromodneo.util.ore.BaseStone;
import com.github.peeftube.spiromodneo.util.ore.OreCoupling;
import com.github.peeftube.spiromodneo.util.stone.*;
import com.github.peeftube.spiromodneo.util.wood.LivingWoodBlockType;
import com.github.peeftube.spiromodneo.util.wood.ManufacturedWoodType;
import com.github.peeftube.spiromodneo.util.wood.PlankBlockSubType;
import com.github.peeftube.spiromodneo.util.wood.SignType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static com.github.peeftube.spiromodneo.util.stone.StoneSetPresets.getPresets;

public class EN_USLangDataProv extends LanguageProvider
{
    public EN_USLangDataProv(PackOutput output, String locale)
    { super(output, SpiroMod.MOD_ID, locale); }

    @Override
    protected void addTranslations()
    {
        // Metals
        for (MetalCollection metal : MetalCollection.METAL_COLLECTIONS) { metalParser(metal); }

        // Ores
        for (OreCollection ore : OreCollection.ORE_COLLECTIONS) { oreParser(ore); }

        // Grass & soil
        for (GrassLikeCollection grass : GrassLikeCollection.GRASS_COLLECTIONS) { grassParser(grass); }

        // Stone collections
        for (StoneCollection stone : StoneCollection.STONE_COLLECTIONS) { stoneParser(stone); }

        // Wood collections
        for (WoodCollection wood : WoodCollection.WOOD_COLLECTIONS) { woodParser(wood); }

        // Equipment
        for (EquipmentCollection equip : EquipmentCollection.EQUIP_COLLECTIONS) { equipParser(equip); }

        // Override name of "Nether Quartz", call it "Quartz" instead.
        add(Items.QUARTZ, "Quartz");

        // Other / Loose
        add(Registrar.SINEW.get(), "Animal Sinew");

        add(Registrar.SMALL_STONE.get(), "Small Stone");

        add(Registrar.SHARPENED_STICK.get(), "Sharpened Stick");
        add(Registrar.BUNDLE_OF_SHARP_STICKS.get(), "Bundled Sharp Sticks");
        add(Registrar.WOODEN_TOOL_GRAFTING_KIT.get(), "Wooden Tool Grafting Kit");

        add(Registrar.CAST_IRON_MIXTURE.get(), "Cast Iron Mixture");
        add(Registrar.CAST_IRON.get(), "Cast Iron Ingot");
        add(Registrar.STEEL_MIXTURE.get(), "Steel Mixture");
        add(Registrar.WEAK_STEEL_MIXTURE.get(), "Steel Mixture CI");
        add(Registrar.CRUSHED_CARBON.get(), "Carbon Dust");

        add(Registrar.NETHER_CLAY.get(), "\"Nether Clay\"");

        add(Registrar.CAOUTCHOUC.get(), "Raw Caoutchouc");
        add(Registrar.MAPLE_SAP.get(), "Raw Maple Sap");

        add(Registrar.MANUAL_CRUSHER.get(), "Manual Crusher");
        add(Registrar.TAPPER.get(), "Tapper");

        add(Registrar.RUBBER_PRECURSOR.get(), "Prepared Latex");
        add(Registrar.NATURAL_RUBBER.get(), "Natural Rubber");
        add(Registrar.COPPER_WIRE.get(), "Copper Wiring");
        add(Registrar.SHIELDED_COPPER_WIRE.get(), "Shielded Copper Wiring");

        add(Registrar.IRON_STICK.get(), "Iron Rod");
        add(Registrar.LEAD_STICK.get(), "Lead Rod");
        add(Registrar.STEEL_ROD.get(), "Steel Rod");

        // Creative tabs
        add(Registrar.TAB_TITLE_KEY_FORMULAIC + ".minerals_tab", "Ores and Raw Minerals");
    }

    private void woodParser(WoodCollection set)
    {
        boolean isNetherFungus = set.type().isLikeNetherFungus();

        String setName = set.type().getName().replace("_fungus", "");
        String[] soilSubs = setName.split("_");
        setName = "";
        int stIndex = 0;
        for (String st : soilSubs)
        {
            setName = setName + (stIndex > 0 ? " " : "") +
                    st.substring(0, 1).toUpperCase() + st.substring(1);
            stIndex++;
        }

        for (LivingWoodBlockType t : LivingWoodBlockType.values())
        {
            if (set.bulkData().livingWood().get(t) != null)
            {
                boolean isVanilla = BuiltInRegistries.BLOCK.getKey(set.bulkData().livingWood().get(t).getBlock().get())
                                                           .getNamespace().equalsIgnoreCase("minecraft");

                if (!isVanilla)
                {
                    String wood = setName.contains("wood") ? setName : setName + (isNetherFungus ? " Hyphae" : " Wood");

                    switch (t)
                    {
                        case LOG -> add(set.bulkData().livingWood().get(t).getBlock().get(),
                                setName + (isNetherFungus ? " Stem" : " Log"));
                        case STRIPPED_LOG -> add(set.bulkData().livingWood().get(t).getBlock().get(),
                                "Stripped " + setName + (isNetherFungus ? " Stem" : " Log"));
                        case WOOD -> add(set.bulkData().livingWood().get(t).getBlock().get(), wood);
                        case STRIPPED_WOOD -> add(set.bulkData().livingWood().get(t).getBlock().get(),
                                "Stripped" + wood);
                        case SAPLING -> add(set.bulkData().livingWood().get(t).getBlock().get(),
                                setName + (isNetherFungus ? " Fungus" : " Sapling"));
                        case LEAVES -> add(set.bulkData().livingWood().get(t).getBlock().get(),
                                setName + (isNetherFungus ? " Wart Block" : " Leaves"));
                        case ROOTS -> add(set.bulkData().livingWood().get(t).getBlock().get(),
                                setName + " Roots");
                        case ROOTS_WITH_MUD -> add(set.bulkData().livingWood().get(t).getBlock().get(),
                                "Muddy " + setName + " Roots");
                    }
                }
            }
        }

        for (PlankBlockSubType t : PlankBlockSubType.values())
        {
            boolean isVanilla = BuiltInRegistries.BLOCK.getKey(set.bulkData().planks().get(t).getBlock().get())
                                                       .getNamespace().equalsIgnoreCase("minecraft");

            if (!isVanilla)
            {
                switch(t)
                {
                    case BLOCK -> add(set.bulkData().planks().get(t).getBlock().get(), setName + " Planks");
                    case SLAB -> add(set.bulkData().planks().get(t).getBlock().get(), setName + " Slab");
                    case STAIRS -> add(set.bulkData().planks().get(t).getBlock().get(), setName + " Stairs");
                    case BUTTON -> add(set.bulkData().planks().get(t).getBlock().get(), setName + " Button");
                    case PRESSURE_PLATE -> add(set.bulkData().planks().get(t).getBlock().get(),
                            setName + " Pressure Plate");
                    case FENCE -> add(set.bulkData().planks().get(t).getBlock().get(), setName + " Fence");
                    case FENCE_GATE -> add(set.bulkData().planks().get(t).getBlock().get(),
                            setName + " Fence Gate");
                }
            }
        }

        for (ManufacturedWoodType t : ManufacturedWoodType.values())
        {
            switch (t)
            {
                case CRAFTING_TABLE -> add(set.bulkData().manufacturables().get(t).getBlock().get(),
                        "Crafting Table (" + setName + ")");
                case DOOR -> add(set.bulkData().manufacturables().get(t).getBlock().get(),
                        setName + " Door");
                case TRAPDOOR -> add(set.bulkData().manufacturables().get(t).getBlock().get(),
                        setName + " Trapdoor");
                case BARREL -> add(set.bulkData().manufacturables().get(t).getBlock().get(),
                        "Barrel (" + setName + ")");
                case CHEST -> add(set.bulkData().manufacturables().get(t).getBlock().get(),
                        "Chest (" + setName + ")");
                case TRAPPED_CHEST -> add(set.bulkData().manufacturables().get(t).getBlock().get(),
                        "Trapped Chest (" + setName +")");
            }
        }

        for (SignType t : SignType.values())
        {
            switch (t)
            {
                case BASIC -> add(set.bulkData().signs().get(t).getItem().get(),
                        setName + " Sign");
                case HANGING -> add(set.bulkData().signs().get(t).getItem().get(),
                        "Hanging " + setName + " Sign");
            }
        }
    }

    private void grassParser(GrassLikeCollection set)
    {
        for (Soil s : Soil.values())
        {
            boolean sanityCheckDirt =
                    (!(set.type() == GrassLike.GRASS || set.type() == GrassLike.MYCELIUM));
            boolean sanityCheckNetherrack =
                    (!(set.type() == GrassLike.CRIMSON_NYLIUM || set.type() == GrassLike.WARPED_NYLIUM));
            boolean sanityCheck =
                    s == Soil.DIRT ? sanityCheckDirt : s != Soil.NETHERRACK || sanityCheckNetherrack;

            String soilName = "";
            String[] soilSubs = s.getName().split("_");
            int stIndex = 0;
            for (String st : soilSubs)
            {
                soilName = soilName + (stIndex > 0 ? " " : "") +
                        st.substring(0, 1).toUpperCase() + st.substring(1);
                stIndex++;
            }
            stIndex = 0; soilName = "(" + soilName + ")";

            String typeName = "";
            String[] typeSubs = set.type().getName().split("_");
            for (String st : typeSubs)
            {
                typeName = typeName + (stIndex > 0 ? " " : "") +
                        st.substring(0, 1).toUpperCase() + st.substring(1);
                stIndex++;
            }
            typeName = set.type() == GrassLike.GRASS ? "Grass Block" : typeName;
            String name = typeName + " " + soilName;
            name = (set.type() == GrassLike.VITALIUM && s == Soil.SOUL_SOIL) ? "Nether Grass (Vitalium)" : name;

            if (sanityCheck) add(set.bulkData().get(s).getBlock().get(), name);
        }
    }

    protected void equipParser(EquipmentCollection set)
    {
        boolean isStock = false;

        EquipmentMaterial material = set.getMat();

        if (material == EquipmentMaterial.CHAIN || material == EquipmentMaterial.WOOD ||
                material == EquipmentMaterial.STONE || material == EquipmentMaterial.IRON ||
                material == EquipmentMaterial.LEATHER || material == EquipmentMaterial.GOLD ||
                material == EquipmentMaterial.DIAMOND || material == EquipmentMaterial.NETHERITE )
        { isStock = true; }

        if (!isStock)
        {
            String matName = "";
            String[] typeSubs = material.getName().split("_");
            int stIndex = 0;
            for (String st : typeSubs)
            {
                matName = matName + (stIndex > 0 ? " " : "") +
                        st.substring(0, 1).toUpperCase() + st.substring(1);
                stIndex++;
            }

            if (set.checkIfNullThenPass(set.bulkData().getTools()).getResult())
            {
                add(set.bulkData().getTools().getSword().get(), matName + " Sword");
                add(set.bulkData().getTools().getShovel().get(), matName + " Shovel");
                add(set.bulkData().getTools().getHoe().get(), matName + " Hoe");
                add(set.bulkData().getTools().getAxe().get(), matName + " Axe");
                add(set.bulkData().getTools().getPickaxe().get(), matName + " Pickaxe");
            }

            if (set.checkIfNullThenPass(set.bulkData().getArmor()).getResult())
            {
                add(set.bulkData().getArmor().getHelmet().get(), matName + " Helmet");
                add(set.bulkData().getArmor().getChestplate().get(), matName + " Chestplate");
                add(set.bulkData().getArmor().getLeggings().get(), matName + " Leggings");
                add(set.bulkData().getArmor().getBoots().get(), matName + " Boots");
            }

            if (set.checkIfNullThenPass(set.bulkData().getHorseArmor()).getResult())
            { add(set.bulkData().getHorseArmor().get(), matName + " Horse Armor"); }
        }
    }

    // Metal set handler
    protected void metalParser(MetalCollection set)
    {
        boolean ignoreIngotBlocks = false; // For ignoring default ingot and metallic block combinations

        MetalMaterial material = set.getMat();

        if (material == MetalMaterial.IRON || material == MetalMaterial.GOLD || material == MetalMaterial.COPPER
                || material == MetalMaterial.NETHERITE )
        { ignoreIngotBlocks = true; }

        if (!ignoreIngotBlocks)
        {
            // Make this code easier to read, PLEASE..
            Block b = set.ingotData().getMetal().getBlock().get();
            Item i  = set.ingotData().getIngot().get();
            String mat = material.get();

            // Readable block String:
            String blockOf = "Block of ";
            add(b, blockOf + mat.substring(0, 1).toUpperCase() + mat.substring(1));

            // Readable item String:
            String readableMat = mat.substring(0, 1).toUpperCase() + mat.substring(1) + " Ingot";
            add(i, readableMat);
        }
    }

    // Ore set handler
    protected void oreParser(OreCollection set)
    {
        // Flags for what we should ignore.
        boolean ignoreStone = false; // For ignoring default stone, assumes true for deepslate as well
        boolean ignoreNether = false; // For ignoring default Netherrack ore
        // NOTE: these two may be used in an OR statement to determine if this is a vanilla block. If so,
        //       code should ignore the raw ore blocks.

        // Prepare set data.
        OreMaterial                 material = set.getMat();
        Map<StoneMaterial, OreCoupling> bulkData = set.getBulkData();

        if (material == OreMaterial.COAL || material == OreMaterial.IRON || material == OreMaterial.COPPER
                || material == OreMaterial.GOLD || material == OreMaterial.LAPIS || material == OreMaterial.REDSTONE
                || material == OreMaterial.EMERALD || material == OreMaterial.DIAMOND)
        { ignoreStone = true; }

        if (material == OreMaterial.GOLD || material == OreMaterial.QUARTZ)
        { ignoreNether = true; }

        for (StoneMaterial s : StoneMaterial.values())
        {
            if (((s == StoneMaterial.STONE || s == StoneMaterial.DEEPSLATE) && ignoreStone)
                    || ((s == StoneMaterial.NETHERRACK) && ignoreNether))
            { continue; } // Do nothing, we're using a material which already uses this combination...

            // Make this code easier to read, PLEASE..
            Block b = bulkData.get(s).block().get();
            String mat = material.get();

            // Generate a translation string and then add it to the translation set.
            String[] substrings = mat.split("_");
            String readableMat = ""; int stIndex = 0;
            for (String st : substrings)
            {
                readableMat = readableMat + (stIndex > 0 ? " " : "") +
                        st.substring(0, 1).toUpperCase() + st.substring(1);
                stIndex++;
            }
            readableMat = readableMat + " Ore";
            add(b, generateOreBlockString(s.getOreBase(), readableMat));
        }

        // Raw block and item; assume not vanilla.
        if (!(ignoreStone || ignoreNether))
        {
            // Make this code easier to read, PLEASE..
            Block b = set.getRawOre().getCoupling().getBlock().get();
            Item i  = set.getRawOre().getRawItem().get();
            String mat = material.get();

            // Readable block String:
            String rawMineral       = material.isGem() ? "" : "Raw ";

            // Generate a translation string and then add it to the translation set.
            String[] substrings = mat.split("_");
            String readableMat = ""; int stIndex = 0;
            for (String st : substrings)
            {
                readableMat = readableMat + (stIndex > 0 ? " " : "") +
                        st.substring(0, 1).toUpperCase() + st.substring(1);
                stIndex++;
            }
            readableMat = readableMat + (material.isGem() ? "" : " Ore");
            String readableBlockMat = "Block of " + readableMat;
            add(b, rawMineral + readableBlockMat);

            // Readable item String:
            readableMat = rawMineral + readableMat;
            add(i, readableMat);
        }
    }

    private void stoneParser(StoneCollection set)
    {
        StoneData     data = set.bulkData();
        StoneMaterial mat  = set.material();

        for (StoneBlockType k0 : StoneBlockType.values())
        {
            for (StoneVariantType k1 : StoneVariantType.values())
            {
                for (StoneSubBlockType k2 : StoneSubBlockType.values())
                {
                    boolean available = data.doesCouplingExistForKeys(k0, k1, k2);
                    String  baseKey   = ExistingStoneCouplings.getKey(set.material(), k0, k1, StoneSubBlockType.DEFAULT);
                    String  key       = ExistingStoneCouplings.getKey(set.material(), k0, k1, k2);

                    boolean isDefault      = k2 == StoneSubBlockType.DEFAULT;
                    boolean textureIsStock = getPresets().containsKey(isDefault ? key : baseKey);
                    String  ns             = getPresets().containsKey(baseKey) ? "minecraft" : SpiroMod.MOD_ID;

                    if (available && !getPresets().containsKey(key))
                    {
                        Block b = set.bulkData().getCouplingForKeys(k0, k1, k2).getBlock().get();
                        add(b, generateStoneSetString(set, k0, k1, k2));
                    }
                }
            }
        }
    }

    protected String generateStoneSetString(StoneCollection s, StoneBlockType k0, StoneVariantType k1, StoneSubBlockType k2)
    {
        String materialString = s.material().toString().toLowerCase().replace('_', ' ');
        materialString = materialString.equals("endstone") ? "end stone" : materialString;
        String[] subStrings = materialString.split(" ");
        String outputString = "";
        for (int i = 0; i < subStrings.length; i++)
        { outputString = (outputString + ((i > 0) ? " " : "") + capitalize(subStrings[i])); }
        if (outputString.equals("Stone") && k0 == StoneBlockType.COBBLE)
        { outputString = "Cobblestone"; }

        switch(k0)
        {
            case TILES, BRICKS, COLUMN -> { outputString = outputString + " " +
                    ((k2 == StoneSubBlockType.DEFAULT || k0 == StoneBlockType.COLUMN) ? capitalize(k0.toString().toLowerCase())
                            : capitalize(k0.toString().toLowerCase().substring(0, k0.toString().length() - 1))); }
            case BASE -> {} // No need to do anything here.
            case COBBLE ->
            { if (!(outputString.contains("Cobblestone")))
            { outputString = capitalize(k0.toString().toLowerCase() + "d") + " " + outputString; }}
            default -> outputString = capitalize(k0.toString().toLowerCase()) + " " + outputString;
        }

        outputString = k1 != StoneVariantType.DEFAULT ? capitalize(k1.toString().toLowerCase()) + " " + outputString :
                outputString;

        outputString = k2 != StoneSubBlockType.DEFAULT ?
                k2 == StoneSubBlockType.PRESSURE_PLATE ? outputString + " Pressure Plate" :
                k2 == StoneSubBlockType.GROUND_STONES ? "Small " + outputString + " on Ground" :
                outputString + " " + capitalize(k2.toString().toLowerCase()) :
                outputString;

        return outputString;
    }

    private String capitalize(String input)
    { return input.substring(0, 1).toUpperCase() + input.substring(1); }

    // Ore set String subroutine
    protected String generateOreBlockString(BaseStone s, String readable)
    {
        // OPTIONAL, BUT PREFERRED. Defaults to Stone string otherwise.
        Map<BaseStone, String> genFormulae = new HashMap<>();
        genFormulae.put(BaseStone.ANDESITE, readable + " (Andesite)");
        genFormulae.put(BaseStone.DIORITE, readable + " (Diorite)");
        genFormulae.put(BaseStone.GRANITE, readable + " (Granite)");
        genFormulae.put(BaseStone.CALCITE, readable + " (Calcite)");
        genFormulae.put(BaseStone.SMS, readable + " (Smooth Sandstone)");
        genFormulae.put(BaseStone.SMRS, readable + " (Smooth Red Sandstone)");
        genFormulae.put(BaseStone.DEEPSLATE, "Deepslate " + readable);
        genFormulae.put(BaseStone.TUFF, readable + " (Tuff)");
        genFormulae.put(BaseStone.DRIPSTONE, readable + " (Dripstone)");
        genFormulae.put(BaseStone.NETHERRACK, "Nether " + readable);
        genFormulae.put(BaseStone.BASALT, readable + " (Basalt)");
        genFormulae.put(BaseStone.ENDSTONE, "Ender " + readable);
        genFormulae.put(BaseStone.LIMBIPETRA, "Limbo " + readable);

        return genFormulae.getOrDefault(s, readable);
    }
}
