## PlayerRoleChecker

ダウンロードしたら、必ずconfig.ymlを設定してください 

[config.yml (RoleChecker)](https://github.com/SIGMA-L/PlayerRoleChecker/blob/main/PlayerRoleChecker/src/main/resources/config.yml)

[config.yml (Connector)](https://github.com/SIGMA-L/PlayerRoleChecker/blob/main/PlayerRoleCheckerConnector/src/main/resources/config.yml)

## このプロジェクトに必須なもの

- MySQL環境

- Discord BotToken

- マインクラフトのサーバー2個

## PlayerRoleCheckerConnector

1. PlayerRoleCheckerConnectorは参加したプレイヤーが参加可能か確認するためのプラグインです

2. ロールを確認して参加可能か確認します

## PlayerRoleChecker

1. PlayerRoleCheckerはランダムな4桁な数字を生成します

2. Discordで生成された数字を入力します

3. 登録が完了されるはずです


## コマンド一覧

**※1 マインクラフトコマンドはPlayerRoleCheckerConnectorで登録されています**

**※2 DiscordコマンドはPlayerRoleCheckerで登録されています**


| マインクラフトコマンド※1             | 説明               | パーミッション                        |
|---------------------------|------------------|--------------------------------|
| /addbypass {マインクラフトID}    | 除外プレイヤーを追加する     | playerrolechecker.addbypass    |
| /removebypass {マインクラフトID} | 除外プレイヤーを削除する     | playerrolechecker.removebypass |
| /joinmode                 | このプラグインの機能をオフにする | playerrolechecker.joinmode     |

| Discordコマンド※2                      | 説明           | パーミッション                  |
|------------------------------------|--------------|--------------------------|
| {生成された数字}                          | データベースに登録します |                          |
| !remove {マインクラフトID}                | データベースから強制削除 | Permission.ADMINISTRATOR |
| !forcejoin {マインクラフトID} {DiscordID} | データベースに強制登録  | Permission.ADMINISTRATOR |

## LICENSE

[Apache License 2.0](https://github.com/SIGMA-L/PlayerRoleChecker/blob/main/LICENSE)


## API

APIの導入方法:

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
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }

    dependencies {
        implementation 'com.github.SIGMA-L.PlayerRoleChecker:PlayerRoleCheckerAPI:v3.0'
	}

APIを実装して使えるイベント:
    
    JoinEvent
    RemoveEvent
    ForceJoinEvent

## CodeAPI

**開発者だけの特別なプラグイン**

- WebAPIを使ってプレイヤーの情報を取得します

- MySQL DiscordBotTokenは必要ありません

```
値 UUID が返ってきます
http://localhost:8080/v2/get/1212
- {"type":"error","uuid":null,"code":null,"bedrock":null}
- {"type":"success","uuid":"069a79f4-44e9-4726-a5be-fca90e38aaf5","code":1212,"bedrock":false}

- http://localhost:8080/v2/get/code
- http://localhost:8080/v2/post/code
```
