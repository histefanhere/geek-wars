# Milk-Wars manual

Thanks for using this plugin! This is a comprehensive guide for how to implement and use the features of this plugin.

# Table of contents

* [Installation](#installation)
* [Usage](#usage)
  * [For Admins](#for-admins)
    * [Joining and Leaving a clan](#joining-and-leaving-a-clan)
    * [Promoting and demoting clan leaders](#promoting-and-demoting-clan-leaders)
    * [Changing chat colours](#changing-chat-colours)
    * [Creating smart signs](#creating-smart-signs)
  * [For Clan Leaders](#for-clan-leaders)
    * [Managing your clan members](#managing-your-clan-members)
    * [Managing your clan treasure](#managing-your-clan-treasure)
  * [For Players](#for-players)
    * [Capturing the flag](#capturing-the-flag)
    * [Getting kills](#getting-kills)

# Installation

Download the latest release of the plugin and plop it into your server's `plugins` folder. That's it. It requires no extra installation of third-party plugins (the ones that it does required are shaded in and already taken care of).

# Usage

How you use this plugin depends on who you are - an admin of the server, a leader of one of the clans, or a regular player.


## For admins

Admins need to manage and overview the game, and for this the main tool on their utility belt is the `/milkwars` (or `/mw`) command. With this command you can make players join or leave clans, promote them to leaders, and more.

### Joining and leaving a clan

The following is the command for making a player join and leave a clan:
```
/mw clan join <player> <cows | sheep>
/mw clan leave <player>
```
The join command is intended to be in a command block at spawn for players to join into a clan of their choice, and
the leave command is only intended to be manually ran by admins in rare cases.

To see the players in a clan, run the following command:
```
/mw clan members list <cows | sheep>
```

### Promoting and demoting clan leaders

To help run the game smoother and take some responsibility of the admins, clans can have their own _clan leaders_ that can manage their clan. To make someone a leader of their clan or revoke their leader status, do one the following commands:
```
/mw clan members promote <player>
/mw clan members demote <player>
```
**ONLY ASSIGN RESPONSIBLE AND TRUSTWORTHY PEOPLE AS CLAN LEADERS.** Since they have control over their clans treasure, it's no fun if they abuse the commands they have available to them and ruin the game for everyone else.

### Changing chat colours

Since Milk Wars needs to display a prefix on a player's username when they're signed into their clan, it has to take control over the team system. Because of this, there's a special command for allowing players to set their personal chat colours:
```
/mw settings setchatcolour <aqua | black | gold | blue ...> <player>
```
All vanilla colours are available. This command is intended to be ran in command blocks at spawn for players to choose their own colours.

### Creating smart signs

TODO: This part


## For clan leaders

### Managing your clan members

TODO: This part

### Managing your clan treasure

TODO: This part

## For players

It should be noted that everything in this section also applies to both admins and clan leaders.


### Capturing the flag

TODO: This part

### Getting kills

TODO: This part