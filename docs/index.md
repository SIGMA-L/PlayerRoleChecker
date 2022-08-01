# PlayerRoleChecker

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

## API

How to implement the API:

### Maven

    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>

	<dependency>
	    <groupId>com.github.SIGMA-L.PlayerRoleChecker</groupId>
	    <artifactId>PlayerRoleCheckerAPI</artifactId>
	    <version>v3.0</version>
	</dependency>

### Gradle

    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

    dependencies {
        implementation 'com.github.SIGMA-L.PlayerRoleChecker:PlayerRoleCheckerAPI:v3.0'
	}

Events that can be used by using the API:
    
    JoinEvent
    RemoveEvent
    ForceJoinEvent
    
## Whats is Code API

### Special plug-ins for developers only.

- Use WebAPI to retrieve player information

- MySQL & DiscordBotToken are not required

```
UUID is returned

http://localhost:8080/api/get/?code=codehere
http://localhost:8080/api/post/?code=codehere
```

## Support or Contact

Having trouble with PRC? Check out our [documentation](https://github.com/SIGMA-L/PlayerRoleChecker/wiki) or [Check issue](https://github.com/SIGMA-L/PlayerRoleChecker/issues) and we’ll help you sort it out.
