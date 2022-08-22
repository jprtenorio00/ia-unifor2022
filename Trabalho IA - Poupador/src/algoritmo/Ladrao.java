package algoritmo;

import java.awt.Point;
import java.util.ArrayList;

public class Ladrao extends ProgramaLadrao {
	
	public final int PARADO = 0;
	public final int CIMA = 1;
	public final int BAIXO = 2;
	public final int DIREITA = 3;
	public final int ESQUERDA = 4;
	public double[][] infoMapa = new double[100][100];
	public double[] peso;
	public double random;
	public static int id = 0;
	public boolean zeraMatriz = true;
	public ArrayList<Integer> minhasMoedasPassadas = new ArrayList<Integer>();
	public ArrayList<Point> posicoes = new ArrayList<Point>();
	public int minhasMoedasAtual = 0;
	public boolean roubei = false;
	public int olfatoLadrao [] = sensor.getAmbienteOlfatoLadrao();
	public int olfatoPoupador [] = sensor.getAmbienteOlfatoPoupador();
	public int acao() {
		int visao [] =  sensor.getVisaoIdentificacao();
		if(id == 0){
			id = 1;
		}else if(id == 1){
			id = 2;
		}else if(id == 2){
			id = 3;
		}else if(id == 3){
			id = 0;
		}
		posicoes.add(sensor.getPosicao());
		minhasMoedasAtual = sensor.getNumeroDeMoedas();
		minhasMoedasPassadas.add(minhasMoedasAtual);
		mapeando();
		if(temPoupador()){
			return perseguePoupador();
			
		}else if(temCheiroPoupador()){
			return cheiroPoupador();
		}else{
			return escolhaDirecao();
		}
		
	}
	
