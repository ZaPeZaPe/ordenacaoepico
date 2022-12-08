package buscabinaria2;

import java.util.ArrayList;

public class Ordenacao {

    private static long countMove = 0;
    private static long countCompare = 0;

    public static void main(String[] args) {
        getTime(1000);
        getTime(10000);
        getTime(100000);
    }

    public static void getTime(int entrada) {
        long time = System.currentTimeMillis();
        repeat(10, entrada);
        long finalTime = System.currentTimeMillis() - time;
        System.out.println("Process finished in " + finalTime + " ms (~" + Math.round((float) finalTime /1000) + " seconds)\n");
    }

    public static void repeat(int qtd, int arraySize) {
        long somaMove = 0, somaCompare = 0, resultado;
        for (int i = 0; i < qtd; i++) {
            loop(arraySize);
            somaMove += countMove;
            somaCompare += countCompare;
            countMove = 0;
            countCompare = 0;
        }
        resultado = somaCompare/qtd;
        System.out.print("Num medio de comparacoes ("+arraySize+"): " + resultado+"\n");
        resultado = somaMove/qtd;
        System.out.println("Num medio de movimentacoes ("+arraySize+"): " + resultado);
    }

    public static void loop(int qtd) {
        ArrayList<Integer> vet = new ArrayList<>();
        vetorConstruidoAleatoriamente(vet, qtd);
//        quickSort(vet, 0, vet.size() - 1);
//        mergeSort(vet);
//        insertionSort(vet);
//        bubbleSort(vet);
        heapSort(vet);
//        selectionSort(vet);
    }

    public static void vetorConstruidoAleatoriamente(ArrayList<Integer> vetor, int quantidade) {
        for (int i = 0; i < quantidade; i++) {
            vetor.add(aleatorioNum(quantidade));
        }
    }

    public static void vetorConstruido90(ArrayList<Integer> vetor, int quantidade) {
        for (int i = 0; i < quantidade; i++) {
            if (((float) i/(float) quantidade)>=0.9) {
                vetor.add(aleatorioNum(quantidade));
            } else {
                vetor.add(i);
            }
        }
    }

    public static void vetorConstruidoInversamenteOrdenado(ArrayList<Integer> vetor, int quantidade) {
        for (int i = 0; i < quantidade; i++) {
            vetor.add(quantidade-i);
        }
    }

    // ================================== QUICK SORT ===================================================
    public static void quickSort(ArrayList<Integer> vetorDesorganizado, int low, int high) {
        countCompare++;
        if (low < high) {
            int pi = partition(vetorDesorganizado, low, high);
            quickSort(vetorDesorganizado, low, pi - 1);
            quickSort(vetorDesorganizado, pi + 1, high);
        }
    }

    public static int partition(ArrayList<Integer> vetorDesorganizado, int low, int high) {
        int pivot = vetorDesorganizado.get(high);
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            countCompare++;
            if (vetorDesorganizado.get(j) <= pivot) {
                countMove++;
                i++;
                int temp = vetorDesorganizado.get(i);
                vetorDesorganizado.set(i, vetorDesorganizado.get(j));
                vetorDesorganizado.set(j, temp);
            }
        }

        countMove++;
        int temp = vetorDesorganizado.get(i+1);
        vetorDesorganizado.set(i+1, vetorDesorganizado.get(high));
        vetorDesorganizado.set(high, temp);

