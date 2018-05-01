import java.util.Arrays;

public class Packing {

	
	public static int FirstFit(int itens[], int numberOfItens, int capacidade) {

		int NumBins = 0;
		int BinFreeSpace[] = new int[numberOfItens];
		Arrays.fill(BinFreeSpace, capacidade);

		for (int i = 0; i < numberOfItens; i++) {

			int j;
			
			for (j = 0; j < NumBins; j++) {

				// Insere o item[i] no Bin[j], se houver espaco
				if (BinFreeSpace[j] >= itens[i]) {
					BinFreeSpace[j] -= itens[i];
					break;
				}
			}

			// Se nao houver onde inserir o item[i], acrescenta mais um bin e o insere
			if (j == NumBins) {
				NumBins++;
				BinFreeSpace[NumBins] -= itens[i];
			}
		}
		
		NumBins++;
		return (NumBins);
	}

	public static int NextFit(int itens[], int numberOfItens, int capacidade) {
		int NumBins = 0;
		int BinFreeSpace = capacidade;

		for (int i = 0; i < numberOfItens; i++) {

			// Se houver espaco, insere o item[i] no ultimo Bin alocado
			if (BinFreeSpace >= itens[i]) {
				BinFreeSpace -= itens[i];
			}

			// Se n�o houver onde inserir o item[i], acrescenta mais um bin e o insere
			else {
				NumBins++;
				BinFreeSpace = capacidade - itens[i];
			}
		}
		
		NumBins++;
		return NumBins;
	}

	
}