package br.com.waldson.aula12;

import br.com.waldson.aula12.Pessoa;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

public class FilaBanco implements Observer {

    private Pessoa[] pessoas;
    private int size;//quantos elementos tem
    private int capacity; //quantos elementos pode ter

    public FilaBanco() {
        this(10);
    }

    public FilaBanco(int capacity) {
        pessoas = new Pessoa[capacity];
        this.size = 0;
        this.capacity = capacity;
    }

    public void addPessoa(String nome, int idade) {
        addPessoa(new Pessoa(nome, idade));
    }

    public void addPessoa(Pessoa pessoa) {
        this.ensureCapacity();
        this.pessoas[getSize()] = pessoa;
        heapifyUp(getSize());
        size++;
        pessoa.addObserver(this);
    }

    private void heapifyUp(int index) {
        if (!hasParent(index)) {
            return;
        }
        int parentIndex = getParentIndex(index);

        Pessoa node = pessoas[index];
        Pessoa pai  = pessoas[parentIndex];

        if (node.getIdade() > pai.getIdade()) {
            pessoas[index] = pai;
            pessoas[parentIndex] = node;
            heapifyUp(parentIndex);
        }
    }

    private boolean hasParent(int index) {
        return getParentIndex(index) >= 0 && getParentIndex(index) < size;
    }

    private int getParentIndex(int index) {
        return (int) Math.floor((index - 1) / 2);
    }

    private void ensureCapacity() {
        if (getSize() == capacity) {
            this.pessoas = Arrays.copyOf(this.pessoas, getSize() * 2);
            capacity     = getSize() * 2;
        }
    }

    public int getSize() {
        return size;
    }

    public Pessoa peek() {
        if (getSize() == 0)  {
            return null;
        }

        return pessoas[0];
    }

    public void remove() {
        pessoas[0] = pessoas[getSize() - 1];
        pessoas[getSize() - 1] = null;
        size--;
        heapifyDown(0);
   }

    private void heapifyDown(int index) {
        int leftChild  = index * 2 + 1;
        int rightChild = index * 2 + 2;

        int childIndex = -1;

        if (leftChild < getSize()) {
            childIndex = leftChild;
        }

        if (childIndex < 0) {
            return;
        }

        if (rightChild < getSize()) {
            if (pessoas[rightChild].getIdade() > pessoas[leftChild].getIdade()) {
                childIndex = rightChild;
            }
        }

        if (pessoas[index].getIdade() < pessoas[childIndex].getIdade()) {
            Pessoa tmp          = pessoas[index];
            pessoas[index]      = pessoas[childIndex];
            pessoas[childIndex] = tmp;
            heapifyDown(childIndex);
        }
    }

    public Pessoa[] getPessoas() {
        return pessoas;
    }

    @Override
    public void update(Observable pessoaAgeSubject, Object arg) {
        if (pessoaAgeSubject instanceof Pessoa) {
            for(int i=0; i<capacity; ++i) {
                if(pessoas[i].getNome() == ((Pessoa) pessoaAgeSubject).getNome()) {
                    heapifyUp(i);
                    heapifyDown(i);
                    return;
                }
            }
        }
    }
}
