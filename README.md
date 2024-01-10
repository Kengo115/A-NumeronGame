# Numeron(数字当てゲーム)
・GUI上で動作する一対一の双方向通信ゲームの作成をした.
・3つのサーバを作成することでログイン・新規登録をしたユーザが2人１組になってゲームを行い、勝敗を競うことを可能にした。
・さらにゲームの勝敗を左右する3つのアイテム機能を導入した。

利用したサーバは下記である。
・applicationServer
->ゲームの機能を搭載している。マッチングルームの管理, アイテムの処理, CALL結果の処理
・lobbyServer
->ユーザの新規登録, ログイン, ログアウト, マッチング待機ユーザの管理
・databaseServer
->ユーザのID, passward, 戦績情報などを管理

