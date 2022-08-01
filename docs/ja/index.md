# PlayerRoleChecker
[Language] [English](https://prc-mc.net/en/) : 日本語

## PlayerRoleCheckerとは？

PlayerRoleCheckerはMinecraftアカウントとDiscordアカウントを連携させるSpigotプラグインです。

## 動作させるために必要なもの

- MySQL サーバー

- Discord Botトークン

- 二台のMinecraftサーバー

## PlayerRoleCheckerConnectorの機能

1. PlayerRoleCheckerConnectorは参加したプレイヤーが参加可能か確認するためのプラグインです。

2. ロールを確認して参加可能か確認します。

## PlayerRoleCheckerの機能

1. PlayerRoleCheckerはランダムな4桁な数字を生成します。

2. Discordで生成された数字を入力します。

3. 登録が完了されるはずです


詳しくはこちらをご覧ください。 (https://github.com/SIGMA-L/PlayerRoleChecker/).

## コマンド一覧

| Minecraft コマンド           | 詳細 | 権限 |
|---------------------------| --- | --- |
| /addbypass {MCID}    | 除外プレイヤーを追加する | playerrolechecker.addbypass |
| /removebypass {MCID} | 除外プレイヤーを削除する | playerrolechecker.removebypass |
| /joinmode                 | このプラグインの機能をオフにする | playerrolechecker.op |

| Discordコマンド                     | 詳細 | 権限 |
|------------------------------------| --- | --- |
| {生成された数字}                          | データベースに登録します |  |
| !remove {MCID}                | データベースから強制削除 | Permission.ADMINISTRATOR |
| !forcejoin {MCID} {DiscordID} | データベースに強制登録 | Permission.ADMINISTRATOR |

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

    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

    dependencies {
        implementation 'com.github.SIGMA-L.PlayerRoleChecker:PlayerRoleCheckerAPI:v3.0'
	}

APIを実装して使えるイベント:
    
    JoinEvent
    RemoveEvent
    ForceJoinEvent
    
## CodeAPIとは？

### 開発者だけの特別なプラグイン

- WebAPIを使ってプレイヤーの情報を取得します

- MySQLやDiscordBotTokenは必要ありません

```
値 UUID が返ってきます

http://localhost:8080/api/get/?code=codehere
http://localhost:8080/api/post/?code=codehere
```

## お問い合わせ

 プラグインについてわからないことがあればこちらをご確認ください。[documentation](https://github.com/SIGMA-L/PlayerRoleChecker/wiki) また、バグを見つけた場合はこちらへご連絡ください、 [Check issue](https://github.com/SIGMA-L/PlayerRoleChecker/issues)
