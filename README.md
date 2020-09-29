
# AcademicWeb
Projeto de aplicação web Spring Boot em desenvolvimento pessoal para TCC.

***Ferramentas utilizadas:***
- Spring (Boot/MVC/Data/Security), Thymeleaf, Freemarker, JPA, Hibernate, Maven ,Bootstrap, JQuery, MySQL.

***Objetivo:***
- Sistema Acadêmico web à nível de ensino fundamental.

## ENDPOINTS:

**REFERENTES AO ALUNO:**

/aluno/disciplinas                                        - *EXIBIÇÃO DE DISCIPLINAS DO ALUNO*

/aluno/perfil                                             - *INFORMAÇÕES DO PERFIL DO ALUNO*

/aluno/perfil/alterar                                     - *REQUISIÇÃO ALTERAR PERFIL*


**REFERENTES AO PROFESSOR:**

/professor/dashboard                                      - *DASHBOARD PROFESSOR*

/professor/perfil	                                        - *INFORMAÇÕES DO PERFIL DO PROFESSOR*

/professor/perfil/alterar		                              - *REQUISIÇÃO ALTERAR PERFIL*

/professor/aritmetica/alunos                              - *EXIBIR ALUNOS DA DISCIPLINA*

/professor/aritmetica/notas/{id_aluno}                    - *EXIBIR NOTAS DO ALUNO NA DISCIPLINA*

/professor/aritmetica/notas/{id_aluno}/alterar            - *REQUISIÇÃO ALTERAR NOTAS E FALTAS DO ALUNO*


**REFERENTES A SECRETARIA:**

/secretaria/dashboard                                     - *DASHBOARD ALUNO*

/secretaria/alunos                                  	    - *EXIBIR TODOS OS ALUNOS CADASTRADOS*

/secretaria/alunos/ativar                                 - *ATIVAR USUÁRIO DO ALUNO*

/secretaria/alunos/desativar                              - *DESATIVAR USUÁRIO DO ALUNO*

/secretaria/professores                           	      - *EXIBIR TODOS OS PROFESSORES CADASTRADOS*

/secretaria/professores/ativar                            - *ATIVAR USUÁRIO DO PROFESSOR*

/secretaria/professores/desativar                         - *DESATIVAR USUÁRIO DO PROFESSOR*

/secretaria/disciplinas                            	      - *EXIBIR TODAS AS DISCIPLINAS CADASTRADAS*

/secretaria/professor/disciplinas/{id_professor}   	      - *FORM DE ASSOCIAÇÃO DE DISCIPLINAS*

/secretaria/professor/disciplinas/{id_professor}/incluir  - *REQUISIÇÃO DE ASSOCIAÇÃO DE DISCIPLINA*

/secretaria/cadastrar/professor                           - *CADASTRO DE PROFESSOR*

/secretaria/cadastrar/aluno                               - *CADASTRO DE ALUNO*


**REFERENTES AS IMAGENS:**

/imagem/aluno/{id}					                              - *EXIBIR IMAGEM DA FOTO DE PERFIL DO ALUNO*

/imagem/professor/{id}					                          - *EXIBIR IMAGEM DA FOTO DE PERFIL DO PROFESSOR*
