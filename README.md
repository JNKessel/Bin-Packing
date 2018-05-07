1	ALGORITMO GENÉTICO

1.1	Como funcionam os Algoritmos Genéticos

DESCRIÇÃO:
O algoritmo genético começa com uma população inicial de indivíduos. Em seguida, é feita a seleção 2 a 2 dos pais na população, e esses indivíduos são recombinados gerando dois novos indivíduos. Nessa recombinação de cromossomos dos pais podem ocorrer mutações, que alteram um pouco a estrutura do cromossomo do indivíduo gerado. Dessa nova geração de pais e filhos são selecionados os mais aptos e então retornamos a selecionar os pais para reprodução.

ALGORITMO EM ALTO NÍVEL
Formar população inicial;
Enquanto nenhuma condição de parada for atingida:
	Selecionar pais da população;
	Reproduzir os pais;
	Selecionar os indivíduos mais aptos;
Fim Enquanto

DEFINIÇÕES
Nesse tipo de algoritmo os indivíduos representam as soluções para o problema, os cromossomos são as estruturas dessas soluções, e uma população é um conjunto de soluções.

1.2	Alterações para o Trabalho

1.2.1	Estrutura

VETOR DE INTEIROS:
Para o trabalho nós definimos a estrutura dos cromossomos como um vetor de inteiros. Portanto, os cromossomos são as estruturas que definem a ordem dos itens que devem ser alocados nos bins de acordo com o método de alocação definido. E os indivíduos, portanto, não representam as soluções propriamente ditas para o problema, mas uma predefinição dessa solução, de modo que ao aplicar um método de alocação se obtém uma solução para o problema.
A estrutura de vetor de inteiros foi escolhida por ser uma estrutura de fácil manipulação em questões de desenvolvimento do código, de funcionamento da reprodução (crossover e mutação) e de custo de produção. Além disso, uma outra vantagem dessa estrutura é que ela nunca poderá resultar em overflow de itens nos bins por si mesma, este erro fica restrito aos métodos de alocação.
Além disso, a estrutura de vetor de inteiros é capaz de representar para qualquer caso pelo menos uma das melhores configurações finais possíveis para o problema.

DEMONSTRAÇÃO DE EFICIÊNCIA:
Para um problema de Bin Packing, sempre teremos “k” configurações possíveis para os itens nos bins com “x” bins. Sendo “x” o menor número possível de bins em que é possível alocar os itens.
Exemplo: Para os itens [10, 3, 5, 4, 9, 5, 5, 2], supondo capacidade máxima 15. Temos uma configuração ótima com [10, 3, 2] no primeiro bin, [5, 5, 5] no segundo bin e [4, 9] no terceiro bin, totalizando 3 bins. Mas também podemos ter [10, 5] no primeiro bin, [4, 5, 5] no segundo bin e [3, 2, 9] no terceiro bin, que também totaliza 3 bins. E para o problema, o importante é encontrar alguma configuração de itens nos bins cujo número total de bins utilizados seja o menor possível. Ou seja, teremos sempre pelo menos uma configuração ótima para o problema e no máximo “k” configurações ótimas para “x” bins no total.
Seguindo este raciocínio, utilizando vetores de inteiros, podemos provar que sempre é possível encontrar uma configuração da ordem desses itens no vetor de forma que essa configuração seja uma das “k” ótimas configurações para o problema.
Sabendo que os métodos de alocação utilizados para transformar a estrutura em uma solução são os métodos de First Fit e Next Fit. E que a ordem dos itens no vetor é o único atributo que influencia na geração de soluções diferentes, porque a capacidade máxima dos bins e os itens em si não variam de uma estrutura para a outra. Conseguimos provar que sempre existe uma determinada ordem desses itens que gera uma das “k” configurações ótimas.
Supondo uma configuração ótima final conhecida, como [[10, 3, 2], [5, 5, 5], [4, 9]]. Se tirarmos todos os itens desses bins na ordem em que estão, teremos [10, 3, 2, 5, 5, 5, 4, 9]. Aplicando a este vetor uma Next Fit, encontramos a configuração anterior, que é uma configuração ótima para o problema. Isso ocorre porque os itens nessa ordem já estão organizados de modo que quando pegamos os itens 10, 3 e 2 e tentamos colocar o 5, este já não cabe mais no primeiro bin, somente em um novo bin. A capacidade máxima separa os bins no vetor na ordem em que estão da esquerda para a direita. Isso ocorre também para a First Fit, que ao tentar encaixar os itens nos bins, nenhum dos próximos itens a partir do primeiro 5, caberão no bin 1. E se couberem? E se a capacidade máxima fosse 19 ou o item 4 fosse um item 1.  Não teria problema, porque de qualquer maneira esta seria uma outra ótima configuração para o problema, uma vez que continuaríamos com um total ótimo de 3 bins.
Dessa maneira, sempre existem pelo menos uma ordem de itens que para o método de First Fit vai gerar uma solução ótima e pelo menos uma ordem de itens que para o método de Next Fit vai gerar uma solução ótima. Nesse caso, mesmo que não seja possível realizar operações de trocar um item de um bin com o item de outro bin por exemplo, já que uma troca na ordem dos elementos poderia gerar uma solução completamente diferente da esperada com essa operação após a aplicação de um método de alocação, podemos afirmar que o algoritmo sempre pode chegar em pelo menos uma solução ótima para o problema.

