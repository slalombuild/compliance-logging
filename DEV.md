## Build

```
./gradlew clean build
```

## Signing

To be able to deploy to Maven Central, we need to sign the artifacts:
* Create a new file `config/signature/signature.properties` based on the template file `config/signature/signature.properties_template`.<br />
By default this new file will be ignored by git, but please whatever happens, do not synchronize this file with the remote repository, as it would compromise your gpg key.
```properties
signing.password=yourSecretKeyPassword
signing.secretKeyFile=/path/to/private_key
```
The secret key file is the path to your gpg secret key in ascii-armored.
More details [here](https://docs.gradle.org/current/userguide/signing_plugin.html)
* Sign the artifact when publishing to local repository:
```
./gradlew -Psign=true publishToMavenLocal
```
Verify that the `.asc` files are present in your local Maven repository

## Publish to Maven Central

### Snapshot

### Release