	public int perseguePoupador(){
		int visao [] =  sensor.getVisaoIdentificacao();
		int count = 0;
		boolean loop = false;
		//System.out.println("Estou parado: "+estouParado());
		//System.out.println("Loop: "+loop);
		if(estouParado() || loop){
			//System.out.println("Estou parado: "+estouParado());
			//System.out.println("Loop: "+loop);
			//System.out.println(count);
			count++;
			if(count % 2 != 0){
				loop = true;
			}else{
				loop = false;
			}
			
			if(isPoupador(visao[7])){
				if(cellIsEmpty(visao[16])){
					return BAIXO;
				}else if(cellIsEmpty(visao[11])){
					return ESQUERDA;
				}else if(cellIsEmpty(visao[12])){
					return DIREITA;
				}else{
					return (int) (Math.random() * 5);
				}
			}else if(isPoupador(visao[11])){
				if(cellIsEmpty(visao[12])){
					return DIREITA;
				}else if(cellIsEmpty(visao[7])){
					return CIMA;
				}else if(cellIsEmpty(visao[16])){
					return BAIXO;
				}else{
					return (int) (Math.random() * 5);
				}
			} else if(isPoupador(visao[12])){
				if(cellIsEmpty(visao[11])){
					return ESQUERDA;
				}else if(cellIsEmpty(visao[16])){
					return BAIXO;
				}else if(cellIsEmpty(visao[7])){
					return CIMA;
				}else{
					return (int) (Math.random() * 5);
				}
			}else if(isPoupador(visao[16])){
				if(cellIsEmpty(visao[7])){
					return CIMA;
				}else if(cellIsEmpty(visao[11])){
					return ESQUERDA;
				}else if(cellIsEmpty(visao[12])){
					return DIREITA;
				}else{
					return (int) (Math.random() * 5);
				}
			}
			return (int) (Math.random() * 5);
			
		}
		
		if(isPoupador(visao[7])){
			return CIMA;
		}else if(isPoupador(visao[11])){
			return ESQUERDA;
		} else if(isPoupador(visao[12])){
			return DIREITA;
		}else if(isPoupador(visao[16])){
			return BAIXO;
		}else if(isPoupador(visao[8])){
			if(cellIsEmpty(visao[7]) && cellIsEmpty(visao[12])){
				random = Math.random();
				if(random < 0.5){
					return CIMA;
				}else if(random > 0.5){
					return DIREITA;
				}
			}else{
				if(cellIsEmpty(visao[7])){
					return CIMA;
				}else if(cellIsEmpty(visao[12])){
					return DIREITA;
				}else if(cellIsEmpty(visao[11])){
					return ESQUERDA;
				}else{
					return BAIXO;
				}
			}
		}else if(isPoupador(visao[17])){
			if(cellIsEmpty(visao[16]) && cellIsEmpty(visao[12])){
				random = Math.random();
				if(random < 0.5){
					return BAIXO;
				}else if(random > 0.5){
					return DIREITA;
				}	
			}else{
				
				if(cellIsEmpty(visao[16])){
					return BAIXO;
				}else if(cellIsEmpty(visao[12])){
					return DIREITA;
				}else if(cellIsEmpty(visao[7])){
					return CIMA;
				}else{
					return ESQUERDA;
				}
			}
		}else if(isPoupador(visao[15])){
			if(cellIsEmpty(visao[16]) && cellIsEmpty(visao[11])){
				random = Math.random();
				if(random < 0.5){
					return BAIXO;
				}else if(random > 0.5){
					return ESQUERDA;
				}
			}else{
				
				if(cellIsEmpty(visao[16])){
					return BAIXO;
				}else if(cellIsEmpty(visao[11])){
					return ESQUERDA;
				}else if(cellIsEmpty(visao[7])){
					return CIMA;
				}else{
					return DIREITA;
				}
			}
			
		}else if(isPoupador(visao[6])){
			if(cellIsEmpty(visao[7]) && cellIsEmpty(visao[11])){
				random = Math.random();
				if(random < 0.5){
					return CIMA;
				}else if(random > 0.5){
					return ESQUERDA;
				}
			}else{
				
				if(cellIsEmpty(visao[7])){
					return CIMA;
				}else if(cellIsEmpty(visao[11])){
					return ESQUERDA;
				}else if(cellIsEmpty(visao[16])){
					return BAIXO;
				}else{
					return DIREITA;
				}
			}
			
		}else if(isPoupador(visao[0])){
			if(cellIsEmpty(visao[7])){
				return CIMA;
			}else if(cellIsEmpty(visao[11])){
				return ESQUERDA;
			}else if(cellIsEmpty(visao[16])){
				return BAIXO;
			}else{
				return DIREITA;
			}
		}else if(isPoupador(visao[5])){
			if(cellIsEmpty(visao[7])){
				return CIMA;
			}else if(cellIsEmpty(visao[11])){
				return ESQUERDA;
			}else if(cellIsEmpty(visao[16])){
				return BAIXO;
			}else{
				return DIREITA;
			}
		}else if(isPoupador(visao[10])){
			if(cellIsEmpty(visao[11])){
				return ESQUERDA;
			}else if(cellIsEmpty(visao[7])){
				return CIMA;
			}else if(cellIsEmpty(visao[16])){
				return BAIXO;
			}else{
				return DIREITA;
			}
		}else if(isPoupador(visao[14])){
			if(cellIsEmpty(visao[16])){
				return BAIXO;
			}else if(cellIsEmpty(visao[11])){
				return ESQUERDA;
			}else if(cellIsEmpty(visao[7])){
				return CIMA;
			}else{
				return DIREITA;
			}
		}else if(isPoupador(visao[19])){
			if(cellIsEmpty(visao[16])){
				return BAIXO;
			}else if(cellIsEmpty(visao[11])){
				return ESQUERDA;
			}else if(cellIsEmpty(visao[7])){
				return CIMA;
			}else{
				return DIREITA;
			}
		}else if(isPoupador(visao[20])){
			if(cellIsEmpty(visao[16])){
				return BAIXO;
			}else if(cellIsEmpty(visao[11])){
				return ESQUERDA;
			}else if(cellIsEmpty(visao[12])){
				return DIREITA;
			}else{
				return CIMA;
			}
		}else if(isPoupador(visao[21])){
			if(cellIsEmpty(visao[16])){
				return BAIXO;
			}else if(cellIsEmpty(visao[11])){
				return ESQUERDA;
			}else if(cellIsEmpty(visao[12])){
				return DIREITA;
			}else{
				return CIMA;
			}
		}else if(isPoupador(visao[22])){
			if(cellIsEmpty(visao[16])){
				return BAIXO;
			}else if(cellIsEmpty(visao[12])){
				return DIREITA;
			}else if(cellIsEmpty(visao[11])){
				return ESQUERDA;
			}else{
				return CIMA;
			}
		}else if(isPoupador(visao[23])){
			if(cellIsEmpty(visao[16])){
				return BAIXO;
			}else if(cellIsEmpty(visao[12])){
				return DIREITA;
			}else if(cellIsEmpty(visao[11])){
				return ESQUERDA;
			}else{
				return CIMA;
			}
		}else if(isPoupador(visao[1])){
			if(cellIsEmpty(visao[11])){
				return ESQUERDA;
			}else if(cellIsEmpty(visao[7])){
				return CIMA;
			}else if(cellIsEmpty(visao[12])){
				return DIREITA;
			}else{
				return BAIXO;
			}
		}else if(isPoupador(visao[2])){
			if(cellIsEmpty(visao[7])){
				return CIMA;
			}else if(cellIsEmpty(visao[11])){
				return ESQUERDA;
			}else if(cellIsEmpty(visao[12])){
				return DIREITA;
			}else{
				return BAIXO;
			}
		}else if(isPoupador(visao[3])){
			if(cellIsEmpty(visao[7])){
				return CIMA;
			}else if(cellIsEmpty(visao[12])){
				return DIREITA;
			}else if(cellIsEmpty(visao[11])){
				return ESQUERDA;
			}else{
				return BAIXO;
			}
		}else if(isPoupador(visao[4])){
			if(cellIsEmpty(visao[7])){
				return CIMA;
			}else if(cellIsEmpty(visao[12])){
				return DIREITA;
			}else if(cellIsEmpty(visao[11])){
				return ESQUERDA;
			}else{
				return BAIXO;
			}
		}else if(isPoupador(visao[9])){
			if(cellIsEmpty(visao[12])){
				return DIREITA;
			}else if(cellIsEmpty(visao[7])){
				return CIMA;
			}else if(cellIsEmpty(visao[16])){
				return BAIXO;
			}else{
				return ESQUERDA;
			}
		}else if(isPoupador(visao[13])){
			if(cellIsEmpty(visao[12])){
				return DIREITA;
			}else if(cellIsEmpty(visao[7])){
				return CIMA;
			}else if(cellIsEmpty(visao[16])){
				return BAIXO;
			}else{
				return ESQUERDA;
			}
		}else if(isPoupador(visao[18])){
			if(cellIsEmpty(visao[16])){
				return BAIXO;
			}else if(cellIsEmpty(visao[12])){
				return DIREITA;
			}else if(cellIsEmpty(visao[7])){
				return CIMA;
			}else{
				return ESQUERDA;
			}
		}
		return (int) (Math.random() * 5);
	}
	