1.2.2	População Inicial

GERAÇÃO DOS PRIMEIROS INDIVÍDUOS:
A população inicial é gerada com base no vetor inicial de itens recebido como entrada do algoritmo. Esse vetor inicial de itens é formado pelos itens na ordem em que foram lidos dos arquivos de teste. Sendo assim, os indivíduos dessa primeira população são gerados alterando a ordenação desse vetor inicial de itens. As alterações de ordenação utilizadas foram:
•	O próprio vetor inicial de itens em sua ordenação natural
•	O vetor inicial de itens ordenado por ordem decrescente de peso dos itens
•	O vetor inicial de itens ordenado por ordem crescente de peso dos itens
•	O vetor inicial de itens ordenado aleatoriamente
Para diversificação dessa população inicial, não se aceita repetição de indivíduos gerados. Então sempre um indivíduo é adicionado à população há uma verificação para checar se para esse indivíduo gerado, que possui uma ordenação de itens, já não existe um outro indivíduo na população com a mesma ordenação de itens.
Desse modo, começamos com uma população vazia (sem indivíduos) e em seguida começamos a adicionar os indivíduos a população. E primeiro indivíduo gerado é o vetor inicial em sua ordenação natural. E depois adicionado à população. O segundo indivíduo gerado é o vetor inicial ordenado decrescentemente. Ele é verificado para evitar indivíduos repetidos e, caso a verificação diga que este indivíduo ainda não existe na população, ele é adicionado à população. O terceiro indivíduo gerado é o vetor inicial ordenado crescentemente. Ele também é verificado e, se for o caso, adicionado à população. A partir do quarto indivíduo, os indivíduos são gerados ordenando o vetor inicial de forma aleatória. Esses indivíduos, novamente, são verificados e, se for o caso, adicionados à população.
Essa geração de indivíduos ocorre até que a população atinja um determinado número de indivíduos. Esse número de indivíduos da população é previamente definido de acordo com o número de itens existente.

TRATAMENTO DO NÚMERO DE INDIVÍDUOS NA POPULAÇÃO:
Começamos com um número definido de indivíduos para a população de “m = 10”. Mas, para tratar os casos em que o número máximo de indivíduos diferentes é menor que “m”, alteramos o “m” para este número máximo de indivíduos diferentes. De forma que a população inicial já inclua todos os indivíduos possíveis. E, assim, consiga chegar na melhor solução mais rápido.
Esse número máximo de indivíduos diferentes possível é dado pela permutação dos itens da estrutura do indivíduo. Tendo um número “n” de itens, o número total de possíveis ordenações do vetor é a permutação desses itens, que consiste em trocar os itens de ordem até encontrar todas as ordenações diferentes desses itens. A permutação de “n” itens resulta em fatorial de “n” ordenações (n!).
Portanto, os vetores de itens que tem menos de “m” ordenações diferentes geram populações de fatorial de “n” indivíduos. E para os vetores de itens com mais de “m” ordenações diferentes geram populações de “m” indivíduos, pois seria inviável para vetores com muitos itens gerar todos os indivíduos diferentes possível.

1.2.3	Geração de Famílias

