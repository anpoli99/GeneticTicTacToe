# GeneticTicTacToe
This is an implementation of a genetic algorithm to play tic-tac-toe. It uses random mutations to a neural network to output a tic-tac-toe move. 

## Modifiable Parameters 
**int** *n_neurons*:  number of neurons in hidden layer

**int** *n_members*: number of members in the population

**int** *n_trials*: number of games simulated to test population, in each trial

**int** *n_train*: number of times each unique game is tested on population. If the population is shown a random set of games once, there is a low chance that one will adapt to learn how to play against a difficult sequence of moves, so this is motivated to prevent that. If the genetic AI always wins in a given generation, games will not be simulated again. 

**double** *p_survive* probability that member will move on to next generation

## Commands

**train** *(int)*: train for a given number of generations

**play** *(int index)*: play against a given index of the population (0 is strongest opponent). Input where you would like to move 0-8, counting horizontally with 0 in the upper left corner, 3 in the middle left and 6 in the lower left corner.
	
 **exit**: exit the program
 
 ## Results
 
 This AI is capable of learning 'perfect' play. It quickly learns that moving in the middle is an optimal strategy, and overtime learns to block opponent victories and take its own victories. Eventually, it even learns to force victory if the opponent does not move into a corner on the second move. There are easier ways to acheive perfect play in tic-tac-toe, such as via a game tree or simply hard-coding moves in a given state, but this project was intended to explore genetic algorithms, and I found it fascinating that it was able to master the game despite initially moving completely at random.
