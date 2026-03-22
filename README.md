# 🌦️ Clima Now - Previsão do Tempo Reativa

O **Clima Now** é um aplicativo Android moderno de previsão do tempo que utiliza a API da **OpenWeather** para fornecer dados meteorológicos precisos. O projeto foi construído focando em performance offline, reatividade e experiência do usuário (UX) simplificada.

---

## 🚀 Metas do Desafio Técnico Cumpridas

O aplicativo é funcional e recebeu melhorias críticas para atingir os padrões de qualidade exigidos. Abaixo estão as prioridades resolvidas:

* **🔑 Segurança:** Chave de API movida para o arquivo `local.properties` (`WEATHER_API_KEY`), protegendo dados sensíveis.
* **📊 Dados de Pressão:** Obtenção e exibição dos dados de pressão atmosférica vindos da API.
* **🔄 Refresh:** Implementação da atualização de dados em tempo real ao clicar no botão **Atualizar**.
* **🎨 UI Fix:** Correção de ícones de clima que estavam quebrados ou não mapeados.
* **🧹 Data Clean:** Eliminação de problemas de dados duplicados no banco de dados local.

### ✅ Essenciais & Funcionalidades
* **Listagem de Cidades:** Implementada com **Paging 3** para carregamento infinito de uma base local de cidades brasileiras.
* **Busca de Cidades:** Filtro em tempo real por nome, estado e região geográfica.
* **Detalhamento de Clima:** Tela dedicada exibindo temperatura, sensação térmica, umidade e pressão atmosférica.
* **Favoritos:** Sistema de persistência para salvar e remover cidades favoritas com estado compartilhado entre telas.
* **Sincronização Geográfica (GPS):** Integração com coordenadas para buscar o clima local automaticamente na tela Home.

---

## 🛠️ Diferenciais Técnicos (Qualidade de Código)

* **Arquitetura Clean & MVVM:** Separação rigorosa entre as camadas de *Presentation*, *Domain* e *Data*.
* **Injeção de Dependência:** Uso do **Koin** para gestão de instâncias de ViewModels, Repositories e Services.
* **Offline-First (Room):** O app funciona sem internet exibindo os últimos dados cacheados.
* **Consistência de Dados (Unique Constraint):** Implementação de chaves primárias e `OnConflictStrategy.REPLACE` no Room para eliminar dados duplicados.
* **Mapeamento de Ícones:** Sistema inteligente em `Constants` para traduzir códigos da API em recursos locais.
* **UX Dinâmica:** Fundo do aplicativo altera o gradiente automaticamente com base no horário e na temperatura da cidade.

---

## 🏗️ Arquitetura do Projeto

O projeto utiliza uma estrutura modular por camadas para garantir escalabilidade:

1.  **Presentation:** UI construída 100% em **Jetpack Compose**, organizada por *features* (Home, Search, Details).
2.  **Domain:** Contém as interfaces dos repositórios e a lógica de negócio pura.
3.  **Data:** Gerencia as fontes de dados (Retrofit para API e Room para persistência local).
4.  **Core:** Utilidades transversais como Injeção de Dependência e Temas.

---

## 🧪 Tecnologias Utilizadas

* **Jetpack Compose:** UI declarativa e moderna.
* **Koin:** Injeção de dependência leve e rápida.
* **Retrofit + OkHttp:** Comunicação com a API REST.
* **Room Database:** Persistência de dados local (SQLite).
* **Kotlin Coroutines & Flow:** Processamento assíncrono e fluxos de dados reativos.
* **Paging 3:** Paginação eficiente da lista de cidades.
* **DataStore:** Armazenamento de preferências simples do usuário.

---

## 📸 Demonstração das Funcionalidades

| Home (GPS) | Busca & Filtros | Detalhes |
| :--- | :--- | :--- |
| Clima local automático com cores dinâmicas. | Filtro por Região/Estado e busca por nome. | Dados detalhados de pressão e umidade. |

---

## ⚙️ Como rodar o projeto

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/seu-usuario/aplicativo-previsao-tempo.git](https://github.com/seu-usuario/aplicativo-previsao-tempo.git)
    ```
2.  **Chave da API:** Obtenha uma chave gratuita em [OpenWeatherMap](https://openweathermap.org/api).
3.  **Configuração:** No seu arquivo `local.properties`, adicione:
    ```properties
    WEATHER_API_KEY=SUA_CHAVE_AQUI
    ```
4.  **Execução:** Abra no Android Studio (Giraffe ou superior) e execute no emulador ou dispositivo físico.

---

## 👨‍💻 Desenvolvedor

* **LinkedIn:** [Marcos Terto](https://www.linkedin.com/in/marcosterto-dev240417/)

---

### 📝 Nota de Encerramento
Este projeto foi desenvolvido como parte de um desafio técnico, priorizando não apenas a entrega das funcionalidades, mas a aplicação de **boas práticas de engenharia de software**, resultando em um código limpo, testável e de fácil manutenção.
