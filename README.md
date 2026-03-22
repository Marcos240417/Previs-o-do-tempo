Clima Now - Previsão do Tempo Reativa
O Clima Now é um aplicativo Android moderno de previsão do tempo que utiliza a API da OpenWeather para fornecer dados meteorológicos precisos. O projeto foi construído focando em performance offline, reatividade e experiência do usuário (UX) simplificada.

Metas do Desafio Técnico Cumpridas
Abaixo estão listados os requisitos técnicos solicitados e como foram implementados:
O aplicativo é funcional, mas ainda há melhorias e correções de bugs a serem feitas. Veja o que está em nossa lista de prioridades:
•	Adicione sua chave de API ao local.propertiesarquivo: WEATHER_API_KEY= <your-api-key-here>
•	Obtenha e exiba dados de pressão da API
•	Implemente a atualização de dados ao clicar no botão Atualizar.
•	Corrigir ícones de clima quebrados
•	Elimine problemas de dados duplicados

Essenciais & Funcionalidades
•	Listagem de Cidades: Implementada com Paging 3 para carregamento infinito de uma base local de cidades brasileiras.
•	Busca de Cidades: Filtro em tempo real por nome, estado e região geográfica.
•	Detalhamento de Clima: Tela dedicada exibindo temperatura, sensação térmica, umidade e pressão atmosférica.
•	Favoritos: Sistema de persistência para salvar e remover cidades favoritas com estado compartilhado entre telas.
•	Sincronização Geográfica (GPS): Integração com coordenadas para buscar o clima local automaticamente na tela Home.
Diferenciais Técnicos (Qualidade de Código)
•	Arquitetura Clean & MVVM: Separação rigorosa entre as camadas de Presentation, Domain e Data.
•	Injeção de Dependência: Uso do Koin para gestão de instâncias de ViewModels, Repositories e Services.
•	Offline-First (Room): O app funciona sem internet exibindo os últimos dados cacheados.
•	Consistência de Dados (Unique Constraint): Implementação de chaves primárias e OnConflictStrategy.REPLACE no Room para eliminar dados duplicados.
•	Mapeamento de Ícones: Sistema inteligente em Constants para traduzir códigos da API em recursos locais, corrigindo ícones quebrados.
•	UX Dinâmica: Fundo do aplicativo altera o gradiente automaticamente com base no horário e na temperatura da cidade visualizada.

Arquitetura do Projeto
O projeto utiliza uma estrutura modular por camadas para garantir escalabilidade:
•	Presentation: UI construída 100% em Jetpack Compose, organizada por features (Home, Search, Details).
•	Domain: Contém as interfaces dos repositórios e a lógica de negócio pura.
•	Data: Gerencia as fontes de dados (Retrofit para API e Room para persistência local) e implementa os Repositories.
•	Core: Utilidades transversais como Injeção de Dependência e Temas.
________________________________________
Tecnologias Utilizadas
•	Jetpack Compose: UI declarativa e moderna.
•	Koin: Injeção de dependência leve e rápida.
•	Retrofit + OkHttp: Comunicação com a API REST.
•	Room Database: Persistência de dados local (SQLite).
•	Kotlin Coroutines & Flow: Processamento assíncrono e fluxos de dados reativos.
•	Paging 3: Paginação eficiente da lista de cidades.
•	DataStore: Armazenamento de preferências simples do usuário.
________________________________________
Demonstração das Funcionalidades
Home (GPS)	Busca & Filtros	Detalhes
Clima local automático com cores dinâmicas.	Filtro por Região/Estado e busca por nome.	Dados detalhados de pressão e umidade.
________________________________________
Como rodar o projeto
1.	Clone o repositório:
Bash
2.	git clone https://github.com/seu-usuario/aplicativo-previsao-tempo.git
3.	Obtenha uma chave de API gratuita em OpenWeatherMap.
4.	No arquivo Constants.kt, insira sua chave:
Kotlin
const val API_KEY = "SUA_CHAVE_AQUI"
5.	Execute o projeto no Android Studio (Giraffe ou superior).
________________________________________
Desenvolvedor
•	LinkedIn - https://www.linkedin.com/in/marcosterto-dev240417/
________________________________________
Nota de Encerramento
Este projeto foi desenvolvido como parte de um desafio técnico, priorizando não apenas a entrega das funcionalidades, mas a aplicação de boas práticas de engenharia de software, resultando em um código limpo, testável e de fácil manutenção.

