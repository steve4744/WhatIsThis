# WhatIsThis

![alt text](https://user-images.githubusercontent.com/6975392/106388678-9858b180-63d7-11eb-9941-91aaa0ef6fc2.png "WhatIsThis by steve4744")

WhatIsThis is a lightweight utility plugin aimed at providing block information directly to the player without the need to open a UI. This can be helpful in Survival mode to know what a block is without having to break it first, while looking at other people's builds in Creative mode, using custom block plugins like Slimefun4 and Nova, or when connected with an older client version to a server using Via* plugins.

This plugin was inspired by Forge mods such as WAWLA (What Are We Looking At), WAILA (What Am I Looking At) and HWYLA (Here's What You're Looking At) which run client-side to constantly display the name of the block being looked at. This plugin runs server-side, and by simply right-clicking the block with a STICK (default, can be changed in config) or running command `/wt` while looking at a block, the block type and the items it drops will be briefly displayed, either on the side of the screen, on the BossBar, ActionBar or in chat.

By enabling 'auto_display' in the config, the display will automatically update just by looking at a different block without the need for commands or clicking. As this runs server side, having 'auto-display' enabled may impact performance depending on the server hardware, software and the number of players online.

The 'auto_display' option is disabled by default, so when the server starts the 'player move' listener is not registered. To enable the 'auto-display' option the server will need to be restarted so that the listener is correctly registered.

A list of blocks to ignore, for example, AIR, WATER, GRASS_BLOCK, can be specified in the config. These blocks will not display any information when targeted by the player.

You must have a clear view of the block being looked at when running the `/wt` command. The range is currently set to 10 blocks. The plugin can be used under water as well as on land.

The ability to right-click with an item can be disabled in the _config.yml_, or can be toggled on/off with the `/wt toggleclick` command.

The item used to select the block is also configurable. The default item is a STICK. Any item can be used, although the plugin does not cancel any events that might be triggered by right-clicking with the item such as a "block place" event or opening a door or chest, so it doesn't interfere with the normal mechanics of the game.

The name and number of items dropped reported by the plugin are those obtained from Block#getDrops(). Where the drops are variable (e.g. the number of SEEDS dropped by WHEAT) it returns either a range (e.g. 0 -> 3) or what could be dropped by breaking the block, not necessarily what you will get on breaking the block (e.g. number of MELON_SLICEs from a MELON).

The block information can also be displayed on the ActionBar, BossBar or in chat. Using the ActionBar or BossBar, only the name of the block is displayed and not the dropped items.

The ActionBar display is probably the most attractive and least intrusive of the display methods. The text colour can be set in the _config.yml_ file.

## Suppported Plugins

The following plugins which provide a number of custom blocks are supported:

[Slimefun4](https://github.com/Slimefun/Slimefun4/ "Slimefun4") by TheBusyBiscuit

[Nova](https://github.com/xenondevs/Nova/ "Nova") by xenondevs

## Dependencies
Following Mojang's decision to require Java 16 for Minecraft 1.17+, this plugin also now requires a minimum Java version of 16.

From version 4.0 of this plugin, it requires servers to be running a minimum of Java 16 and Minecraft/Spigot 1.17+.

The legacy version of the plugin, v3.4, is supported on Minecraft/Spigot 1.13.2 through to 1.16.5.


## Commands & Permissions
```
/wt - identifies the block being looked at
/wt info - information about the plugin
/wt reload - reload the config file (requires whatisthis.admin permission)
/wt toggleclick - enable or disable right-click functionality (requires whatisthis.admin permission)
```
```
whatisthis.use - default true
whatisthis.admin - default op
```

## Multi Language Support
All Minecraft languages are supported. The player's own language (which is set locally in the Minecraft launcher) is used to display the name of the block and the items it drops when broken. The text for the word "Drops" can be changed from English by manually changing the text in the _config.yml_.

If there is no local language file, then it will revert to "en_us" and display the English equivalent.

As the Scoreboard has a limit of 40 characters, item names which are too long will be truncated to fit.


## Download
WhatIsThis can be [downloaded from Spigot](https://www.spigotmc.org/resources/whatisthis-identify-the-block-you-are-looking-at.65050/ "WhatIsThis by steve4744")

## Installation

    Download WhatIsThis.jar
    Copy to your server's 'plugins' folder
    Restart your server



Updated 15th October 2021 by steve4744
