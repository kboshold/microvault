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
	    <a href="https://github.com/kpalatzky/microvault/releases/latest">
     <img src="https://badge.fury.io/gh/kpalatzky%2Fmicrovault.svg" alt="GitHub version">
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

### Get started

## Installation

```shell
curl https://raw.githubusercontent.com/kpalatzky/microvault/master/src/main/resources/scripts/install.sh | sh
```

## Usage

```shell
# create a new vault with given password at the given location
miva --password=YourPassword --file=./micro.vault create

# open a session to continues edit the vault. The command substitution is required to set a environment variable
$(miva --password=YourPassword --file=./micro.vault open -e) # MICRO_VAULT_SESSION=<SESSION_DATA>

# add data to the vault
miva set db.user Admin
miva set db.password Admin123

miva set docker.user MicroVault
miva set docker.email microvault@example.com
miva set docker.password MicroVault123

# get data from the vault
miva get docker.password

# list content of the vault
miva list

# publish all data as environment variables
$(miva publish environment) # export DOCKER_PASSWORD=<docker.password>

# close the vault again
$(miva close) # MICRO_VAULT_SESSION=

# Use variable as command
MIVA="miva --password=YourPassword --file=./micro.vault"
$MIVA get docker.password
$MIVA publish kubernetes --parameter name=my-secrets

# open vault in interative mode
$MIVA open --interative
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
  "version" : "1.0.0",
  "encryption" : {
    "mode" : "asymmetric",
    "salt" : "<SALT_ENCODED>",
    "readKey" : "<READ_KEY_ENCRYPTED>",
    "writeKey" : "<WRITE_KEY_ENCRYPTED>",
    "key": "<READ_WRITE_KEY_ENCRYPTED>"
  },
  "data" : {
    "<ENCRYPTED_KEY>": "<ENCRYPTED_VALUE>"
  }
}
```

## Development
### Native Build
```shell
./gradlew build -Dquarkus.package.type=native -Dquarkus.native.container-build=true -Dquarkus.banner.enabled=false -Dquarkus.log.console.enable=false
```

### UberJar / FatJar

```shell
 ./gradlew build  -Dquarkus.package.type=uber-jar -Dquarkus.banner.enabled=false -Dquarkus.log.console.enable=false
```