A geração de famílias consiste na seleção dos pais 2 a 2 em uma população, reprodução desses pais, que envolve a recombinação e mutação de genes, gerando sempre 2 filhos, e formação de um conjunto de indivíduos que inclui os pais e os filhos, chamado de famílias.
Esse conjunto é denominado de famílias, para separar do conjunto de indivíduos denominado população. Uma população é um conjunto de “m” indivíduos e o conjunto de famílias é um conjunto de “2*m” indivíduos, que contém os indivíduos de uma população e os indivíduos gerados pela recombinação desses indivíduos da população. Como a geração de indivíduos é feita gerando-se 2 indivíduos novos a cada 2 indivíduos da população, tem-se exatamente o dobro de indivíduos da população utilizada para reprodução no conjunto de famílias.
Esse conjunto de famílias é posteriormente classificado, como em uma seleção natural, e parte desses indivíduos passa para a próxima geração.

1.2.3.1	Seleção de Pais

O primeiro passo para a geração de famílias é a seleção dos pais na população. Para selecionar os pais é utilizado um método randômico para aleatorizar a seleção de dois indivíduos na população e então estes dois indivíduos são definidos como um par de pais. Isso se repete até que não se tenha mais indivíduos ainda não selecionados na população. Utilizando um tratamento para averiguar que o individuo selecionado aleatoriamente ainda não foi selecionado para ser um pai.

1.2.3.2	Reprodução de Pais

A reprodução é a segunda etapa da geração de famílias, e consiste na recombinação dos genes dos pais e numa possível mutação para alterar cada um desses genes que formarão os novos indivíduos, chamados de filhos.

1.2.3.2.1	Recombinação ou Crossover

DESCRIÇÃO:
A técnica utilizada para a recombinação de pais ou crossover foi o mapeamento parcial (PMX). Nessa operação genética, o pai 1 doa uma parte da sua sequência de genes para o filho e em seguida é feito um mapeamento dos genes do pai 2 na sequência correspondente para o filho. Terminado o mapeamento em sequência, o pai 2 doa para o filho os genes restantes para completar sua estrutura. Para gerar o segundo filho, repete-se a mesma operação trocando a ordem dos pais.
Para fácil entendimento das estruturas, o vetor de inteiros é chamado de cromossomo do indivíduo e os itens desse vetor são os genes do cromossomo. Como cada individuo é formado por um cromossomo associa-se ao indivíduo a própria estrutura de vetor de inteiros.

FUNCIONAMENTO:
Primeiro são escolhidos aleatoriamente dois números inteiros que definem os índices do intervalo para a sequência de genes do pai 1 a ser herdada pelo filho.
 
1 – Uma sequência do pai 1 é copiada para o filho 
Em seguida, os genes da sequência correspondente ao pai 2 (genes no mesmo intervalo de índices) são verificados para checar se já existem no filho. Caso não exista, é feito o mapeamento desse gene para filho. E, caso exista, desconsidera-se este gene e continua o mapeamento para o próximo gene da sequência.
O primeiro gene a ser mapeado do pai 2 é o 3, mas como ele já está presente no filho, é desconsiderado. Continua-se então para o segundo gene, 4, que ainda não esta presente no filho. Procura-se o gene correspondente ao gene 4 na mesma posição no pai 1, que é o 6. Encontra-se então o gene 6 no pai 2.
 
2 – O gene 4 do pai 2 corresponde ao gene 6 nessa posição no pai 1 e o gene 6 no pai 2 é destacado
Como a posição ocupada pelo gene 6 no pai 2 já está ocupada no filho, repete-se então a operação de mapeamento agora para este gene. O gene correspondente ao gene 6 do pai 2 no pai 1 é o 5. Encontra-se então o gene 5 no pai 2.
 
3 – O gene 6 do pai 2 corresponde ao gene 5 nessa posição no pai 1 e o gene 5 no pai 2 é destacado
Como a posição ocupada pelo gene 5 no pai 2 já está ocupada no filho, repete-se então a operação de mapeamento agora para este gene. 
 
