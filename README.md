# MicroVault

## Usage

```shell
# create a new vault with given password at the given location
mv --password=YourPassword --file=./micro.vault create

# open a session to continues edit the vault. The command substitution is required to set a environment variable
$(mv --password=YourPassword --file=./micro.vault open -e) # MICRO_VAULT_SESSION=<SESSION_DATA>

# add data to the vault
mv set db.user Admin
mv set db.password Admin123

mv set docker.user MicroVault
mv set docker.email microvault@example.com
mv set docker.password MicroVault123

# get data from the vault
mv get docker.password

# list content of the vault
mv list

# publish all data as environment variables
$(mv publish environment) # export DOCKER_PASSWORD=<docker.password>

# close the vault again
$(mv close) # MICRO_VAULT_SESSION=

# Use variable as command
MV="mv --password=YourPassword --file=./micro.vault"
$MV get docker.password
$MV publish kubernetes --parameter name=my-secrets

```

## Encryption Modes

| Mode       | Encryption                              | Public writable    | Public readable    |
|------------|-----------------------------------------|--------------------|--------------------|
| symmetric  | `AES/GCM/NoPadding`                     | :x:                | :x:                |
| asymmetric | `RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING` | :heavy_check_mark: | :x:                |
| plain      | -                                       | :heavy_check_mark: | :heavy_check_mark: |

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