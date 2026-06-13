# 📘 Sistema de Agendamentos PATP

## 🧠 Visão Geral

Este projeto é um sistema de agendamento de consultas médicas desenvolvido em Java. Ele permite o cadastro de usuários, médicos e o gerenciamento de consultas e agendamentos.

A aplicação segue uma arquitetura em camadas, utilizando:
- Java
- JDBC para acesso ao banco de dados
- Banco de dados relacional (MySQL)
- Interface gráfica (javaFX)
- Envio de e-mails automaticamente(jakarta Mail)

---

## ⚙️ Requisitos da Aplicação

- JDK 21
- javaFX 21.0.10
- jdbc 9.5.0
- jakarta mail 2.0.1

---
## Configuração de Ambiente de Desenvolvimento

- Este guia apresenta a configuração mínima necessária para executar o projeto no **Eclipse** e no **VS Code**, sem utilização de Maven, utilizando bibliotecas `.jar` manuais.
- A pasta lib/ da aplicaco contem os .jar das bibliotecas acima, Para configurar o ambiente de desenvolvimento,faca o seguinte processo:

---

## Para Eclipse IDE

### 1. Importar o projeto
- File → Open Projects from File System
- Selecionar a pasta do projeto

---

### 2. Configurar JDK
- Project → Properties
- Java Build Path → Libraries
- Remover JRE antigo
- Adicionar JDK 21

---

### 3. Criar User Libraries

#### JavaFX
- Window → Preferences → Java → Build Path → User Libraries
- Criar: `javaFX21`
- Adicionar:

- `javafx-swt.jar`
- `javafx.base.jar`
- `javafx.controls.jar`
- `javafx.fxml.jar`
- `javafx.graphics.jar`
- `javafx.media.jar`
- `javafx.swing.jar`
- `javafx.web.jar`


#### JDBC
- Criar: `JDBC`
- Adicionar o seguinte .jar

   - mysql-connector-j-9.5.0.jar


#### Jakarta Mail
- Criar: `jakartaMail`
- Adicionar:
  - `jakarta.mail-2.0.1.jar`
  - `jakarta.activation-2.0.1.jar`

---

### 4. Adicionar bibliotecas ao projeto
- Project → Properties → Java Build Path
- Aba Libraries
- Adicionar:
  - javaFX21
  - JDBC
  - jakartaMail
- **observacao**: ao configurar o projeto no eclipse, lembre se de adicionar javafx21 e jakartamail ao modulepath junto da jre System library[jdk21], enquanto o jdbc deve ficar no classpath 
---

### 5. Configurar VM Arguments (JavaFX)

Run Configurations → Arguments → VM arguments:

