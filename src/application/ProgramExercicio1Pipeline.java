package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import model.entities.Produto;

public class ProgramExercicio1Pipeline {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Entre o caminho do arquivo: ");
		String caminho = sc.nextLine();
		
		try(BufferedReader br = new BufferedReader(new FileReader(caminho))){
			String linha = br.readLine();
			List<Produto> lista = new ArrayList<>();
			
			while(linha != null) {
				String[] campos = linha.split(",");
				lista.add(new Produto(campos[0], Double.parseDouble(campos[1])));
				linha = br.readLine();
			}
			
			//forma de achar os a media dos precos com pipeline
			double media = lista.stream()
					.map(p -> p.getPreco())
					.reduce(0.0, (x,y) -> x + y) / lista.size();
			System.out.println("Média dos produtos: R$ "+ String.format("%.2f", media));
			
			// camparator que será utilizado para ordenar a lista
			Comparator<String> comp = (s1, s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());
					
			//encontrando os nomes dos produtos com preço abaixo da média
			List<String> nomes = lista.stream()
					.filter(p -> p.getPreco() < media) //faz primeiro o filtro
					.map(p -> p.getNome()) //pega todos os produtos que foram filtrados
					.sorted(comp.reversed()) //coloca na ordem reversa utilizando o comparetor
					.collect(Collectors.toList()); //cria a nova lista
			
			//imprimindo a nova lista de produtos abaixo da média
			System.out.println();
			System.out.println("Produtos Abaixo da Média:");
			nomes.forEach(System.out::println);
			
		}catch(IOException e) {
			System.out.println("Erro: "+ e.getMessage());
		}
		sc.close();
		
		
	}

}