	public int cheiroPoupador(){
		//System.out.println("Ki Cheirinho BOM !!!!");
		int visao [] = sensor.getVisaoIdentificacao();
		if(maisRecenteCheiroPoupador() == 1){
			return CIMA;
		}else if(maisRecenteCheiroPoupador() == 3){
			return ESQUERDA;
		}else if(maisRecenteCheiroPoupador() == 4){
			return DIREITA;
		}else if(maisRecenteCheiroPoupador() == 6){
			return BAIXO;
		}else if(maisRecenteCheiroPoupador() == 2){
			if(cellIsEmpty(visao[7]) && cellIsEmpty(visao[12])){
				random = Math.random();
				if(random < 0.5){
					return CIMA;
				}else if(random > 0.5){
					return DIREITA;
				}
			}else{
				if(cellIsEmpty(visao[7])){
					return CIMA;
				}else if(cellIsEmpty(visao[12])){
					return DIREITA;
				}else if(cellIsEmpty(visao[11])){
					return ESQUERDA;
				}else{
					return BAIXO;
				}
			}
		}else if(maisRecenteCheiroPoupador() == 7){
			if(cellIsEmpty(visao[16]) && cellIsEmpty(visao[12])){
				random = Math.random();
				if(random < 0.5){
					return BAIXO;
				}else if(random > 0.5){
					return DIREITA;
				}	
			}else{
				
				if(cellIsEmpty(visao[16])){
					return BAIXO;
				}else if(cellIsEmpty(visao[12])){
					return DIREITA;
				}else if(cellIsEmpty(visao[7])){
					return CIMA;
				}else{
					return ESQUERDA;
				}
			}
		}else if(maisRecenteCheiroPoupador() == 5){
			if(cellIsEmpty(visao[16]) && cellIsEmpty(visao[11])){
				random = Math.random();
				if(random < 0.5){
					return BAIXO;
				}else if(random > 0.5){
					return ESQUERDA;
				}
			}else{
				
				if(cellIsEmpty(visao[16])){
					return BAIXO;
				}else if(cellIsEmpty(visao[11])){
					return ESQUERDA;
				}else if(cellIsEmpty(visao[7])){
					return CIMA;
				}else{
					return DIREITA;
				}
			}
		}else if(maisRecenteCheiroPoupador() == 0){
			if(cellIsEmpty(visao[7]) && cellIsEmpty(visao[11])){
				random = Math.random();
				if(random < 0.5){
					return CIMA;
				}else if(random > 0.5){
					return ESQUERDA;
				}
			}else{
				
				if(cellIsEmpty(visao[7])){
					return CIMA;
				}else if(cellIsEmpty(visao[11])){
					return ESQUERDA;
				}else if(cellIsEmpty(visao[16])){
					return BAIXO;
				}else{
					return DIREITA;
				}
			}
		}
				
		
		return (int) (Math.random() * 5);
	}
	
