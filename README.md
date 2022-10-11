<div align="center">
	<p>
	<a href="https://github.com/kpalatzky/microvault#is=awesome">
            <img src="src/main/resources/assets/logo.svg" alt="MicroVault Logo" width="80%"/>
        </a>
    </p>
    <p>    
	<a href="https://www.codacy.com/gh/kpalatzky/microvault/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=kpalatzky/microvault&amp;utm_campaign=Badge_Grade">
		<img src="https://app.codacy.com/project/badge/Grade/4f7a56fe9664480db770eb9895d6fcf6"/>
	    </a>
	    <a href="https://github.com/kpalatzky/microvault#is=awesome">
      <img src="https://img.shields.io/badge/-in%20development-red" alt="In development">
    </a>
    <a href="https://app.dependabot.com/">
      <img src="https://img.shields.io/badge/Dependabot-active-brightgreen.svg?logo=dependabot" alt="Dependabot active">
    </a>
	</p>
	<p>
	</p>
	<hr>
	<p>
		Keep your secrets secret. Tool to manage secrets in your public and private repositories. 
	</p>
</div>

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

# open vault in interative mode
$MV open --interative
> get docker.password
> set docker.password Docker123
> exit
```

## Encryption Modes

| Mode       | Encryption                              | Key Length | Public writable    | Public readable    |
|------------|-----------------------------------------|------------|--------------------|--------------------|
| symmetric  | `AES/GCM/NoPadding`                     | 256        | :x:                | :x:                |
| asymmetric | `RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING` | 4096       | :heavy_check_mark: | :x:                |
| plain      | -                                       |            | :heavy_check_mark: | :heavy_check_mark: |

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

## Development

### Native Build

Run one of the following command to build the tool in native mode:

```shell
# The first build may take up to 5-10 minutes depending on your computer specs 
./gradlew build -Dquarkus.package.type=native -Dquarkus.native.container-build=true -Dquarkus.banner.enabled=false -Dquarkus.log.console.enable=false
```

### UberJAR / FatJAR
```shell
# The first build may take up to 2-5 minutes depending on your computer specs 
./gradlew build  -Dquarkus.package.type=uber-jar -Dquarkus.banner.enabled=false -Dquarkus.log.console.enable=false
```
