package algoritmo;

import java.awt.Point;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

//import java.awt.Point;

public class Poupador extends ProgramaPoupador {

	// Sensors

	int[] vision;
	int[] smell;

	// Visão
	int wall = 1;
	int bank = 3;
	int coin = 4;
	int power = 5;

	// emotions

	float fear = 0;
	
	// Moedas
	int wallet = this.sensor.getNumeroDeMoedas();
	
	// Movimentação
	public final int cima = 1;
	public final int baixo = 2;
	public final int direita = 3;
	public final int esquerda = 4;

	@Override
	public int acao() {
		vision = this.sensor.getVisaoIdentificacao();
		smell = this.sensor.getAmbienteOlfatoPoupador();
		
		// 0  1  2  3  4
		// 5  6  7  8  9
		// 10 11 [] 12 13
		// 14 15 16 17 18
		// 19 20 21 22 23

		int dangerOverride = checkForThief();
		if (dangerOverride == -1){
			vaiNoBanco();

			return pegaMoeda();
		} else {
			return dangerOverride;
		}
	}
	
	public boolean temBancoProximo() {
		boolean bancoProximo = false;
		int[] vision = this.sensor.getVisaoIdentificacao();
		for (int i = 0; i < vision.length; i++) {
			System.out.println(i);
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
			System.out.println(i);
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

	public int checkForThief() {
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
				if (a==0) directionSafety[a] = !(vision[7] == 0 || vision[7] == coin) || arrayContainsItem(thiefPositions, new Integer[]{0,1,2,3,4,5,6,7,8,9});
				if (a==1) directionSafety[a] = !(vision[16] == 0 || vision[16] == coin) || arrayContainsItem(thiefPositions, new Integer[]{14,15,16,17,18,19,20,21,22,23});
				if (a==2) directionSafety[a] = !(vision[12] == 0 || vision[12] == coin) || arrayContainsItem(thiefPositions, new Integer[]{3,4,8,9,12,13,17,18,22,23});
				if (a==3) directionSafety[a] = !(vision[11] == 0 || vision[11] == coin) || arrayContainsItem(thiefPositions, new Integer[]{0,1,5,6,10,11,14,15,19,20});
			}
			

			if (!arrayContainsItem(directionSafety, new Boolean[]{true})){
				return -1;
			}
			else if(!arrayContainsItem(directionSafety, new Boolean[]{false})) {
				return 0;
			}
			else {
				// select random escape route
				int[] safeDirections = getIndexesOfItem(directionSafety, false);
				return getRandom(safeDirections)+1;
			
			}
		} else {
			return -1;
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

	public static <T> boolean arrayContainsItem(T[] list, T[] items) {
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