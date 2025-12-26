# 🛒 API Rest E-commerce

Este é um projeto de Back-end desenvolvido em **Java** com **Spring Boot**, focado na construção de uma API RESTful segura e escalável para plataformas de comércio eletrônico.

O objetivo principal deste projeto foi implementar regras de negócios complexas, garantindo a integridade dos dados (controle de estoque e transações) e a segurança dos usuários (autenticação e autorização).

## 🚀 Tecnologias Utilizadas

* **Java 17**
* **Spring Boot 3**
* **Spring Security** (Autenticação Stateless com JWT)
* **Spring Data JPA** (Persistência de dados)
* **Bean Validation** (Validação de DTOs)
* **Banco de Dados:** H2 (Ambiente de Dev) / MySQL ou Postgres (Produção)
* **Maven** (Gerenciamento de dependências)

## ✨ Funcionalidades Principais

* **Gestão de Identidade:** Cadastro e Login de usuários com criptografia de senha.
* **Catálogo de Produtos:** Consulta e controle de estoque em tempo real.
* **Carrinho de Compras:** Criação de pedidos em estado de rascunho (`AGUARDANDO_PAGAMENTO`).
* **Checkout Seguro:** Finalização de compra com baixa automática de estoque.
* **Histórico de Pedidos:** Consulta otimizada dos pedidos do usuário logado.

## 🧠 Destaques Técnicos (Arquitetura & Decisões)

### 1. Integridade de Dados e Transações (`@Transactional`)
Uma das maiores preocupações em e-commerces é a consistência do estoque.
* Utilizei a anotação `@Transactional` no método de finalização de compra.
* **Cenário:** Se um usuário tenta comprar 3 itens, mas o terceiro falha por falta de estoque, o sistema realiza um **Rollback** automático, cancelando a baixa de estoque dos dois primeiros itens. Isso garante atomicidade: ou a compra acontece por completo, ou nada muda.

### 2. Segurança Contra IDOR (Insecure Direct Object References)
Para evitar que usuários mal-intencionados manipulem pedidos de terceiros trocando o ID na URL:
* Implementei uma verificação lógica que compara o `email` extraído do Token JWT com o dono do pedido no banco de dados.
* Se não forem idênticos, a transação é bloqueada antes mesmo de processar dados sensíveis.

### 3. Padrão DTO (Data Transfer Object)
* Utilização de DTOs para entrada e saída de dados, evitando a exposição direta das entidades do banco (JPA Entities) e prevenindo problemas de serialização cíclica (StackOverflowError) em relacionamentos bidirecionais.

---

## 🔌 Documentação da API

### Autenticação

| Método | Rota | Descrição |
| :--- | :--- | :--- |
| `POST` | `/auth/register` | Cria uma nova conta de cliente. |
| `POST` | `/auth/login` | Autentica o usuário e retorna o **Bearer Token**. |

### Produtos

| Método | Rota | Descrição |
| :--- | :--- | :--- |
| `GET` | `/produto` | Lista todos os produtos disponíveis. |
| `POST` | `/produto` | Cadastra um novo produto (Admin). |

### Pedidos (Fluxo de Compra)

| Método | Rota | Descrição |
| :--- | :--- | :--- |
| `POST` | `/pedido/add` | **Carrinho:** Cria um pedido com status `AGUARDANDO`. Não altera estoque. |
| `POST` | `/pedido/{id}/finalizar` | **Checkout:** Valida estoque, desconta quantidades e muda status para `FINALIZADO`. |
| `GET` | `/pedido/meus-pedidos` | **Histórico:** Lista compras do usuário logado (Ordenado por data). |

## 🛠️ Como Rodar o Projeto

### Pré-requisitos
* Java 17 instalado.
* Maven instalado.

### Passos
1.  Clone o repositório:
    ```bash
    git clone [https://github.com/seu-usuario/nome-do-repo.git](https://github.com/seu-usuario/nome-do-repo.git)
    ```
2.  Entre na pasta:
    ```bash
    cd nome-do-repo
    ```
3.  Execute a aplicação via Maven:
    ```bash
    mvn spring-boot:run
    ```
4.  A API estará disponível em: `http://localhost:8080`

---

## 👨‍💻 Autor

Desenvolvido por **Ian Thiago**.
* Focado em desenvolvimento Backend com Java e Spring Boot.
* Interesse em arquitetura de software e segurança.
