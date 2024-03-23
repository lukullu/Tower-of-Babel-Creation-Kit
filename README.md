# Tower of Babel Creation Kit

## Description

Basic physics and collision engine for game/simulation development.\
Mostly meant to improve my skills with Java, algorithm design and implementation.

Currently hooking into the Processing API for easy Rendering. Will use pure OpenGL someday.

## How to Use

Demo in:\
`src/main/java/com.lukullu.undersquare`

For own project:
1. create a new package under:\
`src/main/java/com.yourName.projectName`
2. Create a "main class" with the title of your project as its name.\
`src/main/java/com.yourName.projectName.projectName.class`
3. Go to the Main class in:\
`src/main/java/com.lukullu.Main.class`
4. Change "Undersquare3" to the name of your projects main class and import it.
5. Make your project main class extend:
`src/main/java/com.kilix.processing.ExtendedPApplet.class`
6. You can now use this class as you would a Processing sketch.\
Documentation can be found here: `https://processing.org/reference`
7. For every other class you want to use Processing features in implement\
`src/main/java/com.kilix.processing.ProcessingClass.class`



## ToDo
- [x] General Collision Detection (SAT)
- [x] General Collision Resolution
- [ ] Spacial Partitioning (KD-Tree)
- [x] Physics Engine
- [ ] Physics Mass Bug Fix
- [ ] Physics Friction
- [ ] Physics Weird Corner Bug
- [x] Mesh Entity
- [x] Mesh Entity Dismemberment
- [x] Mesh Entity Collision
- [x] Mesh Entity Rotation
- [ ] Compound Entity
- [ ] Static Objects (No-Phyiscs Objects)
- [ ] Meta Objects (Trigger)
- [ ] UI Objects