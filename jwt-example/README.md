### ⚙️ What does this plugin do?

This is a demo plugin that uses [JWT](https://fr.wikipedia.org/wiki/JSON_Web_Token) with an [SHA-256](https://fr.wikipedia.org/wiki/SHA-2#SHA-256) hash to authenticate connections.

**This plugin blocks connections that come from a transfer and which do not come from this proxy.** This is an interesting security feature to prevent players from connecting to your servers without going through your proxy.

The JWT token and hash password are configurable in the `config.yml`

If you want to use this plugin as a base and then modify the transfer rules, you can simply modify [this method](https://github.com/Darkkraft/TransferProxy-Demo-plugin/blob/master/jwt-example/src/main/java/be/darkkraft/transferproxy/jwtexample/listener/TransferListener.java#L36).