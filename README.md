# What is this
A little tech demo in the form of a game I made during / for my high school Computer Science class during Spring of 2020.

The assignment was to simply "make a game," but I decided to go all out and, by doing so, teach myself something in the process.

I wrote the 3D renderer itself (the thing that figures out how to draw 3D objects on your screen) to learn more about computer graphics and basic linear algebra. The renderer is pretty simple, as I only had a few weeks to complete the whole project, but it works nonetheless. If I were to re-write the renderer now, I would write a proper depth buffer instead of the somewhat hacky (and as it turns out, much more complex) method that I used of placing all of the triangles to be rendered into a priority queue (max heap).

In terms of cleanliness, the code in here isn't the greatest (as is frequently the case when writing something complicated in a short period of time) but it's also not the worst and given the time constraints of the project, I'm proud of it.

Surprisingly it ended up being one of the most fun games in the class (as per my teacher and classmates), despite the fact that I didn't have much time to spend working on gameplay -- focusing my efforts largely on the renderer. 

# How to play using [Processing](https://processing.org/ "Link to Processing Foundation")
 - If it isn't already installed, install the Processing Sound Library
 - Press space to start / restart
 - Arrow keys to move

# Credits / Thanks
 - [Javidx9](https://www.youtube.com/channel/UC-yuWVUplUJZvieEligKBkA "OLC Youtube Channel") / [OneLoneCoder](https://github.com/OneLoneCoder "OLC Github") for his excellent series on writing a 3D engine, as well as the spaceship model
 - [wyver9](https://wyver9.bandcamp.com/ "wyver9 BandCamp page") for their music
