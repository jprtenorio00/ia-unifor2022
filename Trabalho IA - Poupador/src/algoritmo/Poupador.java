package algoritmo;

<<<<<<< HEAD
//import java.awt.Point;
=======
import java.awt.Point;
>>>>>>> branch 'master' of https://github.com/jprtenorio00/ia-unifor2022.git

public class Poupador extends ProgramaPoupador {

	// Olfato
	int wall = 1;
	int bank = 3;
<<<<<<< HEAD
	int coin = 4;
	int power = 5;

	// Movimentação
	public final int cima = 1;
	public final int baixo = 2;
	public final int direita = 3;
	public final int esquerda = 4;
=======
	int power_pellet = 5;
>>>>>>> branch 'master' of https://github.com/jprtenorio00/ia-unifor2022.git

	@Override
	public int acao() {
		int[] vision = this.sensor.getVisaoIdentificacao();
<<<<<<< HEAD
		// int wallet = this.sensor.getNumeroDeMoedas();
		// Point position = this.sensor.getPosicao();
		// Point posBank;
		
=======
		int wallet = this.sensor.getNumeroDeMoedas();
		Point position = this.sensor.getPosicao();
		Point posBank;

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

>>>>>>> branch 'master' of https://github.com/jprtenorio00/ia-unifor2022.git
		// 0  1  2  3  4
		// 5  6  7  8  9
		// 10 11 [] 12 13
		// 14 15 16 17 18
		// 19 20 21 22 23
<<<<<<< HEAD
		
		// Visualizando o banco e pegando sua posição
		

		// Entrada para as direções adjacentes
=======

>>>>>>> branch 'master' of https://github.com/jprtenorio00/ia-unifor2022.git
		if (vision[7] == coin) {
			return cima;
		} else if (vision[16] == coin) {
			return baixo;
		} else if (vision[12] == coin) {
			return direita;
		} else if (vision[11] == coin) {
			return esquerda;
		}

<<<<<<< HEAD
		// Entrada para as direções na diagonal
		if (vision[6] == coin) {
			if ((vision[11] != wall) && (vision[11] != bank) && (vision[11] != power)) {
				System.out.println("Foi para Esquerda! 6");
				return esquerda;
			} else if ((vision[7] != wall) && (vision[7] != bank) && (vision[7] != power)) {
				System.out.println("Foi para Cima! 6");
				return cima;
			}
		} else if (vision[8] == coin) {
			if ((vision[12] != wall) && (vision[12] != bank) && (vision[12] != power)) {
				System.out.println("Foi para Direita! 8");
				return direita;
			} else if ((vision[7] != wall) && (vision[7] != bank) && (vision[7] != power)) {
				System.out.println("Foi para Cima! 8");
				return cima;
			}
		} else if (vision[15] == coin) {
			if ((vision[11] != wall) && (vision[11] != bank) && (vision[11] != power)) {
				System.out.println("Foi para Esquerda! 15");
				return esquerda;
			} else if ((vision[16] != wall) && (vision[16] != bank) && (vision[16] != power)) {
				System.out.println("Foi para Baixo! 15");
				return baixo;
			}
		} else if (vision[17] == coin) {
			if ((vision[12] != wall) && (vision[12] != bank) && (vision[12] != power)) {
				System.out.println("Foi para Direita! 17");
				return direita;
			} else if ((vision[16] != wall) && (vision[16] != bank) && (vision[16] != power)) {
				System.out.println("Foi para Baixo! 17");
				return baixo;
			}
		}

		// Entrada para as direções na extremidade
		if (coin == vision[21] && (vision[16] != wall && vision[16] != bank && vision[16] != power)) {
=======
		if (coin == vision[21] && (vision[16] != wall && vision[16] != bank && vision[16] != power_pellet)) {
			System.out.println(vision[21]+" "+vision[16]);
>>>>>>> branch 'master' of https://github.com/jprtenorio00/ia-unifor2022.git
			System.out.println("Down");
			return 2;
<<<<<<< HEAD
		} else if (coin == vision[2] && (vision[7] != wall && vision[7] != bank && vision[7] != power)) {
=======
		} else if (coin == vision[2] && (vision[7] != wall && vision[7] != bank && vision[7] != power_pellet)) {
>>>>>>> branch 'master' of https://github.com/jprtenorio00/ia-unifor2022.git
			System.out.println("Up");
			return 1;
<<<<<<< HEAD
		} else if ((coin == vision[0] || coin == vision[1] || coin == vision[5] || coin == vision[14] || coin == vision[19] || coin == vision[20] || coin == vision[10]) && (vision[11] != wall && vision[11] != bank && vision[11] != power)) {
=======
		} else if ((coin == vision[0] || coin == vision[1] || coin == vision[5] || coin == vision[14] || coin == vision[19] || coin == vision[20] || coin == vision[10]) && (vision[11] != wall && vision[11] != bank && vision[11] != power_pellet)) {
>>>>>>> branch 'master' of https://github.com/jprtenorio00/ia-unifor2022.git
			System.out.println("Left");
			return 4;
<<<<<<< HEAD
		} else if ((coin == vision[3] || coin == vision[4] || coin == vision[9] || coin == vision[18] || coin == vision[22] || coin == vision[23] || coin == vision[13]) && (vision[12] != wall && vision[12] != bank && vision[12] != power)) {
=======
		} else if ((coin == vision[3] || coin == vision[4] || coin == vision[9] || coin == vision[18] || coin == vision[22] || coin == vision[23] || coin == vision[13]) && (vision[12] != wall && vision[12] != bank && vision[12] != power_pellet)) {
>>>>>>> branch 'master' of https://github.com/jprtenorio00/ia-unifor2022.git
			System.out.println("Right");
			return 3;
		}

		return (int) (Math.random() * 5);
	}

}