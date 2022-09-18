package algoritmo;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

//import java.awt.Point;

public class Poupador extends ProgramaPoupador {

	// Sensors
	int[] vision;
	int[] smell;

	int immunity;

	// Visão
	int wall = 1;
	int bank = 3;
	int coin = 4;
	int power = 5;

	int oob = -1;

	ArrayList<Integer> thieves = new ArrayList<>(Arrays.asList(200,210,220,230));

	// emotions

	float fear = 0;
	Random r = new Random();
	
	// Moedas
	int wallet = 0;
	
	// Movimentação
	public final int cima = 1;
	public final int baixo = 2;
	public final int direita = 3;
	public final int esquerda = 4;

	// Memory
	int[][] map = new int[30][30];
	Point bankLocation;
	int[][] locations = new int[30][30];

	@Override
	public int acao() {
		vision = this.sensor.getVisaoIdentificacao();
		smell = this.sensor.getAmbienteOlfatoPoupador();
		wallet = this.sensor.getNumeroDeMoedas();
		immunity = this.sensor.getNumeroJogadasImunes();
		
		// 0  1  2  3  4
		// 5  6  7  8  9
		// 10 11 [] 12 13
		// 14 15 16 17 18
		// 19 20 21 22 23


		ArrayList<Integer> pellet_directions = PowerPelletDirections();
		int[] escape_directions = EscapeRouteDirections();
		if (escape_directions.length == 4){
			if (temMoedaProxima() && wallet < 10){
				return pegaMoeda();
			} else {
				if (wallet > 10 && temBancoProximo()) {
					return vaiNoBanco();
				} else {
					return explore();
				}
			}
		} else {
			if (pellet_directions.size() > 1){
				ArrayList<Integer> optimal_directions = new ArrayList<>();
				for (int direction : escape_directions){
					if (pellet_directions.contains(direction)) optimal_directions.add(direction);
				}
				if (optimal_directions.size() > 0 && pegarpastilha() && immunity == 0) {
					int[] directions = Arrays.stream(optimal_directions.toArray()).mapToInt(i -> (int) i).toArray();
					int choice = getRandom(directions);
					System.out.println("My Choice: "+choice);
					return choice;
				}
				else {
					ArrayList<Integer> escape = pellet_directions;
					for (int direction : escape_directions){
						escape.add(direction);
					}
					int[] directions = Arrays.stream(escape.toArray()).mapToInt(i -> (int) i).toArray();
					return getRandom(directions);
				}

			}
			else {
				if (escape_directions.length > 0){
					return getRandom(escape_directions);
				} else {
					return new Random().nextInt(4);
				}
			}
		}
	}

	public int explore() {
		Point position = this.sensor.getPosicao();

		int[] lista = new int[5];

		for(int i = 0; i<lista.length; i++) {
			lista[i]=Integer.MAX_VALUE;
		}

		int min = 0;
		ArrayList<Integer> empates= new ArrayList<Integer>();

		if(vision[7]!=wall &&  vision[7]!=oob && vision[7]!=power){
			lista[1]=map[position.x][position.y-1];
		}
		if(vision[16]!=wall &&  vision[16]!=oob && vision[16]!=power){
			lista[2]=map[position.x][position.y+1];
		}
		if(vision[12]!=wall &&  vision[12]!=oob && vision[12]!=power){
			lista[3]=map[position.x+1][position.y];
		}
		if(vision[11]!=wall  && vision[11]!=oob && vision[11]!=power){
			lista[4]=map[position.x-1][position.y];
		}
		for (int i = 1; i < lista.length; i++) {
			//Compare elements of array with min
			if(lista[i]<=lista[min]){
				min = i;
			}
		}
		for (int i = 1; i < lista.length; i++) {
			if(lista[min]==lista[i]) {
				empates.add(i);
			}
		}
		if(empates.size()>0){
			int rnd = new Random().nextInt(empates.size());
			int choice = empates.get(rnd);
			switch (choice){
				case cima:
					map[position.x][position.y-1]++;
				break;
				case baixo:
					map[position.x][position.y+1]++;
				break;
				case direita:
					map[position.x+1][position.y]++;
				break;
				case esquerda:
					map[position.x-1][position.y]++;
				break;
			}

			return choice;
		}
		switch (min){
			case cima:
				map[position.x][position.y-1]++;
				break;
			case baixo:
				map[position.x][position.y+1]++;
				break;
			case direita:
				map[position.x+1][position.y]++;
				break;
			case esquerda:
				map[position.x-1][position.y]++;
				break;
		}
		return min;

	}
	