```bash
--module-path lib/javafx-sdk-21.0.10/lib --add-modules javafx.controls,javafx.fxml,javafx.graphics
````

---

### 7. Executar aplicação
- Executar `main.Main`
- Ou rodar o projeto normalmente no arquivo main do package main

---

## Para VS Code

### 1. Abrir o projeto
- Abrir a pasta raiz do projeto

---

### 2. Extensões necessárias
- Extension Pack for Java

---

### 3. Configurar JDK
- Definir Java Home para JDK 21

---

### 4. settings.json

```json
{
  "java.project.sourcePaths": ["src"],
  "java.project.outputPath": "bin",
  "java.project.referencedLibraries": [
    "lib/**/*.jar"
  ]
}
````
### 5. launch.json (JavaFX obrigatório)
```json
{
  "version": "0.2.0",
  "configurations": [
    {
      "type": "java",
      "name": "Run Main",
      "request": "launch",
      "mainClass": "main.Main",
      "vmArgs": "--module-path lib/javafx-sdk-21.0.10/lib --add-modules javafx.controls,javafx.fxml,javafx.graphics"
    }
  ]
}
````

---



Este guia contém apenas o necessário para executar o projeto no Eclipse e no VS Code sem Maven, utilizando bibliotecas .jar manualmente.
## 🏗️ Estrutura do Projeto
-  src/
- ├── banco/
- ├── config/
- ├── dao/
- ├── List/
- ├── main/
- ├── model/
- ├── utils/
- └── view/
- lib/ 


---
## Lib

- Contem todos os arquivos de configuracoes de ambiente de desenvolvimento do projeto, Para eclipse e para vs code, Conforme descrito  em [Configuração de Ambiente de Desenvolvimento](#Configuração-de-Ambiente-de-Desenvolvimento)

## 🔹 Model (Entidades)

Responsável por representar as tabelas do banco de dados em forma de classes Java.

- Cada atributo → coluna no banco
- Cada classe → tabela
- Cada objeto → registro

## 🔹 DAO (Data Access Object)

- Responsável por fazer a comunicação com o banco de dados.

- Funções principais:
- Inserir dados
- Buscar registros
- Atualizar informações
- Remover dados

## 🔹 View (Interface do Usuário)

- A camada View é responsável pela interação com o usuário.

- 📌 Funções:
- Receber dados do usuário
- Exibir informações
- Acionar operações do sistema


## 🔹 Utils (Utilitários)

- Contém classes auxiliares reutilizáveis no sistema.

- 📌 Exemplos:
- Manipulação de datas
- Controle de sessão
- Envio de e-mails
- Converter os dias de atendimento do medico para o formato lido no backend 
- 🧠 Importância:

- Evita repetição de código e centraliza funcionalidades comuns.

## 🔹 List (DTO)

- A camada List funciona como um DTO (Data Transfer Object).

- 📌 Função:
- Transportar dados entre camadas
- Facilitar exibição em tabelas
- Representar resultados de consultas

## 🔹 Config

- Responsável por carregar e disponibilizar as configurações da aplicação.



- A classe Config realiza a leitura desse arquivo durante a inicialização da aplicação e permite que outras partes do sistema acessem essas configurações de forma centralizada.
- AS credenciais estao num arquivo .properties, para executar a aplicacao e preciso configurar um arquivo proprieties com credenciais

## 🔹 Main (Ponto de Entrada)

- A classe principal que inicia o sistema.
- 📌 Função:
- Inicializar a aplicação
- Abrir a tela inicial

## 🗄️ Banco de Dados

- O sistema utiliza um banco relacional com as seguintes tabelas:

- medicos_dias_semana
- usuarios
- medicos
- consultas
- agendamentos

## ⚠️ Observações Importantes

### 🔐 Segurança

- As credenciais do banco de dados não estão diretamente no código-fonte, sendo armazenadas em um arquivo `.properties`.  
- Para executar a aplicação, é necessário configurar manualmente esse arquivo com os dados de conexão (URL, usuário e senha).  
- Recomenda-se não versionar esse arquivo com credenciais reais em repositórios públicos.

---

### 🧱 Arquitetura

- O sistema segue o padrão de arquitetura em camadas, separando responsabilidades entre Model, DAO, View e demais componentes.  
- Essa abordagem facilita a manutenção, organização e evolução do sistema.

---

### 🗄️ Banco de Dados

- O banco de dados é relacional e estruturado com as seguintes entidades principais:
  
  - medicos_dias_semana
  - usuários  
  - médicos  
  - consultas  
  - agendamentos  

- O sistema utiliza chaves primárias e estrangeiras para garantir integridade referencial.

---

### 🔄 Dependência do Banco

- A aplicação depende diretamente da estrutura do banco de dados.  
- Caso o banco não esteja configurado corretamente ou não contenha as tabelas necessárias, o sistema não funcionará.

---

## 🔑 Usuário Administrador

O sistema não possui um usuário administrador padrão.

Para acessar funcionalidades administrativas, é necessário inserir manualmente um usuário com o tipo `adm` no banco de dados.

### Exemplo:

```sql
INSERT INTO usuarios (matricula, senha, email, tipo, nome, cpf)
VALUES (123456, 'senha', 'admin@email.com', 'adm', 'Administrador', '00000000000');
```

- 📌 Observação:

- O campo tipo deve ser definido como 'adm'.
- Em ambiente de teste,é recomendavel que a senha deve seguir o padrão utilizado pelo sistema.

---
## melhorias solicitadas por colegas:

- [x] Renomear botao de editar como reagendar.
- [x] Adicionar visualizacao de data de agendamento na consulta.
- [x] Adicionar pesquisa por nome de usuario na aba de consultas e pesquisa de data nos agendamentos.
## Planejamentos futuros
- reuniao com a encarregada do setor de ti para apresentar o sistema

