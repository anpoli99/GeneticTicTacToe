package tictactoe;

import java.util.Random;
import java.util.Scanner;

/*
 * genetic algorithm that uses neural network to learn to play tictactoe
 */
public class TicTacToe {

	static int[] board = new int[9];
	static int[] inputBoard = new int[27];
	static int n_neurons = 30;  //number of neurons in hidden layer
	static int n_members = 1000;//number of members in the population
	static int n_trials = 20;	//number of games simulated to test population
	static int n_train = 50;	//number of times games simulated on each member of population
	static double p_survive = .3; //probability that member will move on to next generation
	
	//neural network implementation
	static double[][][] w1 = new double[n_members][27][n_neurons];
	static double[] hL = new double[n_neurons];
	static double[][][] w2 = new double[n_members][n_neurons][9];
	static double[] output = new double[9];
	
	
	//score = number of games won, antiscore =  number of games lost (discrepancy = ties)
	static double[] score = new double[n_members];
	static double[] antiscore = new double[n_members];	
	static double initW = 1;
	
	static double var = .4; //variance of weigths in each network
	static Random r = new Random();
	static int currTest = 0;
	static int[][] testers = new int[n_trials][9];
	
	public static void main(String[] args) {
		for(int i = 0 ; i < w1.length; i++) {
			for(int j = 0; j < w1[0].length; j++) {
				for(int k = 0; k < w1[0][0].length; k++) {
					w1[i][j][k] = initW - (Math.random() * initW / 2);
				}
			}
		}
		for(int i = 0 ; i < w2.length; i++) {
			for(int j = 0; j < w2[0].length; j++) {
				for(int k = 0; k < w2[0][0].length; k++) {
					w2[i][j][k] = initW - (Math.random() * initW / 2);;
				}
			}
		}
		run();

	}
	public static void newGame(int i) {
		board = new int[9];
		inputBoard = new int[27];
		for(int j = 0; j < 27; j += 3) {
			inputBoard[j] = 1;
		}
		game(i);
	}
	public static void game(int idx) {
		
		hL = new double[n_neurons];
		for(int i = 0; i < w1[idx].length; i++) {
			for(int j = 0; j < w1[idx][i].length; j++) {
				hL[j] += inputBoard[i] * w1[idx][i][j];
			}
		}
		output = new double[9];
		for(int i = 0; i < w2[idx].length; i++) {
			for(int j = 0; j < w2[idx][i].length; j++) {
				output[j] += hL[i] * w2[idx][i][j];
			}
		}
		double currmin = -100000; int curridx = -1;
		for(int i = 0; i < 9; i++) {
			if(output[i] > currmin && board[i] == 0) {
				currmin = output[i];
				curridx = i;
			}
		}
		board[curridx] = 1;
		inputBoard[curridx * 3 + 1] = 1; inputBoard[curridx * 3] = 0;
		boolean empty = false;
		for(int i = 0; i < board.length; i++) {
			if(board[i] == 0) {empty = true; break;}
		}
		
		int o_idx = -1;
		if((board[0] == 1 && board[3] == 1 && board[6] == 1) ||
				(board[1] == 1 && board[4] == 1 && board[7] == 1) ||
				(board[2] == 1 && board[5] == 1 && board[8] == 1) ||
				(board[0] == 1 && board[1] == 1 && board[2] == 1) ||
				(board[3] == 1 && board[4] == 1 && board[5] == 1) ||
				(board[6] == 1 && board[7] == 1 && board[8] == 1) ||
				(board[0] == 1 && board[4] == 1 && board[8] == 1) ||
				(board[2] == 1 && board[4] == 1 && board[6] == 1) ) {
			score[idx] += 1; return;
		}
		if(!empty) {
			score[idx] -= .5; /*antiscore[idx] += .5;*/ return;
			
		}
		for(int i = 0; i < 9; i++) {
			if(board[testers[currTest][i]] == 0){
				o_idx = testers[currTest][i];
				break;
			}
		}
		if(board[0] == 2 && board[3] == 2 && board[6] == 0) {o_idx = 6;}
		if(board[0] == 2 && board[3] == 0 && board[6] == 2) {o_idx = 3;}
		if(board[0] == 0 && board[3] == 2 && board[6] == 2) {o_idx = 0;}
		
		if(board[1] == 2 && board[4] == 2 && board[7] == 0) {o_idx = 7;}
		if(board[1] == 2 && board[4] == 0 && board[7] == 2) {o_idx = 4;}
		if(board[1] == 0 && board[4] == 2 && board[7] == 2) {o_idx = 1;}
		
		if(board[2] == 2 && board[5] == 2 && board[8] == 0) {o_idx = 8;}
		if(board[2] == 2 && board[5] == 0 && board[8] == 2) {o_idx = 5;}
		if(board[2] == 0 && board[5] == 2 && board[8] == 2) {o_idx = 2;}
		
		if(board[0] == 2 && board[1] == 2 && board[2] == 0) {o_idx = 2;}
		if(board[0] == 2 && board[1] == 0 && board[2] == 2) {o_idx = 1;}
		if(board[0] == 0 && board[1] == 2 && board[2] == 2) {o_idx = 0;}
		
		if(board[3] == 2 && board[4] == 2 && board[5] == 0) {o_idx = 5;}
		if(board[3] == 2 && board[4] == 0 && board[5] == 2) {o_idx = 4;}
		if(board[3] == 0 && board[4] == 2 && board[5] == 2) {o_idx = 3;}
		
		if(board[6] == 2 && board[7] == 2 && board[8] == 0) {o_idx = 8;}
		if(board[6] == 2 && board[7] == 0 && board[8] == 2) {o_idx = 7;}
		if(board[6] == 0 && board[7] == 2 && board[8] == 2) {o_idx = 6;}
		
		if(board[0] == 2 && board[4] == 2 && board[8] == 0) {o_idx = 8;}
		if(board[0] == 2 && board[4] == 0 && board[8] == 2) {o_idx = 4;}
		if(board[0] == 0 && board[4] == 2 && board[8] == 2) {o_idx = 0;}
		
		if(board[2] == 2 && board[4] == 2 && board[6] == 0) {o_idx = 6;}
		if(board[2] == 2 && board[4] == 0 && board[6] == 2) {o_idx = 4;}
		if(board[2] == 0 && board[4] == 2 && board[6] == 2) {o_idx = 2;}
		
		board[o_idx] = 2; 
		inputBoard[o_idx * 3 + 2] = 1; inputBoard[o_idx * 3] = 0;
		if((board[0] == 2 && board[3] == 2 && board[6] == 2) ||
				(board[1] == 2 && board[4] == 2 && board[7] == 2) ||
				(board[2] == 2 && board[5] == 2 && board[8] == 2) ||
				(board[0] == 2 && board[1] == 2 && board[2] == 2) ||
				(board[3] == 2 && board[4] == 2 && board[5] == 2) ||
				(board[6] == 2 && board[7] == 2 && board[8] == 2) ||
				(board[0] == 2 && board[4] == 2 && board[8] == 2) ||
				(board[2] == 2 && board[4] == 2 && board[6] == 2) ) {
			antiscore[idx]++; return;
		}
		game(idx);
	}
	public static  void run() {
		Scanner sc = new Scanner(System.in);
		System.out.println("input command:");
		String s = sc.next();
		if(s.equals("train")) {
			System.out.println("how many?");
			int i;
			try {
				i = Integer.parseInt(sc.next());
			}catch(NumberFormatException e) {
				e.printStackTrace();
				i = 0;
			}
			
			while(i > 0) {
				i--;
				for(int j = 0; j < n_trials; j++) {
					boolean[] assigned = new boolean[9];
					for(int k = 0; k < 9; k++) {
						int it = (int)(Math.random() * 9);
						if(assigned[it]) {k--; continue;}
						assigned[it] = true;
						testers[j][k] = it;
					}
				}
				int trial = n_train;
				while(trial > 0) {
					trial--;
					
					System.out.println(i + "," + trial);
					for(int j = 0; j < n_members; j++) {
						for(int k = 0; k < n_trials; k++) {
							newGame(j);
							currTest++;
						}
						currTest = 0;
					}
					int[] survivors = new int[(int)(p_survive * n_members)];
					for(int j = 0; j < (int)(p_survive * n_members); j++) {
						int idx = 0;
						for(int k = 1; k < score.length; k++) {if(antiscore[k] < antiscore[idx] 
								|| (antiscore[k] == antiscore[idx] && score[k] > score[idx])) {
							idx = k; }  
						}
						if(score[idx] == n_trials) {
							trial = 0;
						}
						score[idx] = 0;
						survivors[j] = idx;
					}
					double[][][] w1clone = new double[n_members][27][n_neurons];
					double[][][] w2clone = new double[n_members][n_neurons][9];
					int idx = 0;
					for(int j = 0; j < survivors.length; j++) {
						for(int k = 0; k < w1[0].length; k++) {
							for(int h = 0; h < w1[0][0].length; h++) {
								w1clone[idx][k][h] = w1[survivors[j]][k][h];
							}
						}
						for(int k = 0; k < w2[0].length; k++) {
							for(int h = 0; h < w2[0][0].length; h++) {
								w2clone[idx][k][h] = w2[survivors[j]][k][h];
							}
						}
						idx++;
					}
					int foo = idx; 
					for(idx = foo; idx < n_members - (int)(p_survive * n_members); idx++) {
						if(Math.random() > .5) {
							int thisIdx = (int)(Math.random() * survivors.length);
							w1clone[idx] = w1[survivors[thisIdx]];
							w2clone[idx] = w2[survivors[thisIdx]];
							for(int k = 0; k < w1[0].length; k++) {
								for(int h = 0; h < w1[0][0].length; h++) {
									w1clone[idx][k][h] +=  (var - 2 * var * Math.random());
								}
							}
							for(int k = 0; k < w2[0].length; k++) {
								for(int h = 0; h < w2[0][0].length; h++) {
									w2clone[idx][k][h] += (var - 2 * var * Math.random());
								}
							}
						}else {
							int p1 = (int)(Math.random() * survivors.length), p2 = (int)(Math.random() * survivors.length);
							double favor = Math.random();
							for(int k = 0; k < w1[0].length; k++) {
								for(int h = 0; h < w1[0][0].length; h++) {
									if(Math.random() > favor) {
										w1clone[idx][k][h] = w1[survivors[p1]][k][h];
									}else {
										w1clone[idx][k][h] = w1[survivors[p2]][k][h];
									}
								}
							}
							for(int k = 0; k < w2[0].length; k++) {
								for(int h = 0; h < w2[0][0].length; h++) {
									if(Math.random() > favor) {
										w2clone[idx][k][h] = w2[survivors[p1]][k][h];
									}else {
										w2clone[idx][k][h] = w2[survivors[p2]][k][h];
									}
								}
							}
						}
					}
					w1 = w1clone;
					w2 = w2clone;
					score = new double[n_members];
					antiscore = new double[n_members];
				}
			}
			
			run();
		}else if(s.equals("play")) {
			System.out.println("idx?");
			int idx;
			try {
				idx = Integer.parseInt(sc.next());
			}catch(NumberFormatException e) {
				e.printStackTrace();
				run();
				sc.close();
				return;
			}
			board = new int[9];
			inputBoard = new int[27];
			for(int j = 0; j < 27; j += 3) {
				inputBoard[j] = 1;
			}
			int state = 0;
			while(state == 0){
				hL = new double[n_neurons];
				for(int i = 0; i < w1[idx].length; i++) {
					for(int j = 0; j < w1[idx][i].length; j++) {
						hL[j] += inputBoard[i] * w1[idx][i][j];
					}
				}
				output = new double[9];
				for(int i = 0; i < w2[idx].length; i++) {
					for(int j = 0; j < w2[idx][i].length; j++) {
						output[j] += hL[i] * w2[idx][i][j];
					}
				}
				double currmin = -1000000000; int curridx = -1;
				for(int i = 0; i < 9; i++) {
					if(output[i] > currmin && board[i] == 0) {
						currmin = output[i];
						curridx = i;
					}
				}
				board[curridx] = 1;
				inputBoard[curridx * 3 + 1] = 1; inputBoard[curridx * 3] = 0;
				boolean empty = false;
				for(int i = 0; i < board.length; i++) {
					if(board[i] == 0) {empty = true; break;}
				}
				
				if((board[0] == 1 && board[3] == 1 && board[6] == 1) ||
						(board[1] == 1 && board[4] == 1 && board[7] == 1) ||
						(board[2] == 1 && board[5] == 1 && board[8] == 1) ||
						(board[0] == 1 && board[1] == 1 && board[2] == 1) ||
						(board[3] == 1 && board[4] == 1 && board[5] == 1) ||
						(board[6] == 1 && board[7] == 1 && board[8] == 1) ||
						(board[0] == 1 && board[4] == 1 && board[8] == 1) ||
						(board[2] == 1 && board[4] == 1 && board[6] == 1) ) {
					state = 1; break;
				}
				if(!empty) {
					state = -1; break;
				}
				for(int i = 0; i < 9; i++) {
					if(i % 3 == 0) {System.out.println();}
					if(board[i] == 0) {
						System.out.print("E");
					}else if(board[i] == 1) {
						System.out.print("X");
					}else if(board[i] == 2) {
						System.out.print("O");
					}
				}
				System.out.println();
				int o_move = -1;
				while(o_move < 0 || o_move > 9 || board[o_move] != 0 ){
					System.out.println("move?");
					String str = sc.next();
					try {
						o_move = Integer.parseInt(str); 
					}catch (NumberFormatException e) {
						System.out.println("Enter a number [0-8]");
						o_move = -5;
					}
					if(o_move < 0 || o_move >= 9 ||  board[o_move] != 0) {
						System.out.println("illegal move! Enter a number [0-8]");
					}
				}
				board[o_move] = 2;
				inputBoard[o_move * 3 + 2] = 1; inputBoard[o_move * 3] = 0;
				if((board[0] == 2 && board[3] == 2 && board[6] == 2) ||
						(board[1] == 2 && board[4] == 2 && board[7] == 2) ||
						(board[2] == 2 && board[5] == 2 && board[8] == 2) ||
						(board[0] == 2 && board[1] == 2 && board[2] == 2) ||
						(board[3] == 2 && board[4] == 2 && board[5] == 2) ||
						(board[6] == 2 && board[7] == 2 && board[8] == 2) ||
						(board[0] == 2 && board[4] == 2 && board[8] == 2) ||
						(board[2] == 2 && board[4] == 2 && board[6] == 2) ) {
					state = 2;
				}
			}
			for(int i = 0; i < 9; i++) {
				if(i % 3 == 0) {System.out.println();}
				if(board[i] == 0) {
					System.out.print("E");
				}else if(board[i] == 1) {
					System.out.print("X");
				}else if(board[i] == 2) {
					System.out.print("O");
				}
			}
			System.out.println();
			switch(state) {
			case 0:System.out.println("Error?"); break;
			case 1:System.out.println("X wins!"); break;
			case 2:System.out.println("O wins!"); break;
			case -1:System.out.println("Cat's game!"); break;
			}
			run();
		}else if(!s.equals("exit")) {
			System.out.println("unknown command!");
			run();
		}
		sc.close();
	}
}