	public boolean temBancoProximo() {
		boolean bancoProximo = false;
		int[] vision = this.sensor.getVisaoIdentificacao();
		for (int i = 0; i < vision.length; i++) {
			if (vision[i] == bank) {
				bancoProximo = true;
			}
		}		
		return bancoProximo;		
	}
	
	public boolean temMoedaProxima() {
		boolean moedaProxima = false;
		int[] vision = this.sensor.getVisaoIdentificacao();
		for (int i = 0; i < vision.length; i++) {
			if (vision[i] == coin) {
				moedaProxima = true;
			}
		}		
		return moedaProxima;		
	}
	
	
	public int vaiNoBanco() {
		int[] vision = this.sensor.getVisaoIdentificacao();
		Point position = this.sensor.getPosicao();
		Point posBank;		
		
		// Visualizando o banco e pegando sua posição
		if (vision[7] == bank || vision[2] == bank) {
			posBank = position;
			System.out.println("Pos. do banco: " + posBank);
			return cima;
		} else if (vision[16] == bank || vision[21] == bank) {
			posBank = position;
			System.out.println("Pos. do banco: " + posBank);
			return baixo;
		}else if (vision[11] == bank || vision[10] == bank) {
			posBank = position;
			System.out.println("Pos. do banco: " + posBank);
			return esquerda;
		} else if (vision[12] == bank || vision[13] == bank) {
			posBank = position;
			System.out.println("Pos. do banco: " + posBank);
			return direita;
		}

		if (vision[6] == bank) {
			if (vision[11] != wall && vision[11] != oob) {
				return esquerda;
			} else if (vision[7] != wall && vision[7] != oob) {
				return cima;
			}
		} else if (vision[8] == bank) {
			if (vision[12] != wall && vision[12] != oob) {
				return direita;
			} else if (vision[7] != wall && vision[7] != oob) {
				return cima;
			}
		} else if (vision[15] == bank) {
			if (vision[11] != wall && vision[11] != oob) {
				return esquerda;
			} else if (vision[16] != wall && vision[16] != oob) {
				return baixo;
			}
		} else if (vision[17] == coin) {
			if (vision[12] != wall && vision[12] != oob) {
				return direita;
			} else if (vision[16] != wall && vision[16] != oob) {
				return baixo;
			}
		}

		if (bank == vision[21] && (vision[16] != wall && vision[16] != oob)) {
			return baixo;
		} else if (bank == vision[2] && (vision[7] != wall && vision[7] != oob)) {
			return cima;
		} else if ((bank == vision[0] || bank == vision[1] || bank == vision[5] || bank == vision[14] || bank == vision[19] || bank == vision[20] || bank == vision[10]) && (vision[11] != wall && vision[11] != oob)) {
			return esquerda;
		} else if ((bank == vision[3] || bank == vision[4] || bank == vision[9] || bank == vision[18] || bank == vision[22] || bank == vision[23] || bank == vision[13]) && (vision[12] != wall && vision[12] != oob)) {
			return direita;
		}
		
		return (int) (Math.random() * 5);		
	}
	
