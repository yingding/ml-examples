# 0. Config Server seed (Linux & Macosx & Windows)
To make the local sbt executable
* `cd server`
* chmod 700 sbt
* chmod -R 700 ./sbt-dist/bin

# 1. How to use this seed? (Linux & Macosx)
## 1.1 Deploy your web-client
* Change the path in your console to folder webClient with `cd webClient`
* Install npm packages for building the client with `npm install`
* Deploy the production client codes to the server `npm run build`

## 1.2 config your server
* open the config file `server/conf/application.conf` in IDE
* edit the myApp.home variable with the absolute path of your conf folder
```
myApp.home="/Users/yingding/VCS/github/hca17/week3/server/conf"
```
Note: Windows OS shall use the exactly the same path string as linux and macosx, for local.properties in Android, windows need to `\\` and `\:` in the path.
* edit the mongodb connection URL
```
playjongo.uri="mongodb://username:password@127.0.0.1:27017/mydb"
```
Note: 
* replace the username with your mongodb username
* replace the password with your mongodb password for the username
* replace the 127.0.0.1 with your host ip
* replace mydb with your mongodb database name, in demo case it is the same "mydb"

## 1.3 Start server
* Change the path in your console to folder server with `cd ../server`
* Open the play interactive console with `./sbt`
* Type `~run` to start play server in watch mode
* Stop play server with `Ctl + D`
* Then `enter` 

## 1.4 See daemon in action
* The play module follows lazy loading, with out the tcp request server module will not be loaded.
* Call your web-client `localhost:9000` in browser and examine the output in the sbt console
* A example daemon printing out a date string for every minutes can be seen.

# 2. Issue while import the sbt project in IntelliJ IDEA
* right click on week3 (root of the project)
* Click on "File" (in Menu) -> Project Structure... -> remove the root and root-build module in Module section with "-"
* open the build.sbt in server folder in IntelliJ IDEA utimate
* Click on the "import project" on the yellow popup message
* After indexing the play java project shall be imported properly

# 3. Usefull commands
* get the sbt version with `sbt sbtVersion`

# Opening project and adding modules
1. Open the project in week3 level, choose java 1.8 sdk for week3
2. import existing module, choose the server module and import as sbt project, this can only be done from the project settings -> + -> Import Module -> sbt (you shall installed the sbt plugin before)
3. add the angular cli module
4. if you got some error sbt sdk shall be set, go to project week3 settings and choose jdk 1.8