	public boolean estouParado(){
		Point posicaoPassada = new Point();
		boolean parado = false;
		
		if(posicoes.size()>1){
			posicaoPassada = posicoes.get(posicoes.size()-2);
		}else{
			parado = false;
		}
		
		if((sensor.getPosicao().x == posicaoPassada.x) && (sensor.getPosicao().y == posicaoPassada.y)){
			parado = true;
		}
		return parado;
	}
	
	public boolean roubeiPoupador(){
		int moedaPassada = 0;
		boolean roubei = false;
		
		if(minhasMoedasPassadas.size()>1){
			moedaPassada = minhasMoedasPassadas.get(minhasMoedasPassadas.size()-2);
		}else{
			moedaPassada = minhasMoedasPassadas.get(minhasMoedasPassadas.size()-1);
		}
		System.out.println("Atual: "+minhasMoedasAtual);
		System.out.println("Passada: "+moedaPassada);
		if(minhasMoedasAtual>moedaPassada){
			roubei = true;
		}
		System.out.println("Roubei: "+roubei);
			return roubei;
	}
	
	public boolean cellIsEmpty(int cell){
		boolean empty = false;
		if(cell == -2 || cell == -1 || cell == 1 || cell == 3 || cell == 4 || cell == 5){
			empty = false;
		}else if(cell == 0){
			empty = true;
		}
		
		return empty;
	}
	
	public boolean isPoupador(int cell){
		boolean poupador = false;
		if(cell>=100 && cell<200){
			poupador = true;
		}
		return poupador;
	}
	
	public boolean temPoupador(){
		int visao [] =  sensor.getVisaoIdentificacao();
		boolean temPoupador = false;
		for (int i = 0; i < visao.length; i++) {
			if(isPoupador(visao[i])){
				temPoupador = true;
			}
		}
		return temPoupador;
	}
	
	public boolean isCheiroPoupador(int cell){
		boolean isCheiroPoupador = false;
			if(cell == 1 || cell == 2 || cell == 3 || cell == 4  || cell == 5){
				isCheiroPoupador = true;
			}
		return isCheiroPoupador;
	}
	
	public boolean temCheiroPoupador(){
		int olfato [] =  sensor.getAmbienteOlfatoPoupador();
		boolean temCheiro = false;
		for (int i = 0; i < olfato.length; i++) {
			if(olfato[i] == 1 || olfato[i] == 2 || olfato[i] == 3 || olfato[i] == 4  || olfato[i] == 5){
				temCheiro = true;
			}
		}
		return temCheiro;
	}
	
