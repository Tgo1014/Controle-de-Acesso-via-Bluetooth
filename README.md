#Controle de Acesso via Bluetooth

Este repositório contém os códigos-fonte desenvolvidos pelos alunos da Anhembi Morumbi para o Trabalho de Conclusão de Curso.

##Objetivo

O objetivo deste trabalho é a *prototipação* de um sistema de controle de acesso composto por um aplicativo desenvolvido para smartphone, usado para requisição do acesso por meio de rede sem fio Bluetooth, em uma abordagem com múltiplos identificadores do usuário e do dispositivo móvel. Outra aplicação, executada em outro computador, simula um dispositivo a ser acessado.

##Implementação

A implementação do protótipo foi dividida em três partes: Cadastro, Aplicativo Móvel e Autenticação. Abaixo está descrito a implementação das partes bem como as ferramentas utilizadas.

###Cadastro
Através da aplicação de cadastro é possível criar os papéis de acesso. São possíveis 4 tipos de cadastro: smartphone, grupo, dispositivo de acesso e usuário. O cadastro de usuário irá gerar um certificado ao termino do cadastro, caso o usuário tenha cadastrado um e-mail, além de gerar na área de trabalho será enviada uma cópia para o e-mail dele. Este certificado deverá ser armazenado no smartphone do usuário e será utilizado no processo de autenticação.

###Aplicativo Móvel
	
É através do aplicativo móvel que o usuário solicita acesso ao dispositivo. O aplicativo é utilizado para a busca de dispositivos de acesso, o envio de requisição de acesso e como um monitor de referência exibindo mensagens de acesso concedido ou negado. 

Para o desenvolvimento do aplicativo móvel foi utilizado Android Studio - IDE (Integrated Development Enviroment) para aplicativos móveis.
A utilização do recurso Bluetooth permitiu o envio das credenciais e o certificado digital ao dispositivo de acesso. No entanto para o envio do certificado digital foi necessário serializar e converter o certificado para um formato que o serviço REST conseguisse receber.

###Simulador Dispositivo de Acesso
	
Para simular o dispositivo de acesso foi necessário o desenvolvimento de um programa com um objeto a ser controlado. O programa deveria ter capacidades de comunicação via Bluetooth para receber as credenciais do usuário e smartphone e o certificado digital do usuário. O simulador do dispositivo de acesso deve adicionar a estas credenciais seu Mac Address e enviar todas informações através de uma requisisção via REST ao servidor de autenticação. 
O simulador do dispositivo de acesso foi desenvolvido na linguagem Java utilizando a IDE NetBeans.

##Organização
Este trabalho está dividido em 3 pastas principais Android, Java e MySQL. Abaixo a explicação do conteúdo de cada uma destas pastas.

**Android**: 
> Contém os arquivos necessários para a execução do projeto do aplicativo android através do Android Studio. Também contém um .apk funcional, já compilado, que pode ser instalado diretamente no celular.

**Java**: 
>Contém 3 subpastas: 
>
>**Gerenciador**: Contém os arquivos para serem executados no netbeans. O gerenciador é o responsável por fazer o cadastros dos dispositivos a serem acessados e dos celulares que poderam acessar estes dispositivos. Também é o gerenciador que gera o certificado que deverá ser usado no celular para ser autenticado.
>
>**deviceBluetooth**: Contém os arquivos para serem executados no netbeans. Esta aplicação simula um device Bluetooth que irá aguardar por requisições de celulares. Assim que receber ele irá solicitar a autorização do acesso ao webService.
>
>**webService**: Contém os arquivos para serem executados no eclipse. É o responsável por verificar os dados recebidos pelo deviceBluetooth no banco de dados e autorizar ou não o acesso.

**MySQL**
>Contém a modelagem utilizada e o script para gerar as bases necessárias no MySQL.

##Licença
Este projeto está sobre a licença [Attribution-NonCommercial 4.0 International](https://creativecommons.org/licenses/by-nc/4.0/)