        return i + 1;
    }

    //========================== MERGE SORT =========================================================

    public static ArrayList<Integer> mergeSort(ArrayList<Integer> vetorDesorganizado) {

        if (vetorDesorganizado.size() == 1) {
            return vetorDesorganizado;
        }

        ArrayList<Integer> l1 = subArray(vetorDesorganizado, 1);
        ArrayList<Integer> l2 = subArray(vetorDesorganizado, 2);

        l1 = mergeSort(l1);
        l2 = mergeSort(l2);

        return merge(l1, l2);
    }

    public static ArrayList<Integer> merge(ArrayList<Integer> a, ArrayList<Integer> b) {
        ArrayList<Integer> c = new ArrayList<>();
        while(!a.isEmpty() && !b.isEmpty()) {
            countCompare++;
            if (a.get(0) > b.get(0)) {
                countMove++;
                c.add(b.get(0));
                b.remove(0);
            } else {
                countMove++;
                c.add(a.get(0));
                a.remove(0);
            }
        }

        while (!a.isEmpty()) {
            countMove++;
            c.add(a.get(0));
            a.remove(0);
        }

        while (!b.isEmpty()) {
            countMove++;
            c.add(b.get(0));
            b.remove(0);
        }

        return c;
    }

    //Método que cria um subArray para o l1 e l2;
    public static ArrayList<Integer> subArray(ArrayList<Integer> vet, int code) {
        if (code == 1) {
            ArrayList<Integer> vetorSec = new ArrayList<>();
            for (int i=0; i < vet.size()/2; i++) {
                vetorSec.add(vet.get(i));
            }
            return vetorSec;
        }

        if (code == 2) {
            if (vet.size() / 2 > 0) {
                vet.subList(0, vet.size() / 2).clear();
            }
        }

        return vet;
    }

    //===================================== INSERTION SORT ======================================

    public static void insertionSort(ArrayList<Integer> vetorDesorganizado) {
        int atual, j;
        for (int i=1; i < vetorDesorganizado.size(); i++) {
            atual = vetorDesorganizado.get(i);
            for (j=i-1; j >=0; j--) {
                countCompare++;
                if (atual < vetorDesorganizado.get(j)) {
                    countMove++;
                    vetorDesorganizado.set(j+1, vetorDesorganizado.get(j));
                } else {
                    break;
                }
            }
            countMove++;
            vetorDesorganizado.set(j+1, atual);
        }
    }

    //=================================== BUBBLE SORT =================================================

    public static void bubbleSort(ArrayList<Integer> vetorDesorganizado) {
        int loop = vetorDesorganizado.size();
        boolean swapped;

        for (int i=0; i < loop-1; i++) {
            swapped=false;

            for (int j=0; j < loop-1; j++) {
                countCompare++;
                if (vetorDesorganizado.get(j) > vetorDesorganizado.get(j+1)) {
                    countMove++;
                    swap(vetorDesorganizado, j);
                    swapped=true;
                }
            }

            if(!swapped) {
                break;
            }
        }
    }

    public static void swap (ArrayList<Integer> vetor, int index) {
        int aux;
        aux = vetor.get(index+1);
        vetor.set(index+1, vetor.get(index));
        vetor.set(index, aux);
    }

    //========================================== HEAP SORT ===============================================
    public static void heapSort(ArrayList<Integer> vetorDesorganizado) {
        int N = vetorDesorganizado.size();

        for (int i = N / 2 - 1; i >= 0; i--)
            heapify(vetorDesorganizado, N, i);

        for (int i = N - 1; i > 0; i--) {
            countMove++;
            int temp = vetorDesorganizado.get(0);
            vetorDesorganizado.set(0, vetorDesorganizado.get(i));
            vetorDesorganizado.set(i, temp);

            heapify(vetorDesorganizado, i, 0);
        }
    }

    public static void heapify(ArrayList<Integer> vetorDesorganizado, int N, int i)
    {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        countCompare++;
        if (l < N && vetorDesorganizado.get(l) > vetorDesorganizado.get(largest))
            largest = l;

        countCompare++;
        if (r < N && vetorDesorganizado.get(r) > vetorDesorganizado.get(largest))
            largest = r;

        countCompare++;
        if (largest != i) {
            countMove++;
            int swap = vetorDesorganizado.get(i);
            vetorDesorganizado.set(i, vetorDesorganizado.get(largest));
            vetorDesorganizado.set(largest, swap);

            heapify(vetorDesorganizado, N, largest);
        }
    }

    //========================================== SELECTION SORT ==========================================
    public static void selectionSort(ArrayList<Integer> vetorDesorganizado){
        for (int i = 0; i < vetorDesorganizado.size() - 1; i++)
        {
            int index = i;
            for (int j = i + 1; j < vetorDesorganizado.size(); j++){
                countCompare++;
                if (vetorDesorganizado.get(j) < vetorDesorganizado.get(index)){
                    index = j;
                }
            }
            countMove++;
            int smallerNumber = vetorDesorganizado.get(index);
            vetorDesorganizado.set(index, vetorDesorganizado.get(i));
            vetorDesorganizado.set(i, smallerNumber);
        }
    }

    public static int aleatorioNum(int qtd) {
        int i = 0;
        return (int) Math.floor(Math.random() * (qtd - i + 1) + i);
    }
}
