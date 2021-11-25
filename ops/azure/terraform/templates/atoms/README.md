# Terraform Modules
## Atoms
The provided atoms mostly will create one resource with predefined configuration. 
Most of the configurations are static, this is necessary to keep their usage easy. In daily work you have rarely more 
than a couple definitions for a resource, it is a good practice to keep your atoms configurable, but not over dynamic, 
sometimes it is better to create a copy of the atom if its configuration deviates to far from your common use cases. That way they are easier to use
## Modules
Compositions are chained atoms, mostly you should not include atoms directly, but using modules instead. 
You should try to keep your modules as clean as possible, they should ensure your naming conventions and the 
streamline the orchestration of your resources.

The given modules will use `single resource group` approach, there will be one Resource Group for one terraform configuration. 
If you want to split some of your resources (e.g. databases/app insights gets own central group) you will need to change the `locals.tf` naming building.



// TODO
- add resource group atom
