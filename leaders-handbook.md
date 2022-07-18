# Leaders handbook

## Table of contents

* [Managing your clan members](#managing-your-clan-members)
* [Managing your clan treasure](#managing-your-clan-treasure)

Congratulations on being a clan leader! This means you're responsible and trustworthy enough to help manage the Geek Wars event.

As a clan leader you have access to the `/clan` (or `/cl`) command. This gives you control over the members of your clan, and most importantly, your clan's flag. Let's take a closer look at how you do those things.

## Managing your clan members

As a clan leader you are able to see the members of your clan and even kick members out if they're misbehaving or cheating with the following commands:
```
/clan members list
/clan members kick <player>
```
Note that only admins (or command blocks) are allowed to join players into a clan.

## Managing your clan treasure

Your most important role as a clan leader is to manage your clan's treasure. the main command you will do this with is:
```
/clan treasure sethome
```
This sets the respawn point of the treasure and teleports it to you. Your location becomes the home point of the treasure, and should be in _a visible and well-lit area of your clan hall._ Players shouldn't have to break blocks to get to it! As a clan leader it's your responsibility to make your clan's treasure actually accessible to the enemies to make it fun and enjoyable for everyone. 10 layers of obsidian? No! A fun and challenging parkour to get to the treasure? Perfect!

While we're on the subject of your clan hall, your clan members need to be able to sign in and out at it. Use the following command to spawn command blocks that do only this:
```
/clan setcommandblock <signin | signout>
```

If for some reason the treasure gets destroyed (which should be impossible for survival players) or lost, you can run the following command. Note that the treasure automatically respawns after 24 hours back at its home location, so you should rarely (if ever) need to run this command:
```
/clan treasure setlocation
```
