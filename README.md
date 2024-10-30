# Minecraft Account Linker Plugin

A Minecraft plugin for version **1.20.6** that allows players to link their in-game accounts with their Discord accounts. This plugin simplifies community management by connecting player information to Discord, enhancing both in-game and community interactions.

## Features

- **/link** command for players to link their in-game account with their Discord account.
- **/linker** command for managing plugin settings.

## Installation

1. Place the plugin `.jar` file into your server's `plugins` folder.
2. Start the server to generate the default `config.yml`.
3. Open `config.yml` in the plugin folder and add your Discord bot token.
4. Restart the server or do `/linker reload`, and the Discord bot will load with the token.

## Configuration

- **config.yml**: Stores the Discord bot token and other general settings.
- **linked_accounts.yml**: Stores information about linked accounts.

## Commands & Permissions

### Player Commands

- **/link**
  - Links a player’s Minecraft account to their Discord account.
  - **Permission**: `linker.link`

### Admin Commands

- **/linker**
  - General command for plugin management.
  - **Permission**: `linker.linker`

- **/linker version**
  - Displays the current plugin version.
  - **Permission**: `linker.linker`

- **/linker reload**
  - Reloads the plugin and restarts the Discord bot to apply changes.
  - **Permission**: `linker.linker`

## Permissions

| Command           | Description                                        | Permission         |
|-------------------|----------------------------------------------------|--------------------|
| `/link`           | Link Minecraft account with Discord account        | `linker.link`      |
| `/linker`         | Access plugin management commands                  | `linker.linker`    |
| `/linker version` | Display current plugin version                     | `linker.linker`    |
| `/linker reload`  | Reload plugin and restart Discord bot              | `linker.linker`    |

## Changelog
No new changes.

### v1.0
- Initial release with `/link` and `/linker` command functionalities, still very basic.
- Support for Discord bot integration and account linking.

**Enjoy linking your Minecraft and Discord accounts!**
