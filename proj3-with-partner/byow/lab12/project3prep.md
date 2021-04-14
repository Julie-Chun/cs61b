# Project 3 Prep

**For tessellating hexagons, one of the hardest parts is figuring out where to place each hexagon/how to easily place hexagons on screen in an algorithmic way.
After looking at your own implementation, consider the implementation provided near the end of the lab.
How did your implementation differ from the given one? What lessons can be learned from it?**

Answer: My implementation did not get far because I was not sure how to utilize the given classes. There were many things to learn from the implementation provided at the end of lab. It was very elegant in how each class predicts (in a way) what information another class would need from them, and provides information through public methods.

-----

**Can you think of an analogy between the process of tessellating hexagons and randomly generating a world using rooms and hallways?
What is the hexagon and what is the tesselation on the Project 3 side?**

Answer: Maybe we could have used recursion for fractal to create a solution for tessellating hexagons.

-----
**If you were to start working on world generation, what kind of method would you think of writing first? 
Think back to the lab and the process used to eventually get to tessellating hexagons.**

Answer: I would think of writing a method that would be needed in other classes. So, knowing what informations need to be sent back and forth to reduce complexity.

-----
**What distinguishes a hallway from a room? How are they similar?**

Answer: A hallway is defined by two walls, whereas a room has four. they are similar in that they are defined by the number of walls they have.