	public int maisRecenteCheiroPoupador(){
		int olfato [] = sensor.getAmbienteOlfatoPoupador();
		ArrayList <Integer> posMin = new ArrayList<Integer>();
		posMin.add(0);
		int menor = 6;
		int retorno = 0;
		for (int i = 0; i < olfato.length; i++) {
//		Retornar os menores valores de um array
			if(menor>olfato[i]){
				posMin.set(0,i);
				menor = olfato[i];
				retorno = i;
			}
		}
		for (int j = 0; j < olfato.length; j++) {
//			Retornar os menores valores de um array
				if(menor == olfato[j] && j != retorno){
					posMin.add(j);
					menor = olfato[j];
					retorno = j;
				}
		}
		if(posMin.size() == 2){
			random = Math.random();
			if(random < 0.5){
				retorno = posMin.get(0);
			}else if(random > 0.5){
				retorno = posMin.get(1);
			}
		}
		return retorno;
	}
	
	public boolean temBanco(){
		int visao [] =  sensor.getVisaoIdentificacao();
		boolean temBanco = false;
		for (int i = 0; i < visao.length; i++) {
			if(visao[i] == 3){
				temBanco = true;
			}
		}
		return temBanco;
	}
	
	public boolean temMoedas(){
		int visao [] =  sensor.getVisaoIdentificacao();
		boolean temMoedas = false;
		for (int i = 0; i < visao.length; i++) {
			if(visao[i] == 4){
				temMoedas = true;
			}
		}
		return temMoedas;
	}
	
	public void mapeando(){
		int visao [] =  sensor.getVisaoIdentificacao();
		if(zeraMatriz){
			for (int i = 0; i < infoMapa.length; i++) {
				for (int j = 0; j < infoMapa[0].length; j++) {
					infoMapa[i][j] = 0;
				}
			}
			zeraMatriz = false;
		}
		Point posicao = sensor.getPosicao();
		if(temBanco()){
			if(3 == visao[7] || 3 == visao[11] || 3 == visao[12] || 3 == visao[16]){
				infoMapa[posicao.x][posicao.y] = -0.5;
				infoMapa[posicao.x][posicao.y] += 1;
			}else{
				
				infoMapa[posicao.x][posicao.y] = -0.3;
				infoMapa[posicao.x][posicao.y] += 1;
			}
		}else if(temMoedas()){
			if(4 == visao[7] || 4 == visao[11] || 4 == visao[12] || 4 == visao[16]){
				infoMapa[posicao.x][posicao.y] += 0.3;
			}else{
				
				infoMapa[posicao.x][posicao.y] += 0.5;
			}
			
		}else{
			infoMapa[posicao.x][posicao.y] += 1;
		}
	}
	
	public boolean temCheiroLadrao(){
		int olfato [] =  sensor.getAmbienteOlfatoLadrao();
		boolean temCheiro = false;
		for (int i = 0; i < olfato.length; i++) {
			if(olfato[i] == 1 || olfato[i] == 2 || olfato[i] == 3 || olfato[i] == 4  || olfato[i] == 5){
				temCheiro = true;
			}
		}
		return temCheiro;
	}

	public Point posicaoCima(){
		Point posicao = sensor.getPosicao();
		Point posicaoCima = new Point(posicao.x, posicao.y - 1);
		return posicaoCima;
	}
	
	public Point posicaoBaixo(){
		Point posicao = sensor.getPosicao();
		Point posicaoBaixo = new Point(posicao.x, posicao.y + 1);
		return posicaoBaixo;
	}
	
	public Point posicaoEsquerda(){
		Point posicao = sensor.getPosicao();
		Point posicaoEsquerda = new Point(posicao.x - 1, posicao.y);
		return posicaoEsquerda;
	}
	
	public Point posicaoDireita(){
		Point posicao = sensor.getPosicao();
		Point posicaoDireita = new Point(posicao.x + 1, posicao.y);
		return posicaoDireita;
	}
	