	public int pegaMoeda() {		
		int[] vision = this.sensor.getVisaoIdentificacao();
		// Entrada para as direções adjacentes
		if (vision[7] == coin) {
			return cima;
		} else if (vision[16] == coin) {
			return baixo;
		} else if (vision[12] == coin) {
			return direita;
		} else if (vision[11] == coin) {
			return esquerda;
		}

		// Entrada para as direções na diagonal
		if (vision[6] == coin) {
			if ((vision[11] != wall) && (vision[11] != bank) && (vision[11] != power)) {
				//System.out.println("Foi para Esquerda! 6");
				return esquerda;
			} else if ((vision[7] != wall) && (vision[7] != bank) && (vision[7] != power)) {
				//System.out.println("Foi para Cima! 6");
				return cima;
			}
		} else if (vision[8] == coin) {
			if ((vision[12] != wall) && (vision[12] != bank) && (vision[12] != power)) {
				//System.out.println("Foi para Direita! 8");
				return direita;
			} else if ((vision[7] != wall) && (vision[7] != bank) && (vision[7] != power)) {
				//System.out.println("Foi para Cima! 8");
				return cima;
			}
		} else if (vision[15] == coin) {
			if ((vision[11] != wall) && (vision[11] != bank) && (vision[11] != power)) {
				//System.out.println("Foi para Esquerda! 15");
				return esquerda;
			} else if ((vision[16] != wall) && (vision[16] != bank) && (vision[16] != power)) {
				//System.out.println("Foi para Baixo! 15");
				return baixo;
			}
		} else if (vision[17] == coin) {
			if ((vision[12] != wall) && (vision[12] != bank) && (vision[12] != power)) {
				//System.out.println("Foi para Direita! 17");
				return direita;
			} else if ((vision[16] != wall) && (vision[16] != bank) && (vision[16] != power)) {
				//System.out.println("Foi para Baixo! 17");
				return baixo;
			}
		}

		// Entrada para as direções na extremidade
		if (coin == vision[21] && (vision[16] != wall && vision[16] != bank && vision[16] != power)) {
			//System.out.println("Down");
			return 2;
		} else if (coin == vision[2] && (vision[7] != wall && vision[7] != bank && vision[7] != power)) {
			//System.out.println("Up");
			return 1;
		} else if ((coin == vision[0] || coin == vision[1] || coin == vision[5] || coin == vision[14] || coin == vision[19] || coin == vision[20] || coin == vision[10]) && (vision[11] != wall && vision[11] != bank && vision[11] != power)) {
			//System.out.println("Left");
			return 4;
		} else if ((coin == vision[3] || coin == vision[4] || coin == vision[9] || coin == vision[18] || coin == vision[22] || coin == vision[23] || coin == vision[13]) && (vision[12] != wall && vision[12] != bank && vision[12] != power)) {
			//System.out.println("Right");
			return 3;
		}
		
		return (int) (Math.random() * 5);
	}

	public boolean pegarpastilha() {
		if (this.wallet > 5) {
			int fear = this.wallet - 5;
			for (int s : smell) {
				if(s>0){
					fear += 5-s;
				}
			}
			for (int i = 0; i < vision.length; i++) {
				if (i == 7 || i == 11 || i == 12 || i == 16) {
					if (thieves.contains(vision[i])) {
						fear += 10;
					}
				} else if (thieves.contains(vision[i])) {
					fear += 5;
				}
			}
			//Se não tiver medo não irá pegar a pastilha do poder
			if (fear <= 0) return false;
				//Quanto mais forte for o medo mais chances tem de pegar moeda
			else {
				int cahnce = new Random().nextInt(fear);
				return cahnce > 8;
			}
		} else {
			return false;
		}
	}

	public int[] getIndexesOfPower(int[] list) {
		return IntStream.range(0, list.length).filter(index -> list[index] == power).toArray();
	}

