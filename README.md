
### VERY IMPORTANT PLEASE READ
Hello, first off I want to thank you for checking out my project now let me clear some things off:
## 1. This project uses javaFx make sure you have it install if you want to run this project
## 2. I might have messsed up maven installation, if you use intellij and stuff doesn't work do this or I think removing test package might also work
Configure IntelliJ to Use Maven for Tests
Go to File → Settings → Build, Execution, Deployment → Build Tools → Maven
Under Runner, set "Delegate IDE build/run actions to Maven"
Apply & restart IntelliJ.

## 2. Some values might be different than what you expect, this is because I used BigDecimal which does computations using many many decimals, so you might see some rounding errors, but they should be closer to the reality, they especially become apparent for large values I'm really sorry for this, I tried my best