	public int escolhaDirecao(){
		ArrayList<Integer> menores = new ArrayList<Integer>();
		double pesos [] = new double [4];
		double pesoCima;
		double pesoBaixo;
		double pesoEsquerda;
		double pesoDireita;
		
		int visao [] =  sensor.getVisaoIdentificacao();
		if(cellIsEmpty(visao[7])){
			pesoCima = infoMapa[posicaoCima().x][posicaoCima().y];
			
		}else{
			pesoCima = 100000; 
		}	
		if(cellIsEmpty(visao[11])){
			pesoEsquerda = infoMapa[posicaoEsquerda().x][posicaoEsquerda().y];
			
		}else{
			pesoEsquerda = 100000;
		}
		if(cellIsEmpty(visao[12])){
			pesoDireita = infoMapa[posicaoDireita().x][posicaoDireita().y];
			
		}else{
			pesoDireita = 100000;
		}
		if(cellIsEmpty(visao[16])){
			pesoBaixo = infoMapa[posicaoBaixo().x][posicaoBaixo().y];
		}else{
			pesoBaixo = 100000;
		}
		//System.out.println("Peso Cima: "+ pesoCima);
		//System.out.println("Peso Esquerda: "+ pesoEsquerda);
		//System.out.println("Peso Direita: "+ pesoDireita);
		//System.out.println("Peso Baixo: "+ pesoBaixo);
		
		pesos[0] = pesoCima;
		pesos[1] = pesoBaixo;
		pesos[2] = pesoEsquerda;
		pesos[3] = pesoDireita;
		int retorno = 0;
		double menor = 10000;
		menores.add(0);
		
		for (int i = 0; i < pesos.length; i++) {
			if(menor>pesos[i]){
				menores.set(0,i);
				menor = pesos[i];
				retorno = i;
			}
		}
		
		for (int j = 0; j < pesos.length; j++) {
			//System.out.println("Peso: "+pesos[j]);
			if(menor == pesos[j] && j != retorno){
				menores.add(j);
				menor = pesos[j];
				retorno = j;
			}
		}
		
		for (int i = 0; i < menores.size(); i++) {
			//System.out.println("Menores: "+menores.get(i));
		}
		//System.out.println("Menores lenght: "+menores.size());
		
		if(menores.size() == 4){
			random = Math.random();
			if(random < 0.25){
				return CIMA;
			}else if(random >= 0.25 && random < 0.5){
				return BAIXO;
			}else if(random >= 0.5 && random < 0.75){
				return ESQUERDA;
			}else if(random >= 0.75){
				return DIREITA;
			}
		}else if(menores.size() == 3){
			random = Math.random();
			if(random < 0.33){
				if(menores.get(0) == 0){
					return CIMA;
				}else if(menores.get(0) == 1){
					return BAIXO;
				}else if(menores.get(0) == 2){
					return ESQUERDA;
				}else if(menores.get(0) == 3){
					return DIREITA;
				}
			}else if(random >= 0.33 && random < 0.66){
				if(menores.get(1) == 0){
					return CIMA;
				}else if(menores.get(1) == 1){
					return BAIXO;
				}else if(menores.get(1) == 2){
					return ESQUERDA;
				}else if(menores.get(1) == 3){
					return DIREITA;
				}
			}else if(random >= 0.66){
				if(menores.get(2) == 0){
					return CIMA;
				}else if(menores.get(2) == 1){
					return BAIXO;
				}else if(menores.get(2) == 2){
					return ESQUERDA;
				}else if(menores.get(2) == 3){
					return DIREITA;
				}
			}
		}else if(menores.size() == 2){
			random = Math.random();
			if(random <= 0.5){
				if(menores.get(0) == 0){
					return CIMA;
				}else if(menores.get(0) == 1){
					return BAIXO;
				}else if(menores.get(0) == 2){
					return ESQUERDA;
				}else if(menores.get(0) == 3){
					return DIREITA;
				}
			}else if(random > 0.5){
				if(menores.get(1) == 0){
					return CIMA;
				}else if(menores.get(1) == 1){
					return BAIXO;
				}else if(menores.get(1) == 2){
					return ESQUERDA;
				}else if(menores.get(1) == 3){
					return DIREITA;
				}
			}
		}else if(menores.size() == 1){
			if(menores.get(0) == 0){
				return CIMA;
			}else if(menores.get(0) == 1){
				return BAIXO;
			}else if(menores.get(0) == 2){
				return ESQUERDA;
			}else if(menores.get(0) == 3){
				return DIREITA;
			}
		}
		return (int) (Math.random() * 5);
	}
	
}