# WhatIsThis

![alt text](https://user-images.githubusercontent.com/6975392/106388678-9858b180-63d7-11eb-9941-91aaa0ef6fc2.png "WhatIsThis by steve4744")

WhatIsThis is a lightweight utility plugin aimed at providing block information directly to the player without the need to open a UI. This can be helpful in Survival mode to know what a block is without having to break it first, while looking at other people's builds in Creative mode, using custom block plugins like Slimefun4 and Nova, or when connected with an older client version to a server using Via* plugins.

Starting with version 5.0, the plugin will also identify entities in the same way as blocks.

This plugin was inspired by Forge mods such as WAILA (What Am I Looking At) and HWYLA (Here's What You're Looking At) which run client-side to constantly display the name of the block being looked at. This plugin runs server-side, and will display the block information either on the ActionBar, the side of the screen (scoreboard), on the BossBar, or in chat. The information is displayed either automatically by looking at a block, or by right-clicking the block with a STICK (default, can be changed in config) or running command `/wt` while looking at a block. The items dropped by the block are also displayed in the scoreboard and chat display options.

By enabling 'auto_display' in the config, the display will automatically update just by looking at a different block without the need for commands or clicking. As this runs server side, having 'auto-display' enabled may impact performance depending on the server hardware, software and the number of players online.

The 'auto_display' option is disabled by default, so when the server starts the 'player move' listener is not registered. To enable the 'auto-display' option the server will need to be restarted so that the listener is correctly registered.

A list of blocks to ignore, for example, AIR, WATER, GRASS_BLOCK, NETHERRACK, can be specified in the config. These blocks will not display any information when targeted by the player.

You must have a clear view of the block being looked at when running the `/wt` command. The range is currently set to 10 blocks. The plugin can be used under water as well as on land.

The ability to right-click with an item can be disabled in the _config.yml_, or can be toggled on/off with the `/wt toggleclick` command.

The item used to select the block is also configurable. The default item is a STICK. Any item can be used, although the plugin does not cancel any events that might be triggered by right-clicking with the item such as a "block place" event or opening a door or chest, so it doesn't interfere with the normal mechanics of the game.

The name and number of items dropped reported by the plugin are those obtained from Block#getDrops(). Where the drops are variable (e.g. the number of SEEDS dropped by WHEAT) it returns the range (e.g. 0 -> 3) of possible values. Using the ActionBar or BossBar, only the name of the block is displayed and not the dropped items.

The text and BossBar colours can be set in the _config.yml_ file.


## Suppported Plugins

The following plugins which provide a number of custom blocks are supported:

[Slimefun4](https://github.com/Slimefun/Slimefun4/ "Slimefun4") by TheBusyBiscuit

[Nova](https://github.com/xenondevs/Nova/ "Nova") by xenondevs

[ItemsAdder](https://www.spigotmc.org/resources/%E2%9C%85must-have%E2%9C%85-itemsadder%E2%9C%A8custom-items-huds-guis-mobs-3dmodels-emojis-blocks-wings-hats-liquids.73355/ "ItemsAdder") by LoneDev

[Oraxen](https://github.com/oraxen/oraxen/ "Oraxen") by Th0rgal

[Craftory Tech](https://www.spigotmc.org/resources/craftory-tech.81151/ "Craftory Tech") by CraftoryStudios

[MythicMobs](https://www.spigotmc.org/resources/mythicmobs-free-version.5702/ "MythicMobs") by Xikage

## Dependencies
The latest version of this plugin requires Java 17 and Minecraft/Spigot 1.17.1 - 1.19.2.<br>
The minimum requirement for Oraxen is version 1.140.0.<br>
The minimum requirement for Nova is version 0.10.

Version 4.5 is supported for servers running a minimum of Java 16 and Minecraft/Spigot 1.17.1.

The legacy version of the plugin, v3.4, is supported on Minecraft/Spigot 1.13.2 through to 1.16.5.

PlaceholderAPI is required to use this plugin's placeholders.

## Commands & Permissions
```
/wt - identifies the block or entity being looked at
/wt info - information about the plugin
/wt reload - reload the config file (requires whatisthis.admin permission)
/wt toggleclick - enable or disable right-click functionality (requires whatisthis.admin permission)
```
```
whatisthis.use - default true
whatisthis.admin - default op
```

## Multi Language Support
All Minecraft languages are supported. The player's own language (which is set locally in the Minecraft launcher) is used to display the name of the block and the items it drops when broken. Translation of custom block names is done by the plugin providing the custom blocks - currently this is only supported by Nova.

If there is no local language file, then it will revert to "en_us" and display the English equivalent.

As the Scoreboard has a limit of 40 characters, item names which are too long will be truncated to fit.


## Placeholders
Support for placeholders can be enabled in the config.
```
%whatisthis_blockname% - name of the block
%whatisthis_resourcename% - name of the providing resource
%whatisthis_location% - location of block
%whatisthis_locationX% - X coordinate of block
%whatisthis_locationY% - Y coordinate of block
%whatisthis_locationZ% - Z coordinate of block
%whatisthis_version% - version of the plugin
```
    
## Download
WhatIsThis can be [downloaded from Spigot](https://www.spigotmc.org/resources/whatisthis-identify-the-block-you-are-looking-at.65050/ "WhatIsThis by steve4744")

## Installation

    Download WhatIsThis.jar
    Copy to your server's 'plugins' folder
    Restart your server



Updated 1st December 2022 by steve4744