	public ArrayList<Integer> PowerPelletDirections() {
		int[] pelletP = getIndexesOfPower(vision);

		if (pelletP.length > 0) {
			Integer[] pelletPositions = new Integer[pelletP.length];
			Arrays.setAll(pelletPositions, index -> pelletP[index]);

			ArrayList<Integer> pelletDirections = new ArrayList<>();
			for (int a = 0; a < 4; a++) {
				int nextSlot = 0;
				Integer[] directionSlots = new Integer[0];
				switch (a) {
					case 0:
						nextSlot = 7;
						directionSlots = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
					break;
					case 1:
						nextSlot = 16;
						directionSlots = new Integer[]{14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
					break;
					case 2:
						nextSlot = 12;
						directionSlots = new Integer[]{3, 4, 8, 9, 12, 13, 17, 18, 22, 23};
					break;
					case 3:
						nextSlot = 11;
						directionSlots = new Integer[]{0, 1, 5, 6, 10, 11, 14, 15, 19, 20};
					break;
				}


				if (vision[nextSlot] != wall && arrayContainsItem(pelletPositions, directionSlots)) {
					pelletDirections.add(a + 1);
				}
			}
			return pelletDirections;

		}
		return new ArrayList<>(0);
	}

	public int[] EscapeRouteDirections() {
		// finds thieves within vision
		int[] thiefP = getIndexesOfThief(vision);
		Integer[] thiefPositions = new Integer[thiefP.length];
		Arrays.setAll(thiefPositions, index -> thiefP[index]);
		if (thiefPositions.length > 0) {

		/* Smell WIP
		int[] RecentThiefTracks = getIndexesOfItem(smell, 3);
		int[] SemiRecentThiefTracks = getIndexesOfItem(smell, 4);
		int[] OldThiefTracks = getIndexesOfItem(smell, 5); */

			// verifies what directions are available for escape
			Boolean[] directionSafety = new Boolean[4];
			for (int a=0; a<4; a++){
				if (a==0) directionSafety[a] = !(vision[7] == 0 || vision[7] == coin || vision[7] == power || vision[7] == bank) || arrayContainsItem(thiefPositions, new Integer[]{0,1,2,3,4,5,6,7,8,9});
				if (a==1) directionSafety[a] = !(vision[16] == 0 || vision[16] == coin || vision[16] == power || vision[16] == bank) || arrayContainsItem(thiefPositions, new Integer[]{14,15,16,17,18,19,20,21,22,23});
				if (a==2) directionSafety[a] = !(vision[12] == 0 || vision[12] == coin || vision[12] == power || vision[12] == bank) || arrayContainsItem(thiefPositions, new Integer[]{3,4,8,9,12,13,17,18,22,23});
				if (a==3) directionSafety[a] = !(vision[11] == 0 || vision[11] == coin || vision[11] == power || vision[11] == bank) || arrayContainsItem(thiefPositions, new Integer[]{0,1,5,6,10,11,14,15,19,20});
			}


			// all directions are safe
			if (!arrayContainsItem(directionSafety, new Boolean[]{true})){
				return new int[]{1,2,3,4};
			}
			// no directions are safe
			else if(!arrayContainsItem(directionSafety, new Boolean[]{false})) {
				return new int[0];
			}
			else {
				// return possible escape routes
				int[] er = getIndexesOfItem(directionSafety, false);
				ArrayList<Integer> escape_r = new ArrayList<>();
				for (int i = 0; i < er.length; i++) {
					escape_r.add(er[i]+1);
				}

				//prioritize bank
				if (wallet >= 10 && temBancoProximo()){
					if (vision[7] == bank || vision[2] == bank){
						for (int i = 0; i < wallet ; i++){
							escape_r.add(cima);
						}
					}
					if (vision[16] == bank || vision[21] == bank){
						for (int i = 0; i < wallet ; i++){
							escape_r.add(baixo);
						}
					}
					if (vision[12] == bank || vision[13] == bank){
						for (int i = 0; i < wallet ; i++){
							escape_r.add(direita);
						}
					}
					if (vision[11] == bank || vision[10] == bank){
						for (int i = 0; i < wallet ; i++){
							escape_r.add(esquerda);
						}
					}

				}
				int[] escape_routes = new int[escape_r.size()];
				int count = 0;
				for (int num : escape_r) {
					escape_routes[count] = num;
					count++;
				}
				return escape_routes;
			
			}
		} else {
			return new int[]{1,2,3,4};
		}

	}

	public static int getRandom(int[] array) {
		int rnd = new Random().nextInt(array.length);
		return array[rnd];
	}

	public static <T> int[] getIndexesOfItem(T[] list, T item) {
		return IntStream.range(0, list.length).filter(index -> list[index] == item).toArray();
	}

	public static int[] getIndexesOfThief(int[] list) {
		return IntStream.range(0, list.length).filter(index -> list[index] >= 200 && list[index] < 300 ).toArray();
	}

	public <T> boolean arrayContainsItem(T[] list, T[] items) {
		boolean returnVal;
		for (int a = 0; a < items.length; a++){
			int finalA = a;
			returnVal = Arrays.stream(list).anyMatch(x -> x == items[finalA]);
			if (returnVal) {
				return true;
			}
		}
		return false;
	}

}