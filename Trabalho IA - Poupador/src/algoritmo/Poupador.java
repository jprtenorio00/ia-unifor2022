package algoritmo;

import java.awt.Point;
import java.util.Random;

//import java.awt.Point;

public class Poupador extends ProgramaPoupador {

	// Visão
	int wall = 1;
	int bank = 3;
	int coin = 4;
	int power = 5;
	int thief = 200;
	
	// Moedas
	int wallet = this.sensor.getNumeroDeMoedas();
	
	// Movimentação
	public final int cima = 1;
	public final int baixo = 2;
	public final int direita = 3;
	public final int esquerda = 4;

	@Override
	public int acao() {
		
		// 0  1  2  3  4
		// 5  6  7  8  9
		// 10 11 [] 12 13
		// 14 15 16 17 18
		// 19 20 21 22 23
		
		vaiNoBanco();		
		
		return pegaMoeda();
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

	//Chamar essa função toda vez que aparecer uma pastilha do poder na visão
	public boolean Pegarpastilha() {
		if (this.wallet > 5) {
			int[] smell = this.sensor.getAmbienteOlfatoLadrao();
			int count = 0;
			for (int s : smell) {
				count += s;
			}
			//Se não tiver ladrão por perto não pega pastilha
			if (count == 0) return false;
			//Quanto mais forte for o cheiro mais chances tem de o poupador pegar a pastilha
			else return new Random().nextInt(count) == 0;
		} else {
			return false;
		}
	}

}