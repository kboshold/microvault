# MicroVault

## Usage

```shell
mv --password=YourPassword --file=./micro.vault create

$(mv --password=YourPassword --file=./micro.vault open) # export MICRO_VAULT_SESSION=<SESSION_DATA>

mv set db.user Admin
mv set db.password Admin123

mv set docker.user MicroVault
mv set docker.email microvault@example.com
mv set docker.password MicroVault123

mv get docker.password

mv list

$(mv publish environment) # export DOCKER_PASSWORD=<docker.password>

$(mv close) # export MICRO_VAULT_SESSION=
```

## Encryption Modes

| Mode       | Encryption                            | Public writable | Public readable |
|------------|---------------------------------------|-----------------|-----------------|
| symmetric  | AES/GCM/NoPadding                     | [ ]             | [ ]             |
| asymmetric | RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING | [x]             | [ ]             |
| plain      | -                                     | [x]             | [x]             |

## Vault file

```json
{
  "version": "1.0",
  "checksum": "ksdjklsjadfkö",
  "encryption": {
    "key": "<readWriteKey>",
    "read": "<readKey>",
    "write": "<writeKey>",
    "mode": ""
  },
  "options": {
    
  },
  "data": {
    "<key>": "<value>"
  }
}
```