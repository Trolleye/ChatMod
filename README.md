# Chat Mod

A server-side Minecraft Fabric mod that allows players to use selected operator commands without requiring OP permissions.

## Making Changes

Edit:

`src/main/java/troll/eye/CommandHandler.java`

Then build the mod using Gradle.

## Installation

Copy the built JAR from:

`build/libs/<buildname>.jar`

into your server's `mods` folder.

## Current Commands

The mod currently implements the following commands:

* `/day` — Sets the time to day.
* `/night` — Sets the time to night.
* `/noon` — Sets the time to noon.
* `/sun` — Clears the weather.
* `/rain` — Starts rain.

Whenever one of these commands is used, the name of the player who executed it is broadcast to all players on the server. For example: 

`<Player Name> changed the time to noon`
