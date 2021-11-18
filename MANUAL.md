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
**ONLY ASSIGN RESPONSIBLE AND TRUSTWORTHY PEOPLE AS CLAN LEADERS.** Since they have control over their clan's treasure, it's no fun if they abuse the commands they have available to them and ruin the game for everyone else.

### Changing chat colours

Since Milk Wars needs to display a prefix on a player's username when they're signed in to their clan, it has to take control over the team system. Because of this, there's a special command for allowing players to set their personal chat colours:
```
/mw settings setchatcolour <aqua | black | gold | blue ...> <player>
```
All vanilla colours are available. This command is intended to be run in command blocks at spawn for players to choose their own colours.

### Creating smart signs

To help display clan statistics you can use smart signs. Smart signs work in a similar way to normal signs, but with the added benefit of giving you more power to format the text and the ability to display automatically updating clan statistics.

**Variable tags:**

In order to create a smart sign simply place down a sign and make sure that anywhere in the sign text you include `#MW`. This `#MW` tag tells the plugin that you want this sign to be a smart sign, don't worry about it looking ugly: the tag won't actually be displayed on the sign.
To insert a variable into the sign you can use variable tags. Like the `#MW` tag these all start with a hash followed by some symbol. The available tags are:

- `#0` - Milk Drinkers kill counter: the amount of times a signed in Milk Drinker has killed a signed in enemy.
- `#1` - Milk Drinkers capture counter: the amount of times the Milk Drinkers clan have captured the enemy flag.
- `#2` - Wool Wearers kill counter: the amount of times a signed in Wool Wearer has killed a signed in enemy.
- `#3` - Wool Wearers capture counter: the amount of times the Wool Wearers clan have captured the enemy flag.

**Variable tag usage example:**

<!-- ![Variable tag use example edit message](images/VarTagExampleEdit.png)
![Variable tag use example after edit](images/VarTagExample.png) -->
 
<img src="images/VarTagExampleEdit.png" alt="Variable tag example edit message" style="width:250px;"/>

```
Milk Drinkers:
#MW
Kills: #0
Captures: #1
```
Once you finish editing the sign the sign will display like so:

<img src="images/VarTagExample.png" alt="Variable tag example result" style="width:250px;"/>

Note that in this example the Milk Drinkers clan has 10 kills and has captured the Wool Wearers flag 3 times. Using these smart signs can be very useful as the signs automatically update. So if, for example, the Milk Drinkers got another kill then the sign would update and display `Kills: 11`.

**Formatting codes:**

If you've used colour/format codes in less vanila servers then this may be familar to you. These tags start with a `&` followed by some symbol, the avalible codes are:

<img src="images/ColourCodes.png" alt="Bukkit Colour Codes" style="width:400px;"/>

**Smart Sign Usage Example:**

Of course format/colour codes can be used along with variable codes to create some really neat signs:

<img src="images/SmartSignExampleEdit.png" alt="Smart sign example edit message" style="width:250px;"/>

```
&7&n&lKills:
#MW
&7MD: #0
&7WW: #2
```

Entering the above text would result in a sign that looks like this:

<img src="images/SmartSignExample.png" alt="Smart sign example after edit" style="width:250px;"/>

And of course these values would automatically update so you don't need to worry about them getting out of date.

> **Note:** Please don't use smart signs to simply make fancy signs: the more there are the greater each subsequent sign will impact preformance. This imapct should be neglegible unless there are lots of these signs (100+ maybe). If you want fancy signs really badly then let us know and I can make that possible with no impact to server performance.

## For clan leaders

Congratulations on being a clan leader! This means you're responsible and trustworthy enough to help manage the Milk Wars event.

As a clan leader you have access to the `/clan` (or `/cl`) command. This gives you control over the members of your clan, and most importantly, your clan's flag. Let's take a closer look at how you do those things.

### Managing your clan members

As a clan leader you are able to see the members of your clan and even kick members out if they're misbehaving or cheating with the following commands:
```
/clan members list
/clan members kick <player>
```
Note that only admins are allowed to join players into a clan.

### Managing your clan treasure

Your most important role as a clan leader is to manage your clan's treasure. the main command you will do this with is:
```
/clan treasure sethome
```
This sets the respawn point of the treasure and teleports it to you. Your location becomes the home point of the treasure, and should be in _a visible and well-lit area of your clan hall._ Players shouldn't have to break blocks to get to it! As a clan leader it's your responsibility to make your clan's treasure actually accessible to the enemies to make it fun and enjoyable for everyone. 10 layers of obsidian? No! A fun and challenging parkour to get to the treasure? Perfect!

If for some reason the treasure gets destroyed (which should be impossible) or lost, you can run the following command. Note that the treasure automatically respawns after 24 hours back at its home location, so you should rarely (if ever) need to run this command:
```
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