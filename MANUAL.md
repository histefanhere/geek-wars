# Milk-Wars manual

Thanks for using this plugin! This is a comprehensive guide for how to implement and use the features of this plugin.

# Table of contents

* [Installation](#installation)
* [Usage](#usage)
  * [For admins](#for-admins)
    * [Joining and leaving a clan](#joining-and-leaving-a-clan)
    * [Promoting and demoting clan leaders](#promoting-and-demoting-clan-leaders)
    * [Changing chat colours](#changing-chat-colours)
    * [Creating smart signs](#creating-smart-signs)
  * [For clan leaders](#for-clan-leaders)
    * [Managing your clan members](#managing-your-clan-members)
    * [Managing your clan treasure](#managing-your-clan-treasure)
  * [For players](#for-players)
    * [Capturing the other clan's treasure](#capturing-the-other-clans-treasure)
    * [Preventing the other clan from capturing your treasure](#preventing-the-other-clan-from-capturing-your-treasure)
    * [Getting kills](#getting-kills)

# Installation

Download the latest release of the plugin and plop it into your server's `plugins` folder. That's it. It requires no extra installation of third-party plugins (the ones that it does required are shaded in and already taken care of).

# Usage

How you use this plugin depends on who you are - an admin of the server, a leader of one of the clans, or a regular player.


## For admins

Admins need to manage and overview the game, and for this the main tool on their utility belt is the `/milkwars` (or `/mw`) command. With this command you can make players join or leave clans, promote them to leaders, and more.

### Joining and leaving a clan

The following is the command for making a player join and leave a clan:
```mcfunction
/mw clan join <player> <cows | sheep>
/mw clan leave <player>
```
The join command is intended to be in a command block at spawn for players to join into a clan of their choice, and
the leave command is only intended to be manually ran by admins in rare cases.

To see the players in a clan, run the following command:
```mcfunction
/mw clan members list <cows | sheep>
```

### Promoting and demoting clan leaders

To help run the game smoother and take some responsibility of the admins, clans can have their own _clan leaders_ that can manage their clan. To make someone a leader of their clan or revoke their leader status, do one the following commands:
```mcfunction
/mw clan members promote <player>
/mw clan members demote <player>
```
**ONLY ASSIGN RESPONSIBLE AND TRUSTWORTHY PEOPLE AS CLAN LEADERS.** Since they have control over their clan's treasure, it's no fun if they abuse the commands they have available to them and ruin the game for everyone else.

### Changing chat colours

Since Milk Wars needs to display a prefix on a player's username when they're signed in to their clan, it has to take control over the team system. Because of this, there's a special command for allowing players to set their personal chat colours:
```mcfunction
/mw settings setchatcolour <aqua | black | gold | blue ...> <player>
```
All vanilla colours are available. This command is intended to be run in command blocks at spawn for players to choose their own colours.

### Creating smart signs

TODO: This part


## For clan leaders

Congratulations on being a clan leader! This means you're responsible and trustworthy enough to help manage the Milk Wars event.

As a clan leader you have access to the `/clan` (or `/cl`) command. This gives you control over the members of your clan, and most importantly, your clan's flag. Let's take a closer look at how you do those things.

### Managing your clan members

As a clan leader you are able to see the members of your clan and even kick members out if they're misbehaving or cheating with the following commands:
```mcfunction
/clan members list
/clan members kick <player>
```
Note that only admins are allowed to join players into a clan.

### Managing your clan treasure

Your most important role as a clan leader is to manage your clan's treasure. the main command you will do this with is:
```mcfunction
/clan treasure sethome
```
This sets the respawn point of the treasure and teleports it to you. Your location becomes the home point of the treasure, and should be in _a visible and well-lit area of your clan hall._ Players shouldn't have to break blocks to get to it! As a clan leader it's your responsibility to make your clan's treasure actually accessible to the enemies to make it fun and enjoyable for everyone. 10 layers of obsidian? No! A fun and challenging parkour to get to the treasure? Perfect!

If for some reason the treasure gets destroyed (which should be impossible) or lost, you can run the following command. Note that the treasure automatically respawns after 24 hours back at its home location, so you should rarely (if ever) need to run this command:
```mcfunction
/clan treasure setlocation
```

## For players

NOTE: everything in this section also applies to both admins and clan leaders.

Thanks for choosing to play and participate in the official Geek NZ Milk Wars! There's a couple of things you should know before you go into it, including how to score points for your clan and geek etiquette.

### Capturing the other clan's treasure

To capture the enemy's treasure, firstly you must be signed in to your clan. You can do this at your clan's hall. At their hall there may be traps or parkour to do in order to obtain their treasure, so be careful! Right click it to pick it up, and return it to your clan's treasure to score a point.

Easy, right? We thought so too. That's why while you're carrying treasure, you're significantly nerfed:
- Elytras don't work
- Enderpearls don't work
- Nether portals don't work
- Carpet teleportation doesn't work

Yes that's right, the only way to carry the enemies treasure from their clan hall to yours is by _walking._ Doesn't sound so easy now, right? You'll need to work together with your team to be protected and score for your clan.

Once you capture the enemies treasure, it becomes deactivated and will only activate in the morning. This means you can only capture their treasure once per day.

### Preventing the other clan from capturing your treasure

So what can you do to prevent the other clan from capturing your treasure? Options include keeping the location of your clan hall a tight keep secret, concealing it, and even booby-trapping the hall. Think Indiana Jones style!

If you manage to kill the carrier of your treasure, it will drop on the ground, and right-clicking it will send it back to your base.

### Getting kills

The other aspect of the Milk Wars is killing. If **and only if an enemy player is signed in to their clan** and you are signed in to yours, you can kill them for an extra point for your clan. Being signed in essentially puts a target on your back, so be careful!
