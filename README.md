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

| マインクラフトコマンド | 説明 |
| --- | --- |
| /addbypass {マインクラフトID} | 除外プレイヤーを追加する |
| /removebypass {マインクラフトID} | 除外プレイヤーを削除する |
| /joinmode | このプラグインの機能をオフにする |
| /sqldebug | 隠しコマンド、あまり実用的ではありません |

| Discordコマンド | 説明 |
| --- | --- |
| !join {生成された数字} | データベースに登録します |
| !remove {マインクラフトID} | データベースから強制削除 |


## LICENSE

[Apache License 2.0](https://github.com/SIGMA-L/PlayerRoleChecker/blob/main/LICENSE)
