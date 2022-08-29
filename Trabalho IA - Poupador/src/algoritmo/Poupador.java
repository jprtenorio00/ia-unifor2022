package algoritmo;

import java.awt.Point;
import java.util.Arrays;
import java.util.Random;

public class Poupador extends ProgramaPoupador {

	int wall = 1;
	int coin = 4;
	int bank = 3;
	int OOB = -1;
	Random r = new Random();
	int[][] map = new int[30][30];
	
	
	@Override
	public int acao() {
		int[] vision = this.sensor.getVisaoIdentificacao();
		int wallet = this.sensor.getNumeroDeMoedas();
		int pace = 1;
		Point position = this.sensor.getPosicao();
		Point posBank;
		boolean explore = true;
		
		System.out.println(position.x+" "+position.y);
		map[position.x][position.y]++;
		
		if(explore){
			if(position.x==0) {
				if(position.y==0) {
					while(pace==0 || pace==4 || pace==1) {
						pace = (int) r.nextInt(4-1) + 1;
					}
					return pace;
				}else if(position.y==29) {
					while(pace==0 || pace==4 || pace==2) {
						pace = (int) r.nextInt(4-1) + 1;
					}
					return pace;
				}else{
					while(pace==0 || pace==4) {
						pace = (int) r.nextInt(4-1) + 1;
					}
					return pace;
				}
			}else if(position.x==29){
				if(position.y==0) {
					while(pace==0 || pace==3 || pace==1) {
						pace = (int) r.nextInt(4-1) + 1;
					}
					return pace;
				}else if(position.y==29) {
					while(pace==0 || pace==3 || pace==2) {
						pace = (int) r.nextInt(4-1) + 1;
					}
					return pace;
				}else{
					while(pace==0 || pace==3) {
						pace = (int) r.nextInt(4-1) + 1;
					}
					return pace;
				}
			}else if(position.y==0){
				while(pace==0 || pace==1) {
					pace = (int) r.nextInt(4-1) + 1;
				}
				return pace;
			}else if(position.y==29){
				while(pace==0 || pace==2) {
					pace = (int) r.nextInt(4-1) + 1;
				}
				return pace;
			}else {
				pace = (int) r.nextInt(4-1) + 1;
				return pace;
			}
		/*if(map[position.x][position.y+1]<map[position.x][position.y] && map[position.x][position.y+1]!= OOB) {
			System.out.println("Down");
			return 2;
		}else if(map[position.x][position.y-1]<map[position.x][position.y] && map[position.x][position.y-1]!= OOB) {
			System.out.println("Up");
			return 1;
		}else if(map[position.x-1][position.y]<map[position.x][position.y] && map[position.x-1][position.y]!= OOB) {
			System.out.println("Left");
			return 4;
		}else if(map[position.x+1][position.y]<map[position.x][position.y] && map[position.x+1][position.y]!= OOB) {
			System.out.println("Right");
			return 3;
		}*/
		
		}
		
		else{
		
		
		if (wallet >= 15) {
			if (vision[7] == bank || vision[2] == bank) {
				posBank = position;
				System.out.println(posBank);
				return 1;
			} else if (vision[16] == bank || vision[21] == bank) {
				posBank = position;
				System.out.println(posBank);
				return 2;
			}else if (vision[11] == bank || vision[10] == bank) {
				posBank = position;
				System.out.println(posBank);
				return 4;
			} else if (vision[12] == bank || vision[13] == bank) {
				posBank = position;
				System.out.println(posBank);
				return 3;
			}
		}
		// Integer[] visionCopy = Arrays.stream(vision).boxed().toArray(Integer[]::new);
		// int coin = Arrays.asList(visionCopy).indexOf(coin);
		
		// 0  1  2  3  4
		// 5  6  7  8  9
		// 10 11 [] 12 13
		// 14 15 16 17 18
		// 19 20 21 22 23
		
		if (vision[7] == coin) {
			return 1;
		} else if (vision[16] == coin) {
			return 2;
		} else if (vision[11] == coin || vision[6] == coin || vision[15] == coin) {
			return 4;
		} else if (vision[12] == coin || vision[8] == coin || vision[17] == coin) {
			return 3;
		}

		if (coin == vision[21] && (vision[16] != wall || vision[16] != bank)) {
			//System.out.println("Down");
			return 2;
		} else if (coin == vision[2] && (vision[7] != wall || vision[7] != bank)) {
			//System.out.println("Up");
			return 1;
		} else if ((coin == vision[0] || coin == vision[1] || coin == vision[5] || coin == vision[14] || coin == vision[19] || coin == vision[20] || coin == vision[10]) && (vision[11] != wall || vision[11] != bank)) {
			//System.out.println("Left");
			return 4;
		} else if ((coin == vision[3] || coin == vision[4] || coin == vision[9] || coin == vision[18] || coin == vision[22] || coin == vision[23] || coin == vision[13]) && (vision[12] != wall || vision[12] != bank)) {
			//System.out.println("Right");
			return 3;
		}
		}
		
		return 404;
		
		
	}

}