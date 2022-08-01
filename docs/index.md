#PlayerRoleChecker

## What is PlayerRoleChecker?

PlayerRoleChecker is a Spigot plugin that connects Minecraft and Discord accounts.

## What you need to prepare

- MySQL Server

- Discord BotToken

- Two Minecraft Servers


For more details see (https://github.com/SIGMA-L/PlayerRoleChecker/).

## Command List

| Minecraft Command※1             | Description | Permission |
|---------------------------| --- | --- |
| /addbypass {MCID}    | Add Excluded Players | playerrolechecker.addbypass |
| /removebypass {MCID} | Remove Excluded Players | playerrolechecker.removebypass |
| /joinmode                 | Turn off this plugin's functionality | playerrolechecker.op |

| DiscordCommand※2                      | Description | Permission |
|------------------------------------| --- | --- |
| {generated number}                          | Register in the database |  |
| !remove {MCID}                | Forcibly deleted from database | Permission.ADMINISTRATOR |
| !forcejoin {MCID} {DiscordID} | Forced registration in database | Permission.ADMINISTRATOR |

### Support or Contact

Having trouble with Pages? Check out our [documentation](https://docs.github.com/categories/github-pages-basics/) or [contact support](https://support.github.com/contact) and we’ll help you sort it out.
