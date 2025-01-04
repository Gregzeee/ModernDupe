
# ModernDupe

This is a simple plugin aimed towards Minecraft dupe servers. ModernDupe includes configurable messages, a blacklist system which works with shulkers aswell as normal items and some additional configuration. 


## Authors

- [@Gregzeee](https://www.github.com/Gregzeee)


## FAQ

#### What versions does ModernDupe support?

ModernDupe supports all versions above 1.20.4



## Supported versions

##### [x] 
##### [x] 
##### [x] 
##### [x] 
##### [x] 


## Installation

Installation is simple. Download the plugin from release [here]. Drop it in your plugins folder and its ready to go! 

P.S - I heavily recommend also editing the config.yml before using the plugin 

## Config.yml

```yaml
# All permissions linked to commands
permissions:
  dupe: "moderndupe.use" # Permission required to use /dupe
  reload: "moderndupe.reload" # Permission required to reload the configuration

# TODO - new format: 1: moderndupe.dupecount.vip|2 etc
# Dupe count limits.
dupeCountLimits:
  moderndupe.dupecount.vip: 2 # Players with this permission will be able to dupe up to 2
  moderndupe.dupecount.mvp: 4 # Players with this permission will be able to dupe up to 4 and so on
  moderndupe.dupecount.legend: 6

# Blacklist for items that you are not allowed to dupe
blacklist:
  - "BEDROCK"
  - "AIR"
  - "COMMAND_BLOCK"
  - "GRASS_BLOCK"

# All messages used within the plugin
messages:
  reloadSuccessful: "&aConfiguration reload was successful!" # Sent when reloading the config was successful
  reloadFailed: "&cConfiguration reload was unsuccessful. Check console for more details" # Sent when reloading the configuration failed
  noPermission: "&cYou don't have permission to use this command!" # Sent if the player doesn't have permission to execute said command
  exceededMaxDupeCount: "&cYou've exceeded the maximum dupe count. Please try a lower amount!" # Sent if the player tries to dupe more than they are allowed to at once
  onlyPlayers: "&cOnly players can use this command!" # Sent if a command is executed by someone that isn't a player
  cantDupe: "&cYou can't dupe that item, because it contains a blacklisted item!" # Sent if the player tries to use /dupe, but one of the items is blacklisted
  invalidDupeCount: "&cYou entered an invalid dupe count!"
```

## Issues

For reporting issues simply create an issue [here].

