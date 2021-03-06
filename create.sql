create table aluno (id_aluno int8 generated by default as identity, cpf varchar(255), data_nascimento timestamp, email_responsavel varchar(255), matricula varchar(255), mensalidade numeric(19, 2), nome varchar(255), nome_responsavel varchar(255), sobrenome varchar(255), id_usuario int8, primary key (id_aluno));
create table aluno_disciplina (id_aluno int8 not null, id_disciplina int8 not null, a1 float8 not null check (a1<=10 AND a1>=0), a2 float8 not null check (a2<=10 AND a2>=0), faltas int4 not null check (faltas<=10), primary key (id_aluno, id_disciplina));
create table disciplina (id_disciplina int8 generated by default as identity, codigo varchar(255), nome varchar(255), primary key (id_disciplina));
create table professor (id_professor int8 generated by default as identity, cpf varchar(255), data_nascimento date, matricula varchar(255), nome varchar(255), salario numeric(19, 2), sobrenome varchar(255), id_usuario int8, primary key (id_professor));
create table professores_disciplinas (id_professor int8 not null, id_disciplina int8 not null);
create table role (id_role int8 generated by default as identity, nome varchar(255), primary key (id_role));
create table secretaria (id_secretaria int8 generated by default as identity, cpf varchar(255) not null, nome varchar(255) not null, sobrenome varchar(255) not null, id_usuario int8, primary key (id_secretaria));
create table usuario (id_usuario int8 generated by default as identity, ativo boolean, email varchar(255), imagem bytea, login varchar(255), nome varchar(255), senha varchar(255), primary key (id_usuario));
create table usuario_roles (id_usuario int8 not null, id_role int8 not null);
create table validation_token (id int8 generated by default as identity, expiry_date timestamp not null, token varchar(255) not null, id_usuario int8 not null, primary key (id));
alter table if exists aluno add constraint UK_m0orp81uob48mwwen1b8kxac0 unique (id_usuario);
alter table if exists professor add constraint UK_2q62v1s64oamaje5gg5aw7d74 unique (id_usuario);
alter table if exists professores_disciplinas add constraint UKl3v2trkkvp3m5q30irm9l22l3 unique (id_professor, id_disciplina);
alter table if exists role add constraint UK_psbnsrja0jvuncak7b0sqo2fi unique (nome);
alter table if exists secretaria add constraint UK_pjqcupo3u6uwh19bhlbfw8hkt unique (cpf);
alter table if exists secretaria add constraint UK_l925v22iv8oeubx0j1xqoss65 unique (id_usuario);
alter table if exists validation_token add constraint UK_2mci4xi57qdl2wxr0vg4m2xfx unique (token);
alter table if exists aluno add constraint FK52hmywkcaj1tbxgiofx92clf2 foreign key (id_usuario) references usuario;
alter table if exists aluno_disciplina add constraint FK1k24treo6t0cpruvx66jn25b1 foreign key (id_aluno) references aluno;
alter table if exists aluno_disciplina add constraint FK63wa144etj0sdx5pa9k3dafu4 foreign key (id_disciplina) references disciplina;
alter table if exists professor add constraint FKb5tn8eq563vbgmih943d22d6s foreign key (id_usuario) references usuario;
alter table if exists professores_disciplinas add constraint FKey16vsl0ru7kui21kicmya8td foreign key (id_disciplina) references disciplina;
alter table if exists professores_disciplinas add constraint FKh1bk8kllr3m3b2ab71au73iik foreign key (id_professor) references professor;
alter table if exists secretaria add constraint FKpk3e7yahwqjlytp9w1evenc6p foreign key (id_usuario) references usuario;
alter table if exists usuario_roles add constraint FK478qg359ptrhmrpw0ijep6qh foreign key (id_role) references role;
alter table if exists usuario_roles add constraint FKo29oupngknrqv049pvyvqb4kp foreign key (id_usuario) references usuario;
alter table if exists validation_token add constraint FK767yolrmk8xi350qlbiibntmt foreign key (id_usuario) references usuario;
