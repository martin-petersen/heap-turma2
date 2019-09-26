package br.com.waldson.aula11;

import br.com.waldson.aula12.FilaBanco;
import br.com.waldson.aula12.Pessoa;

public class Main {

    public static void main(String[] args) {
        Pessoa fulano = new Pessoa("Delcio", 50);
        Pessoa fulano2 = new Pessoa("Lucas", 40);
        Pessoa fulano3 = new Pessoa("Hallesandro", 20);
        FilaBanco fila = new FilaBanco();


        fila.addPessoa(fulano);
        fila.addPessoa(fulano2);
        fila.addPessoa(fulano3);

        fulano2.setIdade(86);
    }

}
