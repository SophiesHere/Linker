# Minecraft Account Linker Plugin

A Minecraft plugin for version **1.18.2+** that allows players to link their in-game accounts with their Discord accounts. This plugin simplifies community management by connecting player information to Discord, enhancing both in-game and community interactions.

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
- **messages.yml**: Stores all messages, supports color code
- **linked_accounts.yml**: Stores information about linked accounts.

## Commands & Permissions

### Player Commands

- **/link**
  - Links a player’s Minecraft account to their Discord account.
  - **Permission**: `linker.command.link`

### Admin Commands

- **/linker**
  - General command for plugin management.
  - **Permission**: `linker.command.linker`

- **/linker version**
  - Displays the current plugin version.
  - **Permission**: `linker.command.linker`

- **/linker reload**
  - Reloads the plugin and restarts the Discord bot to apply changes.
  - **Permission**: `linker.command.linker`

## Permissions

| Command           | Description                                        | Permission                 |
|-------------------|----------------------------------------------------|----------------------------|
| `/link`           | Link Minecraft account with Discord account        | `linker.command.link`      |
| `/linker`         | Access plugin management commands                  | `linker.command.linker`    |
| `/linker version` | Display current plugin version                     | `linker.command.linker`    |
| `/linker reload`  | Reload plugin and restart Discord bot              | `linker.commmand.linker`   |

### v1.1
- Same as previous release, but with a messages.yml file to configure all messages being sent. Also, still very basic and lacks alot of 
- Support for Discord bot integration and account linking.

### v1.0
- Initial release with `/link` and `/linker` command functionalities, still very basic.
- Support for Discord bot integration and account linking.

**Enjoy linking your Minecraft and Discord accounts!**
