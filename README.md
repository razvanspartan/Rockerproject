
# VERY IMPORTANT PLEASE READ
Project made by Razvan Bichiu.
### Hello, first off I want to thank you for checking out my project and I hope you enjoy it, now let me clear some things off:
### 1. Have sound on please.
### 2. This project uses javaFx make sure you have it install if you want to run this project
### 3. Maven might bug out on your machine if you use intellij and stuff doesn't work do this or I think removing test package might also work
Configure IntelliJ to Use Maven for Tests
Go to File → Settings → Build, Execution, Deployment → Build Tools → Maven
Under Runner, set "Delegate IDE build/run actions to Maven"
Apply & restart IntelliJ.

### 4. Some values might be different than what you expect, this is because I used BigDecimal which does computations using many many decimals, so you might see some rounding errors, but they should be closer to the reality, they especially become apparent for large values I'm really sorry for this, I tried my best

## Now if you're interested or if you are just really confused by what's happening in the code (like I was at some points), I will explain everything for each stage below:

### Setup:
For the setup I tried to go for a MVC design pattern, I made my models, I have decided to make extension classes (and not do the computations in ServiceForCalculations) for the models for some calculations since I thought stuff like angular position and escape velocity are strongly coupled with the model itself.

For parsing I used a regex to check if the line was in the correct format, then I simply split the line in tokens and put each value where it was supposed to be, I have decided to make the app take all file paths at once since letting the user choose which files to give and which not to give would've been a headache.

### Stages 1 and 2
These ones were pretty straighforward, I simply used the formula given in the pdf file to compute the values and called the class directly for the values, nothing really to explain here, all the methods are in PlanetCalculations and RocketCalculations

### Stage 3
This is where things got a bit interesting I made 6 methods in total:

getCruisingSpeed - this just compares the escape velocities of the 2 planets and returns the highest one

getCruisingDistance - this got the distance between the 2 planets in meters and substracted the radii and 2 times the distance to reach escape velocity (the distance to reach this velocity is computed in RocketUtils)

getJourneyTimeAtCruisingVelocity - is the getCruisingDistance divided by cruising speed;

getTotalJourneyTime (this one is returned in seconds)  - here I simply added the Journey time at cruising velocity + time to reach escape velocity * 2 (the time to accelerate or deaccelerate is the same so we can just do escape velo*2)

getDistanceFromPlanetAtAccOrDeacc - pretty straightforward, add distancefromPlanet at reaching escape velocity + planet's radius

getTimeInDaysMinutesSeconds - this returned the time in Days\Hours\Seconds as an Array

And Finally toStringGetTime - simply formatted the array from the previous method as a String

## Stage 4

Nothing to explain here just applied the formula for the angular position method is found in PlanetCalculations

## Stage 5 and 6

Here is where it got a bit complicated and I regretted not making more methods, I thought that by making less method the code would be cleaner but it just made me have 10 method calls in one method.
Nevertheless I thought about it like this: I used a formula to see when the next alignment would be (angular positions are equal), this would be our launching time.
Next I went through an array of the planets in order, starting from our launching planet to our destination planet, if the starting planet was ahead of the destination planet I simply switched them.
In this loop, I checked if any planets were in the way like this:
1. Get the coordinates of the  starting planet and destination planet
2. Compute the distance from a point (Planet that's in the way) to a line (Rocket's path)
3. If this distance was smaller or equal to the radius of the planet that's in the way this means that we are inside the planet (AKA collision)
4. If collision happened I went to the next window of allignment and repeated the steps

For stage 6 stuff were a little bit more complicated but in the end I managed to find a way, basically I used pretty much all the methods used for stage 5 with some exceptions:
1. To get a good time to launch I basically went through 36500 days and checked the position of my planet,  and the position of the target planet at the time I was on + the time it took to get to it, if the positions were equal this meant that if I launched at time 0, target planet would be in the same position my starting planet was at time 0, at time 0 + timeToGetToTargetPlanet
2. To compute collisions in this stage, I just used the same tactic as stage 5 except I computed planet's positions based on the position they would be when my rocket got to them.

## Stage 7

For this I was really excited, from the start of the project I came up with the idea to make it as if each stage is a testing sequence for our rocket and that Stage 6 would be the launch to another planet
This was mostly javaFX so there's no point in explaining what I did here.

If you've read so far you're a cool guy, again thank you for checking out my project I hoped my explanations were useful.

Have a wonderful day,
Razvan

CREDITS: 
hyperspace video: https://www.youtube.com/watch?v=6ga4IICXyCE&ab_channel=Jeelh

warp drive active sound: EVE online game

AI profile picture: Alpha core from the game STARSECTOR

