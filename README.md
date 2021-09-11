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


| マインクラフトコマンド | 説明 | パーミッション |
| --- | --- | --- |
| /addbypass {マインクラフトID} | 除外プレイヤーを追加する | playerrolechecker.addbypass |
| /removebypass {マインクラフトID} | 除外プレイヤーを削除する | playerrolechecker.removebypass |
| /joinmode | このプラグインの機能をオフにする | playerrolechecker.op |
| /sqldebug | 隠しコマンド、あまり実用的ではありません | playerrolechecker.sqldebug |

| Discordコマンド | 説明 | パーミッション |
| --- | --- | --- |
| !join {生成された数字} | データベースに登録します |  |
| !remove {マインクラフトID} | データベースから強制削除 | Permission.ADMINISTRATOR |
| !forcejoin {マインクラフトID} {DiscordID} | データベースに強制登録 | Permission.ADMINISTRATOR |

## LICENSE

[Apache License 2.0](https://github.com/SIGMA-L/PlayerRoleChecker/blob/main/LICENSE)


## CodeAPI

**開発者だけの特別なプラグイン**

- WebAPIを使ってプレイヤーの情報を取得します

- MySQL DiscordBotTokenは必要ありません

```
値 UUID が返ってきます

http://localhost:8080/api/get/?code=codehere
http://localhost:8080/api/post/?code=codehere
```
