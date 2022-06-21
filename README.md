# ExplodingArrow

A Minecraft plugin that adds a custom Bow with a custom "Explode" enchantment that can be obtained by Fishing (3% chance).

Note: This plugin does not add custom enchantment **books**.

[Download the lastest version.]()

_The max level is IV._

![](https://user-images.githubusercontent.com/81848639/174739551-fb6eb462-1398-457c-8f60-7f44542a104b.png)

Works with Minecraft 1.15.x

```yml
commands:
  giveCustomBowWithExplodeEnchant: by command (op only)
```

## ⚠️ If you want to build this yourself (probably for a newer version of Minecraft), do these steps:

**VSCode**

1. Copy the `.vscode_sample` and change the name to `.vscode`.
2. Change this line _(for intellisense)_.
   ```json
   "java.project.referencedLibraries": ["path/to/spigot-1.15.2.jar"]
   ```
3. Make changes _(If there are any changes to the Minecraft API)_.
4. In `src/plugin.yml`, change the `api-version`.
5. Build with:
   1. `CTRL+P` and type `Export Jar`
      ![step1](https://user-images.githubusercontent.com/81848639/174690739-dc636b1c-18ad-4fc6-848e-cd5ff2be14ab.png)
   2. Uncheck spigot-x.x.x.jar and click `OK`
      ![step2](https://user-images.githubusercontent.com/81848639/174696226-1097085e-4d18-416f-8978-98042f862f1f.png)

**Other editor**

_Figure it out yourself._

## See also

[How to add a plugin to your spigot server.](https://www.alphr.com/spigot-how-to-add-plugins/#:~:text=Adding%20Your%20Own%20Plugins)