4 – O gene 5 do pai 2 corresponde ao gene 2 nessa posição no pai 1 e o gene 2 no pai 2 é destacado
Agora que encontramos uma posição livre na estrutura do filho, completamos ela com o gene que se desejava adicionar inicialmente, o gene 4.
Esse mapeamento continua até que se acabem os genes da sequência de genes do intervalo. E por fim, o filho tem seus genes vazios completos com os genes correspondentes do pai 2 nessas posições.
 
5 – O filho tem suas posições vazias completas pelos genes correspondentes no pai 2

TRATAMENTO DE ITENS REPETIDOS
Como pode ser visto no funcionamento do método PMX descrito acima, este mapeamento funciona para vetores de inteiros cujos itens são diferentes entre si. No caso de itens repetidos, o mapeamento não funciona porque pode entrar em ciclo.
Para tratar o mapeamento evitando a repetição de itens, os vetores de inteiros são convertidos em vetores de strings através de um dicionário. Esse dicionário é definido no início do algoritmo genético de acordo com o vetor inicial de itens. Sabendo que esses itens são sempre os mesmos para todos os indivíduos, para cada item é associado uma string do tipo “N” concatenado com o índice correspondente a sua posição no vetor mais 1. Assim, o item1 na posição 0 do vetor inicial corresponde à string “N1”, o item 2 na posição 1 do vetor inicial corresponde a string “N2” e assim por diante.
Com itens únicos e diferentes entre si nos vetores convertidos, é feito o mapeamento parcial (PMX) e ao final, o vetor de strings do filho gerado é reconvertido para inteiros utilizando o mesmo dicionário, para manter a forma da estrutura de vetor de inteiros.

1.2.3.2.2	Mutação

As mutações são aplicadas aos indivíduos gerados logo após a recombinação de um par de pais. O método de mutação, no entanto, precisa que o indivíduo ganhe um sorteio baseado na probabilidade de mutação da geração para sofrer a mutação.

REALOCATE:
Seleciona duas posições aleatórias pos1 e pos2 no vetor de inteiros, sendo pos1 < pos2. Troca o item da posição pos2 com o item da posição pos1 + 1 aplicando um “shift right” para os itens entre eles. Ou seja, todos os itens entre pos1 e pos2 se movem uma posição para a direita.

SWAP:
Seleciona duas posições aleatórias no vetor de inteiros e troca os dois itens correspondentes de posição.

SCRAMBLE:
Seleciona duas posições aleatórias no vetor de inteiros e seleciona dois itens aleatórios nesse intervalo para trocar de posição 15 vezes.

TWO-OPT:
Seleciona duas posições aleatórias no vetor de inteiros e inverte a ordem dos elementos nesse intervalo.

1.2.3.3	Formação de Famílias

A formação de famílias é a terceira etapa da geração de famílias. Nessa etapa os filhos gerados e seus respectivos pais são adicionados à um único conjunto chamado de famílias.

1.2.4	Classificação

1.2.4.1	Classificação dos Indivíduos

A classificação dos indivíduos é feita aplicando à sua estrutura de vetor de inteiros um método de alocação. Os métodos de alocação utilizados foram o First Fit e o Next Fit. Esses métodos retornam o número de bins utilizado para alocar os itens ordenados daquela maneira. Assim os indivíduos são classificados pelo número de bins utilizados no total, esse número também é chamado de fitness do indivíduo.

1.2.4.2	Avaliação Geral do Algoritmo

No método de classificação dos indivíduos também são atualizados os parâmetros de avaliação geral do algoritmo. Esses parâmetros são:
•	A média dos valores de fitness dos indivíduos
•	O melhor fitness encontrado
•	O pior fitness encontrado
•	O número de gerações sem melhoria de melhor fitness
Os três primeiros parâmetros são utilizados para avaliar ao final do algoritmo como os indivíduos se comportaram ao longo das gerações. O quarto parâmetro é utilizado como condição de parada para o algoritmo. E todos eles são utilizados para alterar a probabilidade de ocorrência de mutações na geração de novos indivíduos. 

1.2.4.3	Probabilidade de Mutação

No método de classificação dos indivíduos é alterada a probabilidade de mutação de acordo os parâmetros de avaliação da seguinte forma:
A probabilidade de mutação é zerada no início do método e possui limite inferior de 0 e limite superior de 0.10.
Caso a média dos valores de fitness dos indivíduos não tenha melhorado desde a última geração, aumenta-se 0.1 à probabilidade de mutação.
Caso o melhor fitness encontrado não tenha melhorado desde a última geração, aumenta-se 0.1 à probabilidade de mutação.
Caso o pior fitness encontrado não tenha melhorado desde a última geração, aumenta-se 0.1 à probabilidade de mutação.
Caso o número de gerações sem melhoria de melhor fitness seja superior a 15, aumenta-se 0.1 à probabilidade de mutação, caso seja superior a 20, aumenta-se 0.2 à probabilidade de mutação, e caso seja superior a 25, aumenta-se 0.3 à probabilidade de mutação. Não acumulativamente.
Assim a probabilidade de mutação pode variar de 0 a 0.6 a cada geração.
A ideia é aumentar a probabilidade de mutação a medida que nos aproximamos de populações de indivíduos que estão em um ótimo local, para tentar mudar a solução a ponto de escapar desses ótimos locais. E diminuir a probabilidade quando está havendo melhoria normal nos indivíduos através do crossover.

1.2.4.4	Condição de Parada

A condição de parada do algoritmo é o número de gerações sem melhoria de melhor fitness ser maior que 50 ou o número de gerações ultrapassar 1000.
1.2.5	Próxima Geração
A próxima geração é formada selecionando-se metade dos melhores indivíduos do conjunto de famílias. Os melhores indivíduos, são os que tem melhor fitness. Assim se gera uma nova população sempre se mantendo o número de “m” indivíduos.

1.2.6	Métodos de Alocação de Itens

1.2.6.1	First Fit

O método de First Fit utilizado para alocar os itens de um vetor de inteiros em bins consiste em retirar os itens do vetor um a um e tentar coloca-los em algum bin já existente, caso não seja possível então cria-se um bin para receber o item.

1.2.6.2	Next Fit

O método de Next Fit utilizado para alocar os itens de um vetor de inteiros em bins consiste em retirar os itens do vetor um a um e tentar coloca-los no último bin criado, caso não seja possível então cria-se um bin para receber o item.

1	Busca Local

2.1	Como funciona uma Busca Local

DESCRIÇÃO:
A busca local é feita partindo-se de uma solução inicial e aplicando a ela métodos de vizinhança para gerar novas soluções. Para mudar a solução atual para uma solução vizinha podemos aceitar a primeira solução vizinha que tenha um fitness melhor que a atual ou encontrar todas as soluções vizinhas e mudar para a que tem o melhor fitness de todos os vizinhos e que é melhor que o fitness da solução atual. Caso não seja gerada nenhuma solução vizinha melhor que a atual, o algoritmo termina e a solução atual é a solução final. Existem também variações desse algoritmo que aceitam soluções piores ou soluções com fitness igual.

ALGORITMO EM ALTO NÍVEL
Gerar solução inicial;
Enquanto nenhuma condição de parada for atingida:
	Aplicar vizinhanças à solução;
	Classificar as soluções;
Fim Enquanto

2.2	Alterações para o Trabalho

Para o trabalho foi utilizada uma busca local que parte de uma solução inicial e à esta solução são aplicadas todas as vizinhanças. Substitui-se a solução atual pela melhor solução vizinha somente se esta tiver um melhor fitness que a solução atual. Caso isso a melhor solução vizinha tem um fitness pior ou igual a atual, o algoritmo para. 

2.2.1	Estrutura

A estrutura utilizada para representar as soluções foram vetores de vetores de inteiros. O primeiro vetor guarda em cada posição um bin, que é um vetor de inteiros onde em suas posições são guardados os seus itens.

2.2.2	Solução Inicial

A solução inicial é gerada ordenando o vetor inicial de itens por ordem decrescente de peso e em seguida aplicando-se o método de alocação First Fit.

2.2.3	Vizinhanças

2.2.3.1	Troca 1-1:
Na vizinhança Troca 1-1, um bin A é sorteado e um item B desse bin A é sorteado. Em seguida, para todos os outros bins tenta-se trocar o item B com um item do bin. Caso nenhuma troca funcione, outro bin é sorteado e um item desse bin é sorteado. Então tenta-se novamente para todos os outros bins trocar o item sorteado com um item do bin. Isso se repete até que se encontre uma troca viável ou que se sorteie todos o bins possíveis.
Não necessariamente cobre todos os casos de troca de um item por outro item pois quando um bin é sorteado, um item desse bin é sorteado. Caso não seja viável a troca desse item sorteado com nenhum dos itens de nenhum outro bin a tentativa não é repetida para os outros itens do bin sorteado.

2.2.3.2	Troca 2-0A:
Na vizinhança Troca 2-0A, um bin é sorteado e dois itens desse bin são sorteados. Para todos os outros bins tenta-se então inserir os dois itens sorteados em um bin. Se nenhum movimento for possível, sorteia-se outro bin, dois itens desse bin, e para todos os outros bins tenta-se inserir os dois itens em um bin. Isso se repete até que se encontre um movimento viável ou que se sorteie todos os bins possíveis.
Não necessariamente cobre todos os casos de tentativa de inserir dois itens de um bin em outro bin pois quando um bin é sorteado, dois itens desse bin são sorteados. Caso não seja viável nenhum movimento de inserção desses dois itens em outro bin a tentativa não é repetida para os outros itens do bin sorteado.

2.2.3.3	Troca2-0B:
Na vizinhança Troca 2-0B, um bin é sorteado e dois itens desse bin são sorteados. Para todos os outros bins tenta-se então inserir os itens sorteados em um mesmo bin ou em dois bins diferentes. Se não houver nenhum movimento possível, sorteia-se outro bin e a tentativa é repetida. Isso se repete até que um movimento seja possível ou que se sorteie todos os bins possíveis. 
Não necessariamente cobre todos os casos de tentativa de inserir dois itens de um bin em outro bin ou um item em um bin A e o outro item em um bin B pois os itens do bin são sorteados. E se não houver um movimento possível a tentativa não é repetida para os outros itens do bin sorteado.

2.2.3.4	Troca 2-1:
Na vizinhança Troca 2-1, um bin A é sorteado e dois itens B e C desse bin são sorteados. Para todos os outros bins tenta-se então inserir os itens sorteados B e C em um bin D. Sendo que, simultaneamente, este bin D, para o qual os itens A e B estão sendo movidos, doa um item E sorteado para o bin A. Se não houver nenhum movimento possível, sorteia-se outro bin e a tentativa é repetida. Isso se repete até que um movimento seja possível ou que se sorteie todos os bins possíveis. 
Não necessariamente cobre todos os casos de tentativa de inserir dois itens B e C de um bin A em outro bin D e um item E do bin D no bin A pois os itens dos bins são sorteados. E se não houver um movimento possível a tentativa não é repetida para os outros itens desses bins.

2.2.3.5	Perturbação:
Na vizinhança Perturbação, sorteia-se um item de um bin A e tenta inserir em um bin B ao mesmo tempo que se sorteia um item do bin B e tenta inserir em um bin C. Isso é repetido ate se encontrar um movimento viável ou acabarem as combinações de bins 3 a 1.
Não necessariamente cobre todos os casos de troca de um item do bin A para o bin B e um item do bin B para um bin C pois os itens do bins são sorteados. E caso não seja encontrado um movimento possível, não se volta a tentar para os outros itens dos bins.

2.2.3.6	Reconstrução:
A vizinhança Reconstrução, retira todos os itens do bin com mais folga de capacidade e retira 20% dos itens dos outros bins (fazendo um round down caso essa conta resulte em números decimais e não inteiros). Em seguida é feito um shuffle do vetor de bins trocando aleatoriamente os bins de lugar e então tenta-se encaixar um a um os itens retirados nos bins, na ordem nova em que estão. Caso nenhum movimento seja possível ou seja atingido o número máximo de iterações a tentativa não se repete.

2.2.4	Método de alocação de itens

2.2.4.1	First Fit:
O método de First Fit utilizado para alocar os itens de um vetor de inteiros em bins consiste em retirar os itens do vetor um a um e tentar coloca-los em algum bin já existente, caso não seja possível então cria-se um bin para receber o item.

2.2.4.2	Personalizado:
O método personalizado criado é utilizado para realocação dos itens de um vetor de vetor de inteiros. Ele ordena os bins do bin com maior folga de capacidade para o mais cheio (menor folga de capacidade) e tenta inserir os itens dos bins com maior folga nos bins mais cheios. Isso é feito para tenta minimizar o número de bins utilizado.
